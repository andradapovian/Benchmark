package benchmark.HDD.ReadVersusWrite;

import java.io.*;
import java.util.Random;

import timing.Timer;

public class FileReader {

	private static final int MIN_BUFFER_SIZE = 1024 * 1; // KB
	private static final int MAX_BUFFER_SIZE = 1024 * 1024 * 32; // 32 MB
	private static final int MIN_FILE_SIZE = 1024 * 1024 * 1; // MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 512; // 512 MB
	private Timer timer = new Timer();
	private long time;
	private String tmp="";
	private double benchScore;
	private boolean running;

	public String streamReadFixedSize(String filePrefix, String fileSuffix,
			int minIndex, int maxIndex, long fileSize, boolean clean,boolean running)
			throws IOException {

		tmp="Stream read benchmark with fixed file size: \n";
		int currentBufferSize = MIN_BUFFER_SIZE;
		String fileName;
		int counter = 0;
		benchScore = 0;
		this.running=running;

		while (currentBufferSize <= MAX_BUFFER_SIZE
				&& counter <= maxIndex - minIndex && running==true) {
			fileName = filePrefix+counter+fileSuffix;
			readWithBufferSize(fileName, currentBufferSize, fileSize,clean);
			currentBufferSize*=2;
			counter++;
		}

		benchScore /= (maxIndex - minIndex + 1);
		String partition = filePrefix.substring(0, filePrefix.indexOf(":\\"));
		tmp+=("\nFile read score from partition " + partition + ": "
				+ String.format("%.2f", benchScore) + " MB/sec");
		return tmp;
	}

	public String streamReadFixedBuffer(String filePrefix, String fileSuffix,
			int minIndex, int maxIndex, int bufferSize, boolean clean,boolean running)
			throws IOException {

		tmp="Stream read benchmark with fixed buffer size: \n";
		int currentFileSize = MIN_FILE_SIZE;
		int counter=0;
		int BufferSize = bufferSize;
		this.running=running;
		String fileName;
		while (currentFileSize <= MAX_FILE_SIZE
				&& counter <= maxIndex - minIndex && running==true) {
			fileName = filePrefix+counter+fileSuffix;
			readWithBufferSize(fileName, BufferSize, currentFileSize,clean);
			currentFileSize*=2;
			counter++;
		}

		benchScore /= (maxIndex - minIndex + 1);
		String partition = filePrefix.substring(0, filePrefix.indexOf(":\\"));
		tmp+=("\nFile read score from partition " + partition + ": "
				+ String.format("%.2f", benchScore) + " MB/sec");
		return tmp;
	}

	private void readWithBufferSize(String fileName, int myBufferSize,
			long fileSize, boolean clean) throws IOException {

		File folderPath = new File(fileName.substring(0,
				fileName.lastIndexOf(File.separator)));

		if (!folderPath.isDirectory())
			folderPath.mkdirs();

		final File file = new File(fileName);
		final BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file), myBufferSize);

		byte[] buffer = new byte[myBufferSize];
		int i = 0;
		long toRead = fileSize / myBufferSize;
		Random rand = new Random();

		timer.start();
		while (i < toRead && running==true) {
			rand.nextBytes(buffer);

			inputStream.read(buffer);
			i++;
		}		
		time=timer.stop();
		printStats(fileName, fileSize, myBufferSize);

		inputStream.close();
		if(clean)
			file.delete();
	}

	private void printStats(String fileName, long totalBytes, int myBufferSize) {
		
		double mseconds = time/(Math.pow(10,6)); 
		double megabytes = totalBytes / (1024*1024);
		double rate = megabytes/(mseconds/1000); 

		benchScore += rate;
	}
}