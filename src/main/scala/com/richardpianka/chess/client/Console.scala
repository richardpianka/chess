package com.richardpianka.chess.client

import com.codehale.logula.Logging
import com.richardpianka.chess.network.{Client, Server}
import org.apache.log4j.Level
import java.io.File

/**
 * A terminal-based chess client
 */
object Console extends Logging {
  def main(args: Array[String]) {
    initializeLogging()

    val client = new Client(Handler.distribution, "localhost", 1000)
    client.start()
  }

  private[this] def initializeLogging() {
    Logging.configure { log =>
      log.level = Level.INFO

      log.console.enabled = true
      log.console.threshold = Level.ALL

      log.file.enabled = true
      log.file.filename = "logs/client.log"
      log.file.maxSize = 10 * 1024
      log.file.retainedFiles = 5
    }
  }
}
