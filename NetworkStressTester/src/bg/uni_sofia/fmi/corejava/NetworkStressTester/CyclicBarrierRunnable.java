package bg.uni_sofia.fmi.corejava.NetworkStressTester;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierRunnable implements Runnable {

	private CyclicBarrier barrier;

	private StressTester tester;

	private int numRequests;

	private int port;

	private String host;

	public CyclicBarrierRunnable(CyclicBarrier barrier1, StressTester tester,
			int numRequests, String host, int port) {
		this.barrier = barrier1;
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
