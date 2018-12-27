package com.sfxcode.music.itunes

import com.sfxcode.music.itunes.model.{ Playlist, Track }
import org.specs2.mutable.Specification

class MusicLibrarySpec extends Specification {
  val path: String = getClass.getClassLoader.getResource("itunes.xml").getPath
  val library = MusicLibrary(path)

  "MusicLibrary" should {

    "be valid in" in {
      library.tracks must haveSize(49)
      library.playlists must haveSize(7)
      library.builtTime must be greaterThan 0
    }

    "have valid playlist info" in {
      val option = library.playlists.find(p => p.name == "90’s Music")
      option must beSome[Playlist]
      val playlist = option.head
      playlist.data.trackSet must haveSize(24)
      playlist.tracks must haveSize(24)
      playlist.kind must be equalTo 0
      playlist.totalTime must be equalTo 6735373
      playlist.totalTimeString() must be equalTo "1h52m15s"

      playlist.folder must beFalse

    }

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

