package com.example.stickyheaderbottomsheetlibrary

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class FeatureAdapter : ListAdapter<String, FeatureAdapter.FeatureViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        return FeatureViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_feature_item, parent, false))
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        // do something
        holder.textview.text = getItem(position)
    }

    class FeatureViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val colorsList = listOf<Int>(R.color.teal_200, R.color.teal_700, android.R.color.holo_blue_light, R.color.purple_500)
        val textview by lazy { view.findViewById<TextView>(R.id.tvName) }
    }
}