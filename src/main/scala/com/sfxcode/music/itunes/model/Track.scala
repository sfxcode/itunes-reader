package com.sfxcode.music.itunes.model

import com.typesafe.config.ConfigFactory
import org.joda.time.Duration
import org.joda.time.format.{ PeriodFormatter, PeriodFormatterBuilder }

import scala.collection.mutable

case class Track(data: TrackData, customValues: mutable.HashMap[String, Any] = new mutable.HashMap()) {

  def id: Long = data.id

  def name: String = data.name

  def artist: String = data.artist

  def album: String = data.album

  def composer: String = data.composer

  def albumArtist: String = data.albumArtist

  def genre: String = data.genre

  def year: Int = data.year

  def sampleRate: Int = data.sampleRate

  def bitRate: Int = data.bitRate

  def totalTime: Int = data.totalTime

  def totalTimeString(formatter: PeriodFormatter = Track.TotalTimeFormatter): String = {
    val duration = new Duration(totalTime)
    formatter.print(duration.toPeriod)
  }

  def artworkCount: Int = data.artworkCount

  def trackType: String = data.trackType

  def location: String = data.location

  override def toString: String = "%s - %s (%s)".format(artist, name, id)
}

object Track {

  val MinuteSuffix: String = ConfigFactory.load().getString("com.sfxcode.music.itunes.format.suffix.minute")
  val SecondSuffix: String = ConfigFactory.load().getString("com.sfxcode.music.itunes.format.suffix.second")

  val TotalTimeFormatter: PeriodFormatter = new PeriodFormatterBuilder().appendMinutes.appendSuffix(MinuteSuffix).appendSeconds.appendSuffix(SecondSuffix).toFormatter
}
