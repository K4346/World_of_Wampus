package com.example.worldofwampus

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.worldofwampus.databinding.RoomItemBinding


class RoomAdapter() : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    var list = arrayListOf<Room>()
    var player: Int = 12

    var boardView = true
    var textView: Boolean=false

    var blackBoard = arrayOfNulls<Memory.BloackBoard>(16)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = RoomItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RoomViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = list[position]

        setRoomView(holder.binding, room, position)

        blackBoard.forEachIndexed { index, it ->
            Log.i("kpopp","$index ${it?.beenHere}")
        }
//        setImage(holder.binding.root.resources, room, position == player, holder.img)


    }

    private fun setRoomView(binding: RoomItemBinding, room: Room, position:Int) {
        if (textView){
            setTextRoom(binding,room,position)
        } else{
            setImageRoom(binding,room,position)
        }
    }

    private fun setImageRoom(binding: RoomItemBinding, room: Room, position: Int) {
        val imageView = binding.imageRoom
        val resources = binding.root.resources

        binding.imageRoom.isVisible=true
        binding.cardView.isVisible = false

        if (!boardView && blackBoard[position]?.beenHere != true){
            imageView.setImageDrawable(resources.getDrawable(R.drawable.kavo))
            return
        }

        val isPlayer = position==player
        val idImg = with(room) {
            //wumpus && gold && hole && wind && isPlayer
            if (isPlayer && wind && smell && gold) R.drawable.player_wind_smell_gold
            else if (wumpus && hole && gold && wind) R.drawable.wumpus_hole_gold_wind
            else if (wumpus && hole && shine && wind) R.drawable.shine_wampus_wind_hole
            else if (wumpus && hole && wind) R.drawable.wumpus_hole_wind
            else if (wumpus && gold && hole) R.drawable.wumpus_gold_hole
            else if (wumpus && gold && wind) R.drawable.wumpus_gold_wind
            else if (wumpus && wind && shine) R.drawable.shine_wumpus_wind
            else if (wumpus && wind) R.drawable.wumpus_wind
            else if (wumpus && gold) R.drawable.wumpus_gold
            else if (wumpus && hole && shine) R.drawable.shine_wumpus_hole
            else if (wumpus && hole) R.drawable.wumpus_hole
            else if (wumpus && shine) R.drawable.shine_wumpus
            else if (wumpus) R.drawable.wumpus
            else if (gold && smell && wind && hole) R.drawable.gold_smell_wind_hole
            else if (gold && wind && hole) R.drawable.gold_wind_hole
            else if (gold && hole && smell) R.drawable.gold_hole_smell
            else if (wind && hole && smell && shine) R.drawable.shine_smell_hole_wind
            else if (wind && hole && smell) R.drawable.wind_hole_smell
            else if (gold && hole) R.drawable.gold_hole
            else if (hole && smell && shine) R.drawable.shine_smell_hole
            else if (hole && smell) R.drawable.hole_smell
            else if (wind && hole && shine) R.drawable.shine_wind_hole
            else if (wind && hole) R.drawable.wind_hole
            else if (hole && shine) R.drawable.shine_hole
            else if (hole) R.drawable.hole
            else if (isPlayer && wind && smell && shine) R.drawable.shine_player_smell_wind
            else if (isPlayer && wind && smell) R.drawable.player_wind_smell
            else if (isPlayer && wind && gold) R.drawable.player_wind_gold
            else if (isPlayer && wind && shine) R.drawable.shine_wind_player
            else if (isPlayer && wind) R.drawable.player_wind
            else if (isPlayer && smell && gold) R.drawable.player_smell_gold
            else if (isPlayer && smell && shine) R.drawable.shine_smell_player
            else if (isPlayer && smell) R.drawable.player_smell
            else if (isPlayer && shine) R.drawable.player_shine
            else if (isPlayer) R.drawable.player
            else if (gold && smell && wind) R.drawable.gold_smell_wind
            else if (wind && smell && gold) R.drawable.wind_smell_gold
            else if (wind && smell && shine) R.drawable.shine_smell_wind
            else if (wind && smell) R.drawable.wind_smell
            else if (gold && wind) R.drawable.gold_wind
            else if (gold && smell) R.drawable.gold_smell
            else if (gold) R.drawable.gold
            else if (wind && shine) R.drawable.shine_wind
            else if (wind) R.drawable.wind
            else if (smell && shine) R.drawable.shine_smell
            else if (smell) R.drawable.smell
            else if (shine) R.drawable.shine
            else R.drawable.empty
        }
        val image = resources.getDrawable(idImg)
        imageView.setImageDrawable(image)
    }

    private fun setTextRoom(binding: RoomItemBinding,room: Room, position: Int) {

        val roomView = binding.tvRoom

        binding.imageRoom.isVisible=false
        binding.cardView.isVisible = true

        roomView.text = ""

        if (!boardView && blackBoard[position]?.beenHere != true){
            roomView.text="?"
            return
        }

        roomView.text=position.toString()+"\n"
        if (room.wumpus) roomView.text = "${roomView.text}Wum"
        if (room.gold) roomView.text = "${roomView.text}G"
        if (room.hole) roomView.text = "${roomView.text}Ho"
        if (room.smell) roomView.text = "${roomView.text}Sm"
        if (room.shine) roomView.text = "${roomView.text}Sh"
        if (position==player) roomView.text = "${roomView.text}P"
        if (room.wind) roomView.text = "${roomView.text}Wi"
    }

    inner class RoomViewHolder(val binding: RoomItemBinding) : ViewHolder(binding.root) {
    }

}

