package benchmark.HDD.SequentialVersusRandom;

import java.io.*;
import timing.Timer;
import java.util.Random;

class RandomAccess {
    private Random random;

    RandomAccess() {
        random = new Random();
    }

    public long randomReadFixedSize(String filePath, int bufferSize,
            int toRead) throws IOException {
        RandomAccessFile file = new RandomAccessFile(filePath, "rw");
        int fileSize = (int) (file.getChannel().size() % Integer.MAX_VALUE);
        int counter = 0;
        byte[] bytes = new byte[bufferSize];
        Timer timer = new Timer();

        timer.start();
        while (counter++ < toRead) {
            int position=random.nextInt(fileSize);
            file.seek(position);
            random.nextBytes(bytes);
            file.read(bytes);
        }

        file.close();
        return timer.stop() / 1000000; // ns to ms
    }
    
    public long randomWriteFixedSize(String filePath, int bufferSize,
            int toWrite) throws IOException {
        RandomAccessFile file = new RandomAccessFile(filePath, "rw");
        int fileSize = (int) (file.getChannel().size() % Integer.MAX_VALUE);
        int counter = 0;
        Timer timer = new Timer();

        byte[] bytes = new byte[bufferSize];
        
        timer.start();
        while (counter++ < toWrite) {
            int position=random.nextInt(fileSize);
            file.seek(position);
            random.nextBytes(bytes);
            file.write(bytes);
        }

        file.close();
        return timer.stop() / 1000000; // ns to ms
    }

    public long randomReadFixedTime(String filePath, int bufferSize,
            int millis) throws IOException {
        RandomAccessFile file = new RandomAccessFile(filePath, "rw");
        int fileSize = (int) (file.getChannel().size() % Integer.MAX_VALUE);
        int counter = 0;
        byte[] bytes = new byte[bufferSize];

        long now = System.nanoTime();
        
        while ((System.nanoTime()-now)/1000000 < millis) {
            int position=random.nextInt(fileSize);
            file.seek(position);
            random.nextBytes(bytes);
            file.read(bytes);
            
            counter++;
        }

        file.close();
        return counter;
    }

    public int randomWriteFixedTime(String filePath, int bufferSize,
            int millis) throws IOException {
        RandomAccessFile file = new RandomAccessFile(filePath, "rw");
        int fileSize = (int) (file.getChannel().size() % Integer.MAX_VALUE);
        int counter = 0;

        byte[] bytes = new byte[bufferSize];

        long now = System.nanoTime();
        
        while ((System.nanoTime()-now)/1000000 < millis) {
            int position=random.nextInt(fileSize);
            file.seek(position);
            random.nextBytes(bytes);
            file.write(bytes);
            
            counter++;
        }

        file.close();
        return counter;
    }
}