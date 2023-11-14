package LRUCache;

public class LRUCacheProvider {
    public static <T> LRUCache<T> createLRUCache(CacheLimits options) {
        return new LRUCacheImpl<T>(options.getMaxItemsCount());
    }
}
