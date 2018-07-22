package com.sfxcode.music.itunes.xml

import com.sfxcode.music.itunes.xml.LibrarySection._
import com.typesafe.scalalogging.LazyLogging

class SimpleLoggingCallback extends LibraryCallback with LazyLogging {

  def callback(section: LibrarySection, map: Map[String, Any]): Unit = {
    val name = map.getOrElse("Name", "")
    if (PlaylistType == section) {
      val tracks = map.getOrElse("Tracks", Set())
      logger.info("%s (%s): %s".format(section, name, tracks))
    } else {
      logger.info("%s : %s".format(section, name))
    }
  }

}
