package social_network.graph;
import social_network.domain.Tuple;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph<T> {

    public final Map<T, LinkedList<T>> map = new HashMap<>();

    /**
     * Adds a node to the graph. If we don't already have a list
     * for the node we're adding, then we'll associate an empty one.
     * @param t T - the node we'll add
     */
    public void addNode(T t) {
        map.computeIfAbsent(t, k -> new LinkedList<>());
    }

    /**
     * The constructor firstly takes all the pair of nodes we'll store in graph
     * @param nodes - a list of pair of nodes
     */
    public Graph(List<Tuple<T, T>> nodes) {
        for (var node : nodes) {
            T first = node.getFirst();
            T second = node.getSecond();
            addNode(first);
            addNode(second);
        }

        for (var node : nodes) {
            map.get(node.getFirst()).push(node.getSecond());
            map.get(node.getSecond()).push(node.getFirst());
        }
    }
}
