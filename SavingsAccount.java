import java.util.concurrent.locks.*;

// A Bathroom Lock allows N genders (thread types) to
// access a common bathroom (critical section) such that
// different genders do not clash.
// 
// Bathroom Lock allows threads of the same type
// (gender) to enter at the same time, but disallows
// different types of threads to occupy the
// critical section (bathroom) at the same time.

class BathroomLock {
  Lock lock;
  Condition condition;
  Lock[] genderLocks;
  int[] counts;
  // lock: common lock
  // condition: no-one in bathroom
  // genderLocks: for ith gender to use bathroom
  // counts: no. of ith gender in bathroom

  public BathroomLock(int types) {
    lock = new ReentrantLock();
    condition = lock.newCondition();
    genderLocks = new GenderLock[types];
    for (int i=0; i<types; i++)
      genderLocks[i] = new GenderLock(i);
    counts = new int[types];
  }

  public Lock genderLock(int i) {
    return genderLocks[i];
  }
  

  class GenderLock extends AbstractLock {
    int type;
    // type: gender type

    public GenderLock(int i) {
      type = i;
    }

    // 1. Acquire common lock.
    // 2. Wait until there is no other gender.
    // 3. Increment my gender count.
    // 4. Release common lock.
    @Override
    public void lock() {
      lock.lock(); // 1
      try {
        while (!oneGender()) // 2
          condition.await(); // 2
        counts[type]++; // 3
      }
      catch (InterruptedException e) {}
      finally { lock.unlock(); } // 4
    }
  
    // 1. Acquire common lock.
    // 2. Decrement my gender count.
    // 3. If my gender cleared, signal others.
    // 4. Release common lock.
    @Override
    public void unlock() {
      lock.lock(); // 1
      counts[type]--; // 2
      if (counts[type] == 0)   // 3
        condition.signalAll(); // 3
      lock.unlock(); // 4
    }

    private boolean oneGender() {
      for (int i=0; i<counts.length; i++)
        if (i!=type && counts[i]>0) return false;
      return true;
    }
  }
}
