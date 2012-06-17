package com.richardpianka.chess.server

import com.richardpianka.chess.network.Connection
import com.richardpianka.chess.commons.PostalService
import com.richardpianka.chess.network.Contracts._

object Handler {
  val distribution = new PostalService[Connection]

  distribution.address(classOf[HandshakeRequest], Handshake)
  distribution.address(classOf[IdentifyRequest], Identify)
  distribution.address(classOf[JoinChatRequest], JoinChat)
  distribution.address(classOf[JoinRoomRequest], JoinRoom)

  def Handshake(connection: Connection, envelope: Envelope) {
    val request = envelope.getHandshakeRequest

  }

  def Identify(connection: Connection, envelope: Envelope) {

  }

  def JoinChat(connection: Connection, envelope: Envelope) {

  }

  def JoinRoom(connection: Connection, envelope: Envelope) {

  }
}
