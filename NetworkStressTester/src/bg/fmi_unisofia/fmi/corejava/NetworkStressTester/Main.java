package bg.fmi_unisofia.fmi.corejava.NetworkStressTester;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CyclicBarrier;

public class Main {

	private static final int THREADS_NUM = 10;

	public static void main(String[] args) {

		try (BufferedReader requestReader = new BufferedReader(new FileReader(
				new File("src/request.txt")));
				BufferedReader responseReader = new BufferedReader(
						new FileReader(new File("src/response.txt")))) {
			
			String query = requestReader.readLine();
			String expectedResponse = responseReader.readLine();

			StressTester tester = new StressTester(query, expectedResponse);

			Runnable barrierAction = () -> System.out
					.println("THE LAST THREAD ARRIVED.EXECUTING STARTED");

			CyclicBarrier barrier = new CyclicBarrier(THREADS_NUM,
					barrierAction);

			CyclicBarrierRunnable barrierRunnable = new CyclicBarrierRunnable(
					barrier, tester, 100);

			for (int i = 0; i < THREADS_NUM; i++) {
				Thread thread = new Thread(barrierRunnable);
				thread.start();
			}
		} catch (FileNotFoundException e1) {
			System.err.println("COULD NOT READ FILES : " + e1.getMessage());
		} catch (IOException e1) {
			System.err.println("SOMETHING WRONG HAPPENED " + e1.getMessage());
		}

	}

}
