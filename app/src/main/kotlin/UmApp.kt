package com.demo.kotlindemo
import android.app.Application
import com.orm.SugarContext

/**
 * Created by sotsys014 on 2/3/16.
 */
class UmApp : Application() {


    override fun onCreate() {
        super.onCreate()
        SugarContext.init(applicationContext)
    }

    public fun isDebug() = BuildConfig.DEBUG

    override fun onTerminate() {
        super.onTerminate()
        SugarContext.terminate()
    }
}