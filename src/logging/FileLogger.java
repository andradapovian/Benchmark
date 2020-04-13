package logging;
import java.io.*;

public class FileLogger implements ILogger{
	private PrintWriter pw;
	private String path;
	public FileLogger(String p) 
	{
		path=p;
		try {
			BufferedWriter bw=new BufferedWriter(new FileWriter(path));
			pw=new PrintWriter(bw);
			} catch (IOException e){
				e.printStackTrace();
				System.exit(-1);
				}
	}
	public FileLogger()
	{
		path="DefaultFileLogger.txt";
		try {
			BufferedWriter bw=new BufferedWriter(new FileWriter(path));
			pw=new PrintWriter(bw);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
				}
	}

	public void write(String s)
	{
		pw.println(s);
	}
	public void write(String s,long l,TimeUnit tu)
	{
		pw.println(s+TimeUnit.transfort(l,tu)+" "+tu);
	}
	public void write(long l)
	{
		pw.println(l);
	}
	public void write(long l, TimeUnit tu)
	{
		pw.println(TimeUnit.transfort(l,tu));
	}
	public void write(Object...params)
	{
		String s="";
		for(Object o:params)
			s=s+o.toString()+" ";
		pw.println(s);
	}

	public void close() 
	{
		if(pw!=null)
			pw.close();
	}
}



