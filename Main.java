class Main {
  static SavingsAccount[] accounts;
  static int N = 10;
  // accounts: savings accounts
  // N: number of accounts

  static Thread person(int id) {
    return new Thread(() -> {
      try {
      int f = (int) (N * Math.random());
      SavingsAccount mine = accounts[id];
      SavingsAccount from = accounts[f];
      log(id+": ["+mine.balance+"] 100 from "+f);
      mine.transfer(100, from, false);
      log(id+": ["+mine.balance+"] done");
      } catch (InterruptedException e) {}
    });    
  }

  // Setup N accounts with random balance.
  static void setupAccounts(int kmax) {
    accounts = new SavingsAccount[N];
    for (int i=0; i<N; i++) {
      int k = (int) (kmax * Math.random());
      accounts[i] = new SavingsAccount();
      accounts[i].deposit(k);
    }
  }

  // Start all transfers (by person).
  static Thread[] startTransfers() {
    Thread[] t = new Thread[N];
    for (int i=0; i<N; i++)
      t[i] = person(i);
    for (int i=0; i<N; i++)
      t[i].start();
    return t;
  }

  // Deposit amount to all accounts.
  static void depositAll(int k) {
    for (int i=0; i<N; i++)
      accounts[i].deposit(k);
  }

  // Wait for all transfers to complete.
  static void awaitTransfers(Thread[] t) {
    try {
    for (int i=0; i<t.length; i++)
      t[i].join();
    } catch (InterruptedException e) {}
  }


  public static void main(String[] args) {
    log("Setting up accounts ...");
    setupAccounts(200);
    
    log("\nStarting transfers ...");
    Thread[] t = startTransfers();
    sleep(1000);

    log("\nBoss donates 1000 to all");
    depositAll(1000);
    awaitTransfers(t);
  }


  static void sleep(long ms) {
    try { Thread.sleep(ms); }
    catch (InterruptedException e) {}
  }

  static void log(String x) {
    System.out.println(x);
  }
}
