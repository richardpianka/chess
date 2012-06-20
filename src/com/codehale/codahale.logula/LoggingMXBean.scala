package com.codehale.logula

/**
 * A JMX MBean which allows logger levels to be set at runtime.
 *
 * @author coda
 */
trait LoggingMXBean {
  /**
   * Returns the logger's effective level as a string.
   */
  def getLoggerLevel(name: String): String

  /**
   * Sets the specified logger's level to the specific level.
   *
   * (If the level name is unknown, INFO is used as a default.)
   */
  def setLoggerLevel(name: String, level: String): String
}
