package ru.kalianova.rpgtracker

import android.app.Application
import ru.kalianova.rpgtracker.db.ObjectBox

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}