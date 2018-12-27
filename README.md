# itunes-reader

## Info

A small library for converting itunes xml files to scala.
I made this library for using itunes Tracks and Playlists in some of my projects.

## Travis

[![Build Status](https://travis-ci.org/sfxcode/sapphire-core.svg?branch=master)](https://travis-ci.org/sfxcode/itunes-reader)

## Download

[ ![Download](https://api.bintray.com/packages/sfxcode/maven/itunes-reader/images/download.svg) ](https://bintray.com/sfxcode/maven/itunes-reader/_latestVersion)
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
