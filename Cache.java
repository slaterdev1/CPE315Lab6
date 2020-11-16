import java.lang.*;

public class Cache{

    private int cacheNum;
    private int cacheSize;
    private int associativity;
    private int blockSize;
    private int hits = 0;
    private int queries = 0;
    private double hitRate = 0.0;
    private int byteBits = 2;
    private int blockBits;
    private int indexBits;
    private int tagBits;
    private int waySize;
    private int[] memory = new int[1024];
    private int[] accessTimes = new int[1024];

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    private boolean verbose = false;


    public Cache(int cacheNum, int cacheSize, int associativity, int blockSize){
        this.cacheNum = cacheNum;
        this.cacheSize = cacheSize;
        this.associativity = associativity;
        this.blockSize = blockSize;

        // calculate these now
        this.blockBits = Math.floorDiv(blockSize, 2);
        double indices = (double) cacheSize / (associativity * blockSize * 4);
        this.waySize = (int) indices;
        this.indexBits = bitsFromIndices(indices);
        this.tagBits = 32 - (blockBits + indexBits + byteBits);
    }

    public void feed(Integer address){
        int bloff = getBlockOffset(address);
        int index = getIndex(address);
        int tag = getTag(address);
        if(this.verbose)System.out.println("\tGot tag: " + tag + ", index: " + index + ", and bloff:" + bloff);
        int way = presentAt(tag, index);
        if(way != -1) {
            hits += 1;
            accessTimes[getRealIndex(way, index)] = CacheSimulator.line;
        } else {
            store(tag, index);
        }
        queries += 1;
        hitRate = (double) hits / queries;
    }

    private void store(int tag, int virtualIndex){
        int way = findWay(virtualIndex);
        int realIndex =  getRealIndex(way, virtualIndex);
        //int blockStartIndex = realIndex - bloff;
        if(this.verbose)System.out.println("Storing tag: " + tag + " using way: " + way + " at block starting at: " + realIndex);
        memory[realIndex] = tag;
        accessTimes[realIndex] = CacheSimulator.line;
        if(this.verbose)System.out.println("\tStored tag: " + (tag) + " at real index: " + (realIndex));
    }

    private int findWay(int virtualIndex){
        long lowestAccess = Long.MAX_VALUE;
        int thisIsTheWay = 0;
        int realIndex;
        long accessTime;
        for(int way = 0; way < associativity; way += 1){
            realIndex = getRealIndex(way, virtualIndex);
            accessTime = accessTimes[realIndex];
            if(accessTime < lowestAccess){
                lowestAccess = accessTime;
                thisIsTheWay = way;
            }
        }
        return thisIsTheWay;
    }
    private int getRealIndex(int way, int virtualIndex) {
        return (way * this.waySize) + (virtualIndex);

    }

    private int getBlockOffset(Integer addr){
        return addr % blockSize;
    }

    private int presentAt(int tag, int virtualIndex){
        // check associativity
        int memVal;
        int realIndex;
        for(int way = 0; way < associativity; way += 1) {
            realIndex =  getRealIndex(way, virtualIndex);
            memVal = memory[realIndex];
            if(this.verbose)System.out.println("\tFound tag val " + memVal + " at real index " + realIndex);
            if(memVal == tag){
                if(this.verbose)System.out.println("\tmatch!");
                return way;
            }
        }
        if(this.verbose)System.out.println("\tno matches, not present");
        return -1;

    }

    private int getIndex(Integer addr){
        if(this.verbose)System.out.println(this.toString() + "\nGetting index for address " + addr);
        // shift left to get rid of tag bits, then logical shift right to get rid of the blockBits
        int temp1 = addr << tagBits;
        int temp2 = temp1 >>> (tagBits + blockBits + byteBits);
        return temp2;
    }
    private int getTag(Integer addr){
        return addr >>> (indexBits + blockBits + byteBits);
    }

    private int bitsFromIndices(double indices){
        return (int) Math.ceil(Math.log(indices) / Math.log(2));
    }

    public void printResult(int num)
    {
        System.out.println("Cache Num: " + num + "\t");
        System.out.println("Cache size: " + cacheSize + "\t");
        System.out.print("Associativity: " + associativity + "\t");
        System.out.println("Block size: " + blockSize);
        System.out.println("Hits: " + hits);
        System.out.println("Hit Rate: " + hitRate * 100 + "%");
        System.out.println("---------------------------");
    }

    @Override
    public String toString() {
        return "Cache{" +
                "cacheNum=" + cacheNum+
                "cacheSize=" + cacheSize +
                ", associativity=" + associativity +
                ", blockSize=" + blockSize +
                ", blockBits=" + blockBits +
                ", indexBits=" + indexBits +
                ", tagBits=" + tagBits +
                '}';
    }
}
