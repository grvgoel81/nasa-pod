package com.gaurav.nasa_pod.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gaurav.nasa_pod.data.model.Apod
import com.gaurav.nasa_pod.databinding.HomeListItemBinding
import com.gaurav.nasa_pod.ui.adapters.holders.ApodViewHolder
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
class FavoriteAdapter(private val delegate: ApodViewHolder.ItemDelegate) :
    RecyclerView.Adapter<ApodViewHolder>() {

    private var data = mutableListOf<Apod>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodViewHolder {
        val view = HomeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val apodViewHolder = ApodViewHolder(view)
        apodViewHolder.setDelegate(delegate)
        return apodViewHolder
    }

    override fun onBindViewHolder(holder: ApodViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(data: MutableList<Apod>) {
//        this.data.clear()
        this.data = data
        notifyDataSetChanged()
    }
}