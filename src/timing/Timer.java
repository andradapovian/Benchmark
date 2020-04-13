package timing;

public class Timer implements ITimer{
	private long pos1,pos2,totalt=0; //pos1=starttime; pos2=stop/pause time
	public void start()
	{
		totalt=0;
		pos1=System.nanoTime();
	}
	public long stop()
	{	
		pos2=System.nanoTime();
		totalt+=pos2-pos1;
		return totalt;
	}
	public long pause()
	{
		pos2=System.nanoTime();
		totalt+=pos2-pos1;
		return pos2-pos1;
	}
	public void resume()
	{
		pos1=System.nanoTime();
	}
}
