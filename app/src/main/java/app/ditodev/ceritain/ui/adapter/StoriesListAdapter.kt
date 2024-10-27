package app.ditodev.ceritain.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.ditodev.ceritain.data.remote.response.ListStoryItem
import app.ditodev.ceritain.databinding.ItemRowBinding
import app.ditodev.ceritain.utils.Utils
import com.bumptech.glide.Glide

class StoriesListAdapter :
    ListAdapter<ListStoryItem, StoriesListAdapter.ViewHolder>(Utils.DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        iewType: Int
    ): ViewHolder {
        val view = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
    }

    inner class ViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(stories: ListStoryItem) {
            Glide.with(binding.root)
                .load(stories.photoUrl)
                .into(binding.ivItemPhoto)
            binding.tvItemName.text = stories.name
            binding.tvDescription.text = stories.description
        }
    }
}