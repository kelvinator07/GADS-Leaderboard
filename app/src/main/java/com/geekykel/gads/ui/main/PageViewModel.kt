package com.geekykel.gads.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekykel.gads.model.LearnerModel
import com.geekykel.gads.model.ResultWrapper
import com.geekykel.gads.repository.leadershipRepository
import kotlinx.coroutines.launch

class PageViewModel : ViewModel() {

    private val _skillIqList: MutableLiveData<ResultWrapper<List<LearnerModel>>> = MutableLiveData()
    private val _learningHoursList: MutableLiveData<ResultWrapper<List<LearnerModel>>> =
        MutableLiveData()

    val skillIqList: LiveData<ResultWrapper<List<LearnerModel>>> = _skillIqList
    val learningHoursList: LiveData<ResultWrapper<List<LearnerModel>>> = _learningHoursList

    init {
        loadLeaders()
    }

    fun loadLeaders() {
        viewModelScope.launch {
            _skillIqList.postValue(leadershipRepository.getSkillIqLeadership())
            _learningHoursList.postValue(leadershipRepository.getLearningLeadership())
        }
    }
}

sealed class LeadershipTypes {
    object SkillIqLeaders : LeadershipTypes()
    object LearningLeaders : LeadershipTypes()
}