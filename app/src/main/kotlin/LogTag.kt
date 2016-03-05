package com.demo.kotlindemo.utils
import android.util.Log
import com.demo.kotlindemo.UmApp

/**
 * Created by sotsys014 on 2/3/16.
 */

fun Log.d(tag: String, message: String) {
    if (UmApp().isDebug())
        Log.d(tag, message);
}