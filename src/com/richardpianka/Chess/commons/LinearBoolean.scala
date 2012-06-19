package com.richardpianka.chess.commons

/**
 * A boolean that can only be moved from false to true, and never back again
 */
final class LinearBoolean {
  private[this] var _value: Boolean = false

  /**
   * The current value of the boolean
   *
   * @return The current value of the boolean
   */
  def value = _value

  /**
   * Sets the boolean to true
   */
  def actuate() {
    _value = true
  }
}

object LinearBoolean {
  def apply() = new LinearBoolean

  implicit def linearBooleanToBoolean(linearBoolean: LinearBoolean): Boolean = linearBoolean.value
}