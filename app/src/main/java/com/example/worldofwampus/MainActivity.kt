package com.example.worldofwampus

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.worldofwampus.Room.Companion.imageRoom
import com.example.worldofwampus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel:WumpusViewModel by viewModels()

    lateinit var adapter: RoomAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = RoomAdapter()
        binding.rvWorld.adapter = adapter
        binding.rvWorld.layoutManager =
            GridLayoutManager(applicationContext, 4, GridLayoutManager.VERTICAL, false)
        adapter.list = imageRoom()
        adapter.blackBoard = viewModel.memory.blackBoard
        adapter.notifyDataSetChanged()

        setListenners()

viewModel.gameOverMLD.observe(this,::gameOver)

        viewModel.playerIndexMLD.observe(this) {
            adapter.player = it
            adapter.notifyDataSetChanged()
        }

        viewModel.bestWayFoundedMLD.observe(this) {
            viewModel.isBlockingTurn=false
            if (it.second==WumpusViewModel.Action.KillWumpus){
                viewModel.killWumpus(adapter.list,it.first)
                adapter.notifyDataSetChanged()
            } else
            if (it.first == -1) {
                gameOver(Player.GameEnd.NoTurns)
            } else {
                viewModel.goTo(adapter.list, it.first)
            }
        }
    }

    private fun setListenners() {
        binding.bRefresh.setOnClickListener {
            adapter.list = imageRoom()
            adapter.player=12
            viewModel.isGameEnded=false
            viewModel.refresh()
            adapter.blackBoard = viewModel.memory.blackBoard
            adapter.notifyDataSetChanged()
        }

        binding.bTurn.setOnClickListener {
            if (viewModel.isBlockingTurn) {
                return@setOnClickListener
            }
            if (viewModel.isGameEnded) {
                Toast.makeText(this,"Начните новую игру",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.makeTurn()
        }

        binding.cbBoardView.setOnCheckedChangeListener { buttonView, isChecked ->
            adapter.boardView=isChecked
            adapter.notifyDataSetChanged()
        }

        binding.cbTextVIew.setOnCheckedChangeListener { buttonView, isChecked ->
            adapter.textView=isChecked
            adapter.notifyDataSetChanged()
        }
    }

    private fun gameOver(gameEnd: Player.GameEnd) {
        Toast.makeText(this, gameEnd.title, Toast.LENGTH_LONG).show()
        viewModel.isGameEnded = true
    }
}