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
import com.surf2024.learningdagger2andrx.domain.entity.Category
import com.surf2024.learningdagger2andrx.domain.entity.Product
import com.surf2024.learningdagger2andrx.domain.entity.Subcategory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: PresenterInterface

    val categories = listOf(
        Category(1, listOf(Subcategory(101), Subcategory(102))),
        Category(2, listOf(Subcategory(201), Subcategory(202)))
    )

    val products = listOf(
        Product(1, "Товар 1", 101),
        Product(2, "Товар 2", 102),
        Product(3, "Товар 3", 201),
        Product(4, "Товар 4", 202),
        Product(5, "Товар 5", 103)
    )

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

        val productsByCategory = getProductsByCategoryId(1)

        presenter.doSmth()
    }

    private fun getProductsByCategoryId(categoryId: Int): List<Product> {
        return products.filter { product ->
            categories.filter { it.id == categoryId }
                .flatMap { it.subcategories }
                .map { it.id }
                .contains(product.subcategoryId)
        }
    }

    private fun initActivityComponent() {
        (application as MyApp).appComponent
            .getActivityComponent(ActivityModule(this))
            .inject(this)
    }

}