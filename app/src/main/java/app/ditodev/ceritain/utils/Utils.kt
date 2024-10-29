package app.ditodev.ceritain.utils

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.DiffUtil
import app.ditodev.ceritain.data.remote.response.ListStoryItem

object Utils {
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
        override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
            return oldItem == newItem
        }
    }

    fun showLoading(isLoading: Boolean, progressBar: ProgressBar) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    const val EXTRA_ID = "extra_id"
}