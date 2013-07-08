package com.richardpianka.chess.common

import collection.mutable

/**
 * A keyed storage mechanism
 *
 * @tparam A The key type
 * @tparam B The stored item type
 */
abstract class WarehouseLite[A, B] {
  private[this] val allItems = new mutable.HashSet[B]
                               with mutable.SynchronizedSet[B]

  private[this] def map = all.map(x => keyNormalize(key(x)) -> x).toMap

  /**
   * Defines the function for producing a key from an item
   *
   * @param item The item to be keyed
   * @return A key for the item
   */
  protected def key(item: B): A

  /**
   * A function for ensuring keys fit a standard pattern.  This function
   * should be idempotent.
   *
   * For example, all strings to a specific casing.
   *
   * @param id The key to normalize
   * @return The true key
   */
  protected def keyNormalize(id: A) = id

  /**
   * Adds an item to the set of all items
   *
   * @param item The item to add
   * @return The item
   */
  protected def add(item: B) = {
    allItems += item
    item
  }

  /**
   * Removes an item from the set of all items
   *
   * @param item The item to remove
   * @return The item
   */
  protected def remove(item: B) = {
    allItems -= item
    item
  }

  /**
   * The set of all items
   *
   * @return The set of all items
   */
  def all = allItems.toIterable

  /**
   * Gets an item by its key
   *
   * @param id The key
   * @return The item
   */
  def apply(id: A) = map(keyNormalize(id))

  /**
   * Whether or not an item exists for a certain key
   *
   * @param id The key
   * @return Whether or not an item exists for a certain key
   */
  def exists(id: A) = map.contains(keyNormalize(id))
}
