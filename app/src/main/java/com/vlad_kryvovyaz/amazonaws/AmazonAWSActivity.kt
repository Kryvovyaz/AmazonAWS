package com.vlad_kryvovyaz.amazonaws

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vlad_kryvovyaz.amazonaws.View.JobsAdapter
import com.vlad_kryvovyaz.amazonaws.databinding.ActivityAmazonawsBinding
import com.vlad_kryvovyaz.amazonaws.model.JobsContainer
import com.vlad_kryvovyaz.amazonaws.model.JobsContainerResult
import com.vlad_kryvovyaz.viewmodel.JobsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class AmazonAWSActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityAmazonawsBinding
    private val binding get() = _binding
    val viewModel: JobsViewModel by viewModels()
    lateinit var jobsAdapter: JobsAdapter
    val TAG = "ListOfJobs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAmazonawsBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        createRecycler()
        viewModel.jobsContainerResultLiveData.observe(this,
            Observer { jobsContainerResult ->
                jobsContainerResult.let { it ->
                    when (it) {
                        is JobsContainerResult.Failure -> {
                            Log.d(TAG, "Failure")
                            Toast.makeText(
                                this, "Something went wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is JobsContainerResult.IsLoading -> {
                            _binding.progressBar.visibility = View.VISIBLE
                            Log.d(TAG, " Loading")
                        }
                        is JobsContainerResult.Success -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            Log.d(TAG, "Success")
                            CoroutineScope(Dispatchers.Default).launch {
                                val list = viewModel.sortList(it.jobsContainer as JobsContainer)
                                withContext(Dispatchers.Main) {
                                    jobsAdapter.differ.submitList(list)
                                }
                            }

                        }
                    }
                }
            })
    }

    private fun createRecycler() {
        jobsAdapter = JobsAdapter()
        binding.recyclerView.apply {
            adapter = jobsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}
