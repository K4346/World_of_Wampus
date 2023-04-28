package com.example.worldofwampus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.worldofwampus.databinding.RoomItemBinding


class RoomAdapter() : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    var list = arrayListOf<Room>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = RoomItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RoomViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = list[position]
        holder.room.text = ""
        holder.room.text=position.toString()+"\n"
        if (room.wumpus) holder.room.text = "${holder.room.text}Wum"
        if (room.gold) holder.room.text = "${holder.room.text}G"
        if (room.hole) holder.room.text = "${holder.room.text}Ho"
        if (room.smell) holder.room.text = "${holder.room.text}Sm"
        if (room.shine) holder.room.text = "${holder.room.text}Sh"
        if (room.player) holder.room.text = "${holder.room.text}P"
        if (room.wind) holder.room.text = "${holder.room.text}Wi"
    }

    inner class RoomViewHolder(itemView: RoomItemBinding) : ViewHolder(itemView.root) {
        val room = itemView.tvRoom
    }

}

