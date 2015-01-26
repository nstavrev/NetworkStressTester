package bg.fmi_unisofia.fmi.corejava.NetworkStressTester;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierRunnable implements Runnable {

	private CyclicBarrier barrier = null;

	private StressTester tester;

	private int numRequests;

	public CyclicBarrierRunnable(CyclicBarrier barrier1, StressTester tester,
			int numRequests) {
		this.barrier = barrier1;
		this.tester = tester;
		this.numRequests = numRequests;
	}

	@Override
	public void run() {
		try {
			this.barrier.await();
			tester.makeRequest("java.voidland.org", 80, this.numRequests);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}

	}

}
