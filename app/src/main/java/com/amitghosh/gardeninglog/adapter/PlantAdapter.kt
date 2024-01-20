package com.amitghosh.gardeninglog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amitghosh.gardeninglog.model.Plant
import com.amitghosh.gradeninglog.R
import com.amitghosh.gradeninglog.databinding.ItemRecyclerviewPlantBinding

class PlantAdapter(private val clickListener: PlantListener) :
    RecyclerView.Adapter<PlantAdapter.PlanViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Plant>() {
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem == newItem
        }
    }

    internal val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlanViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_recyclerview_plant,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        holder.binding.plant = differ.currentList[position]
        holder.binding.clickListener = clickListener
    }

    class PlanViewHolder(val binding: ItemRecyclerviewPlantBinding) :
        RecyclerView.ViewHolder(binding.root)
}

class PlantListener(val clickListener: (plant: Plant) -> Unit) {
    fun onClick(plant: Plant) = clickListener(plant)
}