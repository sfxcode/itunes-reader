package com.sfxcode.music.itunes.xml

import org.specs2.mutable.Specification

class PullParserSpec extends Specification {
  val path: String = getClass.getClassLoader.getResource("itunes.xml").getPath

  "PullParser" should {

    "parse Resource File" in {
      PullParser.parseFile(path) must beTrue
    }
  }
}

