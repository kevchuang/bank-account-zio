import errors.BankAccountOperationErrors._
import zio.test.Assertion.*
import zio.test.{DefaultRunnableSpec, assert, suite, assertM}

object BankAccountOperationSpec extends DefaultRunnableSpec {
  override def spec = suite("BankAccountOperationSpec")(

    test("Deposit amount in bank account") {
      for {
        bankAccount <- BankAccountOperation.depositAmount(BankAccount(id = 1), 1)
      } yield assert(bankAccount.amount)(equalTo(1))
    },
    test("Deposit multiple amount in bank account") {
      for {
        firstDeposit <- BankAccountOperation.depositAmount(BankAccount(id = 1), 1)
        secondDeposit <- BankAccountOperation.depositAmount(firstDeposit, 2)
        finalDeposit <- BankAccountOperation.depositAmount(secondDeposit, 3)
      } yield assert(finalDeposit)(equalTo(BankAccount(id = 1, amount = 6)))
    },
    test("Should throw NegativeNumberDepositException when deposit amount is equal to or less than 0") {
      assert(BankAccountOperation.depositAmount(BankAccount(id = 1), 0))(throws(isSubtype[NegativeNumberException](anything)))
      assert(BankAccountOperation.depositAmount(BankAccount(id = 1), -2))(throws(isSubtype[NegativeNumberException](anything)))
    }

  )
}
