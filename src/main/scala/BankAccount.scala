import errors.BankAccountOperationErrors._
import zio._
import java.time.LocalDate

case class Operation(
                      amount: Long,
                      date: LocalDate
                    )

case class BankAccount(
                        id: Long,
                        operations: Seq[Operation]
                      ) {

  def getBalance: UIO[Long] = UIO.succeed(operations.foldLeft(0.toLong)((balance, operation) => balance + operation.amount))

  def depositAmount(operation: Operation): Task[BankAccount] =
    if (operation.amount <= 0)
      throw new NegativeNumberException(NEGATIVE_NUMBER_DEPOSIT_ERROR_MESSAGE)
    else
      Task.succeed(this.copy(id = id, operations.:+(Operation(operation.amount, operation.date))))

  def withdrawAmount(operation: Operation): Task[BankAccount] =
    if (operation.amount <= 0)
      throw new NegativeNumberException(NEGATIVE_NUMBER_WITHDRAW_ERROR_MESSAGE)
    else
      Task.succeed(this.copy(id = id, operations.:+(Operation(-operation.amount, operation.date))))

}
