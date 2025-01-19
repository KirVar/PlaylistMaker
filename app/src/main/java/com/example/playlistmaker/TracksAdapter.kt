package com.example.playlistmaker

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView



class TracksAdapter (
    private var track: List<Track>,
    private val onItemClickListener: (Track) -> Unit
) : RecyclerView.Adapter<TracksViewHolder> () {

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newTracks: List<Track>) {
        track = newTracks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_item, parent, false)
        return TracksViewHolder(view)
    }

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        holder.bind(track[position])
        holder.itemView.setOnClickListener {
            onItemClickListener(track[position])
        }
    }

    override fun getItemCount(): Int {
        return track.size
    }
}