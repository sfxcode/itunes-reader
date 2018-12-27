package com.sfxcode.music.itunes.model

case class PlaylistData(id: Long, name: String, kind: Int,
  visible: Boolean, music: Boolean, master: Boolean, trackSet: Set[Long])

object PlaylistData extends MapHandling {

  val PlaylistId = "Playlist ID"
  val Name = "Name"

  val Master = "Master"
  val Visible = "Visible"
  val Music = "Music"
  val DistinguishedKind = "Distinguished Kind"

  val Tracks = "Tracks"

  def apply(map: Map[String, Any]): PlaylistData = {
    PlaylistData(intValue(map, PlaylistId), stringValue(map, Name), intValue(map, DistinguishedKind),
      booleanValue(map, Visible), booleanValue(map, Music), booleanValue(map, Master),
      setValue(map, Tracks))
  }

}
