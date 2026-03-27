# EVVN Project Architecture Guide

This document defines the architectural standards for the EVVN Android project, following **Clean Architecture** principles and the **MVI (Model-View-Intent)** pattern for the presentation layer.

## 🏗️ Project Structure (Tree View)

The project is organized into three main layers to ensure a strict separation of concerns:

```text
app/src/main/java/com/king/evvn/
├── data/                       # Data Layer (Infrastructure)
│   ├── repository/             # Implementation of Domain Repositories
│   ├── source/                 # Data Sources (Remote/Local)
│   │   ├── remote/             # Retrofit API Interfaces & DTOs
│   │   └── local/              # Room DAO, DataStore, or SQLDelight
│   └── mapper/                 # Mappers: DTO <-> Domain Model
├── domain/                     # Domain Layer (Business Logic)
│   ├── model/                  # Pure Kotlin Data Classes (Entities)
│   ├── repository/             # Repository Interfaces
│   └── usecase/                # Single-purpose Business Logic classes
├── presentation/               # Presentation Layer (UI & MVI)
│   ├── component/              # Shared UI Components (Composables)
│   ├── feature_name/           # Feature-specific package
│   │   ├── ui/                 # Composable Screens
│   │   └── viewmodel/          # Hilt ViewModels
│   │       ├── FeatureIntent.kt # User Actions
│   │       ├── FeatureState.kt  # UI State (StateFlow)
│   │       ├── FeatureEvent.kt  # One-time Effects (SharedFlow)
│   │       └── FeatureViewModel.kt
│   └── navigation/             # Navigation 3 (Experimental) Setup
├── di/                         # Dependency Injection (Hilt Modules)
├── ui/theme/                   # Design System (Colors, Typography, Theme)
└── EVVNApplication.kt          # Hilt Application Class
```

---

## 💎 Architectural Principles

### 1. Clean Architecture Layers
- **Domain Layer (Center):** The core of the app. It contains business rules and entities. It must have **zero dependencies** on Android or external libraries (except pure Kotlin).
- **Data Layer:** Handles data operations from the network or database. It implements the interfaces defined in the Domain layer.
- **Presentation Layer:** Handles UI rendering and user interaction. It communicates with the Domain layer via **Use Cases**.

### 2. MVI Pattern (Model-View-Intent)
We use a reactive MVI approach to manage state and side effects:
- **Intent:** Represents an action the user takes (e.g., `OnLoginClicked`).
- **State:** A single, immutable object representing the entire UI state (e.g., `isLoading`, `userData`). Observed via `collectAsStateWithLifecycle()`.
- **Event:** Represents one-time side effects that don't change state (e.g., `ShowSnackbar`, `NavigateToHome`).

### 3. Dependency Injection (Hilt)
- Use `@HiltViewModel` for all ViewModels.
- Define `AppModule`, `NetworkModule`, and `DatabaseModule` in the `di/` package to provide dependencies.

### 4. Reactive Flow
1. **View** sends an **Intent** to the **ViewModel**.
2. **ViewModel** executes a **UseCase** (Domain).
3. **UseCase** calls a **Repository** (Data).
4. **Repository** returns data to the **ViewModel**.
5. **ViewModel** updates the **State** (observed by View) or emits an **Event**.

---

## 🛡️ Best Practices
- **Strict English Only:** Code, documentation, and comments must be in English.
- **Single Responsibility:** Each class and function should do one thing.
- **Mapping:** Always map DTOs to Domain models in the Data layer.
- **Testing:** ViewModels and UseCases should be covered by Unit Tests.
