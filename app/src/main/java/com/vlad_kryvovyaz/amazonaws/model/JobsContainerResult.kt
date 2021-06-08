package com.vlad_kryvovyaz.amazonaws.model

import java.lang.Error

sealed class JobsContainerResult {
    class Success(val jobsContainer: JobsContainer) : JobsContainerResult()
    class Failure(val error: Error) : JobsContainerResult()
    object IsLoading : JobsContainerResult()
}