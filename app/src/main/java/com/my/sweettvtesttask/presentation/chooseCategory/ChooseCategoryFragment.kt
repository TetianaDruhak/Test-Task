package com.my.sweettvtesttask.presentation.chooseCategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.my.sweettvtesttask.databinding.FragmentChooseCategoryBinding
import com.my.sweettvtesttask.domain.response.CategoryResponse
import com.my.sweettvtesttask.presentation.HomeViewModel
import com.my.sweettvtesttask.presentation.chooseVideo.VIDEO_DATA
import com.my.sweettvtesttask.utils.Res

class ChooseCategoryFragment : Fragment(), CategoriesAdapter.Callback {

    private lateinit var binding: FragmentChooseCategoryBinding

    private val viewModel: HomeViewModel by activityViewModels()
    private val categoriesAdapter = CategoriesAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentChooseCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadCategories(requireContext())
        viewModel.getCategories().observe(viewLifecycleOwner) {
            when (it) {
                is Res.SUCCESS -> {
                    binding.progressBar.isVisible = false
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

    private fun initRecycler(data: List<CategoryResponse>) {
        binding.rvCategories.adapter = categoriesAdapter
        categoriesAdapter.setCategories(data)
        val divider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.rvCategories.addItemDecoration(divider)
    }

    override fun openCategory(category: CategoryResponse) {
        findNavController().navigate(
            ChooseCategoryFragmentDirections.actionChooseCategoryToChooseVideo(
                category.alias, category.name
            )
        )
    }
}