package com.androiddd.utils.java;

import java.util.Map;

/**
 * Created by liuzhenhua on 2017/11/13.
 */

public class JavaCollectionUtil {
    public static class MapBuilder<T extends Map<K, V>, K, V> {
        final private T mMap;

        public MapBuilder(T map) {
            mMap = map;
        }

        public MapBuilder<T, K, V> put(K k, V v) {
            mMap.put(k, v);
            return this;
        }

        public T build() {
            return mMap;
        }
    }
}
