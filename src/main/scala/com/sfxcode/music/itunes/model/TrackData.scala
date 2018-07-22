package com.sfxcode.music.itunes.model

case class TrackData(id: Long, name: String, artist: String, album: String,
                     composer: String, albumArtist: String, genre: String, year: Int,
                     sampleRate: Int, bitRate: Int, totalTime: Int, artworkCount: Int,
                     trackType: String, location: String)

object TrackData extends MapHandling {
  val TrackId = "Track ID"
  val Name = "Name"

  val Artist = "Artist"
  val Album = "Album"
  val Composer = "Composer"
  val AlbumArtist = "Album Artist"
  val Genre = "Genre"
  val SampleRate = "Sample Rate"
  val BitRate = "Bit Rate"
  val ArtworkCount = "Artwork Count"
  val TotalTime = "Total Time"
  val Year = "Year"

  val PlayCount = "Play Count"
  val PlayDate = "Play Date"

  val TrackType = "Track Type"
  val Location = "Location"

  def apply(map: Map[String, Any]): TrackData = {
    new TrackData(longValue(map, TrackId), stringValue(map, Name),
      stringValue(map, Artist), stringValue(map, Album),
      stringValue(map, Composer), stringValue(map, AlbumArtist), stringValue(map, Genre), intValue(map, Year),
      intValue(map, SampleRate), intValue(map, BitRate), intValue(map, TotalTime),
      intValue(map, ArtworkCount), stringValue(map, TrackType), stringValue(map, Location))
  }

  def applyBasic(map: Map[String, Any]): TrackData = {
    new TrackData(longValue(map, TrackId), stringValue(map, Name),
      stringValue(map, Artist), stringValue(map, Album),
      "", "", stringValue(map, Genre), intValue(map, Year),
      0, 0, intValue(map, TotalTime), 0, "", "")
  }

}

