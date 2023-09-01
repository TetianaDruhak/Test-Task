package com.my.sweettvtesttask.presentation.videoPlayer

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.my.sweettvtesttask.R
import com.my.sweettvtesttask.databinding.ActivityVideoPlayerBinding
import com.my.sweettvtesttask.domain.response.VideoResponse
import com.my.sweettvtesttask.domain.response.VideoType
import com.my.sweettvtesttask.presentation.chooseVideo.SELECTED_ITEM
import com.my.sweettvtesttask.presentation.chooseVideo.VIDEO_DATA


@UnstableApi
class VideoPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoPlayerBinding
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPlayer(
            getVideoResource(),
            getSelectedVideoPosition()
        )
    }

    private fun getVideoResource(): List<VideoResponse>? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getParcelableArrayList(VIDEO_DATA, VideoResponse::class.java)
        } else {
            intent.extras?.getParcelableArrayList(VIDEO_DATA)
        }
    }

    private fun getSelectedVideoPosition(): Int? {
        return intent.extras?.getInt(SELECTED_ITEM)
    }

    private fun showError() {
        Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
    }

    private fun setUpPlayer(videos: List<VideoResponse>?, selectedPosition: Int?) {
        if (videos == null) {
            showError()
            return
        }
        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player
        player.setMediaSources(
            createMediaSourcesList(videos),
            selectedPosition ?: 0,
            0L)
        player.prepare()
        player.play()
    }

    private fun createMediaSourcesList(videos: List<VideoResponse>): List<MediaSource> {
        val mediaSources = mutableListOf<MediaSource>()
        videos.forEach { video ->
            val mimeType = getMimeType(video.videoUrl)
            val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
            val mediaItem =
                MediaItem.Builder()
                    .setUri(video.videoUrl)
                    .setDrmConfiguration(
                        MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
                            .setLicenseUri(video.drmLicenseUrl)
                            .setMultiSession(true)
                            .build()
                    )
                    .setMimeType(mimeType)
                    .build()
            val mediaSource: MediaSource =
                when (video.type) {
                    VideoType.DASH -> DashMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(mediaItem)

                    VideoType.HLS -> HlsMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(mediaItem)

                    else -> ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(mediaItem)
                }
            mediaSources.add(mediaSource)
        }
        return mediaSources
    }

    private fun getMimeType(url: String?): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    override fun onStop() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        player.stop()
        player.clearMediaItems()
        super.onStop()
    }

}