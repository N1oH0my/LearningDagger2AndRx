package com.surf2024.learningdagger2andrx.activity.module

import com.surf2024.learningdagger2andrx.activity.MainActivity
import com.surf2024.learningdagger2andrx.activity.ActivityScope
import dagger.Subcomponent


@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

}