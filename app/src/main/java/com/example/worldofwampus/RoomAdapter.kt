package com.example.worldofwampus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.worldofwampus.databinding.RoomItemBinding


class RoomAdapter() : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    var list = arrayListOf<Room>()
    var player: Int =12

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
        if (room.wumpus == true) holder.room.text = "${holder.room.text}Wum"
        if (room.gold == true) holder.room.text = "${holder.room.text}G"
        if (room.hole == true) holder.room.text = "${holder.room.text}Ho"
        if (room.smell == true) holder.room.text = "${holder.room.text}Sm"
        if (room.shine == true) holder.room.text = "${holder.room.text}Sh"
        if (position==player) holder.room.text = "${holder.room.text}P"
        if (room.wind == true) holder.room.text = "${holder.room.text}Wi"
    }

    inner class RoomViewHolder(itemView: RoomItemBinding) : ViewHolder(itemView.root) {
        val room = itemView.tvRoom
    }

}

