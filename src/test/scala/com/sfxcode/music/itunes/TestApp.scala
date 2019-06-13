package com.sfxcode.music.itunes

object TestApp extends App {
  println(BuildInfo)

  println()

  val path = getClass.getClassLoader.getResource("itunes.xml").getPath

  val lib = MusicLibrary.applyBasic(path)

  println(lib)
  println()
  println(lib.playlists.head)
  println()

  lib.playlists.filter(pl => pl.smart).foreach(playlist => {
    println(playlist.name + " : " + playlist.totalTimeString() + " : " + playlist.tracksSize)
  })

  println()
  println(lib.tracks.size)
  println()
  println(lib.tracks.head + " => " + lib.playlistsForTrack(lib.tracks.head).map(playlist => playlist.name))
  println()

  lib.tracks.take(5).foreach(track => {
    println(track.name + " : " + track.artist + " : " + track.album + " : " + track.totalTimeString())
  })

}
