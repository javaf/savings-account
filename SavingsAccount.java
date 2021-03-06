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
// 
// Note: Using 2 separate conditions avoids unnecessary
// waking up of non-preferred withdrawls when preferred
// withdrals are present.

class SavingsAccount {
  Lock lock;
  Condition hasNormal;
  Condition hasPreferred;
  int balance;
  int preferred;
  // lock: common lock
  // hasNormal: account has balance (no preferred deposits)
  // hasPreferred: account has balance (some preferred)
  // balance: amount of currency in account
  // preferred: pending preferred withdrawls


  public SavingsAccount() {
    lock = new ReentrantLock();
    hasNormal = lock.newCondition();
    hasPreferred = lock.newCondition();
    balance = 0;
    preferred = 0;
  }


  // 1. Acquire common lock.
  // 2. Deposit to account.
  // 3. Signal that account has currency.
  // 4. Release common lock.
  public void deposit(int k) {
    lock.lock();   // 1
    balance += k;  // 2
    signal();      // 3
    lock.unlock(); // 4
  }


  // 1. Acquire common lock.
  // 2. Update pending preferred withdrawls.
  // 3. Wait until sufficient balance available.
  // 4. Withdraw from account.
  // 5. If non-zero balance, then signal it.
  // 6. Update pending preferred withdrawls.
  // 7. Release common lock.
  public void withdraw(int k, boolean pref)
  throws InterruptedException {
    lock.lock();           // 1
    if (pref) preferred++; // 2

    while (balance<k) await(pref); // 3
    balance -= k;                  // 4
    
    if (balance>0) signal(); // 5
    if (pref) preferred--;   // 6
    lock.unlock();           // 7
  }


  // 1. Withdraw from source account.
  // 2. Deposit to this account.
  public void transfer(int k,
  SavingsAccount from, boolean pref)
  throws InterruptedException {
    from.withdraw(k, pref); // 1
    deposit(k);             // 2
  }


  private void signal() {
    if (preferred>0) hasPreferred.signal();
    else hasNormal.signal();
  }

  private void await(boolean pref)
  throws InterruptedException {
    if (pref) hasPreferred.await();
    else hasNormal.await();
  }
}
