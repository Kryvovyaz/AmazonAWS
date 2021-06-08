package com.vlad_kryvovyaz.amazonaws.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

class JobsContainer : ArrayList<JobsContainer.JobsContainerItem>(){
    @Parcelize
    data class JobsContainerItem(
        val id: Int,
        val listId: Int,
        val name: String?
    ) : Parcelable
}