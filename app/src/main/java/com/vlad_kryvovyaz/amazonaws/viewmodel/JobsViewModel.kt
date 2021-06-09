package com.vlad_kryvovyaz.amazonaws.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad_kryvovyaz.amazonaws.model.JobsContainer
import com.vlad_kryvovyaz.amazonaws.model.JobsContainerResult
import com.vlad_kryvovyaz.amazonaws.repository.JobsContainerRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(private val jobsContainerRepository: JobsContainerRepositoryImp) :
    ViewModel() {
    private var _jobsContainerResultLiveData = MutableLiveData<JobsContainerResult>()
    val jobsContainerResultLiveData: LiveData<JobsContainerResult>
        get() = _jobsContainerResultLiveData

    init {
        fetchJobsContainer()
    }

    fun fetchJobsContainer() {
        _jobsContainerResultLiveData.value = JobsContainerResult.IsLoading
        viewModelScope.launch {
            _jobsContainerResultLiveData.value = jobsContainerRepository.fetchJobContainer()
        }
    }

    fun sortList(jobsContainer: List<JobsContainer>): MutableList<JobsContainer> {
        val list = jobsContainer
                as MutableList<JobsContainer>
        list.removeIf { it.name.isNullOrEmpty() }
        return list.sortedWith(
            compareBy<JobsContainer> { it.listId }
                .thenBy {
                    it.name?.substring(5, it.name.length)?.toInt()
                }) as MutableList<JobsContainer>
    }
}