package com.learning

import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class RunTest {

    interface Foo {
        suspend fun executeCallToExternalService(): Boolean
    }

    private val serviceFoo = spyk<Foo>()

    @Before
    fun setup() {
        clearMocks(serviceFoo, serviceFoo)
    }

    //Test execution -> 761ms
    @Test
    fun `runTest skips delays on testDispatchers`() {
        runTest {
            coEvery { serviceFoo.executeCallToExternalService() } coAnswers {
                delay(1000)
                println("External call done, Executed at ${Thread.currentThread()}")
                true
            }

            serviceFoo.executeCallToExternalService()
        }
    }

    //Test execution -> 765ms
    @Test
    fun `runTest skips delays on testDispatchers complex example`() {
        runTest {
            coEvery { serviceFoo.executeCallToExternalService() } coAnswers {
                withContext(StandardTestDispatcher(testScheduler)) {
                    delay(1000)
                    println("External call done, Executed at ${Thread.currentThread()}")
                    true
                }
            }

            serviceFoo.executeCallToExternalService()
        }
    }

    //Test execution -> 1s851ms
    @Test
    fun `runTest does not skip delays on normal Dispatchers`() {
        runTest {
            coEvery { serviceFoo.executeCallToExternalService() } coAnswers {
                withContext(Dispatchers.Default) {
                    delay(1000)
                    println("External call done, Executed at ${Thread.currentThread()}")
                    true
                }
            }

            serviceFoo.executeCallToExternalService()
        }
    }
}