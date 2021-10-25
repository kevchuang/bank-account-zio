import zio.*

import java.io.IOException
import scala.annotation.tailrec

object BankAccountPrinter {

  val DEPOSIT_OPERATION = "Deposit"
  val WITHDRAW_OPERATION = "Withdraw"

  def printOperation(operation: Operation): ZIO[Has[Console], IOException, Unit] = {
    val operationType = if (operation.amount > 0)
      DEPOSIT_OPERATION
    else
      WITHDRAW_OPERATION

    Console.printLine(s"$operationType amount ${operation.amount} made at ${operation.date}.")
  }


  def printOperations(bankAccount: BankAccount): UIO[Unit] = {
    UIO.succeed(bankAccount.operations.foreach(printOperation))
  }

}
