package errors

object BankAccountOperationErrors {
  val NEGATIVE_NUMBER_DEPOSIT_ERROR_MESSAGE = "Cannot deposit 0 or negative number on BankAccount"
  val NEGATIVE_NUMBER_WITHDRAW_ERROR_MESSAGE = "Cannot withdraw 0 or negative number from BankAccount"

  class NegativeNumberException(val message: String) extends Exception(message)
}
