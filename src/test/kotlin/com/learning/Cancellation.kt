package com.learning

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import org.junit.Test

class Cancellation {
    @Test
    fun `Cancellation is cooperative`() {
        runBlocking {

            val job = launch {
                while (true) {
                    delay(10)
                    println("Cancellable =(")
                    yield()
                }
            }

            val job2 = launch(Job()) {
                withContext(NonCancellable) {
                    while (true) {
                        println("Non Cancellable task =)")
                        delay(10)
                    }
                }
            }

            delay(100)
            job.cancelAndJoin()
            job2.cancel()
            println("Both jobs cancelled")
            delay(100)
        }
    }

    @Test
    fun `Simple explanation on shutdown and it's cancellation`() {
        runBlocking {

            val scope = CoroutineScope(Job() + Dispatchers.Default)

            val parentJob = scope.launch {
                val childJob = launch {
                    withContext(NonCancellable) {
                        repeat(100) {
                            println("Non cancellable execution")
                            delay(10)
                        }
                    }
                }
            }

            parentJob.cancelAndJoin()
            println("Both jobs cancelled")
        }
    }
}