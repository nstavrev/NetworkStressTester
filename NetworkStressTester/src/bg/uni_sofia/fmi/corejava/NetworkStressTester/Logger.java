package bg.uni_sofia.fmi.corejava.NetworkStressTester;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger implements Closeable {
	
	protected static final SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS");
	
	protected Path logFile;
	protected FileOutputStream logStream;
	protected PrintWriter logWriter;
	protected Calendar lastMessageTime;
	
	public Logger(Path logFile) throws IOException {
		this.logFile = logFile;
		this.openLogFile();
	}
	
	protected void openLogFile() throws FileNotFoundException {
		this.logStream = new FileOutputStream(this.logFile.toString(), true);
		this.logWriter = new PrintWriter(this.logStream);
	}

	public void close() throws IOException {
		this.closeLogFile();
	}
	
	protected void closeLogFile() throws IOException {
		this.logWriter.close();
		this.logStream.close();
	}
	
	public void log(String message) throws IOException {
		this.lastMessageTime = Calendar.getInstance();
		String logMessage = ft.format(this.lastMessageTime.getTime()) + "  " + message;
		this.logWriter.println(logMessage);
	}
}

