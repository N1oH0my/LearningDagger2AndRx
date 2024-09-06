package com.surf2024.learningdagger2andrx.activity.presenter

import android.app.Application
import javax.inject.Inject

class PresenterImpl @Inject constructor(
    private val application: Application
) : PresenterInterface {

    override fun doSmth() {
        TODO("Not yet implemented")
    }

}