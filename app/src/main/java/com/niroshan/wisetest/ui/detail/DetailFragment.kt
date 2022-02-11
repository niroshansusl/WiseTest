package com.niroshan.wisetest.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.niroshan.wisetest.R
import com.niroshan.wisetest.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailBinding.bind(view)

        binding.apply {

            imageViewLogo.apply {
                transitionName = args.restaurants.name

                Glide.with(requireActivity())
                    .load(args.restaurants.logo)
                    .into(imageViewLogo)
            }

            textViewName.text = args.restaurants.name
            textViewType.text = args.restaurants.type
            textViewAddress.text = args.restaurants.address
        }

        ViewCompat.setTransitionName(binding.imageViewLogo, "${args.restaurants.name}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}