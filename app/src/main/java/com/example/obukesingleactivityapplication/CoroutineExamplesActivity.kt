package com.example.obukesingleactivityapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class CoroutineExamplesActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking {
//            printHelloWorld()
            jobExample()
            jobRepeatExample()
        }
//        lifecycleScope.launch(Dispatchers.){
//
//        }
    }

    private suspend fun printHello(){
        delay(1000)
        Log.e("coroutine","World!")
    }
    private suspend fun printHelloWorld() = coroutineScope {
        launch {
            delay(2000)
            Log.e("coroutine","World! 2")
        }
        launch {
            delay(1000)
            Log.e("coroutine","World! 1")
        }
        Log.e("coroutine","Hello")
    }

    private suspend fun jobExample() = coroutineScope {
        val job = launch {
            delay (1000)
            Log.e("coroutine","World!")
        }
        Log.e("coroutine","Hello")
        job.join()
        Log.e("coroutine","Done")
    }

    private suspend fun jobRepeatExample() = coroutineScope {
        val job = launch {
            repeat(10) {
                Log.e("coroutine", "Sleeping $it...")
                delay(500)
            }
        }
        delay(2000)
        job.cancel()
        Log.e("coroutine", "No more sleeping.")
        job.join()
        Log.e("coroutine", "Job done.")
    }
}