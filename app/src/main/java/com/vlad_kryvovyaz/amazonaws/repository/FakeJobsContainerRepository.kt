package com.vlad_kryvovyaz.amazonaws.repository

import com.vlad_kryvovyaz.amazonaws.model.JobsContainer
import com.vlad_kryvovyaz.amazonaws.model.JobsContainerResult
import com.vlad_kryvovyaz.amazonaws.utils.RESPONSE_PARSING_ERROR_MESSAGE
import javax.inject.Inject

class FakeJobsContainerRepository
@Inject
constructor() : JobsContainerRepositoryImp {
    private val jobsContainer: List<JobsContainer> =
        listOf<JobsContainer>(
            JobsContainer(755, 2, ""),
            JobsContainer(684, 1, "Item 684"),
            JobsContainer(736, 3, null),
            JobsContainer(599, 1, null),
            JobsContainer(680, 3, "Item 680"),
            JobsContainer(231, 2, null),
            JobsContainer(681, 4, "Item 681"),
            JobsContainer(177, 1, null),
            JobsContainer(263, 1, ""),
            JobsContainer(969, 4, "Item 969"),
            JobsContainer(947, 2, null)
        )
    var shouldNetworkSuccess = true

    override suspend fun fetchMemeContainer(): JobsContainerResult {
        return if (shouldNetworkSuccess) {
            JobsContainerResult.Success(jobsContainer )
        } else {
            JobsContainerResult.Failure(Error(RESPONSE_PARSING_ERROR_MESSAGE))
        }
    }
}