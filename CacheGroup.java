import java.util.ArrayList;
import java.util.List;

public class CacheGroup {
    private static List<Cache> caches = new ArrayList<>(){{
        add(new Cache(2000, 1, 1));
        add(new Cache(2000, 1, 2));
        add(new Cache(2000, 1, 4));
        add(new Cache(2000, 2, 1));
        add(new Cache(2000, 4, 1));
        add(new Cache(2000, 4, 4));
        add(new Cache(4000, 1, 1));
    }};

    public static void feedAddress(Integer address){
        for(Cache cache : caches){
            cache.feed(address);
        }
    }

}
