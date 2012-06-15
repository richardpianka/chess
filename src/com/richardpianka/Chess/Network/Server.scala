package com.richardpianka.chess.network

import actors.Actor
import java.net._
import java.io._
import collection.mutable._
import com.richardpianka.chess.network.Contracts.Envelope
import com.codehale.logula.Logging
import com.richardpianka.chess.commons.PostalService

/**
 *
 */
class Server(val distribution: PostalService[Connection], val port: Int = 1000) extends Logging {
  val clients = new ListBuffer[Connection]

  /**
   *
   */
  def start() {
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
        val data = Envelope.parseFrom(in)
        distribution.deliver(this, data)

        Envelope.newBuilder().build().writeTo(out)
      }
    } catch {
      case e: SocketException => log.info("Connection closed from %s", socket.getRemoteSocketAddress.toString)
    }
  }
}