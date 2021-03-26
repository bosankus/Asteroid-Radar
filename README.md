# Purpose of this Folder

This folder should contain the scaffolded project files to get a student started on their project. This repo will be added to the Classroom for students to use, so please do not have any solutions in this folder.

## Note: Android Kotlin Gradle Update
Use the updated Gradle version in the `~/gradle/wrapper/gradle-wrapper.properties` file:
```
distributionUrl = https\://services.gradle.org/distributions/gradle-6.1.1-all.zip
```

# Asteroid Radar App:
This app fetches data from NASA API NeoWs (https://api.nasa.gov/) to fetch close approach asteroids and shows details in a list.

## Following were useed
```
Retrofit library to download the data from the Internet.
Moshi to convert the JSON data we are downloading to usable data in the form of custom classes.
Glide library to download and cache images.
```

Below Jetpack Components were used:
```
ViewModel
Room
LiveData
Data Binding
Navigation
```
