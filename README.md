# MyPictures

## Programming Language
The project was written primarily in Kotlin
## App Architecture 
The architecture approach chosen was [Android Architecture Components](https://developer.android.com/jetpack/guide?gclid=Cj0KCQjwgtWDBhDZARIsADEKwgNXH3aKpLvuKhDYfQSfx9Pr9y9NR2ckaCN5EWqOC7UUpvVSRHXX0p0aAltyEALw_wcB&gclsrc=aw.ds) relying on the MVVM(Model - View -View Model) and [LiveData](https://developer.android.com/topic/libraries/architecture/livedata). Reasons for selecting this approach include

- Separation of concerns
 The different layers of the app(such as business logic,UI,domain) are more decoupled from each other.This allows for easier testability of each component,code modularity and reusability and ease of understanding of new developers to the codebase

- Easier support for event driven programming
 LiveData and Data Binding allows for UI components to react to events and respond accordingly.



## Dependency Injection
Dependency injection allows classes to define their dependencies without constructing them. At runtime, another class is responsible for providing these dependencies.This helps us achieve Single Responsibility Principle and making each class focus on a single functionality.I elected to choose [Hilt](https://developer.android.com/training/dependency-injection/hilt-android);A dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.It has quite a less steep learning curve and less boilerplate code to achieve the same results as compared to other dependency injection frameworks

## RXJava2
RXJava2 provide an efficient way to make asynchronous/non blocking scalable data fetching operations.This is used by the ViewModel to asynchronously call the use case
## Retrofit
Retrofit is a popular networking library to make networking calls.This was used to make the api call

## Room
Room provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.I am using Room to cache the network response data and use this cache whenever there is a network error retrieving the data from the api

## Package Structure
#### di 
This package holds the dependency injection codebase.It holds the Hilt modules that has the necessary components and dependencies to be injected
#### model 
This contains classes that hold application data.Key data being  response data to the various network calls
#### network 
Retrofit API declaration that contains annotations on the interface methods and its parameters indicate how a request will be handled
#### repository 
This holds the repository.Repository will handle where the data source will come from and relay the necessary data to the use case.It contains both the repository interface and its implementation
#### ui 
This is the presentation layer of the app and holds the respective views,their view models,recyclerview adapters and UI state classes.The ViewModels in this case are responsible for
- Calling repository for data and handling the response
- Setting appropriate UI states dependent on API response 

#### util
This holds Utility classes which have various utility methods which are/or have potential for reuse throughout the app.The Constants file also holds the Server API  Address
#### usecase
This holds the use case that contains the business logic of the app
#### remote
This holds the network Retrofit service 
#### local
This holds the necessary Room implementations such as the DAO




