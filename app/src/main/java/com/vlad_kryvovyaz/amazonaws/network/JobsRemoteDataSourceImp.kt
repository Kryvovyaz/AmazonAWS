package com.vlad_kryvovyaz.amazonaws.network

import com.vlad_kryvovyaz.amazonaws.model.JobsContainerResult

interface JobsRemoteDataSourceImp {
    suspend fun fetchJobsContainer(): JobsContainerResult
}