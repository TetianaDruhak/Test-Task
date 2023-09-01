package com.my.sweettvtesttask.presentation.chooseCategory

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.my.sweettvtesttask.R
import com.my.sweettvtesttask.domain.response.CategoryResponse

class CategoriesAdapter(val callback: Callback) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private var categories = mutableListOf<CategoryResponse>()

    fun setCategories(data: List<CategoryResponse>) {
        categories = data as MutableList<CategoryResponse>
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoriesAdapter.ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    inner class ViewHolder internal constructor(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        inflater.inflate(R.layout.item_select_category, parent, false)
    ) {
        private val tvCategoryName: TextView = itemView.findViewById(R.id.tvCategoryName)

        fun bind(data: CategoryResponse) {
            tvCategoryName.text = data.name
            tvCategoryName.setOnClickListener {
                callback.openCategory(data)
            }
        }
    }

    interface Callback {
        fun openCategory(category: CategoryResponse)
    }
}