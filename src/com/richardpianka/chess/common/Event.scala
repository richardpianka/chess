package com.richardpianka.chess.common

/**
 * A single-parameter delegate
 *
 * Source: http://blog.omega-prime.co.uk/?p=21
 * (modified ever so slightly)
 *
 * @tparam A The event argument object
 */
final case class Event[A]() {
  private var invocationList : List[A => Unit] = Nil

  def apply(args: A) {
    for (invoker <- invocationList)
      invoker(args)
  }

  def +=(invoker: A => Unit) {
    invocationList = invoker :: invocationList
  }

  def -=(invoker: A => Unit) {
    invocationList = invocationList filterNot (_ == invoker)
  }
}