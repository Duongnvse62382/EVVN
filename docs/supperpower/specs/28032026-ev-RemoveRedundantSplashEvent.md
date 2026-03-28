# Specification: Remove Redundant SplashEvent from SplashViewModel

## 1. Summary
This specification details the removal of the unused `SplashEvent` flow and its emission from `SplashViewModel.kt`. This change is necessary because the splash screen's lifecycle and subsequent navigation logic are managed by the `SplashScreen.setKeepOnScreenCondition` mechanism in `MainActivity`, which relies on the `isLoading` state, making the explicit `SplashEvent.NavigateToLogin` redundant.

## 2. Requirements
- The splash screen's visibility and duration are controlled by `MainActivity` using `SplashScreen.setKeepOnScreenCondition { state.isLoading }`.
- The `SplashEvent.NavigateToLogin` emitted by `SplashViewModel` is not currently observed or handled by `MainActivity` or any other UI component to trigger navigation.
- The explicit event emission is therefore redundant and can be safely removed to simplify the ViewModel.

## 3. Technical Approach
The following changes will be made to `app/src/main/java/com/king/evvn/presentation/splash/viewmodel/SplashViewModel.kt`:
- Remove the declaration of `private val _event = MutableSharedFlow<SplashEvent>()` and `val event: SharedFlow<SplashEvent> = _event`.
- Remove the line `_event.emit(SplashEvent.NavigateToLogin)` from the `initialize()` function.
- Remove any unused imports related to `MutableSharedFlow` and `SharedFlow` if they become unused after the code removal.

### 3.1. Current Code Snippet (SplashViewModel.kt)
```kotlin
// ... (imports) ...

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state

    private val _event = MutableSharedFlow<SplashEvent>()
    val event: SharedFlow<SplashEvent> = _event

    fun processIntent(intent: SplashIntent) {
        when (intent) {
            SplashIntent.Initialize -> initialize()
        }
    }

    private fun initialize() {
        viewModelScope.launch {
            delay(2000)
            _state.update {
                it.copy(
                    isLoading = false,
                )
            }
            _event.emit(SplashEvent.NavigateToLogin)
        }
    }
}
```

### 3.2. After Fix Code Snippet (SplashViewModel.kt)
```kotlin
// ... (imports) ...

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state

    fun processIntent(intent: SplashIntent) {
        when (intent) {
            SplashIntent.Initialize -> initialize()
        }
    }

    private fun initialize() {
        viewModelScope.launch {
            delay(2000)
            _state.update {
                it.copy(
                    isLoading = false,
                )
            }
        }
    }
}
```

### 3.3. File Changes
- **File:** `app/src/main/java/com/king/evvn/presentation/splash/viewmodel/SplashViewModel.kt`
- **Change:** Removal of `SplashEvent` flow declaration and emission.

## 5. UI/UX Notes
No direct user-facing changes are expected. The application's behavior regarding the splash screen and subsequent navigation should remain unaffected, as the primary control mechanism (`isLoading` state) is preserved.

## 6. Data Models
No changes to data models are required.

## 7. Error Handling
N/A for this specific change, as it involves removing unused code, not altering error handling logic.

## 8. Scope
This change is isolated to `SplashViewModel.kt`.
