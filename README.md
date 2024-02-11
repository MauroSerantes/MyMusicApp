# MyMusic

_This is an application that allows to search music from Deezer Api and reproduce it. It is a free version until
I add a logger_

## About the app 📋

The application has a main activity and three principal fragments which are accessed through a bottom navigation bar

The three principal fragments are called Home,Search and MyMusic.
In Home you can explore into top artists, genres, top albums and top tracks.
In Seach you can search tracks, artists and albums by specific consult and explore
into the top playlists.
In MyMusic you will have the tracks that you add as favourites.

Besides, the app contains two more fragments called Artists and Tracks where the 
list of the artists by genre and the tracks of an album, artist or playlist is showed 

For the music reproduction the app the app contains an unique activity that uses 
a media player service

#### Here some screeshots of the main app presentation

```
Fragment Home
```

![fragment_home](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/3e984292-9d8a-431f-ad2f-992d9d146635)     ![fragment_home_2](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/2ecc37e7-1d0c-4e5c-8e65-6f4101c4ab0e)

```
Fragment Search
```

![fragment_search](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/a4c46692-c916-4cdc-8af7-e1851066fcc4)     ![fragment_search_2](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/a8a12d37-7897-4abe-b58b-9116810baae6)

```
Fragment MyMusic
```
![fragment_mymusic](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/2c498b46-bb92-4709-b763-a82ccadbc08a)


#### Artists Fragment and Tracks Fragment

![fragment_artist](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/70e37e62-f04a-456a-8ec4-a85b874489ff)     ![fragment_tracks](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/36082752-7792-45e1-bc21-e1f4c1213b46)

### Media Player
![activity_player](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/608c9ebd-9719-4495-b50c-5b273f0611a9)


## Tech Stack Used And Architectural pattern ⚙️
* Xml (eXtensible Markup Language) - For the views
* Navigation Component - For simple navigation between fragments
* MVVM(Model-View-ViewModel) - Main Architecture pattern
* MVP(Model-View-Presenter) - Architecture pattern for the Media Player
* Dagger HILT - For dependency injection
* Kotlin - The main language
* Room Database - For easy use of SQLite database
* Retrofit - For easy consume of RESTful APIs in Android application.
* Picasso Library - For Images
* Service Binding


### Personal Commentaries
_This application is not finished. It works but it needs improvements. I will make those changes
when i learn more of certains technologies_ 
_Like I said at begginig i will add a logger for the ones that have an account in Dezeer_
_This app uses a service but i need to add the background functionalities of it (That something that i continue learning)_

## Authors ✒️

* **Mauro Serantes** - [Mauro Serantes](https://github.com/MauroSerantes)

## For The Future

* Improve the Ui
* Add notifications of The Media Service
* Better code structure
* Testing the app
