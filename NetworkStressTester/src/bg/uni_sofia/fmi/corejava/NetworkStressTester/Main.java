package bg.uni_sofia.fmi.corejava.NetworkStressTester;

import java.io.IOException;
import java.util.concurrent.CyclicBarrier;

public class Main {

	private static final int THREADS_NUM = 40;

	private static final String HOST = "java.voidland.org";

	private static final int PORT = 80;

	private static final int REQUESTS_PER_THREAD = 10;

	public static void main(String[] args) {

		StressTester tester;
		CyclicBarrier barrier;

		try {
			tester = new StressTester(HOST, PORT);

			barrier = new CyclicBarrier(THREADS_NUM);

			CyclicBarrierRunnable barrierRunnable = new CyclicBarrierRunnable(
					barrier, tester, REQUESTS_PER_THREAD);

			for (int i = 0; i < THREADS_NUM; i++) {
				Thread thread = new Thread(barrierRunnable);
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
