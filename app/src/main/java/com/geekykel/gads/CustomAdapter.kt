package com.geekykel.gads

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.geekykel.gads.model.DataModel
import kotlinx.android.synthetic.main.custom_item.view.*

/**
 * Provide views to RecyclerView with data from dataSet.
 *
 * Initialize the dataset of the Adapter.
 *
 * @param dataSet DataModel[] containing the data to populate views to be used by RecyclerView.
 */
class CustomAdapter(private val dataSet: List<DataModel>) : RecyclerView.Adapter<CustomAdapter.DataVH>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DataVH {
        // Create a new view.
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.custom_item, viewGroup, false)

        return DataVH(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: DataVH, position: Int) {
        Log.d(TAG, "Element $position set.")

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.bind(dataSet[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = dataSet.size

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    class DataVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: DataModel) = with(itemView) {
            Log.e(TAG, data.toString())
            title.text = data.name
            coverImage.setImageResource(R.drawable.skill_iq_trimmed)
            description.text = data.country

            // Define click listener for the ViewHolder's View.
            setOnClickListener {
                Toast.makeText(itemView.context, "${data.name} - ${data.country}", Toast.LENGTH_SHORT).show()
            }

        }
    }

    companion object {
        private val TAG = "CustomAdapter Data "
    }
}