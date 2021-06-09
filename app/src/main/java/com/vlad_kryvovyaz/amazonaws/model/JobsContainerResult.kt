package com.vlad_kryvovyaz.amazonaws.model

import java.lang.Error

sealed class JobsContainerResult {
    class Success(val jobsContainer: List<JobsContainer>) : JobsContainerResult()
    class Failure(val error: Error) : JobsContainerResult()
    object IsLoading : JobsContainerResult()
}