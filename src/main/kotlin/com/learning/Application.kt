package com.learning

import com.learning.plugins.configureRouting
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

/*fun main2() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}*/
/*
fun Application.module() {
    configureRouting()
}*/





// Coroutine Scope
// Coroutine context
// Coroutine Dispatcher
// Coroutine Job                    <-----
// Suspended function
// Coroutine Exception Handlers
// Cancellation CoOp
// Structured Concurrency

// Tests
//  -  TestScope
//  -  Scheduler
//  -  Kotest Non-Deterministic Features

@OptIn(DelicateCoroutinesApi::class)
fun main() {

}

suspend fun backgroundTask(param: Int): Int {
    delay(100)
    return param
}

/*val threadPooledCoroutineModule = module {
    single { Executors.newFixedThreadPool(get<Config>().performance.scheduler_pool_size) }
    single(AppScope) {
        CoroutineScope(get<ExecutorService>().asCoroutineDispatcher())
    }
}*/


























//Shutdown example
/*runBlocking {

    val scope = CoroutineScope(Job() + Dispatchers.Default)

    val parentJob = scope.launch {
        val childJob = launch {
            withContext(NonCancellable) {
                repeat(6) {
                    println("Non cancellable execution")
                    delay(1000)
                }
            }
        }
    }

    println("cancelling parent")
    scope.cancel()
    println("Job status is cancelled: ${parentJob.isCancelled}")
    println("Both jobs cancelled")
    //parentJob.join()
}*/
// Dispatchers
/*runBlocking {
    launch(Dispatchers.IO){
        println("Executing IO on thread: ${Thread.currentThread()}")
    }
    // launch(Dispatchers.Main){
    //     println("Executing Main on thread: ${Thread.currentThread()}")
    // }
    launch(Dispatchers.Default){
        println("Executing Default on thread: ${Thread.currentThread()}")
    }
    //
    launch(){
        println("Executing without defined dispatcher on thread: ${Thread.currentThread()}")
    }
    launch(Dispatchers.Unconfined){
        println("Executing Unconfined on thread: ${Thread.currentThread()}")
    }
}*/
// Cancellation COOP
/*runBlocking(Dispatchers.Default) {
    supervisorScope {
        val parent = launch(coroutineContext) {
            val childJob = launch(coroutineContext) {
                withContext(Dispatchers.IO) {
                    repeat(6) {
                        println("process something on thread: ${Thread.currentThread()}")
                        delay(1000)
                    }
                    throw Exception("Interrupted by something")
                }
            }
            delay(6001)
            println("Is child cancelled A: ${childJob.isCancelled} thread: ${Thread.currentThread()}")
        }

        println("Is parent cancelled? A: ${parent.isCancelled} thread: ${Thread.currentThread()}")
        delay(10000)
        println("What about now? A: ${parent.isCancelled} thread: ${Thread.currentThread()}")
    }
}*/
//suspend function
/*suspend fun backgroundTask(param: Int): Int {
    delay(100)
    return a
}

fun backgroundTask(param: Int, callback: Continuation<Int>): Int {
    // long running operation
}
Continuation<T> //is an interface that contains two functions that are invoked to resume the coroutine with a return value or with an exception if an error had occurred while the function was suspended.

interface Continuation<in T> {
    val context: CoroutineContext
    fun resume(value: T)
    fun resumeWithException(exception: Throwable)
}
*/
// Exception behavior on coorutine Builders
/*runBlocking {
    val job = GlobalScope.launch { // root coroutine with launch
        println("Throwing exception from launch")
        throw IndexOutOfBoundsException() // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
    }
    job.join()
    println("Joined failed job")
    val deferred = GlobalScope.async { // root coroutine with async
        println("Throwing exception from async")
        throw ArithmeticException() // Nothing is printed, relying on user to call await
    }
    try {
        deferred.await()
        println("Unreached")
    } catch (e: ArithmeticException) {
        println("Caught ArithmeticException")
    }

}*/
//Exception handler
/*
        val handler = CoroutineExceptionHandler { _, throwable -> println("handling exception + $throwable") }
        val supervisorScope = CoroutineScope( SupervisorJob() + Dispatchers.IO + handler)
        supervisorScope.run {
            val child1 = launch {
                throw IllegalArgumentException("error")
            }
            val child2 = launch {
                println("I'm child 2")
            }
        }*/
