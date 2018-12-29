# itunes-reader

## Info

Library for converting itunes xml files to scala model classes.
I made this library for using itunes Tracks and Playlists in some of my projects.

## Travis

[![Build Status](https://travis-ci.org/sfxcode/sapphire-core.svg?branch=master)](https://travis-ci.org/sfxcode/itunes-reader)

## Download

[ ![Download](https://api.bintray.com/packages/sfxcode/maven/itunes-reader/images/download.svg) ](https://bintray.com/sfxcode/maven/itunes-reader/_latestVersion)

## Model

### MusicLibrary

Members:

* tracks: List[Track]
* playlists: List[Playlist]
* builtTime: Long (Parsing and computing of itunes xml data in ms)
* playlistsForTrack: List[Playlist]

### PlayList

Support for: 

Playlist ID, Playlist Persistent ID, Parent Persistent ID, Name,
Master", Folder, Visible, Music, Distinguished Kind

Members:

* data: PlaylistData
* customValues: Map[String, Any] 
* parent: Option[Playlist]
* children: List[Playlist]

Functions:

* trackSet: Set[Track]  (removes duplicate tracks if exist)

* parentName: name of parent playlist (if exist)
* containsTrackWithId

* totalTime: totalTime of of tracks for this Playlist
* totalTimeString: formatted totalTime of of tracks for this Playlist


### Track

Support for: 

Track ID, Name, Artist, Album, Composer, Album Artist, Genre, Sample Rate,
Bit Rate, Artwork Count, Total Time Year, Play Count, Play Date, Track Type, Location

Members:

* data: PlaylistData
* customValues: Map[String, Any]

Functions:

* totalTimeString: **3m43s** (formatted total time)



## Demo

```scala

object MusicDatabaseApp extends App {
 
  val path = getClass.getClassLoader.getResource("itunes.xml").getPath

  val lib = MusicLibrary(path)

  lib.playlists.foreach(playlist => {
    println(playlist.name + " : " + playlist.totalTimeString())
  })

  lib.tracks.foreach(track => {
    println(track.name + " : " + track.totalTimeString())
  })

}


```
