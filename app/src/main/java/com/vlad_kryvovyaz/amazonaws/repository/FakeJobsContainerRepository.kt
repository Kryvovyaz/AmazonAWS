package com.vlad_kryvovyaz.amazonaws.repository

import com.vlad_kryvovyaz.amazonaws.model.JobsContainer
import com.vlad_kryvovyaz.amazonaws.model.JobsContainerResult
import com.vlad_kryvovyaz.amazonaws.utils.RESPONSE_PARSING_ERROR_MESSAGE
import javax.inject.Inject

class FakeJobsContainerRepository
@Inject
constructor() : JobsContainerRepositoryImp {
    private val jobsContainer: List<JobsContainer.JobsContainerItem> =
        listOf<JobsContainer.JobsContainerItem>(
            JobsContainer.JobsContainerItem(755, 2, ""),
            JobsContainer.JobsContainerItem(684, 1, "Item 684"),
            JobsContainer.JobsContainerItem(736, 3, null),
            JobsContainer.JobsContainerItem(599, 1, null),
            JobsContainer.JobsContainerItem(680, 3, "Item 680"),
            JobsContainer.JobsContainerItem(231, 2, null),
            JobsContainer.JobsContainerItem(681, 4, "Item 681"),
            JobsContainer.JobsContainerItem(177, 1, null),
            JobsContainer.JobsContainerItem(263, 1, ""),
            JobsContainer.JobsContainerItem(969, 4, "Item 969"),
            JobsContainer.JobsContainerItem(947, 2, null)
        )
    var shouldNetworkSuccess = true

    override suspend fun fetchMemeContainer(): JobsContainerResult {
        return if (shouldNetworkSuccess) {
            JobsContainerResult.Success(jobsContainer as JobsContainer)
        } else {
            JobsContainerResult.Failure(Error(RESPONSE_PARSING_ERROR_MESSAGE))
        }
    }
}