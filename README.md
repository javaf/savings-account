A savings account is used to store salary/savings in
a bank. This is a concurrent object (RAM) based
account. For educational purposes only.

`withdraw()` from an account blocks until the account
has the necessary balance.
`transfer()` from an account also blocks until the
source account has the necessary balance.
Preferred withdrawls are performed, if waiting.

Using 2 separate conditions avoids unnecessary
waking up of non-preferred withdrawls when preferred
withdrals are present.

> **Course**: [Concurrent Data Structures], Monsoon 2020\
> **Taught by**: Prof. Govindarajulu Regeti

[Concurrent Data Structures]: https://github.com/iiithf/concurrent-data-structures

```java
deposit(k):
1. Acquire common lock.
2. Deposit to account.
3. Signal that account has currency.
4. Release common lock.
```

```java
withdraw(k, pref):
1. Acquire common lock.
2. Update pending preferred withdrawls.
3. Wait until sufficient balance available.
4. Withdraw from account.
5. If non-zero balance, then signal it.
6. Update pending preferred withdrawls.
7. Release common lock.
```

```java
transfer(k, from, pref):
1. Withdraw from source account.
2. Deposit to this account.
```

```bash
## OUTPUT
Setting up accounts ...

Starting transfers ...
6: [21] 100 from 3
4: [55] 100 from 5
7: [145] 100 from 1
0: [195] 100 from 9
5: [199] 100 from 6
2: [68] 100 from 8
1: [36] 100 from 6
3: [39] 100 from 0
9: [22] 100 from 3
8: [73] 100 from 2
3: [139] done
0: [195] done
4: [155] done
9: [122] done

Boss donates 1000 to all
5: [1199] done
2: [1068] done
1: [1036] done
8: [1173] done
7: [1245] done
6: [1021] done
```

See [SavingsAccount.java] for code, [Main.java] for test, and [repl.it] for output.

[SavingsAccount.java]: https://repl.it/@wolfram77/savings-account#SavingsAccount.java
[Main.java]: https://repl.it/@wolfram77/savings-account#Main.java
[repl.it]: https://savings-account.wolfram77.repl.run


### references

- [The Art of Multiprocessor Programming :: Maurice Herlihy, Nir Shavit](https://dl.acm.org/doi/book/10.5555/2385452)

![](https://ga-beacon.deno.dev/G-G1E8HNDZYY:v51jklKGTLmC3LAZ4rJbIQ/github.com/javaf/savings-account)
