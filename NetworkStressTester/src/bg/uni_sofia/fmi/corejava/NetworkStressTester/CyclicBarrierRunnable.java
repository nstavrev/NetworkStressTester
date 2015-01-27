package bg.uni_sofia.fmi.corejava.NetworkStressTester;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierRunnable implements Runnable {

	private CyclicBarrier barrier;

	private StressTester tester;

	private int numRequests;

	private int port;

	private String host;
	
	/**
	 * 
	 * @param barrier
	 * @param tester
	 * @param numRequests - The number of requests that each thread will make
	 * @param host
	 * @param port
	 */
	public CyclicBarrierRunnable(CyclicBarrier barrier, StressTester tester,
			int numRequests, String host, int port) {
		this.barrier = barrier;
		this.tester = tester;
		this.numRequests = numRequests;
		this.host = host;
		this.port = port;
	}

	@Override
	public void run() {
		try {
			this.barrier.await();
			tester.makeRequests(this.host, this.port, this.numRequests);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}

	}

}
