package com.sfxcode.music.itunes.model

import com.sfxcode.music.itunes.MusicLibrary
import org.specs2.mutable.Specification

class PlaylistSpec extends Specification {
  val path: String = getClass.getClassLoader.getResource("itunes.xml").getPath
  val library = MusicLibrary(path)

  "Playlist" should {

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

      playlist.tracksSize must be equalTo 24

    }

    "compute trackSet" in {
      val option = library.playlists.find(p => p.name == "90’s Music")
      option must beSome[Playlist]
      val playlist = option.head
      playlist.tracks must haveSize(24)
      playlist.trackSet must haveSize(24)
      playlist.duplicates must haveSize(0)
    }

    "compute duplicates" in {
      val track = library.tracks(0)
      val secondTrack = library.tracks(1)

      val playlist = Playlist(PlaylistData(Map()), List(track, track, track, secondTrack))
      playlist.tracks must haveSize(4)

      playlist.trackSet must haveSize(2)
      playlist.trackSet must contain(track)
      playlist.trackSet must contain(secondTrack)

      playlist.duplicates must haveSize(1)
      playlist.duplicates must contain(track)
    }

  }
}

