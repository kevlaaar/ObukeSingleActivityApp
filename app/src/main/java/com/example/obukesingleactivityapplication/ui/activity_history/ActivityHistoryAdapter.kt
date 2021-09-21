package com.example.obukesingleactivityapplication.ui.activity_history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.obukesingleactivityapplication.R
import com.example.obukesingleactivityapplication.models.ActivityItem

class ActivityHistoryAdapter(private val context: Context, private val onDeleteActivityListener: OnDeleteActivityListener) :
    RecyclerView.Adapter<ActivityHistoryAdapter.ActivityHistoryViewHolder>() {


    inner class ActivityHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.activityHistoryTitle)
        val dateText: TextView = itemView.findViewById(R.id.activityHistoryDate)
        val deleteButton: Button = itemView.findViewById(R.id.deleteActivityButton)

        fun bind(activityItem: ActivityItem) {
            titleText.text = activityItem.title
            dateText.text = activityItem.date
            deleteButton.setOnClickListener{
                onDeleteActivityListener.onDeleteActivity(activityItem)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHistoryViewHolder {
        return ActivityHistoryViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item_activity_history, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActivityHistoryViewHolder, position: Int) {
        val activityItem = differ.currentList[position]
        holder.bind(activityItem)
    }

    private val activityCallback = object : DiffUtil.ItemCallback<ActivityItem>() {
        override fun areItemsTheSame(oldItem: ActivityItem, newItem: ActivityItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ActivityItem,
            newItem: ActivityItem
        ): Boolean {
            return oldItem.title == newItem.title && oldItem.date == newItem.date
        }
    }

    private val differ: AsyncListDiffer<ActivityItem> =
        AsyncListDiffer(this, activityCallback)

    fun setData(newListOfActivities: List<ActivityItem>) {
        differ.submitList(newListOfActivities.toMutableList())
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnDeleteActivityListener{
        fun onDeleteActivity(activityItem: ActivityItem)
    }

}