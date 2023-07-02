package uz.fergana.fastexpress.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.fergana.fastexpress.models.RestaurantModel
import uz.fergana.fastexpress.screen.restaurant.detail.RestaurantDetailActivity
import uz.fergana.fastexpress.utils.Constants.EXTRA_DATA
import uz.fergana.fastexpress.utils.loadImage
import uz.fergana.fastexpress.databinding.RestaurantItemLayoutBinding

class RestaurantAdapter(val restaurantList: List<RestaurantModel>) :
    RecyclerView.Adapter<RestaurantAdapter.Vh>() {

    inner class Vh(val restaurantItemLayoutBinding: RestaurantItemLayoutBinding) :
        RecyclerView.ViewHolder(restaurantItemLayoutBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            RestaurantItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val restaurantModel = restaurantList[position]
        holder.restaurantItemLayoutBinding.tvName.text = restaurantModel.name
        holder.restaurantItemLayoutBinding.tvAddress.text = restaurantModel.address
        holder.restaurantItemLayoutBinding.tvDistance.text = "${String.format("%.1f", restaurantModel.distance)} km"
        holder.restaurantItemLayoutBinding.image.loadImage(restaurantModel.main_image)
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, RestaurantDetailActivity::class.java)
            intent.putExtra(EXTRA_DATA, restaurantModel)
            it.context.startActivity(intent)
        }
    }
}