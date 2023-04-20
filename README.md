# Currency conversion app
A currency converter app that allows a user to view a given amount in a given currency converted into other currencies

# Description
This is a single page app with a topbar containing title, a textfield to enter amount, a dropdown to select a currency and a grid view to show all other currencies 

# Technologies used
Android Studio Electric Eel | 2022.1.1 Patch 2
Current Desktop: ubuntu:GNOME

# Android versions
>The minimum Android SDK version is 24
>The compile SDK version is 33
>The target SDK version is 33

# It uses the following libraries
- Jetbrains Kotlin Android 1.8.0
- Jetpack compose 1.4.0
- coroutines 1.3.9
- lifecycle 2.6.0-alpha01
- room 2.4.3
- retrofit 2.9.0
- dagger-hilt 2.44
- turbine 0.9.0
- Google Truth 1.1.3
- kotlinx-coroutines-test 1.6.4
- Junit 4.12
- core_testing_version 2.1.0

# Build
extract the zip file and open using Android studio make sure the Android versions and configurations are the same

# Disclaimer
I've used kotlin to write this application as it provides an easy and simpler syntax than Java. Besides that kotlin provides support for coroutines which makes it very easy to write android lifecycle aware processes.
- **Jetpack Compose** - Jetpack compose enables declarative UI which provide control structures developers can use to manipulate the drawing of the UI. Its easy and take less time than Android xml view.
- **Coroutine** - Kotlin's coroutine enables asynchronous and non blocking programming. Coroutines are aware of lifecycle so there's no chance of threads processing out of contexts. Coroutines suspend functions makes it looks like I'm writing a synchronous program hence, the classes looks cleaner. Live data and Flow helps implementing the observer pattern, which I could've done using Rx. At first I used Rx with retrofit and room but the code at viewModel looked clumsier. So I've decided to migrate to coroutine and livedata.
- **Retrofit** - Retrofit makes it is easy to manipulate and maintain a clean architecture and it enables network calling with a very minimum amount of codes. It also supports implementation with coroutines and liveData.
- **Room** - The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite. It also work with coroutines and helps maintaining the clean architecture.
- **Hilt** - Dagger-hilt enables dependency inversion one of the 5 principles of SOLID. It helped me separate my business logics from repository, dao and API layer. It enhances the scalability of the project so that in future if want to implement a different UI I'd merely have to make any change in the technical part of this project.

# Testing
- FunctionsTest - this test class was written to test functions in Functions.kt file under other package
- CurrencyDaoTest - contains test cases for CurrencyDao.kt class of db package
- CurrencyConverterHomeViewModelTest - contains all test cases for all the functions and flows of CurrencyConverterViewModel

# Conclusion
I have tried to use the latest tools of Android SDK hence, I've used Jetpack compose for UI design and Dagger-Hilt for dependency injection. To inherit android app architectural guidelines I tried to briefly divide the app in 3 different layers. 
In the ui layer there are composable, theme and viewModels where UI elements and UI states are in composable files, viewModels are working as UI holder, theme defines colors and resources
In the data layer there are db containing database, entity and dao object, network containing retrofit instance and api interfaces, repository containing the feature repository.
In the domain layer I kept the connectivity observer.


