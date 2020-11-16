import java.util.*;
import java.lang.*;
import java.io.*;

public class CacheSimulator{
    private Cache[] caches; // array of seven caches
    public static int line = 0;
    private String memStream; 

    public CacheSimulator(String memStream){
        this.memStream = memStream;
    }

    private Scanner getScanner()
    {
        try{
            File file = new File(memStream);
            Scanner sc = new Scanner(file);
            return sc;
        } catch (FileNotFoundException e){
            System.out.println("Error creating scanner: " + e);
            System.exit(-1);
        }
        return null;
    }

    public void run()
    {
        Scanner sc = getScanner();

        //just gets first 10 for now since there are a lot of addresses
        CacheGroup.setOneVerbose(4, true);
        while(sc.hasNextLine())
        {
            String rawLine = sc.nextLine();
            String[] tokens = rawLine.split("\t");
            //int loadOrStore = Integer.parseInt(tokens[0]);
            int addr = Integer.parseInt(tokens[1], 16);
            CacheGroup.feedAddress(addr);
            if(line >= 100){
                CacheGroup.setAllVerbose(false);
            }
            line += 1;

        }
        CacheGroup.printAll();
    }

    



}
