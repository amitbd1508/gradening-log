package com.amitghosh.gardeninglog.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import com.amitghosh.gardeninglog.adapter.PlantAdapter
import com.amitghosh.gardeninglog.adapter.PlantListener
import com.amitghosh.gardeninglog.model.Plant
import com.amitghosh.gardeninglog.viewmodel.PlantViewModel
import com.amitghosh.gradeninglog.R
import com.amitghosh.gradeninglog.databinding.FragmentMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class MainFragment : Fragment() {
    val plantViewModel: PlantViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        val plantAdapter = PlantAdapter(PlantListener { plant ->
            plantViewModel.onPlantClicked(plant)
        }
        )
        binding.plantRecyclerview.adapter = plantAdapter
        binding.viewmodel = plantViewModel

        plantViewModel.navigateToPlantDetail.observe(viewLifecycleOwner, { plant ->
            plant?.let {
                this.findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailFragment(
                        plant
                    )
                )
                plantViewModel.onPlantDetailNavigated()
            }
        })

        binding.fabAddPlant.setOnClickListener {
            showCreatePlantPlanner();
        }

        plantViewModel.plants.observe(viewLifecycleOwner, {
            plantAdapter.differ.submitList(it)
            binding.groupLoading.visibility = View.GONE
        })

        plantViewModel.darkMode.observe(viewLifecycleOwner, {
            setDefaultNightMode(if (it) MODE_NIGHT_YES else MODE_NIGHT_NO)
        })

        return binding.root
    }

    private fun showCreatePlantPlanner() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.create_plant_dialog)

        val etTitle = dialog.findViewById(R.id.etName) as EditText
        val etFrequiency = dialog.findViewById(R.id.etFrequiency) as EditText
        val etType = dialog.findViewById(R.id.etType) as EditText


        val addBtn = dialog.findViewById(R.id.btnPlantAdd) as Button
        addBtn.setOnClickListener {
            val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            val currentDateandTime = sdf.format(Date())
            val plant = Plant(etTitle.text.toString(), currentDateandTime, Math.random().toInt(), etType.text.toString(), etFrequiency.text.toString(), "")
            plantViewModel.insertPlant(plant);
            dialog.hide();
        }

        val cancelBtn = dialog.findViewById(R.id.btnPlantCancel) as Button
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}