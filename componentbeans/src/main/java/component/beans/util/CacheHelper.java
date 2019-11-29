package component.beans.util;

import java.util.function.UnaryOperator;

public class CacheHelper<T> {
    T cache = null;

    public T getCache() {
        return cache;
    }

    public void setCache(T cache, UnaryOperator<T> cloner) {
        if (cache == null) return;
        this.cache = cloner.apply(cache);
    }
}
