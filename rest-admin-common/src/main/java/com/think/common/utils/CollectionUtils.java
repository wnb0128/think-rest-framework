package com.think.common.utils;

import java.util.*;

/**
 * @Auther: think
 * @Date: 2020/1/6 21:02
 * @Description:集合工具类
 */
public class CollectionUtils {

    /**
     * flag = 1 正序
     * flag = 0 倒序
     * @param map
     * @param flag
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, int flag) {
        Map<K, V> sortMap = new LinkedHashMap<>();
        if(flag == 1) {
            map.entrySet().stream()
                    .sorted(Comparator.comparing(Map.Entry::getValue))
                    .forEach(entry -> sortMap.put(entry.getKey(), entry.getValue()));
        } else {
            map.entrySet().stream()
                    .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                    .forEach(entry -> sortMap.put(entry.getKey(), entry.getValue()));
        }
        return sortMap;
    }

    /**
     * set取差集操作
     * @param setOne
     * @param setTwo
     * @param <T>
     * @return
     */
    public static <T> Set<T> getDifferenceSet(Set<T> setOne, Set<T> setTwo){
        Set<T> result = new LinkedHashSet<T>();
        result.addAll(setOne);
        result.removeAll(setTwo);
        return result;
    }

    /**
     * set取交集操作
     * @param setOne
     * @param setTwo
     * @param <T>
     * @return
     */
    public static <T> Set<T> getAndSet(Set<T> setOne, Set<T> setTwo){
        Set<T> result = new LinkedHashSet<T>();
        result.addAll(setOne);
        result.retainAll(setTwo);
        return result;
    }

}
