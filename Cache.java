import java.util.*;
import java.lang.*;

public class Cache{
    
    private int cacheSize;
    private int associativity;
    private int blockSize;
    private int hits = 0;
    private double hitRate = 0.0;
    private int blockBits;
    private int indexBits;
    private int tagBits;


    public Cache(int cacheSize, int associativity, int blockSize){
        this.cacheSize = cacheSize;
        this.associativity = associativity;
        this.blockSize = blockSize;

        // calculate these now
        this.blockBits = Math.floorDiv(blockSize, 2);
        double indices = cacheSize / (associativity * blockSize);
        this.indexBits = bitsFromIndices(indices);
        this.tagBits = 32 - (blockBits + indexBits);

    }

    public void feed(Integer address){
        int bloff = getBlockOffset(address);
        int index = getIndex(address);
        return;
    }

    private int getBlockOffset(Integer addr){
        return addr % blockSize;
    }

    private int getIndex(Integer addr){
        System.out.println(this.toString() + "\nGetting index for address " + addr);
        // shift left to get rid of tag bits, then logical shift right to get rid of the blockBits
        int temp1 = addr << tagBits;
        int temp2 = temp1 >>> (tagBits + blockBits);
        System.out.println("\tGot index: " + temp2);
        return temp2;
    }

    private int bitsFromIndices(double indices){
        return (int) Math.ceil(Math.log(indices) / Math.log(2));
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

    @Override
    public String toString() {
        return "Cache{" +
                "cacheSize=" + cacheSize +
                ", associativity=" + associativity +
                ", blockSize=" + blockSize +
                ", blockBits=" + blockBits +
                ", indexBits=" + indexBits +
                ", tagBits=" + tagBits +
                '}';
    }
}
