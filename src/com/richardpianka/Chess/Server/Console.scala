package com.richardpianka.chess.server

import com.codehale.logula.Logging
import com.richardpianka.chess.commons.PostalService
import com.richardpianka.chess.network.{Server, Connection}
import org.apache.log4j.Level

/**
 * A terminal-based chess server
 */
object Console extends Logging {
  def main(args: Array[String]) {
    initializeLogging()

    val server = new Server(Handler.distribution)
    server.start()
  }

  private[this] def initializeLogging() {
    Logging.configure { log =>
      log.level = Level.INFO

      log.console.enabled = true
      log.console.threshold = Level.ALL

      log.file.enabled = true
      log.file.filename = "logs/server.log"
      log.file.maxSize = 10 * 1024
      log.file.retainedFiles = 5
    }
  }
}
