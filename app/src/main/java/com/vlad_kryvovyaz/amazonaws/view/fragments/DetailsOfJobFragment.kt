package com.vlad_kryvovyaz.amazonaws.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.vlad_kryvovyaz.amazonaws.databinding.DetailsOfJobFragmentBinding

class DetailsOfJobFragment : Fragment() {
    private var _binding: DetailsOfJobFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsOfJobFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val job = args.job
        binding.jobNameDetails.text = job.name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DetailsOfJobFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}