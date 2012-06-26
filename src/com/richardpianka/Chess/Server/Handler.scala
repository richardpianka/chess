package com.richardpianka.chess.server

import com.richardpianka.chess.network.Connection
import com.richardpianka.chess.commons.PostalService
import com.richardpianka.chess.network.Contracts._
import com.codehale.logula.Logging

object Handler extends Logging {
  val distribution = new PostalService[Connection]

  distribution.address(classOf[HandshakeRequest], Handshake)
  distribution.address(classOf[IdentifyRequest], Identify)
  distribution.address(classOf[JoinChatRequest], JoinChat)
  distribution.address(classOf[JoinRoomRequest], JoinRoom)

  def Success = Result.Success
  def Failure = Result.Failure

  def Handshake(connection: Connection, envelope: Envelope) {
    val builder = envelope.newBuilderForType

    val version = envelope.getHandshakeRequest.getVersion
    val validate = Version.newBuilder().build

    val result = if (version.getVersionMajor == validate.getVersionMajor &&
                     version.getVersionMinor == validate.getVersionMinor &&
                     version.getVersionRevision == validate.getVersionRevision) {
                   log.info("Handshake challenge passed")
                   Success
                 } else {
                   log.info("Handshake challenge failed")
                   Failure
                 }

    connection.send(builder.setHandshakeResponse(HandshakeResponse.newBuilder.setResult(result)).build)
  }

  def Identify(connection: Connection, envelope: Envelope) {

  }

  def JoinChat(connection: Connection, envelope: Envelope) {

  }

  def JoinRoom(connection: Connection, envelope: Envelope) {

  }
}
