package com.richardpianka.chess.network

import actors.Actor
import java.net._
import com.codehale.logula.Logging
import com.richardpianka.chess.common.PostalService
import com.richardpianka.chess.network.Contracts._

class Client(val distribution: PostalService[Client], val server: String, val port: Short) extends Actor with Logging {
  private[this] def in = socket.getInputStream
  private[this] def out = socket.getOutputStream

  val socket = new Socket

  def act() {
    try {
      socket.connect(new InetSocketAddress(server, port))
      log.info("Connected!")

      send(Envelope.newBuilder()
                   .setHandshakeRequest(HandshakeRequest.newBuilder
                                                        .setVersion(Version.newBuilder
                                                                           .build)
                                                        .build)
                   .build)

      while (true) {
        val data = Envelope.parseDelimitedFrom(in)
        distribution.deliver(this, data)
      }
    } catch {
      case e: Throwable => {
        log.error(e, "Error connecting to host")
      }
    }
  }

  def send(envelope: Envelope) {
    envelope.writeDelimitedTo(out)
    out.flush()
  }
}