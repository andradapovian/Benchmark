package logging;

public interface ILogger {
	void write(String s);
	void write(String s,long l,TimeUnit tu);
	void write(long l);
	void write(long l,TimeUnit tu);
	void write(Object...params);
	void close();
}
