package com.amonteiro.a23_11_sparks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amonteiro.a23_11_sparks.databinding.RowWeatherBinding

class WindListAdapter : ListAdapter<WindBean, WindListAdapter.ViewHolder>(Comparator()) {

    class ViewHolder(val binding:RowWeatherBinding) : RecyclerView.ViewHolder(binding.root)

    class Comparator : DiffUtil.ItemCallback<WindBean>(){
        override fun areItemsTheSame(oldItem: WindBean, newItem: WindBean) = oldItem === newItem

        override fun areContentsTheSame(oldItem: WindBean, newItem: WindBean)= oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(RowWeatherBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.tvtemp.text = item.speed.toString()
    }
}