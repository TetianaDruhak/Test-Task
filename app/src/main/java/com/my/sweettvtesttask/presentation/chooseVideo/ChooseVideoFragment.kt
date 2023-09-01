package com.my.sweettvtesttask.presentation.chooseVideo

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.my.sweettvtesttask.R
import com.my.sweettvtesttask.databinding.FragmentChooseVideoBinding
import com.my.sweettvtesttask.domain.response.VideoResponse
import com.my.sweettvtesttask.presentation.HomeViewModel
import com.my.sweettvtesttask.utils.Res

const val VIDEO_DATA = "video_data"
const val SELECTED_ITEM = "selected_item"

class ChooseVideoFragment : Fragment(), VideoAdapter.Callback {

    private lateinit var binding: FragmentChooseVideoBinding
    private val args: ChooseVideoFragmentArgs by navArgs()

    private val viewModel: HomeViewModel by activityViewModels()
    private val videoAdapter = VideoAdapter(this)

    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentChooseVideoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryAlias = args.categoryAlias
        binding.tvTitle.text =
            resources.getString(R.string.choose_video_from) + args.categoryName
        viewModel.loadVideos(requireContext(), categoryAlias)
        viewModel.getVideos().observe(viewLifecycleOwner) {
            when (it) {
                is Res.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    val parcelableList = arrayListOf<VideoResponse>()
                    it.data.forEach { video ->
                        parcelableList.add(video)
                    }
                    bundle.putParcelableArrayList(VIDEO_DATA, parcelableList)
                    initRecycler(it.data)
                }

                is Res.LOADING -> {
                    binding.progressBar.isVisible = true
                }

                is Res.ERROR -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun initRecycler(data: List<VideoResponse>) {
        videoAdapter.setVideos(data)
        val column = getNeededColumnNum()
        binding.rvVideo.layoutManager = GridLayoutManager(requireContext(), column)
        binding.rvVideo.adapter = videoAdapter
    }

    private fun getNeededColumnNum(): Int {
        val displayMetrics: DisplayMetrics = requireContext().resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels
        val columnWidth = requireActivity().resources.getDimension(R.dimen.video_item_width)
        return (screenWidthDp / columnWidth).toInt()
    }

    override fun openVideo(selectedItem: Int) {
        bundle.putInt(SELECTED_ITEM, selectedItem)
        findNavController().navigate(R.id.action_chooseVideo_to_videoPlayer, bundle)
    }
}