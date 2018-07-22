package com.sfxcode.music.itunes.model

import com.typesafe.scalalogging.LazyLogging
import org.joda.time.DateTime

trait MapHandling extends LazyLogging {

  def stringValue(map: Map[String, Any], key: String): String = {
    if (map.contains(key))
      map(key) match {
        case s: String => s
        case v: Any => v.toString
        case _ => ""
      }
    else
      ""
  }

  def intValue(map: Map[String, Any], key: String): Int = {
    if (map.contains(key))
      map(key) match {
        case i: Int => i
        case v: Any => v.toString.toInt
        case _ => 0
      }
    else
      0
  }

  def longValue(map: Map[String, Any], key: String): Long = {
    if (map.contains(key))
      map(key) match {
        case l: Long => l
        case v: Any => v.toString.toInt
        case _ => 0
      }
    else
      0
  }

  def booleanValue(map: Map[String, Any], key: String): Boolean = {
    if (map.contains(key))
      map(key) match {
        case b: Boolean => b
        case v: Any => "true".equalsIgnoreCase(v.toString)
        case _ => false
      }
    else
      false
  }

  def dateValue(map: Map[String, Any], key: String): DateTime = {
    if (map.contains(key))
      map(key) match {
        case d: DateTime => d
        case v: Any => DateTime.parse(v.toString)
        case _ => null
      }
    else
      null
  }

  def setValue(map: Map[String, Any], key: String): Set[Long] = {
    if (map.contains(key))
      map(key).asInstanceOf[Set[Long]]
    else
      Set()
  }

}
