package bg.uni_sofia.fmi.corejava.NetworkStessTester;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.CyclicBarrier;

import org.junit.Before;
import org.junit.Test;

import bg.uni_sofia.fmi.corejava.NetworkStressTester.CyclicBarrierRunnable;
import bg.uni_sofia.fmi.corejava.NetworkStressTester.Main;
import bg.uni_sofia.fmi.corejava.NetworkStressTester.StressTester;

public class StressTesterTest {
	
	private StressTester tester;
	
	private static final String HOST = "java.voidland.org";
	
	private static final int PORT = 80;
	
	@Before
	public void readFiles() throws FileNotFoundException, IOException {
		 tester = new StressTester(HOST,PORT);
	}
	
	@Test
	public void makeRequestWithCorrectResponseTest() throws UnknownHostException, IOException {
		tester.makeRequest();
	}
	
	@Test 
	public void makeRequestWithIncorrectResponseTest() throws UnknownHostException, IOException{
		tester = new StressTester(HOST,PORT, "GET /", "incorrect");
		tester.makeRequest();
	}
	
	@Test(expected = UnknownHostException.class)
	public void makeRequestWithUnknowhostTest() throws UnknownHostException, IOException{
		tester = new StressTester("unknown", PORT);
		tester.makeRequest();
	}
	
	@Test
	public void makeRequestsTest() throws UnknownHostException, IOException {
		tester.makeRequests(10);
	}
	
	@Test
	public void barrierTest() {
		CyclicBarrier barrier = new CyclicBarrier(1);
		
		CyclicBarrierRunnable barrierRunnable = new CyclicBarrierRunnable(barrier, tester,10);
		new Thread(barrierRunnable).start();
	}
	
}
