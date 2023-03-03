package com.learning

import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class Scheduler {

    interface Foo {
        suspend fun executeCallToExternalService(): Boolean
    }

    private val serviceFoo = spyk<Foo>()


    @Before
    fun setup() {
        clearMocks(serviceFoo)
    }

    @Test
    fun `understanding the scheduler`() = runTest {

        val state = MutableStateFlow(false)

        coEvery { serviceFoo.executeCallToExternalService() } coAnswers {
            delay(1000)
            state.emit(true)
            true
        }

        launch { serviceFoo.executeCallToExternalService() }

        println(currentTime)
        coVerify(exactly = 0) { serviceFoo.executeCallToExternalService() }

        advanceTimeBy(1)
        coVerify(exactly = 1) { serviceFoo.executeCallToExternalService() }
        state.value shouldBe false

        advanceTimeBy(999)
        state.value shouldBe false

        runCurrent()  //  OR advanceTimeBy(1)
        state.value shouldBe true
    }

}