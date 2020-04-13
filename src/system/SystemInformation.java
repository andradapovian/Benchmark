package system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemInformation {
	
    public static String getOS() throws IOException
    {
    	String[] com = new String[] {"CMD", "/C", "WMIC OS GET Version"};
		Process process = Runtime.getRuntime().exec(com);
		process.getOutputStream().close();
		
		String s = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())); 
		
		String result = "Operating System: " + System.getProperty("os.name") + "\n";
		
		while ((s = reader.readLine()) != null) { 
			result = result + s + "\n";
		}
		
        return result;
    }

    public static String getRAM() throws IOException
    {
    	String[] com = new String[] {"CMD", "/C", "WMIC MEMORYCHIP GET Capacity"};
		Process process = Runtime.getRuntime().exec(com);
		process.getOutputStream().close();
		
		String s = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())); 
		
		String result = "";
		
		while ((s = reader.readLine()) != null) { 
			result = result + s + "\n";
		}
		
        return result;
    }
    
    public static String getCPU() throws IOException
    {
    	String[] com = new String[] { "CMD", "/C", "WMIC CPU GET Name, MaxClockSpeed, NumberOfCores"};
		Process process = Runtime.getRuntime().exec(com);
		process.getOutputStream().close();
		
		String s = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())); 
		
		String result = "";
		
		while ((s = reader.readLine()) != null) { 
			result = result + s + "\n";
		}
		
        return result;
    }
    
    public static String getHDD() throws IOException
    {
    	String[] com = new String[] {"CMD", "/C", "WMIC diskdrive GET Manufacturer, Model"};
		Process process = Runtime.getRuntime().exec(com);
		process.getOutputStream().close();
		
		String s = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())); 
		
		String result = "";
		
		while ((s = reader.readLine()) != null) { 
			result = result + s + "\n";
		}
		
        return result + "\n";
    }
    
}