package com.richardpianka.chess.common

import com.google.protobuf.AbstractMessageLite
import java.io.InputStream

class KeyedWarehouse[A, B <: AbstractMessageLite](file: String, private[this] val key: B => A, parse: InputStream => B) extends Warehouse[B](file, parse) {
  lazy val map = all.groupBy(key)
}
