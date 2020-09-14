package com.geekykel.gads.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.geekykel.gads.R
import com.geekykel.gads.model.LearnerModel
import com.geekykel.gads.ui.main.LeadershipTypes
import kotlinx.android.synthetic.main.custom_item.view.*

/**
 * Provide views to RecyclerView with data from dataSet.
 *
 * Initialize the dataset of the Adapter.
 *
 * @param dataSet LearnerModel[] containing the data to populate views to be used by RecyclerView.
 */
class CustomAdapter(private val type: LeadershipTypes?) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var learners: List<LearnerModel>? = null

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view.
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.custom_item, viewGroup, false)
        return ViewHolder(view, type)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.d(TAG, "Element $position set.")

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        learners?.get(position)?.let { viewHolder.bind(it) }
        //viewHolder.bind(dataSet[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = learners?.size ?: 0

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    class ViewHolder(itemView: View, private val type: LeadershipTypes?) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(data: LearnerModel) = with(itemView) {
            Log.e(TAG, data.toString())

            when (type) {
                is LeadershipTypes.SkillIqLeaders -> {
                    title.text = data.name
                    coverImage.setImageResource(R.drawable.skill_iq_trimmed)
                    description.text = data.country
                }

                is LeadershipTypes.LearningLeaders -> {
                    title.text = data.name
                    coverImage.setImageResource(R.drawable.top_learner)
                    description.text = data.country
                }
            }

            // Define click listener for the ViewHolder's View.
            setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    "${data.name} - ${data.country}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    }

    fun submitList(learners: List<LearnerModel>) {
        this.learners = learners
        notifyDataSetChanged()
    }

    companion object {
        private val TAG = "CustomAdapter Data "
    }
}