package com.richardpianka.chess.network

import actors.Actor
import java.net._
import collection.mutable._
import com.codehale.logula.Logging
import com.richardpianka.chess.common.PostalService
import com.richardpianka.chess.network.Contracts.{EnvelopeOrBuilder, Envelope}

/**
 * A tcp server that uses [[com.richardpianka.chess.network.Contracts.Envelope]] as a transport.
 *
 * @param distribution The postal service instance for routing envelopes
 * @param port The port on which to accept connection
 */
class Server(val distribution: PostalService[Connection], val port: Int = 1000) extends Actor with Logging {
  val clients = new ListBuffer[Connection]

  /**
   * Begins listening for connections on its own thread
   */
  def act() {
    val sockets = new ServerSocket(port)

    log.info("Listening on %s:%d", sockets.getInetAddress.toString, sockets.getLocalPort)

    while (true) {
      val socket = sockets.accept()
      val connection = new Connection(socket, distribution)
      clients += connection
      connection.start()
    }
  }
}

/**
 * An incoming tcp client connection
 */
class Connection(socket: Socket, private[this] val distribution: PostalService[Connection]) extends Actor with Logging {
  private[this] val in = socket.getInputStream
  private[this] val out = socket.getOutputStream

  def act() {
    log.info("Connection accepted from %s", socket.getRemoteSocketAddress.toString)

    try {
      while (true) {
        val data = Envelope.parseDelimitedFrom(in)
        distribution.deliver(this, data)

        Envelope.newBuilder().build().writeTo(out)
      }
    } catch {
      case e: SocketException => log.info("Connection closed from %s", socket.getRemoteSocketAddress.toString)
    }
  }

  def send(envelope: Envelope) {
    envelope.writeTo(out)
  }
}