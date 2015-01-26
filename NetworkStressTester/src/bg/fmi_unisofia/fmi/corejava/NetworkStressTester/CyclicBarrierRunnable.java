package bg.fmi_unisofia.fmi.corejava.NetworkStressTester;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierRunnable implements Runnable {
	
	private CyclicBarrier barrier = null;

	private StressTester tester;
	
	public CyclicBarrierRunnable(CyclicBarrier barrier1, StressTester tester){
		this.barrier = barrier1;
		this.tester = tester;
	}

	@Override
	public void run() {
		try {
            //Thread.sleep(1000);
			long t1 = System.currentTimeMillis();
//            System.out.println(Thread.currentThread().getName() +
//                                " waiting at barrier 1");
            this.barrier.await();
            tester.makeRequest("java.voidland.org", 80, 10);
            long t2 = System.currentTimeMillis();
            //System.out.println("Thread " + Thread.currentThread().getName() + " finished for " + (t2-t1));
            //Thread.sleep(1000);
//            System.out.println(Thread.currentThread().getName() +
//                                " waiting at barrier 2");
//            this.barrier2.await();
//
//            System.out.println(Thread.currentThread().getName() +
//                                " done!");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
		
	}
	
}
