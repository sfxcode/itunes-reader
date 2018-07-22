package com.sfxcode.music.itunes.xml

import com.sfxcode.music.itunes.xml.LibrarySection.LibrarySection

trait LibraryCallback {

  def callback(section: LibrarySection, map: Map[String, Any]): Unit
}
