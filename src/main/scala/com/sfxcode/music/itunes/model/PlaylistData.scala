package com.sfxcode.music.itunes.model

import com.typesafe.config.ConfigFactory

case class PlaylistData(id: Long, persistantKey: String, parentPersistantKey: String,
  name: String, kind: Int,
  visible: Boolean, music: Boolean, master: Boolean, folder: Boolean, trackSet: Set[Long]) {
}

object PlaylistData extends MapHandling {

  val EmptyParentString: String = ConfigFactory.load().getString("com.sfxcode.music.itunes.model.parent.emptyString")

  val PlaylistId = "Playlist ID"
  val PlaylistPersistentId = "Playlist Persistent ID"
  val ParentPersistentId = "Parent Persistent ID"
  val Name = "Name"

  val Master = "Master"
  val Folder = "Folder"
  val Visible = "Visible"
  val Music = "Music"
  val DistinguishedKind = "Distinguished Kind"

  val Tracks = "Tracks"

  def apply(map: Map[String, Any]): PlaylistData = {
    PlaylistData(intValue(map, PlaylistId), stringValue(map, PlaylistPersistentId), stringValue(map, ParentPersistentId),
      stringValue(map, Name), intValue(map, DistinguishedKind),
      booleanValue(map, Visible), booleanValue(map, Music), booleanValue(map, Master), booleanValue(map, Folder),
      setValue(map, Tracks))
  }

}
