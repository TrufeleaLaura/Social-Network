package social_network.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * a class used for manipulating graphs.
 * @param <T>
 */
public class Components<T> {

    private Graph<T> graph;

    public Components(Graph<T> graph) {
        this.graph = graph;
    }

    /**
     * a classic depth first method, recursively.
     * @param node the current node that is visited.
     * @param visited to remember the nodes that were already visited.
     * @param currentComponent where are stored all the new nods.
     */
    private void DFS(T node, Set<T> visited, List<T> currentComponent) {
        if (visited.contains(node)) return;
        visited.add(node);
        currentComponent.add(node);
        for (T nextNode : graph.map.get(node)) {
            if (!visited.contains(nextNode)) {
                DFS(nextNode, visited, currentComponent);
            }
        }
    }

    /**
     * Computes the number of the connected components in the graph
     * @return a list of lists of elements representing the list of components
     */
    public List<List<T>> ConnectedComponents() {
        List<List<T>> components = new ArrayList<>();
        Set<T> visitedNodes = new HashSet<>();
        for (var node : graph.map.keySet()) {
            if (!visitedNodes.contains(node)) {
                List<T> currentComponent = new ArrayList<>();
                DFS(node, visitedNodes, currentComponent);
                components.add(currentComponent);
            }
        }
        return components;
    }
    /**
     * a DFS recursive function which is creating a new set for every node we're visiting
     * @param nod the node we're currently visiting
     * @param visited a set of the already visited nodes
     * @param longestList the maximum size we've found in the given component with the
     *                    nodes visited in the visited set and currently beign on the *node* node
     */
    private void longestDfsUtil(T nod, Set<T> visited, List<T> currentList, List<T> longestList) {
        int setSize = visited.size();
        if (setSize > longestList.size()) {
            longestList.clear();
            longestList.addAll(currentList);
        }
        for (T nextNode : graph.map.get(nod)) {
            if (visited.contains(nextNode)) continue;
            Set<T> newVisited = new HashSet<>(visited);
            List<T> newCurrentList = new ArrayList<>(currentList);
            newVisited.add(nextNode);
            newCurrentList.add(nextNode);
            longestDfsUtil(nextNode, newVisited, newCurrentList, longestList);
        }
    }

    /**
     * Get the longest path in a given component
     * @param component a list of the elementss forming the component
     * @return an integer representing the length of the longest path we've found
     */
    public List<T> longestPathInComponent(List<T> component) {
        List<T> longestPath = new ArrayList<>();
        for (T node : component) {
            Set<T> visitedNodes = new HashSet<>();
            List<T> currentList = new ArrayList<>();
            List<T> currentLongestPath = new ArrayList<>();
            visitedNodes.add(node);
            currentList.add(node);
            longestDfsUtil(node, visitedNodes, currentList, currentLongestPath);
            if (currentLongestPath.size() > longestPath.size())
                longestPath = currentLongestPath;
        }
        return longestPath;
    }



}
