package com.richardpianka
package Chess.Common

/**
 * Directed acyclic graph
 *
 * @param value The payload for this node
 * @param children The children (or none) of this node
 * @tparam A The common type of the graph's payload
 */
class Node[A](val value: A, val children: Iterable[Node[A]] = Seq()) {
  /**
   * Whether or not this node is at the end of the tree
   *
   * @return Whether or not this node is at the end of the tree
   */
  def isLeaf = children.isEmpty
}
