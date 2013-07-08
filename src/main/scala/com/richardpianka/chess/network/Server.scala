package com.richardpianka.chess.network

import actors.Actor
import java.net._
import collection.mutable._
import com.codehale.logula.Logging
import com.richardpianka.chess.common.PostalService
import com.richardpianka.chess.network.Contracts.Envelope
import com.richardpianka.chess.server.state.Sessions

/**
 * A tcp server that uses [[com.richardpianka.chess.network.Contracts.Envelope]] as a transport.
 *
 * @param distribution The postal service instance for routing envelopes
 * @param port The port on which to accept connection
 */
class Server(val distribution: PostalService[ServerConnection], val port: Int = 1000) extends Actor with Logging {
  val clients = new ListBuffer[ServerConnection]

  /**
   * Begins listening for connections on its own thread
   */
  def act() {
    val sockets = new ServerSocket(port)

    log.info("Listening on %s:%d", sockets.getInetAddress.toString, sockets.getLocalPort)

    while (true) {
      val socket = sockets.accept()
      val connection = new ServerConnection(socket, distribution)
      clients += connection
      connection.start()
    }
  }
}

/**
 * An incoming tcp client connection
 */
class ServerConnection(socket: Socket, private[this] val distribution: PostalService[ServerConnection]) extends Actor with Logging {
  private[this] def in = socket.getInputStream
  private[this] def out = socket.getOutputStream

  val session = Sessions.create(this)

  def act() {
    log.info("Connection accepted from %s", socket.getRemoteSocketAddress.toString)

    try {
      while (true) {
        val data = Envelope.parseDelimitedFrom(in)
        distribution.deliver(this, data)
      }
    } catch {
      case e: SocketException => {
        log.info("Connection closed from %s", socket.getRemoteSocketAddress.toString)
        Sessions.kill(session.connection)
      }
    }
  }

  def send(envelope: Envelope) {
    envelope.writeDelimitedTo(out)
    out.flush()
  }
}