package com.example.mytranslator

import android.app.Application
import androidx.room.Room
import com.example.mytranslator.data.room.HistoryDAO
import com.example.mytranslator.data.room.HistoryDataBase
import com.example.mytranslator.di.mainKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(mainKoinModule)
        }
    }

    companion object {
        private var appInstance: App? = null
        private var db: HistoryDataBase? = null
        private const val DB_NAME = "mytranslator.db"
        private const val ERROR_MESSAGE = "APP must not be null"

        fun getHistoryDAO(): HistoryDAO {
            synchronized(HistoryDataBase::class.java) {
                if (db == null) {
                    if (appInstance == null) throw IllegalStateException(ERROR_MESSAGE)
                    db = Room.databaseBuilder(
                        appInstance!!.applicationContext,
                        HistoryDataBase::class.java,
                        DB_NAME
                    ).build()
                }
            }
            return db!!.historyDAO()
        }
    }

}
