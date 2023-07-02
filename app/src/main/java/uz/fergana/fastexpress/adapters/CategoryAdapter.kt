package uz.fergana.fastexpress.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.fergana.fastexpress.models.CategoryModel
import uz.fergana.fastexpress.utils.loadImage
import uz.fergana.fastexpress.databinding.CategoryItemLayoutBinding

class CategoryAdapter(val categoryList: List<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.Vh>() {

    inner class Vh(val categoryItemLayoutBinding: CategoryItemLayoutBinding) :
        RecyclerView.ViewHolder(categoryItemLayoutBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            CategoryItemLayoutBinding.inflate(
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
        holder.categoryItemLayoutBinding.tvTitle.text = categoryModel.title
        holder.categoryItemLayoutBinding.slideImage.loadImage(categoryModel.image)
    }
}