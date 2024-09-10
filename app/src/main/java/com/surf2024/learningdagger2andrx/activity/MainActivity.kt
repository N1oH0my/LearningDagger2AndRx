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
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: PresenterInterface

    private fun fetchCategories(): Observable<Category> {
        return Observable.create { emitter ->
            val categories = listOf(
                Category(1, listOf(Subcategory(101), Subcategory(102))),
                Category(2, listOf(Subcategory(201), Subcategory(202)))
            )
            for (category in categories) {
                if (!emitter.isDisposed) {
                    emitter.onNext(category)
                }
            }
            if (!emitter.isDisposed) {
                emitter.onComplete()
            }
            /**
             * TODO:
             * fetchCategories вернуть Observable<Category> не как list<list> а через emitter по 1 получать
             *     аналогично fetchProducts
             */
        }
    }

    private fun fetchProducts(): Observable<Product> {
        return Observable.create { emitter ->
            val products = listOf(
                Product(1, "Product 1", 101),
                Product(2, "Product 2", 102),
                Product(3, "Product 3", 201),
                Product(4, "Product 4", 202),
                Product(5, "Product 5", 103)
            )

            for (product in products) {
                if (!emitter.isDisposed) {
                    emitter.onNext(product)
                }
            }

            if (!emitter.isDisposed) {
                emitter.onComplete()
            }
        }
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

        val disposable = getProductsByCategoryId(1)
            .subscribe(
                { filteredProducts ->
                    Log.e("MainActivity", "Fetched products $filteredProducts")
                },
                { error ->
                    Log.e("MainActivity", "Error fetching products", error)
                }
            )

        presenter.doSmth()
    }

    private fun getProductsByCategoryId(categoryId: Int): Observable<List<Product>> {
        return fetchCategories()
            .filter { it.id == categoryId }
            .flatMap { category ->
                val subcategoryIds = category.subcategories.map { it.id }
                fetchProducts().filter { product ->
                    subcategoryIds.contains(product.subcategoryId)
                }.toList().toObservable()
            }
    }

    private fun initActivityComponent() {
        (application as MyApp).appComponent
            .getActivityComponent(ActivityModule(this))
            .inject(this)
    }

}