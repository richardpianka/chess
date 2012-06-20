package com.codehale.logula

import org.apache.log4j.spi.LoggingEvent
import org.apache.log4j.EnhancedPatternLayout

/**
 * A single-line formatter which logs messages in the following format:
 * <pre>
 * LEVEL  [yyyy-MM-dd HH:mm:ss,SSS] LoggerName: This is the message
 * </pre>
 * Timestamps are always in UTC.
 *
 * Associated exception stack traces are logged on the following lines, preceded
 * by an exclamation point. This allows for easy mechanical extraction of stack
 * traces from log files via standard tools like grep
 * (e.g., {@code tail -f codahale.logula.log | grep '^\!' -B 1}).
 */
class Formatter extends EnhancedPatternLayout("%-5p [%d{ISO8601}{UTC}] %c: %m\n") {
  override def format(event: LoggingEvent) = {
    val msg = new StringBuilder
    msg.append(super.format(event))
    if (event.getThrowableInformation != null) {
      for (line <- event.getThrowableInformation.getThrowableStrRep) {
        msg.append("! ")
        msg.append(line)
        msg.append("\n")
      }
    }
    msg.toString
  }

  override def ignoresThrowable = false
}
