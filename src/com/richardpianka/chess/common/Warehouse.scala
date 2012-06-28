package com.richardpianka.chess.common

import com.google.protobuf.AbstractMessageLite

abstract class Warehouse[A <: AbstractMessageLite] {
  def apply()
}