import java.util.ArrayList;
import java.util.List;

public class CacheGroup {
    private static List<Cache> caches = new ArrayList<Cache>(){{
        add(new Cache(1, 2048, 1, 1));
        add(new Cache(2, 2048, 1, 2));
        add(new Cache(3, 2048, 1, 4));
        add(new Cache(4, 2048, 2, 1));
        add(new Cache(5, 2048, 4, 1));
        add(new Cache(6, 2048, 4, 4));
        add(new Cache(7, 4096, 1, 1));
    }};

    public static void feedAddress(Integer address){
        for(Cache cache : caches){
            cache.feed(address);
        }
    }

    public static void printAll(){
        int i = 1;
        for(Cache cache : caches){
            cache.printResult(i);
            i += 1;
        }
    }

    public static void setAllVerbose(boolean set){
        for(Cache cache : caches){
            cache.setVerbose(set);
        }
    }

    public static void setOneVerbose(int num, boolean v){
        caches.get(num - 1).setVerbose(v);
    }

}
