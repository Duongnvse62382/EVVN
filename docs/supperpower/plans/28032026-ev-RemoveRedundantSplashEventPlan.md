# Implementation Plan: Remove Redundant SplashEvent from SplashViewModel

**Specification Reference:** `docs/supperpower/specs/28032026-ev-RemoveRedundantSplashEvent.md`

## 1. Task Objective
To implement the changes outlined in the specification, which involves removing the unused `SplashEvent` flow and its emission from `SplashViewModel.kt`.

## 2. Prerequisites
- Access to the project codebase.
- Understanding of Kotlin Coroutines, StateFlow, and SharedFlow.
- Ability to modify Kotlin files.
- Development environment set up for Android (Gradle, IDE).

## 3. Implementation Tasks

### Task 1: Modify SplashViewModel.kt
- **Action:** Edit the file `app/src/main/java/com/king/evvn/presentation/splash/viewmodel/SplashViewModel.kt`.
- **Details:**
    1.  Remove the declarations for `_event` and `event`:
        ```kotlin
        // Remove:
        // private val _event = MutableSharedFlow<SplashEvent>()
        // val event: SharedFlow<SplashEvent> = _event
        ```
    2.  Remove the `_event.emit(SplashEvent.NavigateToLogin)` line from the `initialize()` function:
        ```kotlin
        private fun initialize() {
            viewModelScope.launch {
                delay(2000)
                _state.update {
                    it.copy(
                        isLoading = false,
                    )
                }
                // Remove: _event.emit(SplashEvent.NavigateToLogin)
            }
        }
        ```
    3.  (Optional but recommended) Review imports and remove `kotlinx.coroutines.flow.MutableSharedFlow` and `kotlinx.coroutines.flow.SharedFlow` if they are no longer used after the code removal.

### Task 2: Write Unit Tests
- **Action:** Create or update unit tests for `SplashViewModel.kt` to ensure the removal of `SplashEvent` does not introduce regressions and that `isLoading` state logic remains correct.
- **Details:**
    - Write a test case that verifies `SplashViewModel` initializes correctly and updates `isLoading` to false after the delay.
    - Ensure no tests depend on the `SplashEvent` being emitted. If any exist, they should be adapted or removed.
    - (If current tests exist for SplashViewModel, review and update them.)

### Task 3: Run Unit Tests
- **Action:** Execute the unit tests for `SplashViewModel`.
- **Verification:** All unit tests must pass.

### Task 4: Rebuild Project
- **Action:** Clean and rebuild the project to ensure all changes are integrated correctly and there are no compilation errors.
- **Command Example:** `./gradlew clean build` (or appropriate Gradle command for the project).
- **Verification:** The build process must complete successfully without errors or warnings related to the modified code.

### Task 5: Code and Security Review
- **Action:** Perform a thorough code and security review of the implemented changes.
- **Details:**
    - **Code Review:** Ensure the code adheres to project standards (Clean Architecture, MAD best practices, naming conventions, performance optimizations). Check for readability, maintainability, and adherence to the spec.
    - **Security Review:** Verify that no sensitive data is exposed, API keys are handled securely (as per `GEMINI.md` mandates), and no new security vulnerabilities are introduced. Check manifest settings (`android:exported`, `android:allowBackup`) if relevant to the changes.
- **Verification:** Changes are deemed acceptable for merge/PR after successful review.

## 4. Testing Strategy
- **Unit Tests:** Write and run unit tests specifically for `SplashViewModel` to verify state updates and absence of event emission.
- **Runtime Verification:** Test the application flow after the build to ensure the splash screen behaves as expected (correct duration, smooth transition) and that navigation proceeds correctly without relying on the removed event.

## 5. Dependencies
- No external dependencies are introduced or modified.
- Internal dependencies: Relies on `SplashViewModel.kt`, `MainActivity.kt`, and the `SplashScreen` API.

## 6. Rollback Plan
If unexpected issues arise during testing or rebuilding:
- Revert the changes made to `SplashViewModel.kt`.
- Re-evaluate the need for `SplashEvent` or explore alternative navigation triggers if `isLoading` logic proves insufficient.
