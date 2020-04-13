package ui;

import java.io.IOException;

import benchmark.IBenchmark;
import benchmark.CPU.FFT;
import benchmark.CPU.ProducerConsumer;
import benchmark.CPU.ThreadedRoots;
import benchmark.CPU.dhrystn.dhry;
import benchmark.HDD.ReadVersusWrite.HDDReadSpeed;
import benchmark.HDD.ReadVersusWrite.HDDWriteSpeed;
import benchmark.HDD.SequentialVersusRandom.HDDRandomAccess;
import benchmark.HDD.SequentialVersusRandom.HDDSequentialAccess;
import logging.FileLogger;
import logging.ILogger;
import logging.TimeUnit;
import system.SystemInformation;
import timing.ITimer;
import timing.Timer;

public class Run {
	
	private static double cpuScore, hddScore;
	
	public static int getcpuScore() {
		return (int)cpuScore;
	}
	
	public static int gethddScore() {
		return (int)hddScore;
	}
	
	public static void run() throws IOException {
		ITimer t= new Timer();
		ILogger l= new FileLogger("ResultsKP.txt");
		IBenchmark bench;
		TimeUnit u = TimeUnit.s;

		
		//Gather System Information
		l.write(SystemInformation.getOS());
		l.write( SystemInformation.getCPU() );
		l.write( SystemInformation.getRAM() );
		l.write(SystemInformation.getHDD());


		

		//CPU
		
			//ADRIANA:
		double x[] = {0, 0, 0, 0};
		long time;
		
		
		l.write("\n***CPU Testing Started***\n");
		//Dhrystone
		bench = new dhry();
		bench.initialize(500000000L);
		t.start();
		bench.run();
		time = t.pause();
		x[0] = Double.parseDouble(TimeUnit.transfort(time,TimeUnit.s));
		l.write("Dhrystone: ",time,TimeUnit.s);
		
		//FFT 	
		bench = new FFT();
		bench.initialize(1024*512);
		bench.warmUp();
		t.resume();
		bench.run();
		time = t.pause();
		x[1] = Double.parseDouble(TimeUnit.transfort(time,TimeUnit.s));
		l.write("FFT: ", time, u);  
		
		//ProducerConsumer
		bench = new ProducerConsumer();
		bench.initialize(10000000);
		//bench.warmUp();
		t.resume();
		bench.run();
		time = t.pause();
		x[2] = Double.parseDouble(TimeUnit.transfort(time,TimeUnit.s));
		l.write("ProducerConsumer: ", time,u);
		
		//ThreadedRoots
		bench = new ThreadedRoots();
		bench.initialize(500000000);
		t.resume();
		bench.run(32);
		time = t.pause();
		x[3] = Double.parseDouble(TimeUnit.transfort(time,TimeUnit.s));
		l.write("ThreadedRoots 32 threads: ", time,u);

		//ADRIANA CPUSCORE
		cpuScore = Math.round(100 - 1.5*26.3/(3.8/x[0]+10/x[1]+8.3/x[2]+4.2/x[3]));
		
		l.write("\n***CPU Testing Ended***\n");
		t.stop();

		

		//HDD
		l.write("\n***HDD Testing Started***\n");
		//Write Speed vs. Read Speed
		//WriteSpeed- fs
		bench=new HDDWriteSpeed();
		bench.initialize(l);
		bench.run("fs", false);
		l.write("");
		//ReadSpeed- fs
		bench=new HDDReadSpeed();
		bench.initialize(l);
		bench.run("fs", true);
		l.write("");
		//WriteSpeed- fb
		bench=new HDDWriteSpeed();
		bench.initialize(l);
		bench.run("fb", false);
		l.write("");
		//ReadSpeed- fb
		bench=new HDDReadSpeed();
		bench.initialize(l);
		bench.run("fb", true);
		l.write("");
		
		//Random Access vs. Sequential Access
		l.write("Random Access Benchmark:");
		//Random Access- writing, ft
		bench=new HDDRandomAccess();
		bench.initialize(512*1024*1024L);
		bench.run("w","ft",4*1024);
		l.write(((HDDRandomAccess)bench).getResult());
		//Random Access- reading, ft
		bench=new HDDRandomAccess();
		bench.initialize(512*1024*1024L);
		bench.run("r","ft",4*1024);
		l.write(((HDDRandomAccess)bench).getResult());
		//Random Access- writing, fs
		bench=new HDDRandomAccess();
		bench.initialize(512*1024*1024L);
		bench.run("w","fs",4*1024);
		l.write(((HDDRandomAccess)bench).getResult());
		//Random Access- reading, fs
		bench=new HDDRandomAccess();
		bench.initialize(512*1024*1024L);
		bench.run("r","fs",4*1024);
		l.write(((HDDRandomAccess)bench).getResult());
		l.write();

		l.write("Sequential Access Benchmark:");
		//Sequential Access- writing, ft
		bench=new HDDSequentialAccess();
		bench.initialize(512*1024*1024L);
		bench.run("w","ft",4*1024);
		l.write(((HDDSequentialAccess)bench).getResult());
		//Sequential Access- reading, ft
		bench=new HDDSequentialAccess();
		bench.initialize(512*1024*1024L);
		bench.run("r","ft",4*1024);
		l.write(((HDDSequentialAccess)bench).getResult());
		//Sequential Access- writing, fs
		bench=new HDDSequentialAccess();
		bench.initialize(512*1024*1024L);
		bench.run("w","fs",4*1024);
		l.write(((HDDSequentialAccess)bench).getResult());
		//Sequential Access- reading, fs
		bench=new HDDSequentialAccess();
		bench.initialize(512*1024*1024L);
		bench.run("r","fs",4*1024);
		l.write(((HDDSequentialAccess)bench).getResult());

		l.write("\n***HDD Testing Ended***\n");
		




		l.write("CPU score: ", cpuScore, "\n");

		l.close();
	}
}
