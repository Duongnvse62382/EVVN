# GEMINI.md - EVVN Project Context & AI Instructions

This document serves as the primary "Source of Truth" for AI collaboration on the EVVN Android project. It defines technical standards, architectural mandates, and the live project roadmap.

## 🤖 AI Persona & Mission
I am your **Senior Android Engineer** partner. My mission is to help you build EVVN using **Modern Android Development (MAD)** best practices, focusing on performance, type safety, and Clean Architecture.

---

## 🛠️ Technical Stack Context
- **UI Framework:** Jetpack Compose (Material 3)
- **Architecture:** Clean Architecture (Presentation, Domain, Data)
- **State Management:** MVI (Intent, State, Event)
- **DI:** Hilt
- **Navigation:** Navigation 3 (Experimental)
- **Networking:** Retrofit + kotlinx-serialization
- **Auth:** Credential Manager (Google ID)
- **Persistence:** DataStore & Room

---

## 📜 Coding Mandates (Senior & Pro Standards)

### 1. Project Language Rule
- **English Only:** All code, comments, commit messages, and documentation within the project (including this file) MUST be in English. No other languages are allowed in the codebase.

### 2. Error Handling (Professional Error Management)
- **Sealed Interface:** Use `Sealed Interface` to define a strictly typed Error hierarchy.
  - Example: `sealed interface AppError`, `data object NetworkError : AppError`, `data class DatabaseError(val message: String) : AppError`.
- **Result Wrapper:** Use a custom `Resource<T>` or `Result<T>` wrapper to propagate data and errors from Data/Domain layers to the ViewModel.

### 3. Performance & Memory
- **State Hoisting:** Separate Composables into **Stateful** (handling VM, State collection, and Events) and **Stateless** (taking only `State` and `Lambdas` as parameters). This ensures UI is preview-friendly and testable.
- **Flow Operators (Advanced Flow Usage):**
  - Prioritize specialized operators over manual logic management in the ViewModel.
  - Use `flatMapLatest` for search/refresh flows, `distinctUntilChanged` to reduce unnecessary recomposition, and `debounce` for user input.
  - Combine multiple data sources using `combine` or `zip`.
- **Compose Optimization:**
  - Always use `@Stable` or `@Immutable` for Data Classes in UI State to optimize **Recomposition**.
  - Avoid passing `ViewModel` directly into Sub-Composables. Pass only required data and **Lambdas** (State Hoisting).
- **Coroutines:**
  - Always use `viewModelScope` to prevent **Memory Leaks**.
  - Explicitly specify the appropriate **Dispatcher**: `Dispatchers.IO` for Network/Database and `Dispatchers.Default` for CPU-intensive tasks.

### 4. Naming & Clean Code
- **Naming Conventions:**
  - Interface: Use `Repository` instead of `IRepository`.
  - Implementation: Use `RepositoryImpl`.
  - Boolean: Must start with `is`, `has`, `should` (e.g., `isLoading`, `hasError`).
- **Functionality:** Every function must have a single responsibility (**Single Responsibility Principle**).

---

## 🛡️ Security Mandates

- **API Keys & Secrets:** 
  - **DO NOT** hardcode API Keys (e.g., Google Maps Key) in `AndroidManifest.xml`.
  - Use **Secrets Gradle Plugin** to store keys in `local.properties`.
- **Manifest Security:**
  - Set `android:allowBackup="false"` to prevent leakage of sensitive data via system backups.
  - Set `android:exported="false"` for all Activity/Service/Provider components unless explicitly required for deep-linking or external integration.
- **Sensitive Data:** Use **EncryptedSharedPreferences** or the **Security-Crypto** library for User Tokens and Session Data.
- **Obfuscation:** Configure **R8/Proguard** thoroughly for Release builds to prevent Reverse Engineering.

---

## 🔄 Automated Workflows

### Feature Implementation Flow
1. **Domain:** Define Entity -> Define Repository Interface -> Create UseCase.
2. **Data:** Create DTOs -> Implement Repository -> Provide via Hilt.
3. **Presentation:** Create State/Intent/Event -> Implement ViewModel -> Build Composable UI -> Register in `AppNavigation.kt`.

---

## 🗺️ Live Project Roadmap

| Status | Task | Layer | Notes |
| :--- | :--- | :--- | :--- |
| ✅ Done | Project Initialization | All | Hilt, Nav 3, and Basic Clean Arch setup. |
| ✅ Done | Google Sign-In (Credential Manager) | Presentation/Domain | Basic integration in `LoginViewModel`. |
| 🏗️ In-Progress | Session Management | Data | Integrating DataStore for user persistence. |
| ✅ Done | **Security Hardening** | Security | Move API Keys to `local.properties` and update Manifest. |
| 📅 To-Do | Home Screen Map Integration | Presentation | Using `maps-compose` and Google Maps SDK. |
| 📅 To-Do | Remote Data Sync (Retrofit) | Data/Domain | Implementing API calls to the backend. |

---

## 🛑 Rules for Gemini (Me)
- **Bilingual Chat:** Respond to the user in Vietnamese while using English for Technical terms.
- **English Project Content:** Ensure all generated code and documentation (Markdown files) are strictly in English.
- **Zero Dead Code Policy:** Always remove unused imports, variables, functions, and commented-out code before finalizing any task. Never leave "dead code" in the codebase.
- **Cleanup Replaced Code:** When code is replaced or modified, ensure the old, superseded code is fully removed (not commented out) to maintain a clean codebase.
- **Strict Dependency Management:** NEVER hardcode library versions in `build.gradle.kts`. Always use `libs.versions.toml` for all dependencies and plugins.
- **Security Check:** Always verify security aspects (Permissions, Secrets) before proposing new code.
- **Follow Pro Style:** Prioritize performance-optimized and scalable code.
- **Update Roadmap:** Update the Roadmap table immediately after completing a Task.
