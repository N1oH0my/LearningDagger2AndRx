package com.surf2024.learningdagger2andrx.domain.entity

data class Category(
    val id: Int,
    val subcategories: List<Subcategory>
)