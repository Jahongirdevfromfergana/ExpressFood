package uz.fergana.fastexpress.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.fergana.fastexpress.models.OfferModel
import uz.fergana.fastexpress.utils.loadImage
import uz.fergana.fastexpress.databinding.SlideItemLayoutBinding

class SlideAdapter(val offerList: List<OfferModel>) : RecyclerView.Adapter<SlideAdapter.Vh>() {

    inner class Vh(val slideItemLayoutBinding: SlideItemLayoutBinding) :
        RecyclerView.ViewHolder(slideItemLayoutBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            SlideItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return offerList.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val slideModel = offerList[position]
        holder.slideItemLayoutBinding.tvTitle.text = slideModel.title
        holder.slideItemLayoutBinding.slideImage.loadImage(slideModel.image)
    }
}