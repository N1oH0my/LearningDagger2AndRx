package com.surf2024.learningdagger2andrx.app.module

import android.app.Application
import com.surf2024.learningdagger2andrx.MainActivity
import com.surf2024.learningdagger2andrx.activity.module.ActivityComponent
import com.surf2024.learningdagger2andrx.activity.module.ActivityModule
import com.surf2024.learningdagger2andrx.app.MyApp
import dagger.Component
import javax.inject.Singleton

/*
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(app: MyApp)

    fun activityComponentBuilder(): ActivityComponent.Builder

}*/
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: MyApp)

    fun getActivityComponent(activityModule: ActivityModule): ActivityComponent
}
