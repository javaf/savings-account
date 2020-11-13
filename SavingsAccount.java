import java.util.concurrent.locks.*;

// A savings account is used to store salary/savings in
// a bank. This is a concurrent object (RAM) based
// account. For educational purposes only.
// 
// withdraw() from an account blocks until the account
// has the necessary balance.
// transfer() from an account also blocks until the
// source account has the necessary balance.
// Preferred withdrawls are performed, if waiting.

class SavingsAccount {
  Lock lock;
  Condition hasBalance;
  int balance;
  // lock: common lock
  // hasBalance: signalled when account has balance
  // balance: amount of currency in account

  public SavingsAccount() {
    lock = new ReentrantLock();
  }

  // 1. Acquire common lock.
  // 2. Wait until there is no other gender.
  // 3. Increment my gender count.
  // 4. Release common lock.
}
