package com.artemissoftware.core

sealed class FilterOrder{

    object Ascending: FilterOrder()

    object Descending: FilterOrder()
}
