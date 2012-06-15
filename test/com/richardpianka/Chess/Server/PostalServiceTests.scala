package com.richardpianka.chess.commons

import org.junit.Test
import com.richardpianka.chess.network.Contracts._

class PostalServiceTests {
  @Test
  def Handshake() {
    val service = new PostalService[String]

    val handshake = HandshakeRequest.newBuilder
                                    .setVersionMajor(1)
                                    .setVersionMinor(2)
                                    .setVersionRevision(3)
    val envelope = Envelope.newBuilder
                           .setHandshakeRequest(handshake)

    service.address(classOf[HandshakeRequest], handle)
    service.deliver("source", envelope.build)

    def handle(source: String, envelope: Envelope) {
      assert(source.equals("source"))

      val handshakeRequest = envelope.getHandshakeRequest
      assert(handshakeRequest.getVersionMajor == 1)
      assert(handshakeRequest.getVersionMinor == 2)
      assert(handshakeRequest.getVersionRevision == 3)
    }
  }
}
