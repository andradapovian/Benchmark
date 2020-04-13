package benchmark.HDD.ReadVersusWrite;

import java.io.IOException;
import logging.*;

import benchmark.IBenchmark;

public class HDDWriteSpeed implements IBenchmark {
	
	private boolean running=true;
	private ILogger l;
	
	@Override
	public void initialize(Object... params) {
		l=(ILogger)params[0];
	}

	@Override
	public void warmUp() {
	}
	
	public void cancel()
	{
		running=false;
	}

	@Override
	public void run() {
		throw new UnsupportedOperationException(
				"Method not implemented. Use run(Object) instead");
	}

	@Override
	public void run(Object... options) {
		FileWriter writer = new FileWriter();
		String option = (String) options[0];
		Boolean clean = (Boolean) options[1];

		String prefix =System.getProperty("user.dir")+"\\file";
		String suffix = ".dat";
		int startIndex = 0;
		int endIndex = 12;
		long fileSize = 256*1024*1024; // 256 MB
		int buffersize = 4096; // 4 KB
		
		try {
			if (option.equals("fs"))
				l.write(writer.streamWriteFixedSize(prefix, suffix, startIndex,
						endIndex, fileSize, clean, running));
			else if (option.equals("fb"))
				l.write(writer.streamWriteFixedBuffer(prefix, suffix, startIndex,
						endIndex, buffersize, clean, running));
			else
				throw new IllegalArgumentException("Argument "
						+ options[0].toString() + " is undefined");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void clean() {
	}
}
