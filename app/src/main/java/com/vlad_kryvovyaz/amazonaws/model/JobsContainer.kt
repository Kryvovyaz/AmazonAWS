package com.vlad_kryvovyaz.amazonaws.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
    @Parcelize
    data class JobsContainer(
        val id: Int,
        val listId: Int,
        val name: String?
    ) : Parcelable