package com.richardpianka
package Chess.Game

/**
 * Represents a chess move
 *
 * @param piece The moving piece
 * @param start The starting location of the piece
 * @param end The destination of the piece
 */
final case class Move(piece: Piece, start: Coordinate, end: Coordinate)