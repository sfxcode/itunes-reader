package com.sfxcode.music.itunes

object TestApp extends App {
  println(BuildInfo)

  println()

  val path = getClass.getClassLoader.getResource("itunes.xml").getPath

  val lib = MusicLibrary.applyBasic(path)

  lib.playlists.foreach(playlist => {
    println(playlist.name + " : " + playlist.totalTimeString())
  })

  println(lib.playlists.head)

  println(lib)

  lib.tracks.foreach(track => {
    println(track.name + " : " + track.totalTimeString())
  })

}
