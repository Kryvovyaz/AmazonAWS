package com.vlad_kryvovyaz.amazonaws.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vlad_kryvovyaz.amazonaws.R
import com.vlad_kryvovyaz.amazonaws.view.JobsAdapter
import com.vlad_kryvovyaz.amazonaws.databinding.ListOfJobsFragmentBinding
import com.vlad_kryvovyaz.amazonaws.model.JobsContainer
import com.vlad_kryvovyaz.amazonaws.model.JobsContainerResult
import com.vlad_kryvovyaz.amazonaws.utils.TAG
import com.vlad_kryvovyaz.amazonaws.viewmodel.JobsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ListOfJobsFragment : Fragment() {
    private var _binding: ListOfJobsFragmentBinding? = null
    private val binding get() = _binding!!
    val viewModel: JobsViewModel by viewModels()
    lateinit var jobsAdapter: JobsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRecycler()
        viewModel.jobsContainerResultLiveData.observe(viewLifecycleOwner,
            Observer { jobsContainerResult ->
                jobsContainerResult.let { it ->
                    when (it) {
                        is JobsContainerResult.Failure -> {
                            Log.d(TAG, getString(R.string.failure))
                            Toast.makeText(
                                context, getString(R.string.something_went_wrong),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is JobsContainerResult.IsLoading -> {
                            _binding?.progressBar?.visibility = View.VISIBLE
                            Log.d(TAG, " Loading")
                        }
                        is JobsContainerResult.Success -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            Log.d(TAG, "Success")
                            CoroutineScope(Dispatchers.Default).launch {
                                val list = viewModel.sortList(it.jobsContainer)
                                withContext(Dispatchers.Main) {
                                    jobsAdapter.differ.submitList(list as List<JobsContainer>?)
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

        jobsAdapter.setOnItemClickListener {
            val directions = ListOfJobsFragmentDirections.actionListOfJobsFragmentToFragmentDetailsOfJob(
                    it
                )
            findNavController()
                .navigate(directions)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = ListOfJobsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}