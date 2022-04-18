package ru.kalianova.rpgtracker.db

import android.content.Context
import io.objectbox.BoxStore
import ru.kalianova.rpgtracker.model.MyObjectBox

object ObjectBox {

    lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }

}