package app.ditodev.ceritain.utils

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.DiffUtil
import app.ditodev.ceritain.data.remote.response.ListStoryItem
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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

    fun formatDate(date: String, target: String): String {
        val instant = Instant.parse(date)
        val formatter = DateTimeFormatter
            .ofPattern("dd MMM yyyy | HH:mm")
            .withZone(ZoneId.of(target))

        return formatter.format(instant)
    }
}