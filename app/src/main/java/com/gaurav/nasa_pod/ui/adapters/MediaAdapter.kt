package com.gaurav.nasa_pod.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.gaurav.nasa_pod.data.model.Apod
import com.gaurav.nasa_pod.databinding.HomeListItemBinding
import com.gaurav.nasa_pod.ui.adapters.holders.ApodViewHolder
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
class MediaAdapter(private val delegate: ApodViewHolder.ItemDelegate) :
    PagingDataAdapter<Apod, ApodViewHolder>(ApodDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodViewHolder {
        lateinit var view: ViewBinding
        view = HomeListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val apodViewHolder = ApodViewHolder(view)
        apodViewHolder.setDelegate(delegate)
        return apodViewHolder
    }

    override fun onBindViewHolder(holder: ApodViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    private class ApodDiffCallback : DiffUtil.ItemCallback<Apod>() {
        override fun areItemsTheSame(oldItem: Apod, newItem: Apod): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Apod, newItem: Apod): Boolean {
            return oldItem == newItem
        }
    }
}