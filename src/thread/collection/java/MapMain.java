package thread.collection.java;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapMain {
    public static void main(String[] args) {
        Map<Object, Object> map1 = new ConcurrentHashMap<>();
        map1.put(1, "data1");
        map1.put(2, "data2");
        map1.put(3, "data3");
    }
}