package game.odyssey.engine.utils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

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

    public void bfs(T startVertex, Predicate<T> condition, Consumer<List<T>> consumer) {
        Queue<T> queue1 = new LinkedList<>();
        Queue<T> queue2 = new LinkedList<>();
        Set<T> visited = new HashSet<>();

        queue1.offer(startVertex);
        visited.add(startVertex);

        List<T> levelNodes = new LinkedList<>();

        while (!queue1.isEmpty() || !queue2.isEmpty()) {
            while (!queue1.isEmpty()) {
                T currentVertex = queue1.poll();
                levelNodes.add(currentVertex);

                for (T neighbor : map.get(currentVertex)) {
                    if (!visited.contains(neighbor)) {
                        queue2.offer(neighbor);
                        visited.add(neighbor);
                    }
                }
            }

            if (condition.test(levelNodes.get(levelNodes.size() - 1))) {
                consumer.accept(levelNodes);
                levelNodes = new LinkedList<>();
            }

            while (!queue2.isEmpty()) {
                T currentVertex = queue2.poll();
                levelNodes.add(currentVertex);

                for (T neighbor : map.get(currentVertex)) {
                    if (!visited.contains(neighbor)) {
                        queue1.offer(neighbor);
                        visited.add(neighbor);
                    }
                }
            }

            if (condition.test(levelNodes.get(levelNodes.size() - 1))) {
                consumer.accept(levelNodes);
                levelNodes = new LinkedList<>();
            }
        }
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
