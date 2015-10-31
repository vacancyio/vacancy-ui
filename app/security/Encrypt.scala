package security

import org.mindrot.jbcrypt.BCrypt

object Encrypt {

  /**
   * Encrypts a password
   * @param password A plan text password to be encrypted.
   */
  def encryptPassword(password: String): String =
    BCrypt.hashpw(password, BCrypt.gensalt(12))

  /**
   * @param password A user password
   * @param hash The database hashed password
   *
   * @return Boolean true if password and hash match else false
   */
  def checkPassword(password: String, hash: String): Boolean =
    BCrypt.checkpw(password, hash)
}
