package com.surf2024.learningdagger2andrx.activity.module

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.surf2024.learningdagger2andrx.activity.ActivityScope
import com.surf2024.learningdagger2andrx.activity.presenter.PresenterImpl
import com.surf2024.learningdagger2andrx.activity.presenter.PresenterInterface
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
        Log.d("Dagger", "Creating PresenterImpl")
        return PresenterImpl(activity.application)
    }

}
