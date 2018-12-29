package com.sfxcode.music.itunes

import com.sfxcode.music.itunes.model.{ Playlist, PlaylistData, Track, TrackData }
import com.sfxcode.music.itunes.xml.LibrarySection._
import com.sfxcode.music.itunes.xml.{ LibraryCallback, PullParser }
import com.typesafe.scalalogging.LazyLogging

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class MusicLibrary(filePath: String = "", completeTrackInfo: Boolean = true) extends LibraryCallback with LazyLogging {

  private val start = System.currentTimeMillis()

  private val parseResultTrackMap = new mutable.HashMap[Long, Track]()
  private val parseResultPlaylists = new ArrayBuffer[Playlist]()

  if (filePath.isEmpty)
    PullParser.parse(callback)
  else
    PullParser.parseFile(filePath, callback)

  logger.debug("parsed in %s ms".format(System.currentTimeMillis() - start))

  val tracks: List[Track] = parseResultTrackMap.values.toList
  val playlists: List[Playlist] = parseResultPlaylists.toList

  addPlaylistHirachie()

  val builtTime: Long = System.currentTimeMillis() - start
  logger.debug("build music library in %s ms".format(builtTime))

  protected def addPlaylistHirachie(): Unit = {
    val playlistMap = playlists.map(playlist => (playlist.persistantKey, playlist)).toMap
    playlists.foreach(playList => {
      val key = playList.parentPersistantKey
      if (key.nonEmpty && playlistMap.contains(key)) {
        val parentPlaylist = playlistMap(key)
        playList.parent = Some(parentPlaylist)
        parentPlaylist.children = parentPlaylist.children ++ List(playList)
      }

    })
  }

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

  override def toString: String = "MusicLibrary (@%s) Playlists:%s Tracks:%s BuiltTime:%s ms".format(hashCode(), playlists.size, tracks.size, builtTime)
}

object MusicLibrary {

  def apply(filePath: String = ""): MusicLibrary = new MusicLibrary(filePath)

  def applyBasic(filePath: String = ""): MusicLibrary = new MusicLibrary(filePath, completeTrackInfo = false)

}
