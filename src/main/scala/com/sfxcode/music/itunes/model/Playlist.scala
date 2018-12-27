package com.sfxcode.music.itunes.model

import com.typesafe.config.ConfigFactory
import org.joda.time.Duration
import org.joda.time.format.{ PeriodFormatter, PeriodFormatterBuilder }

import scala.collection.mutable

case class Playlist(data: PlaylistData, tracks: List[Track], customValues: mutable.HashMap[String, Any] = new mutable.HashMap()) {

  var parent:Option[Playlist] = None
  var children:List[Playlist] = List()

  def id: Long = data.id
  def persistantKey:String  = data.persistantKey
  def parentPersistantKey: String = data.parentPersistantKey

  def name: String = data.name

  def parentName = {
    if (parent.isDefined)
      parent.get.name
    else
      PlaylistData.EmptyParentString
  }



  def kind: Int = data.kind

  def visible: Boolean = data.visible

  def music: Boolean = data.music

  def master: Boolean = data.master

  def folder: Boolean = data.folder

  def totalTime: Int = tracks.map(track => track.totalTime).sum

  def totalTimeString(formatter: PeriodFormatter = Playlist.TotalTimeFormatter): String = {
    val duration = new Duration(totalTime)
    formatter.print(duration.toPeriod)
  }

  def containsTrackWithId(trackId: Long): Boolean = data.trackSet.contains(trackId)

  override def toString: String = "%s [%s] (%s)".format(name, tracks, id)

}

object Playlist {
  val HourSuffix = ConfigFactory.load().getString("com.sfxcode.music.itunes.format.suffix.hour")
  val MinuteSuffix = ConfigFactory.load().getString("com.sfxcode.music.itunes.format.suffix.minute")
  val SecondSuffix = ConfigFactory.load().getString("com.sfxcode.music.itunes.format.suffix.second")

  val TotalTimeFormatter: PeriodFormatter = new PeriodFormatterBuilder().appendHours()
    .appendSuffix(HourSuffix).appendMinutes.appendSuffix(MinuteSuffix).appendSeconds.appendSuffix(SecondSuffix).toFormatter

}
