# FlickrFeed
Flickr Image Feed Android Application

## Environment
* AndroidStudio 3.5.1
* Kotlin 1.3
* RxAndroid 2
* Dagger 2


### Structure

```sh
com.tigerspike.flickrfeed
├── app
├── data
├── di
├── domain
├── extension
└── ui

```

* app
  * Application layer without UI
    * android.app.Application / android.app.Service layer, etc
* data
  * Infrastructure layer
    * Put pure access methods and rules to the persistence layer such as network, storage,...
    * No erosion to other layers
* domain
  * Model and Use case with domain knowledge
* di
  * DI Component, Module
* extension
  * Extension classes  
* ui
  * Presentation layer
  * View, UI
  
### Unit test

```sh
./gradlew test 
```  

### UI test

```sh
./gradlew connectedAndroidTest
```
