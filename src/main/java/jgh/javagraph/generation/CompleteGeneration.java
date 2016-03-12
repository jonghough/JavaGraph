package jgh.javagraph.generation;

import jgh.javagraph.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Class containing static methods for generation of <i>complete graphs</i> and other
 * graphs derived from such graphs.
 */
public class CompleteGeneration {

    /**
     * Creates a complete graph on the nodes in the nodeSet.
     *
     * @param nodeSet
     * @return
     */
    public static <N extends INode> Graph<N, Edge<N>> create(HashSet<N> nodeSet) {
        ArrayList<N> nodeList = new ArrayList<N>(nodeSet);
        ArrayList<Edge<N>> edgeList = new ArrayList<Edge<N>>();
        for (int i = 0; i < nodeList.size() - 1; i++) {
            for (int j = i + 1; j < nodeList.size(); j++) {
                Edge e = new Edge(nodeList.get(i), nodeList.get(j));
                edgeList.add(e);
            }
        }
        Graph<N,Edge<N>> graph = new Graph<N,Edge<N>>(edgeList);
        return graph;

    }

    /**
     * Generates a complete graph on N nodes, where N is the size of the nodeSet. Weights are positive, in the range
     * [0,maxWeight] taken from a uniform distribution.
     *
     * @param nodeSet   Set of nodes for the graph
     * @param maxWeight the maximum possible weight.
     * @return Randomly positively weighted complete graph.
     */
    public static <N extends INode> Graph<N, WeightedEdge<N>> createRandomWeighted(HashSet<N> nodeSet, float maxWeight) {
        if (maxWeight < 0) throw new IllegalArgumentException("maxWeight must be non-negative.");
        Random random = new Random(System.currentTimeMillis());
        ArrayList<N> nodeList = new ArrayList<>(nodeSet);
        ArrayList<WeightedEdge<N>> edgeList = new ArrayList<WeightedEdge<N>>();
        for (int i = 0; i < nodeList.size() - 1; i++) {
            for (int j = i + 1; j < nodeList.size(); j++) {
                WeightedEdge e = new WeightedEdge(nodeList.get(i), nodeList.get(j), maxWeight * random.nextFloat());
                edgeList.add(e);
            }
        }
        Graph<N,WeightedEdge<N>> graph = new Graph<N,WeightedEdge<N>>(edgeList);
        return graph;
    }
}
