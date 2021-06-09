package com.vlad_kryvovyaz.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vlad_kryvovyaz.amazonaws.model.JobsContainer
import com.vlad_kryvovyaz.amazonaws.model.JobsContainerResult
import com.vlad_kryvovyaz.amazonaws.repository.FakeJobsContainerRepository
import com.vlad_kryvovyaz.amazonaws.utils.RESPONSE_PARSING_ERROR_MESSAGE
import com.vlad_kryvovyaz.amazonaws.viewmodel.JobsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
import org.junit.*

@ExperimentalCoroutinesApi
class JobsViewModelTest {
    lateinit var viewModel: JobsViewModel
    private val fakeRepository = FakeJobsContainerRepository()
    val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    val testCoroutineRule = InstantTaskExecutorRule()

    @Before
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
            listOf(
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
        val result = viewModel.jobsContainerResultLiveData.value as JobsContainerResult.Success
        MatcherAssert.assertThat(result.jobsContainer, `is`(jobsContainer))
    }
}