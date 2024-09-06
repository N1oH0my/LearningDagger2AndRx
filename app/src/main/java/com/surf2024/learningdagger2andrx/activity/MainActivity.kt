package com.surf2024.learningdagger2andrx.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.surf2024.learningdagger2andrx.R
import com.surf2024.learningdagger2andrx.activity.module.ActivityModule
import com.surf2024.learningdagger2andrx.app.MyApp
import com.surf2024.learningdagger2andrx.activity.presenter.PresenterInterface
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: PresenterInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initActivityComponent()

        presenter.doSmth()

    }

    private fun initActivityComponent() {
        (application as MyApp).appComponent
            .getActivityComponent(ActivityModule(this))
            .inject(this)
    }

}