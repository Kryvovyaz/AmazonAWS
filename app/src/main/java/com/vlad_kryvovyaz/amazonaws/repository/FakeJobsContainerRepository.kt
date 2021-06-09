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
            JobsContainer(684, 1, "Item 684"),
            JobsContainer(680, 3, "Item 680"),
            JobsContainer(681, 4, "Item 681"),
            JobsContainer(969, 4, "Item 969"),
            JobsContainer(964, 4, "Item 964"),
            JobsContainer(963, 4, "Item 963"),
            JobsContainer(962, 4, "Item 962"),
            JobsContainer(961, 4, "Item 961"),
            JobsContainer(960, 4, "Item 960"),
            JobsContainer(951, 4, "Item 951"),
            JobsContainer(952, 4, "Item 952"),
            JobsContainer(953, 4, "Item 953"),
            JobsContainer(954, 4, "Item 954"),
            JobsContainer(961, 4, "Item 955"),
            JobsContainer(961, 4, "Item 956"),
            JobsContainer(961, 4, "Item 957"),
            JobsContainer(961, 4, "Item 958"),
            JobsContainer(961, 4, "Item 959"),
            JobsContainer(961, 4, "Item 940"),
            JobsContainer(961, 4, "Item 941"),
        )
    var shouldNetworkSuccess = true

    override suspend fun fetchJobContainer(): JobsContainerResult {
        return if (shouldNetworkSuccess) {
            JobsContainerResult.Success(jobsContainer )
        } else {
            JobsContainerResult.Failure(Error(RESPONSE_PARSING_ERROR_MESSAGE))
        }
    }
}