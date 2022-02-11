package com.niroshan.wisetest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.niroshan.wisetest.databinding.ActivityRestaurantBinding
import com.niroshan.wisetest.ui.restaurants.RestaurantAdapter
import com.niroshan.wisetest.ui.restaurants.RestaurantViewModel
import com.niroshan.wisetest.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}