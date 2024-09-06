package com.surf2024.learningdagger2andrx.app

import android.app.Application
import com.surf2024.learningdagger2andrx.app.module.AppComponent
import com.surf2024.learningdagger2andrx.app.module.AppModule
import com.surf2024.learningdagger2andrx.app.module.DaggerAppComponent


class MyApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

}
