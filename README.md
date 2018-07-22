# itunes-reader

## Info

A small library for converting itunes xml files to scala.
I made this library for using itunes Tracks and Playlists in some of my projects.

[ ![Download](https://api.bintray.com/packages/sfxcode/maven/itunes-reader/images/download.svg?version=1.0.0) ](https://bintray.com/sfxcode/maven/itunes-reader/1.0.0/link)

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
