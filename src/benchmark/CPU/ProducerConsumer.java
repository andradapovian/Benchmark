package benchmark.CPU;

import java.util.LinkedList;

import benchmark.IBenchmark;

public class ProducerConsumer implements IBenchmark{

	 private final PC pc = new PC();
	 protected static int workload;
	
	@Override
	public void run() {
		
		  // Create producer thread
		 Thread t1 = new Thread(new Runnable() 
	        { 
	            @Override
	            public void run() 
	            { 
	                try
	                { 
	                    pc.produce(); 
	                } 
	                catch(InterruptedException e) 
	                { 
	                    e.printStackTrace(); 
	                } 
	            } 
	        }); 
	  
	        // Create consumer thread 
	        Thread t2 = new Thread(new Runnable() 
	        { 
	            @Override
	            public void run() 
	            { 
	                try
	                { 
	                    pc.consume(); 
	                } 
	                catch(InterruptedException e) 
	                { 
	                    e.printStackTrace(); 
	                } 
	            } 
	        }); 
	        
	  
	        // Start both threads 
	        t1.start(); 
	        t2.start();

	        try {
				t1.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
	        try {
				t2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
	        
		
	}

	@Override
	public void run(Object... params) {
		throw new UnsupportedOperationException(
				"Method not implemented. Use run() instead");
	}

	@Override
	public void initialize(Object... params) {
		workload = (Integer)params[0];
		
	}

	@Override
	public void clean() {
		
	}

	@Override
	public void cancel() {
		pc.cancel();
	}

	@Override
	public void warmUp() {
		run();
		
	}

	
	public static class PC 
    { 
        // Create a list shared by producer and consumer 
        // Size of list is 10. 
        private LinkedList<Integer> list = new LinkedList<>(); 
        private int capacity = 10;
        private boolean running = true;
  
        // Function called by producer thread 
        public void produce() throws InterruptedException 
        { 
            int value = 0; 
            running = true;
            while (running) 
            { 
                synchronized (this) 
                { 
                    // producer thread waits while list 
                    // is full 
                    while (list.size()==capacity) 
                        wait(); 
  
                    // to insert the jobs in the list 
                    list.add(value++); 
  
                    // notifies the consumer thread that 
                    // now it can start consuming 
                    notify(); 

                    if (value == workload)
                    {
                    	cancel();
                    }
                } 
            } 
            //System.out.println("Reached " + value);
        } 
  
        // Function called by consumer thread 
        public void consume() throws InterruptedException 
        { 
            while (running) 
            { 
                synchronized (this) 
                { 
                    // consumer thread waits while list 
                    // is empty 
                    while (list.size()==0) 
                        wait(); 
  
                    //to retrieve the first job in the list 
                    list.removeFirst(); 
  
                
                    // Wake up producer thread 
                    notify(); 
  
                } 
            } 
        }
        
        public void cancel() {
        	running = false;
        }
    }

}
