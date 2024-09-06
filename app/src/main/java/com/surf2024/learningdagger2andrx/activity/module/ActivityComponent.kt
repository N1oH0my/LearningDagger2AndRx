package com.surf2024.learningdagger2andrx.activity.module

import com.surf2024.learningdagger2andrx.MainActivity
import com.surf2024.learningdagger2andrx.activity.ActivityScope
import com.surf2024.learningdagger2andrx.app.module.AppComponent
import com.surf2024.learningdagger2andrx.app.module.AppModule
import dagger.Component
import dagger.Subcomponent


@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

}
