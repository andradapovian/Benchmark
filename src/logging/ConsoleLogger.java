package logging;

public class ConsoleLogger implements ILogger{
	public void write(String s)
	{
		System.out.println(s);
	}
	public void write(String s,long l,TimeUnit tu)
	{
		System.out.println(s+TimeUnit.transfort(l,tu)+" "+tu);
	}
	public void write(long l)
	{
		System.out.println(l);
	}
	public void write(long l, TimeUnit tu)
	{
		System.out.println(TimeUnit.transfort(l,tu));
	}
	public void write(Object...params)
	{
		for(Object o:params)
			System.out.print(o.toString()+" ");
		System.out.println();
	}
	public void close()
	{
		
	}
}
