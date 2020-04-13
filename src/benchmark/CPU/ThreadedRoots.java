package benchmark.CPU;

import benchmark.IBenchmark;

public class ThreadedRoots implements IBenchmark {

	private double result;
	private int size;
	private boolean running = true;
	private double eps=0.00001;

	
	@Override
	public void warmUp() {	
		run(32);
	}

	@Override
	public void run() {
		throw new UnsupportedOperationException(
				"Method not implemented. Use run(Objects...) instead");
	}

	@Override
	public void run(Object... options) {		
		// options[0] -> number of threads 
		
		int nThreads=(Integer) options[0];

		Thread[] threads = new Thread[nThreads];

		// e.g. 1 to 10,000 on 4 threads = 2500 jobs per thread
		final int jobPerThread = size/nThreads;

		running = true; // flag used to stop all started threads
		// create a thread for each runnable (SquareRootTask) and start it
		for (int i = 0; i < nThreads; ++i) {
			threads[i] = new Thread(new SquareRootTask(i*jobPerThread + 1, (i+1)*jobPerThread));
			threads[i].start();
		}

		// join threads
		for (int i = 0; i < nThreads; ++i) {
			try {
				threads[i].join();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}	

	@Override
	public void clean() {
		
	}

	public String getResult() {
		return String.valueOf(result);
	}

	class SquareRootTask implements Runnable {

		private int from, to;
		private final double precision = 1e-4; // fixed
		private double result = 0.0;

		public SquareRootTask(int from, int to) {
			// save params to class members
			this.from=from;
			this.to=to;
		}

		@Override
		public void run() {			
			for(int i=from; i<=to && running; i++) {
				result+= this.getNewtonian(i);		
			}			
		}
		
		private double getNewtonian(double x) {
			
			double s=x;
			
			while(Math.abs(s*s - x) > eps) {
				s=(x/s+s)/2.0;
			}
			
			return s;
		}
	}

	@Override
	public void initialize(Object... params) {
		this.size=(Integer)params[0];		
	}

	@Override
	public void cancel() {
		running = false;
		
	}

}
