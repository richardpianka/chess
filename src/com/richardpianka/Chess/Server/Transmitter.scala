package com.richardpianka.chess.server

import com.richardpianka.chess.network.Contracts._


class Transmitter {
  def builder = Envelope.newBuilder

  def Handshake(result: Result) = {
    HandshakeResponse.newBuilder
                     .setResult(result)
                     .build
  }
}
