package com.richardpianka.chess.common

import collection.mutable._
import com.richardpianka.chess.network.Contracts._

/**
 * Routes Envelopes
 *
 * @tparam A The source which generates Envelope
 */
class PostalService[A] {
  private[this] def classTrail = "com.richardpianka.chess.network.Contracts"

  private[this] def envelopeClass = getClass("Envelope")

  private[this] def envelopeMethods = envelopeClass.getDeclaredMethods.filter(_.getName.startsWith("has"))

  private[this] val directory = HashMap[Class[_], Function2[A, Envelope, Unit]]()

  private[this] def getClass(name: String) = Class.forName(classTrail + "$" + name)

  private[this] def messages(envelope: Envelope) = {
    envelopeMethods.filter(_.invoke(envelope).asInstanceOf[Boolean])
      .map(_.getName.substring(3))
  }

  /**
   * Register a handler for a given message
   *
   * @param packet The message type
   * @param handler The handler function
   */
  def address(packet: Class[_], handler: Function2[A, Envelope, Unit]) {
    directory += packet -> handler
  }

  /**
   * Route a message to its handler
   *
   * @param source The source that generated the message
   * @param envelope The message to be delivered
   */
  def deliver(source: A, envelope: Envelope) {
    messages(envelope).map(getClass(_))
      .filter(directory.contains(_))
      .foreach(directory(_).apply(source, envelope))
  }
}
