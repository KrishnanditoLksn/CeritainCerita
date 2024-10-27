package app.ditodev.ceritain.utils

import androidx.recyclerview.widget.DiffUtil
import app.ditodev.ceritain.data.remote.response.ListStoryItem

object Utils {
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
        override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
            return oldItem.id == newItem.id
        }
    }
}