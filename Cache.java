import java.util.*;
import java.lang.*;

public class Cache{
    
    private int cacheSize;
    private int associativity;
    private int blockSize;
    private int hits = 0;
    private double hitRate = 0.0;


    public Cache(int cacheSize, int associativity, int blockSize){
        this.cacheSize = cacheSize;
        this.associativity = associativity;
        this.blockSize = blockSize;
       
    }

    public void printResult()
    {
        System.out.println("Cache size: " + cacheSize + "\t");
        System.out.print("Associativity: " + associativity + "\t");
        System.out.println("Block size: " + blockSize);
        System.out.println("Hits: " + hits);
        System.out.println("Hit Rate: " + hitRate + "%");
        System.out.println("---------------------------");
    }

}
