package com.sfxcode.music.itunes.xml

import com.sfxcode.music.itunes.model.LibraryInfo
import com.sfxcode.music.itunes.xml.LibrarySection._
import com.typesafe.scalalogging.LazyLogging
import org.joda.time.DateTime

import scala.collection.mutable
import scala.io.Source
import scala.xml.pull.{EvElemEnd, EvElemStart, EvText, XMLEventReader}

object PullParser extends LazyLogging {
  val MusicLibraryPathMac = "Music/iTunes/iTunes Music Library.xml"

  def parse(callback: (LibrarySection, Map[String, Any]) => Unit = new SimpleLoggingCallback().callback): Boolean = {
    val userHome = System.getProperty("user.home")
    parseFile("%s/%s".format(userHome, MusicLibraryPathMac), callback)
  }

  def parseFile(filePath: String, callback: (LibrarySection, Map[String, Any]) => Unit = new SimpleLoggingCallback().callback): Boolean = {
    val source = io.Source.fromFile(filePath)("UTF-8")
    parseSource(source, callback)
  }


  def parseSource(source: Source, callback: (LibrarySection, Map[String, Any]) => Unit = new SimpleLoggingCallback().callback): Boolean = {
    try {
      parseXml(source, callback)
      true
    } catch {
      case e:Exception =>
        logger.error(e.getMessage, e)
        false
    }
  }

  private def parseXml(source: Source, callback: (LibrarySection, Map[String, Any]) => Unit): Unit = {
    val reader = new XMLEventReader(source)

    var dictDepth = 0
    var arrayDepth = 0
    var isTrackMode = false
    var isPlaylistMode = false
    var isKeyElement = false
    var isStringElement = false
    var isIntegerElement = false
    var isBooleanElement = false
    var isDateElement = false
    var key = ""

    var map = new mutable.HashMap[String, Any]()
    var idSet = mutable.HashSet[Long]()

    while (reader.hasNext) {
      val next = reader.next

      next match {

        case EvElemStart(_, "dict", _, _) =>
          dictDepth = dictDepth + 1
          if (isTrackMode && dictDepth == 3)
            map.clear()
          else if (isPlaylistMode && dictDepth == 2) {
            map.clear()
            idSet.clear()
          }

        case EvElemStart(_, "array", _, _) => arrayDepth = {
          arrayDepth + 1

        }

        case EvElemStart(_, "key", attr, _) => isKeyElement = true
        case EvElemStart(_, "string", attr, _) => isStringElement = true
        case EvElemStart(_, "integer", attr, _) => isIntegerElement = true
        case EvElemStart(_, "boolean", attr, _) => isBooleanElement = true
        case EvElemStart(_, "date", attr, _) => isDateElement = true
        case EvElemStart(_, "true", attr, _) =>
          if ((isTrackMode && dictDepth == 3) || (isPlaylistMode && dictDepth == 2)) {
            map.+=(key -> true)
          }

        case EvElemStart(_, "false", attr, _) =>
          if ((isTrackMode && dictDepth == 3) || (isPlaylistMode && dictDepth == 2)) {
            map.+=(key -> false)
          }

        case EvText(text) =>
          if (text.trim.length > 0) {
            if (dictDepth == 1) {
              if ("Tracks".equalsIgnoreCase(text)) {
                logger.debug("start Tracks")
                isTrackMode = true
                isPlaylistMode = false
              } else if ("Playlists".equalsIgnoreCase(text)) {
                logger.debug("start Playlists")

                isPlaylistMode = true
                isTrackMode = false
              }
            }
            if ((isTrackMode && dictDepth == 3) || (isPlaylistMode && dictDepth == 2)) {
              if (isKeyElement)
                key = text
              else if (isStringElement)
                map.+=(key -> text.trim)
              else if (isIntegerElement) {
                if (LibraryInfo.LongKeys.contains(key))
                  map.+=(key -> text.toLong)
                else
                  map.+=(key -> text.toInt)

              } else if (isBooleanElement)
                map.+=(key -> "true".equalsIgnoreCase(text))
              else if (isDateElement)
                map.+=(key -> DateTime.parse(text))
            } else if (isPlaylistMode && dictDepth == 3) {
              if (isIntegerElement)
                idSet.+=(text.toLong)
            }
          }

        case EvElemEnd(_, "key") => isKeyElement = false
        case EvElemEnd(_, "string") => isStringElement = false
        case EvElemEnd(_, "integer") => isIntegerElement = false
        case EvElemEnd(_, "boolean") => isBooleanElement = false
        case EvElemEnd(_, "date") => isDateElement = false

        case EvElemEnd(_, "dict") =>
          dictDepth = dictDepth - 1
          if (isTrackMode && dictDepth == 2) {
            callback(LibrarySection.TrackType, map.toMap)
          } else if (isPlaylistMode && dictDepth == 1) {
            map.+=("Tracks" -> idSet.toSet)
            callback(LibrarySection.PlaylistType, map.toMap)
          }

        case EvElemEnd(_, "array") => arrayDepth = arrayDepth - 1

        case _ =>

      }

    }
  }

}
