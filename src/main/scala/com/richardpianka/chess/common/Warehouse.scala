package com.richardpianka.chess.common

import com.google.protobuf.AbstractMessageLite
import collection.mutable.Set
import java.io._

abstract class Warehouse[A <: AbstractMessageLite](val file: String, private[this] val parse: InputStream => A) {
  private[this] val allItems = Set[A]()
  private[this] var _dirty = false

  def all = allItems.toIterable

  def dirty: Boolean = _dirty

  def load() {
    synchronized {
      if (!new File(file).exists()) {
        return
      }

      val in = new FileInputStream(new File(file))
      allItems.clear()

      Iterator.continually(parse(in)).takeWhile(_ != null).foreach(allItems += _)
    }
  }

  def save() {
    synchronized {
      if (!dirty) return
      val out = new FileOutputStream(new File(file))

      allItems.foreach(_.writeDelimitedTo(out))
      _dirty = false
    }
  }

  def add(item: A, save: Boolean = true) {
    synchronized {
      allItems += item
      _dirty = true
    }

    if (save) {
      this.save()
    }
  }

  def remove(item: A, save: Boolean = true) {
    synchronized {
      allItems -= item
      _dirty = true
    }

    if (save) {
      this.save()
    }
  }
}