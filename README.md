A Bathroom Lock allows N genders (thread types) to
access a common bathroom (critical section) such that
different genders do not clash.

Bathroom Lock allows threads of the same type
(gender) to enter at the same time, but disallows
different types of threads to occupy the
critical section (bathroom) at the same time.

```java
lock():
1. Acquire common lock.
2. Wait until there is no other gender.
3. Increment my gender count.
4. Release common lock.
```

```java
unlock():
1. Acquire common lock.
2. Decrement my gender count.
3. If my gender cleared, signal others.
4. Release common lock.
```

```bash
## OUTPUT
Starting 100 unsafe males ...
Starting 100 unsafe females ...
F101: saw 5 males
F108: saw 2 males
F109: saw 2 males
F107: saw 2 males
F108: saw 2 males
F110: saw 2 males
F109: saw 2 males
F101: saw 2 males
F110: saw 2 males
F103: saw 2 males
F107: saw 2 males
F106: saw 2 males
F103: saw 2 males
F104: saw 2 males
F106: saw 2 males
F100: saw 5 males
F104: saw 2 males
F100: saw 2 males
F105: saw 2 males
F102: saw 2 males
M99: saw 2 females
F105: saw 2 males
F102: saw 2 males
F112: saw 1 males
Clashes occurred: 24

Starting 100 safe males ...
Starting 100 safe females ...
Clashes occurred: 0
```

See [BathroomLock.java] for code, [Main.java] for test, and [repl.it] for output.

[BathroomLock.java]: https://repl.it/@wolfram77/bathroom-lock#BathroomLock.java
[Main.java]: https://repl.it/@wolfram77/bathroom-lock#Main.java
[repl.it]: https://bathroom-lock.wolfram77.repl.run


### references

- [The Art of Multiprocessor Programming :: Maurice Herlihy, Nir Shavit](https://dl.acm.org/doi/book/10.5555/2385452)
