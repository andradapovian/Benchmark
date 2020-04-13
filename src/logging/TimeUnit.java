package logging;

public enum TimeUnit {
	s,ms,us,ns;
	
	public static String transfort(long value,TimeUnit tu)
	{
		switch(tu)
		{
		case s:return String.format("%.2f",(value/(Math.pow(10,9))));
		case ms:return String.format("%.2f",(value/(Math.pow(10,6))));
		case us:return String.format("%.2f",(value/(Math.pow(10,3))));
		case ns:return String.format("%.2f",value);
		default: return "Unknown TimeUnit";
		}
	}
}
