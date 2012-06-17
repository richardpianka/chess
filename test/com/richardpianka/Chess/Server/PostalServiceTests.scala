package com.richardpianka.chess.commons

import org.junit.Test
import com.richardpianka.chess.network.Contracts._
import sun.reflect.Reflection

class PostalServiceTests {
  @Test
  def Handshake() {
    val service = new PostalService[String]

    val version = Version.newBuilder
                         .setVersionMajor(1)
                         .setVersionMinor(2)
                         .setVersionRevision(3)
    val handshake = HandshakeRequest.newBuilder
                                    .setVersion(version)
    val envelope = Envelope.newBuilder
                           .setHandshakeRequest(handshake)

    service.address(classOf[HandshakeRequest], handle)
    service.deliver("source", envelope.build)

    def handle(source: String, envelope: Envelope) {
      assert(source.equals("source"))

      val handshakeRequest = envelope.getHandshakeRequest
      assert(handshakeRequest.getVersion.getVersionMajor == 1)
      assert(handshakeRequest.getVersion.getVersionMinor == 2)
      assert(handshakeRequest.getVersion.getVersionRevision == 3)
    }
  }
}
