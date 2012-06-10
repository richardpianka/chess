package com.richardpianka.Chess.Network

import org.junit.Test
import com.richardpianka.Chess.Network.Contracts._

class PostalServiceTests {
  @Test
  def Handshake() {
    val handshake = HandshakeRequest.newBuilder
                                    .setVersionMajor(1)
                                    .setVersionMinor(2)
                                    .setVersionRevision(3)
    val envelope = Envelope.newBuilder
                           .setHandshakeRequest(handshake)

    PostalService.address(classOf[HandshakeRequest], handle)
    PostalService.deliver(envelope.build)

    def handle(envelope: Envelope) {
      val handshakeRequest = envelope.getHandshakeRequest
      assert(handshakeRequest.getVersionMajor == 1)
      assert(handshakeRequest.getVersionMinor == 2)
      assert(handshakeRequest.getVersionRevision == 3)
    }
  }
}
