package com.richardpianka.chess.server

import com.richardpianka.chess.network.Connection
import com.richardpianka.chess.common.PostalService
import com.richardpianka.chess.network.Contracts._
import com.codehale.logula.Logging
import com.google.protobuf.AbstractMessageLite
import storage.{AccountException, Accounts}

object Handler extends Logging {
  val distribution = new PostalService[Connection]

  distribution.address(classOf[HandshakeRequest], Handshake)
  distribution.address(classOf[IdentifyRequest], Identify)
  distribution.address(classOf[CreateAccountRequest], CreateAccount)
  distribution.address(classOf[ChangePasswordRequest], ChangePassword)
  distribution.address(classOf[JoinChatRequest], JoinChat)
  distribution.address(classOf[JoinRoomRequest], JoinRoom)

  def Success = Result.Success
  def Failure = Result.Failure

  /**
   *
   * @param connection
   * @param envelope
   */
  def Handshake(connection: Connection, envelope: Envelope) {
    val builder = envelope.newBuilderForType

    val version = envelope.getHandshakeRequest.getVersion
    val validate = Version.newBuilder().build

    val result = if (version.getVersionMajor == validate.getVersionMajor &&
                     version.getVersionMinor == validate.getVersionMinor &&
                     version.getVersionRevision == validate.getVersionRevision) {
                   log.info("Handshake challenge passed")
                   Success
                 } else {
                   log.info("Handshake challenge failed")
                   Failure
                 }

    connection.send(builder.setHandshakeResponse(HandshakeResponse.newBuilder.setResult(result)).build)
  }

  /**
   *
   * @param connection
   * @param envelope
   */
  def Identify(connection: Connection, envelope: Envelope) {
    val builder = envelope.newBuilderForType
    val request = envelope.getIdentifyRequest

    def pass() {
      log.info("Identify challenge passed")
      connection.send(builder.setIdentifyResponse(IdentifyResponse.newBuilder()
                                                                  .setResult(Result.Success)
                                                                  .build).build)
    }

    def fail(message: String) {
      log.info("Identify challenge failed: %s", message)
      connection.send(builder.setIdentifyResponse(IdentifyResponse.newBuilder()
                                                                  .setResult(Result.Failure)
                                                                  .setMessage(message)
                                                                  .build).build)
    }

    val username = request.getUsername

    try {
      // for accounts
      if (request.hasPassword) {
        val password = request.getPassword

        if (Accounts.matches(username, password)) {
          //TODO: check for existing sessions
          pass()
        } else {
          fail("That password is incorrect")
        }
      // for anonymous users
      } else {
        if (Accounts.exists(username)) {
          fail("That account is already in use")
        } else {
          // log in anonymously
          pass()
        }
      }
    } catch {
      case ae: AccountException => fail(ae.getMessage)
    }
  }

  /**
   *
   * @param connection
   * @param envelope
   */
  def CreateAccount(connection: Connection, envelope: Envelope) {
    val builder = envelope.newBuilderForType
    val request = envelope.getCreateAccountRequest

    def pass() {
      log.info("Account creation successful")
      connection.send(builder.setCreateAccountResponse(CreateAccountResponse.newBuilder()
                                                                            .setResult(Result.Success)
                                                                            .build).build)
    }

    def fail(message: String) {
      log.info("Account creation failed: %s", message)
      connection.send(builder.setCreateAccountResponse(CreateAccountResponse.newBuilder()
                                                                            .setResult(Result.Failure)
                                                                            .setMessage(message)
                                                                            .build).build)
    }

    if (Accounts.exists(request.getUsername)) {
      fail("That account already exists")
    } else {
      try {
        Accounts.create(request.getUsername, request.getPassword)
        pass()
      } catch {
        case ae: AccountException => fail(ae.getMessage)
      }
    }
  }

  /**
   *
   * @param connection
   * @param envelope
   */
  def ChangePassword(connection: Connection, envelope: Envelope) {

  }

  /**
   *
   * @param connection
   * @param envelope
   */
  def JoinChat(connection: Connection, envelope: Envelope) {

  }

  /**
   *
   * @param connection
   * @param envelope
   */
  def JoinRoom(connection: Connection, envelope: Envelope) {

  }
}
