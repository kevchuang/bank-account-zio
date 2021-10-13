package errors

object BankAccountOperationErrors {
  val NEGATIVE_NUMBER_DEPOSIT_ERROR_MESSAGE = "Cannot deposit 0 or negative number on BankAccount"

  class NegativeNumberException(val message: String) extends Exception(message)
}
