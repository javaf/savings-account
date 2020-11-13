import java.util.concurrent.locks.*;
import java.util.concurrent.atomic.*;


class Main {
  static BathroomLock lock;
  static AtomicInteger males, females, clashes;
  static int ML = 100, FM = 100;
  static int CS = 2;
  // males: number of males in bathroom
  // females: number of females in bathroom
  // clashes: times males & females were together
  // ML: number of males
  // FM: number of females
  // CS: checks per person (thread)


  static Thread male(int id, boolean safe) {
    return new Thread(() -> {
      if (safe) lock.genderLock(0).lock();
      males.incrementAndGet();

      for (int i=0; i<CS; i++) {
        sleep(1);
        int f = females.get();
        if (f == 0) continue;
        clashes.incrementAndGet();
        log("M"+id+": saw "+f+" females");
      }
      
      males.decrementAndGet();
      if (safe) lock.genderLock(0).unlock();
    });
  }


  static Thread female(int id, boolean safe) {
    return new Thread(() -> {
      if (safe) lock.genderLock(1).lock();
      females.incrementAndGet();
      
      for (int i=0; i<CS; i++) {
        sleep(1);
        int m = males.get();
        if (m == 0) continue;
        clashes.incrementAndGet();
        log("F"+id+": saw "+m+" males");
      }
      
      females.decrementAndGet();
      if (safe) lock.genderLock(1).unlock();
    });
  }


  // Tests to see if males and females entered
  // bathroom separately.
  static void testThreads(boolean safe) {
    String type = safe? "safe" : "unsafe";
    log("Starting "+ML+" "+type+" males ...");
    log("Starting "+FM+" "+type+" females ...");

    males.set(0);
    females.set(0);
    clashes.set(0);

    Thread[] t = new Thread[ML+FM];
    for (int i=0; i<ML+FM; i++) {
      t[i] = i<ML? male(i, safe) : female(i, safe);
      t[i].start();
    }

    try {
    for (int i=0; i<ML+FM; i++)
      t[i].join();
    }
    catch(InterruptedException e) {}
    log("Clashes occurred: "+clashes.get()+"\n");
  }


  public static void main(String[] args) {
    lock = new BathroomLock(2);
    males = new AtomicInteger(0);
    females = new AtomicInteger(0);
    clashes = new AtomicInteger(0);
    
    testThreads(false);
    testThreads(true);
  }


  static void sleep(long ms) {
    try { Thread.sleep(ms); }
    catch (InterruptedException e) {}
  }

  static void log(String x) {
    System.out.println(x);
  }
}
