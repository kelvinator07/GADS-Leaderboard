package com.geekykel.gads.repository

import com.geekykel.gads.model.LearnerModel
import com.geekykel.gads.model.ResultWrapper
import com.geekykel.gads.network.GetDataService
import com.geekykel.gads.network.getDataService
import com.geekykel.gads.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

val leadershipRepository by lazy {
    LeadershipRepository(getDataService)
}

class LeadershipRepository(
    private val webService: GetDataService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getSkillIqLeadership(): ResultWrapper<List<LearnerModel>> {
        return safeApiCall(dispatcher) { webService.getLearnersSkilliq() }
    }

    suspend fun getLearningLeadership(): ResultWrapper<List<LearnerModel>> {
        return safeApiCall(dispatcher) { webService.getLearnersHours() }
    }
}