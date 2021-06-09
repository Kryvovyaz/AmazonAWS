package com.vlad_kryvovyaz.amazonaws.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vlad_kryvovyaz.amazonaws.model.JobsContainer
import com.vlad_kryvovyaz.amazonaws.model.JobsContainerResult
import com.vlad_kryvovyaz.amazonaws.repository.FakeJobsContainerRepository
import com.vlad_kryvovyaz.amazonaws.utils.RESPONSE_PARSING_ERROR_MESSAGE
import com.vlad_kryvovyaz.amazonaws.viewmodel.JobsViewModel
import com.vladkryvovyaz.mvvm_hilt_test.di.RepositoryModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.*
import javax.inject.Inject

@UninstallModules(RepositoryModule::class)
@ExperimentalCoroutinesApi
@HiltAndroidTest
class JobsViewModelTest {
    private lateinit var viewModel: JobsViewModel

    @get:Rule
    val testCoroutineRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fakeRepository: FakeJobsContainerRepository

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = JobsViewModel(fakeRepository)
    }

    @Test
    fun fetch_data_from_back_end_returns_success() {
        fakeRepository.shouldNetworkSuccess = true
        runBlockingTest {
            viewModel.fetchJobsContainer()
            delay(200)
        }
        val jobsContainer = listOf(
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
            JobsContainer(961, 4, "Item 941")
        )
        val result = viewModel.jobsContainerResultLiveData.value as JobsContainerResult.Success
        assertThat(result.jobsContainer, `is`(jobsContainer))
    }

    @Test
    fun fetch_data_from_back_end_returns_error() {
        fakeRepository.shouldNetworkSuccess = false
        runBlockingTest {
            viewModel.fetchJobsContainer()
        }
        val result = viewModel.jobsContainerResultLiveData.value as JobsContainerResult.Failure
        assertThat(result.error.message, `is`(RESPONSE_PARSING_ERROR_MESSAGE))
    }
}