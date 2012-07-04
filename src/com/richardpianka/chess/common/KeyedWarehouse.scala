package com.richardpianka.chess.common

import com.google.protobuf.AbstractMessageLite
import java.io.InputStream

class KeyedWarehouse[A, B <: AbstractMessageLite](file: String, private[this] val key: B => A, parse: InputStream => B) extends Warehouse[B](file, parse) {
  private[this] def map = all.map(x => key(x) -> x).toMap

  def apply(key: A) = map(key)

  def contains(key: A) = map.contains(key)

  def update(key: A, value: B, save: Boolean = true) {
    synchronized {
      remove(this(key))
      add(value)
    }

    if (save) {
      this.save()
    }
  }
}
