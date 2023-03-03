package com.learning

import io.mockk.clearMocks
import io.mockk.spyk
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

// What is a scope / TestScope ?
// What is a dispatcher  / TestDispatcher ?
// What is a CoroutineContext ?
// What is RunTest
// what is a scheduler?

class CoroutineTest {

    class Foo() {
        fun executeCallToExternalService(): Boolean {
            Thread.sleep(1000)
            println("External call done, Executed at ${Thread.currentThread()}")
            return true
        }
    }

    class Bar() {
        fun triggersIOBehavior(): Boolean {
            Thread.sleep(2000)
            println("IO Behavior done, Executed at ${Thread.currentThread()}")
            return true
        }

        fun triggersIOBehaviorAsync(): Deferred<Boolean> =
            CoroutineScope(Dispatchers.IO).async {
                Thread.sleep(2000)
                println("Async IO Behavior done, Executed at ${Thread.currentThread()}")
                true
            }
    }

    private val serviceFoo = spyk(Foo())
    private val serviceBar = spyk(Bar())

    @Test
    fun `executing sequential operations`() {
        runBlocking {
            serviceFoo.executeCallToExternalService()
            serviceBar.triggersIOBehavior()
        }
    }

    @Test
    fun `executing simultaneous operations`() {
        runBlocking {
            launch {
                serviceBar.triggersIOBehaviorAsync().join()
            }.join()

            serviceFoo.executeCallToExternalService()
        }
    }

    @Test
    fun `executing simultaneous operations in a new scope`() {
        runBlocking {
            serviceBar.triggersIOBehaviorAsync()
            serviceFoo.executeCallToExternalService()
        }
    }
}
