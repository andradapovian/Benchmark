package benchmark.HDD.SequentialVersusRandom;

import java.io.*;
import java.io.IOException;
import java.util.Random;

import timing.*;

class SequentialAccess {

    Random rand=new Random();

    public long SequentialReadFixedSize(String filePath, int bufferSize,
            int toRead) throws IOException {
        int counter = 0;
        byte[] bytes = new byte[bufferSize];
        Timer timer = new Timer();

        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filePath), bufferSize);

        timer.start();
        while (counter++ < toRead) {
            rand.nextBytes(bytes);
            inputStream.read(bytes);
        }
        inputStream.close();
        return timer.stop() / 1000000; // ns to ms
    }

    public long SequentialWriteFixedSize(String filePath, int bufferSize,
            int toWrite) throws IOException {
        int counter = 0;
        Timer timer = new Timer();
        byte[] bytes = new byte[bufferSize];
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath), bufferSize);
        
        timer.start();
        while (counter++ < toWrite) {
            rand.nextBytes(bytes);
            outputStream.write(bytes);
        }
        outputStream.close();

        return timer.stop() / 1000000; // ns to ms!
    }

    public long SequentialReadFixedTime(String filePath, int bufferSize,
            int millis) throws IOException {
        int counter = 0;
        byte[] bytes = new byte[bufferSize];
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filePath), bufferSize);
        long now = System.nanoTime();
        
        while ((System.nanoTime()-now)/1000000 < millis) {
            rand.nextBytes(bytes);
            inputStream.read(bytes);
            
            counter++;
        }
        inputStream.close();

        return counter;
    }
    
    public int SequentialWriteFixedTime(String filePath, int bufferSize,
            int millis) throws IOException {
        int counter = 0;
        byte[] bytes = new byte[bufferSize];
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath), bufferSize);
        long now = System.nanoTime();
        
        while ((System.nanoTime()-now)/1000000 < millis) {
            rand.nextBytes(bytes);
            outputStream.write(bytes);
            
            counter++;
        }
        outputStream.close();

        return counter;
    }
}