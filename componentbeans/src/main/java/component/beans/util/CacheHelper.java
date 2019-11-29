package component.beans.util;

public class CacheHelper<T> {
    T cache = null;

    public T getCache() {
        return cache;
    }

    public void setCache(T cache) {
        this.cache = cache;
    }
}
