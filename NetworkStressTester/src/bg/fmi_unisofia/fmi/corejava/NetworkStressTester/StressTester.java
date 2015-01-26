package bg.fmi_unisofia.fmi.corejava.NetworkStressTester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Paths;

public class StressTester {

	private String query;

	private String response;

	public StressTester(String query, String response) {
		this.query = query;
		this.response = response;
	}

	public void makeRequest(String host, int port) {
		try (Socket s = new Socket(host, port);
				PrintWriter pw = new PrintWriter(s.getOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(
						s.getInputStream()));) {

			pw.println(this.query);
			pw.flush();

			String line;
			StringBuilder sb = new StringBuilder();

			while ((line = in.readLine()) != null) {
				sb.append(line);
			}

			if (sb.toString().startsWith(response)) {
				System.out.println("Thread " + Thread.currentThread().getName()
						+ " got correct response " + response + "\n");
			} else {
				try(Logger logger = new Logger(Paths.get("src/result.txt"))) {
					logger.log("Thread " + Thread.currentThread().getName()
							+ " got incorrect result " + sb.toString() + "\n");
				}
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			try(Logger logger = new Logger(Paths.get("src/result.txt"))) {
				logger.log("Thread " + Thread.currentThread().getName()
						+ " got exception " + e.getMessage() +  "\n");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}	

	public void makeRequest(String host, int port, int count) {
		for (int i = 0; i < count; i++) {
			makeRequest(host, port);
		}
	}

}
