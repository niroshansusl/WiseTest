package com.niroshan.wisetest.ui.restaurants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.niroshan.wisetest.data.Restaurant
import com.niroshan.wisetest.databinding.RestaurantItemBinding

class RestaurantAdapter :
    ListAdapter<Restaurant, RestaurantAdapter.RestaurantViewHolder>(RestaurantComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding =
            RestaurantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        getItem(position).let { restaurant ->
            with(holder as? RestaurantViewHolder) {
                this!!.itemView.tag = restaurant
                if (restaurant != null) {
                    bind(restaurant, createOnClickListener(binding, restaurant))
                }
            }
        }
    }

    class RestaurantViewHolder(val binding: RestaurantItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant: Restaurant, listener: View.OnClickListener,) {
            binding.apply {
                Glide.with(itemView)
                    .load(restaurant.logo)
                    .into(imageViewLogo)

                textViewName.text = restaurant.name
                textViewType.text = restaurant.type
                textViewAddress.text = restaurant.address

                ViewCompat.setTransitionName(this.imageViewLogo, "${restaurant.name}")

                root.setOnClickListener(listener)
            }
        }
    }

    private fun createOnClickListener(binding : RestaurantItemBinding, restaurant: Restaurant): View.OnClickListener {
        return View.OnClickListener {
            val directions = RestaurantFragmentDirections.actionRestaurantToDetail(restaurants = restaurant)
            val extras = FragmentNavigatorExtras(
                binding.imageViewLogo to "${restaurant.name}")
            it.findNavController().navigate(directions, extras)
        }
    }

    class RestaurantComparator : DiffUtil.ItemCallback<Restaurant>() {
        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant) =
            oldItem == newItem
    }
}