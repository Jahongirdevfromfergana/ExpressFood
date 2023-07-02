package uz.fergana.fastexpress.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import uz.fergana.fastexpress.models.CategoryModel
import uz.fergana.fastexpress.R
import uz.fergana.fastexpress.databinding.CategoryTabItemLayoutBinding


interface CategoryTabAdapterCallback {
    fun onClick(item: CategoryModel)
}

class CategoryTabAdapter(
    val categoryList: List<CategoryModel>,
    val callback: CategoryTabAdapterCallback
) :
    RecyclerView.Adapter<CategoryTabAdapter.Vh>() {

    inner class Vh(val categoryTabItemLayoutBinding: CategoryTabItemLayoutBinding) :
        RecyclerView.ViewHolder(categoryTabItemLayoutBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            CategoryTabItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val categoryModel = categoryList[position]

        holder.itemView.setOnClickListener {
            categoryList.forEach {
                it.active = false
            }
            categoryModel.active = true
            notifyDataSetChanged()
            callback.onClick(categoryModel)
        }

        holder.categoryTabItemLayoutBinding.tvName.setTextColor(
            if (categoryModel.active) {
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.color_primary
                )
            } else {
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.grey_color
                )
            }
        )
    }
}