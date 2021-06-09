package com.vlad_kryvovyaz.amazonaws.repository

import com.vlad_kryvovyaz.amazonaws.model.JobsContainerResult

interface JobsContainerRepositoryImp {
    suspend fun fetchJobContainer(): JobsContainerResult
}