package com.vlad_kryvovyaz.amazonaws.repository

import com.vlad_kryvovyaz.amazonaws.model.JobsContainerResult
import com.vlad_kryvovyaz.amazonaws.network.JobsService
import com.vlad_kryvovyaz.amazonaws.utils.RESPONSE_PARSING_ERROR_MESSAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class JobsContainerRepository @Inject constructor(private val jobsService: JobsService) :
    JobsContainerRepositoryImp {
    override suspend fun fetchJobContainer(): JobsContainerResult =
        withContext(Dispatchers.IO) {
            val jobCall = jobsService.getJobs()
            try {
                val response = jobCall.execute()
                val forecastContainer = response.body()
                forecastContainer?.let {
                    return@withContext JobsContainerResult.Success(it)
                } ?: run {
                    return@withContext JobsContainerResult.Failure(
                        Error(RESPONSE_PARSING_ERROR_MESSAGE)
                    )
                }
            } catch (ex: Exception) {
                return@withContext JobsContainerResult
                    .Failure(java.lang.Error(ex.message))
            }
        }
}