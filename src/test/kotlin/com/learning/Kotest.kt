package com.learning

import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.testCoroutineScheduler
import io.kotest.framework.concurrency.eventually
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.days
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

@OptIn(
    ExperimentalStdlibApi::class, ExperimentalCoroutinesApi::class, ExperimentalKotest::class,
    DelicateCoroutinesApi::class
)

class Kotest : FunSpec() {
    init {
        test("advance time").config(coroutineTestScope = true) {
            val duration = 1.days
            // launch a coroutine that would normally sleep for 1 day
            launch {
                delay(duration.inWholeMilliseconds)
            }
            // move the clock on and the delay in the above coroutine will finish immediately.
            testCoroutineScheduler.advanceTimeBy(duration.inWholeMilliseconds)
            val currentTime = testCoroutineScheduler.currentTime
            println(currentTime)
        }

        test("foo").config(coroutineDebugProbes = true) {
            launch(newSingleThreadContext("example-ctx")) {
                delay(1000)
                println("Process executed")
            }.join()
        }

        //Non-deterministic tests
        test("eventually") {
            val state = MutableStateFlow(false)

            launch(newSingleThreadContext("ctx")) {
                delay(100)
                state.value = true
            }

            eventually(3000) {
                state.value shouldBe true
            }
        }

        test("continually") {
            //TODO
        }

        test("until") {
            //TODO
        }

        test("retry") {
            //TODO
        }

    }
}