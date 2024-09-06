package com.surf2024.learningdagger2andrx.activity.module

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.surf2024.learningdagger2andrx.activity.ActivityScope
import com.surf2024.learningdagger2andrx.app.module.AppComponent
import com.surf2024.learningdagger2andrx.presenter.PresenterImpl
import com.surf2024.learningdagger2andrx.presenter.PresenterInterface
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {


    @Provides
    @ActivityScope
    fun provideActivity(application: Application): AppCompatActivity = activity

    @Provides
    @ActivityScope
    fun providePresenter(): PresenterInterface {
        println("creating PresenterImpl")
        return PresenterImpl(activity.application)
    }
}
