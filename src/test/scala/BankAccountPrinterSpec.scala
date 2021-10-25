import zio.test.Assertion.*
import zio.test.{DefaultRunnableSpec, assert}
import zio.test.environment.TestConsole

import java.time.LocalDate

object BankAccountPrinterSpec extends DefaultRunnableSpec {
  override def spec = suite("BankAccountPrinterSpec")(

    test("Print a statement") {
      val statement = "Deposit amount 10 made at 2021-01-01."
      for {
        _ <- BankAccountPrinter.printOperation(Operation(amount = 10, date = LocalDate.of(2021, 01, 01)))
        output <- TestConsole.output
      } yield (assert(output)(equalTo(Vector(statement + '\n'))))
    },

  )

}
