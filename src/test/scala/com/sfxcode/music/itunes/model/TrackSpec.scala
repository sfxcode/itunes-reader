package com.sfxcode.music.itunes.model

import com.sfxcode.music.itunes.MusicLibrary
import org.specs2.mutable.Specification

class TrackSpec extends Specification {
  val path: String = getClass.getClassLoader.getResource("itunes.xml").getPath
  val library = MusicLibrary(path)

  "Tracks" should {

    "have valid track info" in {
      val option = library.tracks.find(t => t.name == "Smells Like Teen Spirit")
      option must beSome[Track]
      val track = option.head
      track.artist must be equalTo "Nirvana"
      track.album must be equalTo "Nevermind"
      track.genre must be equalTo "Rock"
      track.totalTime must be equalTo 301165
      track.totalTimeString() must be equalTo "5m1s"

    }
  }
}

