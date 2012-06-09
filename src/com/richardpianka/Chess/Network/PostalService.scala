package com.richardpianka.Chess.Network

import collection.mutable._
import com.richardpianka.Chess.Network.Contracts._

object PostalService {
  private[this] def classTrail = "com.richardpianka.Chess.Network.Contracts"

  private[this] def envelopeClass = getClass("Envelope")
  private[this] def envelopeMethods = envelopeClass.getDeclaredMethods.filter(_.getName.startsWith("has"))

  private[this] val directory = HashMap[Class[_], Function1[Envelope, Unit]]()

  private[this] def getClass(name: String) = Class.forName(classTrail + "$" + name)

  private[this] def messages(envelope: Envelope) = {
    envelopeMethods.filter(_.invoke(envelope).asInstanceOf[Boolean])
                   .map(_.getName.substring(3))
  }

  def address(packet: Class[_], handler: Function1[Envelope, Unit]) {
    directory += packet -> handler
  }

  def deliver(envelope: Envelope) {
    messages(envelope).map(getClass(_))
                      .filter(directory.contains(_))
                      .foreach(directory(_).apply(envelope))
  }
}
