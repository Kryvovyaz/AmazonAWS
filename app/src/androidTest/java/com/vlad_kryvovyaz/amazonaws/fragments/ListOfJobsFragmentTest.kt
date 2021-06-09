package com.vlad_kryvovyaz.amazonaws.fragments

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import com.vlad_kryvovyaz.amazonaws.R
import com.vlad_kryvovyaz.amazonaws.AmazonAWSActivity
import com.vlad_kryvovyaz.amazonaws.view.JobsAdapter
import com.vlad_kryvovyaz.amazonaws.repository.FakeJobsContainerRepository
import com.vlad_kryvovyaz.amazonaws.repository.JobsContainerRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@UninstallModules(RepositoryModule::class)
@HiltAndroidTest
class FragmentListOfJobsTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val LIST_ITEM_IN_TEST = 19
    private val JOB_IN_TEST = FakeJobsContainerData.jobs[LIST_ITEM_IN_TEST]

    @Before
    fun init() {
        hiltRule.inject()
        val scenario = launchActivity<AmazonAWSActivity>()
    }

    /*
    * Recyclerview comes into view
   */
    @Test
    fun a_test_isFragmentListOfJobsVisible_onAppLaunch() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

    /**
     * Select item nav to details fragment.Correct job is in view
     */
    @Test
    fun test_selectListItem_isFragmentDetailsOfJobsVisible() {
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition<JobsAdapter.JobsViewHolder>(
            LIST_ITEM_IN_TEST,
            click()))
        onView(withId(R.id.job_name_details)).check(matches(withText(JOB_IN_TEST.name)))
    }

    /**
     * Select item nav to details fragment.Correct job is in view
     */
    @Test
    fun test_backNavigation_to_FragmentListsOfJobs() {
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition<JobsAdapter.JobsViewHolder>(
            LIST_ITEM_IN_TEST, click()))
        onView(withId(R.id.job_name_details)).check(matches(withText(JOB_IN_TEST.name)))
        pressBack()
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMemeRepository(): JobsContainerRepositoryImp {
        return FakeJobsContainerRepository()
    }
}