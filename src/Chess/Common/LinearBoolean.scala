package Chess.Common

final class LinearBoolean {
  private[this] var _value: Boolean = false

  def value = _value

  def actuate() = _value = true
}

object LinearBoolean {
  def apply() = new LinearBoolean

  implicit def linearBooleanToBoolean(linearBoolean: LinearBoolean): Boolean = linearBoolean.value
}