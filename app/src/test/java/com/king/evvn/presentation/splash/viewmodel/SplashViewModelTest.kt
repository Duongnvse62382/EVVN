package com.king.evvn.presentation.splash.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

// Mock definitions for SplashState and SplashIntent assuming basic structure
// SplashEvent is no longer used in SplashViewModel, so tests for its emission are not needed.
sealed interface SplashIntent {
    object Initialize : SplashIntent
}

@ExperimentalCoroutinesApi
class SplashViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: SplashViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SplashViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test initialize sets isLoading to false after delay`() = runTest {
        assertEquals(true, viewModel.state.value.isLoading)

        viewModel.processIntent(SplashIntent.Initialize)
        advanceUntilIdle() // Advance time until all coroutines are idle

        assertEquals(false, viewModel.state.value.isLoading)
    }
}
