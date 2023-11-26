package game.odyssey.engine.utils;

import java.util.*;

public class Graph<T>  {
    private final Map<T, List<T>> map = new HashMap<>();

    public void addVertex(T s) {
        map.put(s, new LinkedList<>());
    }

    public void addEdge(T source, T destination, boolean bidirectional) {
        if (!map.containsKey(source)) return;

        if (!map.containsKey(destination)) return;

        map.get(source).add(destination);

        if (bidirectional) {
            map.get(destination).add(source);
        }
    }

    public void getVertexCount() {
        System.out.println("The graph has " + map.keySet().size() + " vertices");
    }

    public void getEdgesCount(boolean bidirection) {
        int count = 0;
        for (T v : map.keySet()) {
            count += map.get(v).size();
        }
        if (bidirection) {
            count = count / 2;
        }
        System.out.println("The graph has " + count + " edges.");
    }

    public Set<T> getVertex() {
        return map.keySet();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (T v : map.keySet()) {
            builder.append(v.toString()).append(": ");
            for (T w : map.get(v)) {
                builder.append(w.toString()).append(" ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}
