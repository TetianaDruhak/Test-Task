package com.my.sweettvtesttask.presentation.chooseVideo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.my.sweettvtesttask.R
import com.my.sweettvtesttask.domain.response.VideoResponse

class VideoAdapter(val callback: Callback) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    private var videos = mutableListOf<VideoResponse>()

    fun setVideos(data: List<VideoResponse>) {
        videos = data as MutableList<VideoResponse>
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VideoAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int = videos.size

    override fun onBindViewHolder(holder: VideoAdapter.ViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    inner class ViewHolder internal constructor(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        inflater.inflate(R.layout.item_select_video, parent, false)
    ) {
        private val ivPoster: ImageView = itemView.findViewById(R.id.ivPoster)
        private val tvVideoTitle: TextView = itemView.findViewById(R.id.tvVideoTitle)

        fun bind(data: VideoResponse) {
            Glide.with(itemView.context)
                .load(data.posterUrl)
                .error(R.drawable.ic_banner_placeholder)
                .centerCrop()
                .into(ivPoster)
            tvVideoTitle.text = data.title
            itemView.setOnClickListener {
                callback.openVideo(absoluteAdapterPosition)
            }
        }
    }

    interface Callback {
        fun openVideo(selectedItem: Int)
    }
}