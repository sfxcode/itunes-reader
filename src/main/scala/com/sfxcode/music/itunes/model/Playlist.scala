package com.sfxcode.music.itunes.model

import com.typesafe.config.ConfigFactory
import org.joda.time.Duration
import org.joda.time.format.{ PeriodFormatter, PeriodFormatterBuilder }

import scala.collection.mutable

case class Playlist(data: PlaylistData, tracks: List[Track], customValues: mutable.HashMap[String, Any] = new mutable.HashMap()) {

  var parent: Option[Playlist] = None
  var children: List[Playlist] = List()

  def id: Long = data.id
  def persistantKey: String = data.persistantKey
  def parentPersistantKey: String = data.parentPersistantKey

  def name: String = data.name

  def parentName: String = {
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

  def smart: Boolean = data.smart

  def totalTime: Int = tracks.map(track => track.totalTime).sum

  def totalTimeString(formatter: PeriodFormatter = Playlist.TotalTimeFormatter): String = {
    val duration = new Duration(totalTime)
    formatter.print(duration.toPeriod)
  }

  def tracksSize: Int = tracks.size

  def trackSet: Set[Track] = tracks.distinct.toSet

  def duplicates: Set[Track] = tracks.groupBy(identity).collect { case (x, List(_, _, _*)) => x }.toSet

  def containsTrackWithId(trackId: Long): Boolean = data.trackSet.contains(trackId)

  override def toString: String = "%s [%s] (%s)".format(name, tracks, id)

}

object Playlist {
  val HourSuffix: String = ConfigFactory.load().getString("com.sfxcode.music.itunes.format.suffix.hour")
  val MinuteSuffix: String = ConfigFactory.load().getString("com.sfxcode.music.itunes.format.suffix.minute")
  val SecondSuffix: String = ConfigFactory.load().getString("com.sfxcode.music.itunes.format.suffix.second")

  val TotalTimeFormatter: PeriodFormatter = new PeriodFormatterBuilder().appendHours()
    .appendSuffix(HourSuffix).appendMinutes.appendSuffix(MinuteSuffix).appendSeconds.appendSuffix(SecondSuffix).toFormatter

}
