package bg.uni_sofia.fmi.corejava.NetworkStressTester;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Paths;

public class StressTester {

	private String query;

	private String response;
	
	private String host;
	
	private int port;
	
	/**
	 * 
	 * @param host
	 * @param port
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public StressTester(String host, int port) throws FileNotFoundException, IOException {
		try (BufferedReader requestReader = new BufferedReader(new FileReader(
				new File("src/request.txt")));
				BufferedReader responseReader = new BufferedReader(
						new FileReader(new File("src/response.txt")))) {
			
			this.query = requestReader.readLine();
			this.response = responseReader.readLine();
			this.host = host;
			this.port = port;
		}
	}
	
	/**
	 * 
	 * @param host
	 * @param port
	 * @param query
	 * @param response
	 */
	public StressTester(String host, int port, String query, String response){
		this.host = host;
		this.port = port;
		this.query = query;
		this.response = response;
	}
	
	/**
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void makeRequest() throws UnknownHostException, IOException {
		String log = null;
		
		long start = System.currentTimeMillis();
		
		try (Socket s = new Socket(this.host, this.port);
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
			
			try (Logger logger = new Logger(Paths.get("src/result.txt"))) {
				if (sb.toString().startsWith(response)) {
					log = "Thread " + Thread.currentThread().getName()
							+ " got success response " + response + " for "
							+ (System.currentTimeMillis() - start) + " milis \n";
					System.out.println(log);
					logger.log(log);
				} else {
					log = "Thread " + Thread.currentThread().getName()
							+ " got incorrect result for "
							+ (System.currentTimeMillis() - start)
							+ sb.toString() + " milis \n";
					System.err.println(log);
					logger.log(log);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param count - invokes count time makeRequest
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void makeRequests(int count) throws UnknownHostException, IOException {
		for (int i = 0; i < count; i++) {
			makeRequest();
		}
	}

}
