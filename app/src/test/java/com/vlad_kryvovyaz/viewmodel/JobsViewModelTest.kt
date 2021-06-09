package com.vlad_kryvovyaz.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vlad_kryvovyaz.amazonaws.model.JobsContainer
import com.vlad_kryvovyaz.amazonaws.model.JobsContainerResult
import com.vlad_kryvovyaz.amazonaws.repository.FakeJobsContainerRepository
import com.vlad_kryvovyaz.amazonaws.utils.RESPONSE_PARSING_ERROR_MESSAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.*

@ExperimentalCoroutinesApi
class JobsViewModelTest {
    lateinit var viewModel: JobsViewModel
    private val fakeRepository = FakeJobsContainerRepository()
    val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    val testCoroutineRule = InstantTaskExecutorRule()

    @Before//executed before each test case
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = JobsViewModel(fakeRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch data from back end returns error`() {
        fakeRepository.shouldNetworkSuccess = false
        runBlockingTest {
            viewModel.fetchJobsContainer()
        }
        val result = viewModel.jobsContainerResultLiveData.value as JobsContainerResult.Failure
        MatcherAssert.assertThat(result.error.message, `is`(RESPONSE_PARSING_ERROR_MESSAGE))
    }


    @Test
    fun `fetch data from back end returns success`() {
        fakeRepository.shouldNetworkSuccess = true
        runBlockingTest {
            viewModel.fetchJobsContainer()
        }
        val jobsContainer =
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
        val result = viewModel.jobsContainerResultLiveData.value as JobsContainerResult.Success
        MatcherAssert.assertThat(result.jobsContainer, `is`(jobsContainer))
    }
}