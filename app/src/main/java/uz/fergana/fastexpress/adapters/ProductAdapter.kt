package uz.fergana.fastexpress.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.fergana.fastexpress.models.ProductModel
import uz.fergana.fastexpress.utils.PrefUtils
import uz.fergana.fastexpress.utils.loadImage
import uz.fergana.fastexpress.databinding.ProductItemLayoutBinding

class ProductAdapter(val productList: List<ProductModel>) :
    RecyclerView.Adapter<ProductAdapter.Vh>() {

    inner class Vh(val productItemLayoutBinding: ProductItemLayoutBinding) :
        RecyclerView.ViewHolder(productItemLayoutBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ProductItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val productModel = productList[position]
        holder.productItemLayoutBinding.imgAdd.setOnClickListener {
            PrefUtils.add2Cart(productModel.id, ++productModel.cartCount)
            notifyItemChanged(position)
        }
        holder.productItemLayoutBinding.imgMinus.setOnClickListener {
           if (productModel.cartCount>0){
               PrefUtils.add2Cart(productModel.id, --productModel.cartCount)
               notifyItemChanged(position)
           }
        }
        holder.productItemLayoutBinding.image.loadImage(productModel.image)
        holder.productItemLayoutBinding.tvName.text = productModel.name
        holder.productItemLayoutBinding.tvCount.text = productModel.cartCount.toString()
        holder.productItemLayoutBinding.tvPrice.text = "${productModel.price} UZS"
    }
}