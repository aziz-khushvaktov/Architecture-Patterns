package com.example.mvcexample

import java.util.*
import kotlin.collections.ArrayList

class Model: Observable() {

    private val list: MutableList<Int>

    init {
        list = ArrayList(3)
        list.add(0)
        list.add(0)
        list.add(0)
    }

    @Throws(IndexOutOfBoundsException::class)
    fun getValueAtIndex(index: Int): Int {
        return list[index]
    }

    @Throws(IndexOutOfBoundsException::class)
    fun setValueAtIndex(index: Int) {
        list[index] = list[index] + 1
        setChanged()
        notifyObservers()
    }

}