package com.codemonkeys.connectedclock.app.settings.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codemonkeys.connectedclock.R
import com.codemonkeys.shared.status.Status


class SettingStatusAdapter(private val Status: MutableList<Status>,
                           private val listener: SettingRecyclerViewClickListener):
    RecyclerView.Adapter<SettingStatusAdapter.MyViewHolder>() {

        class MyViewHolder(val singleStatusView: View) : RecyclerView.ViewHolder(singleStatusView)

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): SettingStatusAdapter.MyViewHolder {
            // create a new view
            val singleStatusView = LayoutInflater.from(parent.context)
                .inflate(R.layout.single_status_recycling_view_setting, parent, false)
            // set the view's size, margins, paddings and layout parameters
            return MyViewHolder(singleStatusView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val statusName = Status.get(position).statusName ?: ""
            val degree = Status.get(position).clockHandAngle ?: 0
            // set the text view
            val singleStatusTextView = holder.singleStatusView.findViewById<TextView>(R.id.single_statue_text_view_setting)
            singleStatusTextView.setText(statusName)
            // set the degree
            val singleDegreeTextView = holder.singleStatusView.findViewById<TextView>(R.id.single_statue_degree_setting)
            singleDegreeTextView.setText(degree.toString() + " degree")
            // set the delete option
            val singleStatusButton = holder.singleStatusView.findViewById<Button>(R.id.single_statue_deletion_button_setting)
            singleStatusButton.setOnClickListener{
                removeAt(position)
            }
        }

        fun removeAt(position: Int) {
            listener.onDeletionClick(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, this.getItemCount())
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = Status!!.size
}