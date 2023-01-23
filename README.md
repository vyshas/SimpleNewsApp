# Simple News App
App that displays news from newsapi.org.

Create a file in the root of the project (if not already there) called `credentials.properties` and populate with the following.
```
NEWS_API_KEY = 
```
NEWS_API_KEY from newsapi.org

## Architecture
* Built with Modern Android Development practices.
* Follows [Guide to app architecture](https://developer.android.com/topic/architecture)
* Includes unit tests for Use cases, Repository, ViewModels, API Service response.

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Jetpack Compose is Android’s recommended modern toolkit for building native UI
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [Stateflow and Shareflow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) - StateFlow and SharedFlow are Flow APIs that enable flows to optimally emit state updates and emit values to multiple consumers.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
- [Dependency Injection](https://developer.android.com/training/dependency-injection)
  - [Hilt](https://dagger.dev/hilt) - Easier way to incorporate Dagger DI into Android apps. **This is in the [main branch](https://github.com/wajahatkarim3/Imagine)**.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [MockK](https://mockk.io) - For Mocking and Unit Testing
- [Turbine](https://github.com/cashapp/turbine) - For Unit Testing flows
