package com.geekykel.gads.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekykel.gads.model.DataModel

class PageViewModelNew : ViewModel() {

    private val liveData = MutableLiveData<List<DataModel>>()
    //val data: LiveData<DataModel> = liveData

    fun setIndex(index: List<DataModel>) {
        liveData.value = index
    }
}