package com.vlad_kryvovyaz.amazonaws.network

import com.vlad_kryvovyaz.amazonaws.model.JobsContainer
import retrofit2.Call
import retrofit2.http.GET

interface JobsService {
    @GET("/hiring.json")//indicate how a request will be handled.
    fun getJobs(): Call<JobsContainer>
}