import errors.BankAccountOperationErrors.*
import zio.test.Assertion.*
import zio.test.{DefaultRunnableSpec, assert, assertM, suite}

import java.time.LocalDate

object BankAccountOperationSpec extends DefaultRunnableSpec {
  override def spec = suite("BankAccountOperationSpec")(

    test("Deposit amount on bank account") {
      val date = LocalDate.now()
      for {
        bankAccount <- BankAccount(id = 1, Seq.empty).depositAmount(Operation(1, date))
      } yield
        assert(bankAccount)(equalTo(BankAccount(id = 1, operations = Seq(Operation(1, date)))))
    },

    test("Deposit multiple amount on bank account") {
      val date = LocalDate.of(2021, 01, 01)
      val secondDate = LocalDate.of(2021, 01, 02)
      val thirdDate = LocalDate.of(2021, 01, 03)

      for {
        firstDeposit <- BankAccount(id = 1, Seq.empty).depositAmount(Operation(1, date))
        secondDeposit <- firstDeposit.depositAmount(Operation(2, secondDate))
        finalDeposit <- secondDeposit.depositAmount(Operation(3, thirdDate))
      } yield assert(finalDeposit)(equalTo(BankAccount(id = 1, Seq(
        Operation(1, date),
        Operation(2, secondDate),
        Operation(3, thirdDate)
      ))))
    },

    test("Should throw NegativeNumberDepositException when deposit amount is equal to or less than 0") {
      assert(BankAccount(id = 1, Seq.empty).depositAmount(Operation(0, LocalDate.now)))(throws(isSubtype[NegativeNumberException](anything)))
      assert(BankAccount(id = 1, Seq.empty).depositAmount(Operation(-1, LocalDate.now)))(throws(isSubtype[NegativeNumberException](anything)))
    },

    test("Withdraw amount from bank account") {
      val date = LocalDate.now
      for {
        bankAccount <- BankAccount(id = 1, Seq.empty).withdrawAmount(Operation(1, date))
      } yield assert(bankAccount)(equalTo(BankAccount(id = 1, Seq(Operation(-1, date)))))
    },

    test("Withdraw multiple amount from bank account") {
      val date = LocalDate.of(2021, 01, 01)
      val secondDate = LocalDate.of(2021, 01, 02)
      val thirdDate = LocalDate.of(2021, 01, 03)

      for {
        firstDeposit <- BankAccount(id = 1, Seq.empty).withdrawAmount(Operation(1, date))
        secondDeposit <- firstDeposit.withdrawAmount(Operation(2, secondDate))
        finalDeposit <- secondDeposit.withdrawAmount(Operation(3, thirdDate))
      } yield assert(finalDeposit)(equalTo(BankAccount(id = 1, Seq(
        Operation(-1, date),
        Operation(-2, secondDate),
        Operation(-3, thirdDate)
      ))))
    },

    test("Should throw NegativeNumberDepositException when withdraw amount is equal to or less than 0") {
      assert(BankAccount(id = 1, Seq.empty).withdrawAmount(Operation(0, LocalDate.now)))(throws(isSubtype[NegativeNumberException](anything)))
      assert(BankAccount(id = 1, Seq.empty).withdrawAmount(Operation(-1, LocalDate.now)))(throws(isSubtype[NegativeNumberException](anything)))
    },

    test("Get balance from BankAccount") {
      for {
        balance <- BankAccount(id = 1, Seq.empty).getBalance
        secondBalance <- BankAccount(id = 2, Seq(Operation(2, LocalDate.now), Operation(4, LocalDate.now))).getBalance
        thirdBalance <- BankAccount(id = 2, Seq(Operation(-2, LocalDate.now), Operation(4, LocalDate.now))).getBalance
      } yield {
        assert(balance)(equalTo(0))
        assert(secondBalance)(equalTo(6))
        assert(thirdBalance)(equalTo(2))
      }
    }
  )
}
