package LRUCache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LRUCacheImpl<T> implements LRUCache<T> {

    private final Map<String, T> cache;
    private final List<String> ages;
    private final int maxSize;

    public LRUCacheImpl(int maxItemsCount) {
        this.cache = new HashMap<>();
        this.ages = new LinkedList<>();
        this.maxSize = maxItemsCount;
    }

    @Override
    public T get(String key) {
        var ageOfKey = ages.indexOf(key);
        if (ageOfKey >= 0) {
            ages.remove(ageOfKey);
        }
        ages.add(key);
        return cache.get(key);
    }

    @Override
    public void set(String key, T value) {
        if (cache.size() >= this.maxSize) {
            var oldest = ages.remove(0);
            cache.remove(oldest);
        }
        cache.put(key, value);
        ages.add(key);
    }
}
