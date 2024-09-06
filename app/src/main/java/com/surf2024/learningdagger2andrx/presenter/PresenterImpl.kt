package com.surf2024.learningdagger2andrx.presenter

import android.app.Application
import com.surf2024.learningdagger2andrx.app.MyApp
import javax.inject.Inject

class PresenterImpl @Inject constructor(
    private val application: Application
) : PresenterInterface {

    override fun doSmth() {
        TODO("Not yet implemented")
    }

}