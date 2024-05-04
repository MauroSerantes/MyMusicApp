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
![fragment_home](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/ccab6030-2019-44c3-8c67-751d5ac5658f) ![mini_media_player](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/0cdd9068-79d1-48fb-93dc-fa2168d921f9)


```
Fragment Search and Fragment Searching Data
```

![fragment_search](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/4b9a99af-0ac0-48f1-b31e-0eab9817ca4e) ![fragment_searching_data](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/9059110f-7f16-4e1c-b2e1-8de822e32d94) ![fragment_searching_data_2](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/6b73b3f3-5157-42be-ad14-cefc7d25a50b)



```
Fragment MyMusic
```
![fragment_MyMusic](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/4f9f3198-2cf3-4992-88cd-ad3e601982c5)




#### Artists Fragment and Tracks Fragment

![fragment_artists](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/d4712339-a476-4fc0-85d4-aa8a08f5bbb4) ![fragment_tracks](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/955e2ee8-ee0b-4650-aee8-53f01020b2f5) ![bottom_sheet_dialog](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/67f7f154-1de5-4e8b-a075-eb03b6e643e0)


### Media Player
![media_player](https://github.com/MauroSerantes/MyMusicApp/assets/146656323/8b277431-1924-4a3f-97f6-e33d38963894)



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
* Foreground Service
* Notification Manager


### Personal Commentaries
_This application is not finished. I make a lot of changes in comparition to the first version, but stills far away of what i want.
I will continue with all the changes and improvemets that this application needs_ 

_Like I said at begginig i will add a logger for the ones that have an account in Dezeer_

_The general idea behind this app is the realization of a personal copy of Spotify_

## Authors ✒️

* **Mauro Serantes** - [Mauro Serantes](https://github.com/MauroSerantes)

## For The Future

* Improve the Ui
* Add a logger
* Testing the app
