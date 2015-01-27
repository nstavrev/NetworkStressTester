package bg.uni_sofia.fmi.corejava.NetworkStressTester;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierRunnable implements Runnable {

	private CyclicBarrier barrier;

	private StressTester tester;

	private int numRequests;

	/**
	 * 
	 * @param barrier
	 * @param tester
	 * @param numRequests - The number of requests that each thread will make
	 */
	public CyclicBarrierRunnable(CyclicBarrier barrier, StressTester tester, int numRequests) {
		this.barrier = barrier;
		this.tester = tester;
		this.numRequests = numRequests;
	}

	@Override
	public void run() {
		try {
			this.barrier.await();
			tester.makeRequests(this.numRequests);
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
