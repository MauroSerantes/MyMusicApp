# MyMusic

_This is an application that allows to search music from Deezer Api and reproduce it. It is a free version until
I add a logger.Because of that it only allows to reproduce music previews_

## About the app 📋

The application has a main activity and three principal fragments which are accessed through a bottom navigation bar

The three principal fragments are called Home,Search and MyMusic.
In Home you can explore into top artists, playlists, top albums and top tracks.
In Seach you can search tracks, artists and albums by specific consult and explore
into the top radios and genres.
To this search category I add another searching fragment with the propouse of 
search tracks, artists and albums by text and show the last searches at the same time.
In MyMusic you will have the tracks that you add as favourites.

Besides, the app contains two more fragments called Artists and Tracks where the 
list of the artists by genre and the tracks of an album, artist or playlist is showed.
Also, for the tracks fragment i add a bottom sheet dialog as a menu for each showed track

For the music reproduction the app contains a mini player (MediaBottomPlayerFragment) as the 
main way of reproduce music with simple commands (skip_next, play, skip_prev and a seek bar),
also it contains an activity (Player Activity) for more details and commands

Every time a track is played, the app shows the corresponding notification.
This notification alows to control the music service

#### Here some screeshots of the main app presentation

```
Fragment Home
```

![fragment_home](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/6e8acae5-faeb-455d-80ff-5a6a8b319a00)
![mini_media_player](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/6d5c9b1f-59fb-4790-9ccc-add3df8bb3e3)

```
Fragment Search and Fragment Searching Data
```

![fragment_search](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/3b0a72ef-eeb7-41a0-84dc-0007b68c5a6f)
![fragment_searching_data](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/44d94cb6-48a1-496a-9f95-3412c8882ddf)
![fragment_searching_data_2](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/b8fdf8b4-be4e-4a9c-a1a3-c3e156d7477b)


```
Fragment MyMusic
```
![fragment_MyMusic](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/da8b037f-229e-4c4b-aa16-68b8b436b2b5)



#### Artists Fragment and Tracks Fragment

![fragment_artists](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/c6dfc647-801c-4c1a-845b-c1c7c123c478)
![fragment_tracks](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/d8c44aff-4ca3-4460-873f-aff6f87834d6)
![bottom_sheet_dialog](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/ccfb5e5e-4a18-4d72-bbdc-e1fbcb4473eb)


### Media Player
![media_player](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/e791aa9e-e7b9-4ae3-9ac1-124e8651788b)



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

_This app uses a service but i need to add the background functionalities of it (That is something I continue learning)_

## Authors ✒️

* **Mauro Serantes** - [Mauro Serantes](https://github.com/MauroSerantes)

## For The Future

* Improve the Ui
* Add notifications of The Media Service
* Better code structure
* Testing the app
