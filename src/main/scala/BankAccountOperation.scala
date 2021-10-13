import errors.BankAccountOperationErrors._
import zio.*

object BankAccountOperation {

  def depositAmount(account: BankAccount, amount: Long): Task[BankAccount] =
    if (amount <= 0)
      throw new NegativeNumberException(NEGATIVE_NUMBER_DEPOSIT_ERROR_MESSAGE)
    else
      Task.succeed(account.copy(id = account.id, amount = account.amount + amount))


}
