package com.geekykel.gads.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekykel.gads.R
import com.geekykel.gads.adapters.CustomAdapter
import com.geekykel.gads.model.LearnerModel
import com.geekykel.gads.model.ResultWrapper
import com.google.android.material.snackbar.Snackbar

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var adapter: CustomAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var pageViewModel: PageViewModel
    private lateinit var progressBar: ProgressBar


    private val type by lazy {
        val sectionNumber = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1
        if (sectionNumber == 1) {
            LeadershipTypes.LearningLeaders
        } else {
            LeadershipTypes.SkillIqLeaders
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.recycler_view_frag, container, false).apply {
            tag =
                PlaceholderFragment.TAG
        }

        progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
        recyclerView = view.findViewById(R.id.recycler_view)

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = LinearLayoutManager(activity)


        // Set CustomAdapter as the adapter for RecyclerView.
        //recyclerView.adapter = CustomAdapter(dataset)

        adapter = CustomAdapter(type)
        recyclerView.adapter = adapter

        if (type == LeadershipTypes.LearningLeaders) {
            pageViewModel.learningHoursList.observe(
                viewLifecycleOwner,
                Observer { bindList(view, it) })
        } else if (type == LeadershipTypes.SkillIqLeaders) {
            pageViewModel.skillIqList.observe(viewLifecycleOwner, Observer {
                bindList(view, it)
            })
        }


        return view
    }

    private fun bindList(view: View, list: ResultWrapper<List<LearnerModel>>) {
        when (list) {
            is ResultWrapper.Success -> list.value?.let { adapter.submitList(it) }
            is ResultWrapper.GenericError -> showErrorSnackBar(
                view,
                R.string.something_went_wrong
            )
            is ResultWrapper.NetworkError -> showErrorSnackBar(view, R.string.no_internet)
        }
        progressBar.visibility = View.GONE
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val TAG = "PlaceholderFragment"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    private fun showErrorSnackBar(view: View, msgId: Int) {
        Snackbar.make(view, msgId, Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.retry)) {
                pageViewModel.loadLeaders()
            }.show()
    }
}