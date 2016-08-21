package jgh.javagraph.algorithms;


import jgh.javagraph.*;
import jgh.javagraph.trees.SpanningTree;

import java.util.*;
import java.util.stream.Stream;


/**
 * Collection of static utility functions
 */
public class Utilities {


    /**
     * Simple test that a graph is complete.
     *
     * @param graph
     * @param <E>
     * @return
     */
    public static <N, E extends IEdge<N>> boolean isComplete(Graph<N, E> graph) {
        int e = graph.getEdges().size();
        int n = graph.getNodes().size();
        if (e == n * (n - 1) / 2)
            return true;
        else
            return false;
    }

    /**
     * Merges two graphs into a larger graph. THe returned graph will contain the union of the
     * two graphs' edge sets and node sets.
     *
     * @param graph1 first graph to merge
     * @param graph2 second graph to merge
     * @return merged graph, the sum of the two graphs.
     */
    public static <N, E extends IEdge<N>> Graph<N,E> mergeGraphs(Graph<N,E> graph1, Graph<N,E> graph2) {
        if (graph1 == null || graph2 == null) throw new IllegalArgumentException("Cannot pass a null Graph.");
        Collection<E> edges1 = graph1.getEdges();
        Collection<E> edges2 = graph2.getEdges();
        edges1.addAll(edges2);

        return new Graph<N,E>(new ArrayList<E>(edges1));
    }

    /**
     * Gets all edges incident with the given edge. i.e. it returns a list of
     * all edges with a node contained in the edge's node set.
     *
     * @param edgeList
     * @param edge
     * @return
     */
    public static <E extends IEdge> ArrayList<E> getIncidentEdges(Collection<E> edgeList,
                                                                  E edge) {
        ArrayList<E> filtered = new ArrayList<E>();
        for (E e : edgeList) {
            if ((e.nodes().contains(edge.from()) || e.nodes().contains(edge.to()))) {
                filtered.add(e);
            }
        }
        return filtered;
    }

    /**
     * Gets the incident edges of the given node.
     *
     * @param edgeList List of possible edges.
     * @param node     given node.
     * @return ArrayList of edges which are incident with the given node.
     */
    public static <N, E extends IEdge> ArrayList<E> getIncidentEdges(
            Collection<E> edgeList, N node) {
        ArrayList<E> filtered = new ArrayList<E>();

        for (E e : edgeList) {
            if ((e.nodes().contains(node))) {
                filtered.add(e);
            }
        }
        return filtered;
    }

    /**
     * Determines if nodes <i>t1</i> and <i>t2</i> are adjacent on the graph. Nodes are
     * adjacent if they share a common edge
     *
     * @param t1    first node
     * @param t2    second node
     * @param graph graph
     * @param <E>   edge type
     * @return True if the nodes are connected, false otherwise.
     */
    public static <N, E extends IEdge<N>> boolean isAdjacent(N t1, N t2, Graph<N,E> graph) {
        Stream<E> streamt1 = graph.getEdges().stream();
        Stream<E> streamt2 = streamt1.filter(e -> e.nodes().contains(t1)
                && e.nodes().contains(t2));
        if (streamt2.count() > 0)
            return true;
        else
            return false;

    }

    /**
     * Tests if the two nodes <i>node1</i> and <i>node2</i> are adjacent on the edge list.
     * If the nodes are identical then this method returns false.
     *
     * @param edgeList list of edges to consider.
     * @param node1    node on the edge list
     * @param node2    node on the edge list
     * @param <E>      the edge type.
     * @return True if the edges are adjacent, false otherwise.
     */
    public static <N, E extends IEdge> boolean isAdjacent(ArrayList<E> edgeList, N node1, N node2) {
        for (E e : edgeList) {
            if (e.from() == node1 && e.to() == node2) {
                return true;
            } else if (e.to() == node1 && e.from() == node2) {
                return true;
            }
        }
        return false;
    }


    /**
     * Gets all adjacent nodes to the given node. The adjacent nodes exist on an
     * edge containing the given node.
     *
     * @param edgeList
     * @param node
     * @return
     */
    public static <N, E extends IEdge<N>> ArrayList<N> getAdjacent(Collection<E> edgeList, N node) {
        HashSet<N> adjacent = new HashSet<N>();
        for (E e : edgeList) {
            if ((e.nodes().contains(node))) {
                adjacent.addAll(e.nodes());
            }
        }
        adjacent.remove(node);
        return new ArrayList<N>(adjacent);
    }


    /**
     * Gets all adjacent and unvisited nodes to the current node.
     *
     * @param edgeList List of edges to consider.
     * @param node     Current node.
     * @param <E>      Edge type
     * @return List of adjacent, unvisited nodes.
     */
    public static <N, E extends IEdge<N>> ArrayList<N> getAdjacentUnvisited(HashMap<N,NodeData<N>> nodeMap, Collection<E> edgeList, N node) {
        HashSet<N> adjacent = new HashSet<N>();
        for (E e : edgeList) {
            if ((e.nodes().contains(node))) {
                N other = null;
                if (node == e.from())
                    other = e.to();
                else other = e.from();
                if (nodeMap.get(other).isVisited() == false) {
                    adjacent.add(other);
                }
            }
        }
        adjacent.remove(node);
        return new ArrayList<N>(adjacent);
    }

    /**
     * Generates random weights for a graph, <code>Graph<Edge></Edge></code>, between the min and max weights (uniformly distributed).
     *
     * @param graph     the graph, with weightless edges.
     * @param minWeight the minimum weight
     * @param maxWeight the maximum weight
     * @return Weighted Graph
     */
    public static <N> Graph<N, WeightedEdge<N>> generateRandomWeights(Graph<N,Edge<N>> graph, float minWeight,
                                                            float maxWeight) {

        Random random = new Random(System.currentTimeMillis());

        ArrayList<WeightedEdge<N>> weList = new ArrayList<WeightedEdge<N>>();
        //visit all the edges and wrap them with a weighted edge.
        EdgeConversionVisitor ecv = new EdgeConversionVisitor();
        for (Edge edge : graph.getEdges()) {
            float weight = random.nextFloat() * (maxWeight - minWeight) + minWeight;
            ecv.visit(edge);
            WeightedEdge we = ecv.getEdge();
            we.setWeight(weight);
            weList.add(we);
        }

        return new Graph<N, WeightedEdge<N>>(weList, graph.getNodes());
    }

    /**
     * Generates a weighted graph with weights taken uniformly from the domain [minWeight, maxWeight].
     * Random directions are also assigned to the edges of the graph, with an even probability of
     * being a <i>from->to</i> edge or a <i>to->from</i> edge.
     *
     * @param graph     Unweighted, undirected graph.
     * @param minWeight minimum possible weight value, must be less than maxWeight
     * @param maxWeight maximum possible weight value
     * @return Weighted, directed graph.
     */
    public static <N> Graph<N, DirectedWeightedEdge<N>> generateRandomWeightsAndDirection(Graph<N, Edge<N>> graph, float minWeight,
                                                                                float maxWeight) {
        if (minWeight > maxWeight)
            throw new IllegalArgumentException("Minimum weight value must be less than or equal to" +
                    "maximum weight value.");
        Random random = new Random(System.currentTimeMillis());

        ArrayList<DirectedWeightedEdge<N>> weList = new ArrayList<DirectedWeightedEdge<N>>();
        //visit all the edges and wrap them with a weighted edge.
        DirectedEdgeConversionVisitor ecv = new DirectedEdgeConversionVisitor();
        for (Edge edge : graph.getEdges()) {
            float weight = random.nextFloat() * (maxWeight - minWeight) + minWeight;
            ecv.visit(edge);
            DirectedWeightedEdge we = ecv.getEdge();
            we.setWeight(weight);
            weList.add(we);
        }
        return new Graph<N,DirectedWeightedEdge<N>>(weList, graph.getNodes());
    }

    /**
     * Checks if the current edge is disjoint from the edge list.
     *
     * @param edgeList
     * @param edge
     * @return
     */
    public static <N,E extends IEdge> boolean isDisjoint(ArrayList<E> edgeList, E edge) {
        HashSet<N> nodeHash = new HashSet<N>();
        for (E e : edgeList) {
            nodeHash.addAll(e.nodes());
        }
        Iterator<N> it = edge.nodes().iterator();
        int contains = 0;
        while (it.hasNext()) {
            N t = it.next();
            if (nodeHash.contains(t))
                contains++;
        }
        if (contains < 2) {
            return true;
        } else
            return false;
    }

    /**
     * Checks whether the graph contains duplicate edges, edges containing the same pair of
     * nodes. If edge collision is found the method returns false, otherwise it will return
     * true.
     *
     * @param graph graph to test
     * @param <E>   Edge type
     * @return True if all edges are distinct, false otherwise.
     */
    public static <N, E extends IEdge<N>> boolean areGraphEdgesDistinct(Graph<N,E> graph) {
        // must check pairwise no two edges share exactly the same nodes.
        // O(n^2) check.
        ArrayList<E> edgeList = new ArrayList<E>(graph.getEdges());
        for (int i = 0; i < edgeList.size() - 1; i++) {
            for (int j = i + 1; j < edgeList.size(); i++) {
                E e = edgeList.get(i);
                E f = edgeList.get(j);
                if (e.nodes().contains(f.from()) && e.nodes().contains(f.to())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determines whether the graph is connected if the given edge is removed.
     *
     * @param graph Graph to consider.
     * @param edge  Edge to remove from edge set.
     * @param <E>   Edge type.
     * @return True if the remaining edge set creates a connected set, false otherwise.
     */
    public static <N, E extends IEdge<N>> boolean isConnectedWithoutEdge(Graph<N,E> graph, E edge) {
        if (!graph.getEdges().contains(edge))
            throw new IllegalArgumentException("The edge must be contained in the graph.");
        ArrayList<E> edgeList = new ArrayList<>(graph.getEdges());
        edgeList.remove(edge);

        // If a maximal spanning tree contains as many edges as the edge list then
        // it must be connected.
        if (SpanningTree.generateSpanningTree(edgeList).size() == edgeList.size())
            return true;
        return false;
    }

}