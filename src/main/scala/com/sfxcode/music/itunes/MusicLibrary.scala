package com.sfxcode.music.itunes

import com.sfxcode.music.itunes.model.{ Playlist, PlaylistData, Track, TrackData }
import com.sfxcode.music.itunes.xml.LibrarySection._
import com.sfxcode.music.itunes.xml.{ LibraryCallback, PullParser }

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class MusicLibrary(filePath: String = "", completeTrackInfo: Boolean = true) extends LibraryCallback {

  private val parseResultTrackMap = new mutable.HashMap[Long, Track]()
  private val parseResultPlaylists = new ArrayBuffer[Playlist]()

  if (filePath.isEmpty)
    PullParser.parse(callback)
  else
    PullParser.parseFile(filePath, callback)

  val tracks: List[Track] = parseResultTrackMap.values.toList
  val playlists: List[Playlist] = parseResultPlaylists.toList

  override def callback(section: LibrarySection, map: Map[String, Any]): Unit = {
    if (TrackType == section) {
      val data = {
        if (completeTrackInfo)
          TrackData(map)
        else
          TrackData.applyBasic(map)
      }
      parseResultTrackMap.+=(data.id -> Track(data))
    } else if (PlaylistType == section) {
      val data = PlaylistData(map)
      if (!data.master && data.kind == 0) {
        val trackBuffer = new ArrayBuffer[Track]()
        val trackSet = data.trackSet
        trackSet.foreach(l => {
          if (parseResultTrackMap.contains(l))
            trackBuffer.+=(parseResultTrackMap(l))
        })
        parseResultPlaylists.+=(Playlist(data, trackBuffer.toList))
      }
    }
  }
}

object MusicLibrary {

  def apply(filePath: String = ""): MusicLibrary = new MusicLibrary(filePath)

  def applyBasic(filePath: String = ""): MusicLibrary = new MusicLibrary(filePath, completeTrackInfo = false)

}
