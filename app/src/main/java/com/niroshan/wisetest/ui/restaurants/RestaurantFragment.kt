package com.niroshan.wisetest.ui.restaurants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.niroshan.wisetest.R
import com.niroshan.wisetest.databinding.FragmentRestaurantBinding
import com.niroshan.wisetest.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantFragment : Fragment(R.layout.fragment_restaurant) {

    private val viewModel: RestaurantViewModel by viewModels()

    private var _binding: FragmentRestaurantBinding? = null
    private val binding get() = _binding!!
    lateinit var restaurantAdapter: RestaurantAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRestaurantBinding.bind(view)

        restaurantAdapter = RestaurantAdapter()

        binding.apply {
            recyclerView.apply {
                adapter = restaurantAdapter
                layoutManager = LinearLayoutManager(context)
            }

            viewModel.restaurants.observe(viewLifecycleOwner) { result ->
                restaurantAdapter.submitList(result.data)

                progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                textViewError.text = result.error?.localizedMessage
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}