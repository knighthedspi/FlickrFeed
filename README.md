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

### Build And Install

Debug

```sh
./gradlew installDebug
```

Release
* Create keystore file and signing.properties file

```sh
mkdir keystore
cd keystore/
keytool -genkey -v -keystore your_release.keystore -alias your_key_alias -keyalg RSA -keysize 2048 -validity 10000 -deststoretype pkcs12
```

* Edit signing.properties file with below format

```properties
storePassword=(your store password)
keyPassword=(your key password)
keyAlias=(your key alias)
storeFile=(your store file name)
```

* Build and install

```sh
./gradlew installRelease
```

