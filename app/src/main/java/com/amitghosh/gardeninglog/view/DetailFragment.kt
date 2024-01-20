package com.amitghosh.gardeninglog.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.amitghosh.gradeninglog.R
import com.amitghosh.gradeninglog.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)

        val args: com.amitghosh.gardeninglog.view.DetailFragmentArgs by navArgs()
        val plant = args.plant

        binding.plant = plant

        return binding.root
    }
}