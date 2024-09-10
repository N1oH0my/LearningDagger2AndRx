package com.surf2024.learningdagger2andrx.activity

import android.net.http.HttpException
import android.os.Bundle
import android.util.Log
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
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: PresenterInterface

    private fun fetchCategories(): Single<List<Category>> {
        return Single.just(
            listOf(
                Category(1, listOf(Subcategory(101), Subcategory(102))),
                Category(2, listOf(Subcategory(201), Subcategory(202)))
            )
        )
    }

    private fun fetchProducts(): Single<List<Product>> {
        return Single.just(
            listOf(
                Product(1, "Товар 1", 101),
                Product(2, "Товар 2", 102),
                Product(3, "Товар 3", 201),
                Product(4, "Товар 4", 202),
                Product(5, "Товар 5", 103)
            )
        )
    }

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
        return try {
            val resultedListOfProducts: MutableList<Product> = mutableListOf()
            val disposable = fetchProducts().subscribe({ listOfProducts ->
                val subDisposable = fetchCategories().subscribe({ listOfCategories ->
                    val filteredProducts = listOfProducts.filter { product ->
                        listOfCategories.filter { it.id == categoryId }
                            .flatMap { it.subcategories }
                            .map { it.id }
                            .contains(product.subcategoryId)
                    }
                    resultedListOfProducts.addAll(filteredProducts)
                }, { error ->
                    Log.d("MainActivity", "Error Throwable: ${error.message}")
                    emptyList<Product>()
                })
            }, { error ->
                Log.d("MainActivity", "Error Throwable: ${error.message}")
                emptyList<Product>()
            })
            resultedListOfProducts
        } catch (e: Exception) {
            Log.d("MainActivity", "Error Throwable: ${e.message}")
            emptyList<Product>()
        }
    }

    private fun initActivityComponent() {
        (application as MyApp).appComponent
            .getActivityComponent(ActivityModule(this))
            .inject(this)
    }

}