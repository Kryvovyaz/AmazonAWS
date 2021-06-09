package com.vlad_kryvovyaz.amazonaws.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vlad_kryvovyaz.amazonaws.databinding.ItemRecycleViewBinding
import com.vlad_kryvovyaz.amazonaws.model.JobsContainer

class JobsAdapter : RecyclerView.Adapter<JobsAdapter.JobsViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<JobsContainer>() {
        override fun areItemsTheSame(
            oldItem: JobsContainer,
            newItem: JobsContainer
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: JobsContainer,
            newItem: JobsContainer
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    inner class JobsViewHolder(private val binding: ItemRecycleViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            itemView.apply {
                binding.jobName.text = differ.currentList[position].name
                binding.listId.text = differ.currentList[position].listId.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsViewHolder {
        return JobsViewHolder(
            ItemRecycleViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: JobsViewHolder, position: Int) {
        val job = differ.currentList[position]
        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { it(job) } //it refers to onItemClickListener
            }
        }
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        Log.d("myTag", "${differ.currentList.size}")
        return differ.currentList.size
    }
    private var onItemClickListener: ((JobsContainer) -> Unit)? = null

    fun setOnItemClickListener(listener: (JobsContainer) -> Unit) {
        onItemClickListener = listener
    }
}