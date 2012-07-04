package com.richardpianka.chess.server

import com.codehale.logula.Logging
import com.richardpianka.chess.network.Server
import org.apache.log4j.Level
import java.io.File

/**
 * A terminal-based chess server
 */
object Console extends Logging {
  def main(args: Array[String]) {
    initializeLogging()
    createDirectories()

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

  private[this] def createDirectories() {
    new File("data").mkdir
  }
}
