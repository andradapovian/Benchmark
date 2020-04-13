package benchmark.HDD.SequentialVersusRandom;

import java.io.*;
import java.util.Random;

import benchmark.IBenchmark;

public class HDDSequentialAccess implements IBenchmark {

	private final static String PATH = System.getProperty("user.dir") + "\\file.dat";
	private String result;
	private boolean running=true;

	@Override
	public void initialize(Object... params) {
		File tempFile = new File(PATH);
		long fileSize = (Long) params[0];

		try {
			Random rand=new Random();
			int bufferSize = 4 * 1024;
			long toWrite = fileSize / bufferSize;
			byte[] buffer = new byte[bufferSize];
			long counter = 0;
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(PATH), bufferSize);

			while (counter++ < toWrite && running==true) {
				rand.nextBytes(buffer);
				outputStream.write(buffer);
			}
			outputStream.close();
			tempFile.deleteOnExit();

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	@Override
	public void warmUp() {
	}

	@Override
	public void run() {
		throw new UnsupportedOperationException("Use run(Object[]) instead");
	}

	@Override
	public void run(Object ...options) {
		Object[] param = (Object[]) options;
		final int steps = 10000;
		final int runtime = 5000; // ms

		try {
			if (String.valueOf(param[0]).toLowerCase().equals("r")) {
				int bufferSize = Integer.parseInt(String.valueOf(param[2]));

				if (String.valueOf(param[1]).toLowerCase().equals("fs")) {
					long timeMs = new SequentialAccess().SequentialReadFixedSize(PATH, bufferSize, steps);
					result = steps + " Sequential reads in " + Math.round(timeMs*100)/100+ " ms [" 
							+ (steps * bufferSize / 1024 / 1024) + " MB, "
							+ (Math.round((1.0 * (steps * bufferSize / 1024 / 1024) / timeMs * 1000)*100)/100) + " MB/s]";
				}
				else if (String.valueOf(param[1]).toLowerCase().equals("ft")) {
				
					long ios = new SequentialAccess().SequentialReadFixedTime(PATH,
							bufferSize, runtime);
					result = ios + " Inputs per second [" //I/Os
							+ (ios * bufferSize / 1024 / 1024) + " MB, "
							+ String.format("%.2f",(1.0 * (ios * bufferSize / 1024 / 1024) / runtime * 1000)) + " MB/s]";
				} else
					throw new UnsupportedOperationException("Read option \""
							+ String.valueOf(param[1])
							+ "\" is not implemented");

			}
			else if (String.valueOf(param[0]).toLowerCase().equals("w")) {
				int bufferSize = Integer.parseInt(String.valueOf(param[2]));

				if (String.valueOf(param[1]).toLowerCase().equals("fs")) {
					
					long timeMs = new SequentialAccess().SequentialWriteFixedSize(PATH,
							bufferSize, steps);
					result = steps + " Sequential writes in " + Math.round(timeMs*100)/100 + " ms [" 
							+ (steps * bufferSize / 1024 / 1024) + " MB, "
							+ (Math.round((1.0 * (steps * bufferSize / 1024 / 1024) / timeMs * 1000)*100)/100) + " MB/s]";
				}
				else if (String.valueOf(param[1]).toLowerCase().equals("ft")) {
				
					int ios = new SequentialAccess().SequentialWriteFixedTime(PATH,
							bufferSize, runtime);
					result = ios + " Outputs per second [" 
							+ (ios * bufferSize / 1024 / 1024) + " MB, "
							+ (Math.round((1.0 * (ios * bufferSize / 1024 / 1024) / runtime * 1000)*100)/100) + " MB/s]";
				} else
					throw new UnsupportedOperationException("Write option \""
							+ String.valueOf(param[1])
							+ "\" is not implemented");
			} else
				throw new UnsupportedOperationException("Benchmark option \""
						+ String.valueOf(param[0]) + "\" is not implemented");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void clean() {
	}
	
	@Override
	public void cancel() {
		this.running=false;
	}
	
	public String getResult() {
		return String.valueOf(result);
	}

}