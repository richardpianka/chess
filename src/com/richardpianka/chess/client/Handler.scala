package com.richardpianka.chess.client

import com.codehale.logula.Logging
import com.richardpianka.chess.common.PostalService
import com.richardpianka.chess.network.Client
import com.richardpianka.chess.network.Contracts._

object Handler extends Logging {
  val distribution = new PostalService[Client]

  distribution.address(classOf[HandshakeResponse], Handshake)

  def Success = Result.Success
  def Failure = Result.Failure

  /**
   *
   * @param client
   * @param envelope
   */
  def Handshake(client: Client, envelope: Envelope) {
    val builder = envelope.newBuilderForType
    val result = envelope.getHandshakeResponse.getResult

    result match {
      case Result.Success => {
        log.info("Handshake challenge passed")
        client.send(builder.setIdentifyRequest(IdentifyRequest.newBuilder()
                                                              .setUsername("guest")
                                                              .build)
                           .build)
      }
      case Result.Failure => log.error("Handshake challenge failed")
    }
  }
}
