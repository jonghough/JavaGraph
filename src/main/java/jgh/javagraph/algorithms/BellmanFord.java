package jgh.javagraph.algorithms;


import jgh.javagraph.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 * BellmanFord static methods
 */
public class BellmanFord {


    /**
     * Implements Bellman-Ford algorithm to find minimum path between two points on a weighted, directed graph, where
     * weights can be negative or positive.
     *
     * @param graph Weighted, directed graph.
     * @param nodeS Start node
     * @param nodeF Final node
     * @param <E>   Weighted Edge type
     * @return ArrayList of minimum path nodes in order.
     * @throws AlgorithmException If negative cycle is found.
     */
    public static <N, E extends DirectedWeightedEdge<N>> ArrayList<N> findMinPath(Graph<N, E> graph,
                                                                                                N nodeS, N nodeF)
            throws AlgorithmException {
        ArrayList<N> path = new ArrayList<N>();
        ArrayList<N> nodes = new ArrayList<N>(graph.getNodes());
        HashMap<N,NodeData<N>> nodeMap = new HashMap<N,NodeData<N>>();

        for (N t : nodes) {
            NodeData<N> n = new NodeData<>(t);
            if (t == nodeS)
                n.setDistance(0);
            else
                n.setDistance(Float.MAX_VALUE);// essentially infinity
            // all mPrevious are null
            n.setPrevious(Optional.empty());
            nodeMap.put(t,n);
        }

        ArrayList<N> edgeNodes = new ArrayList<N>();
        for (int i = 0; i < nodes.size(); i++) {
            for (E edge : graph.getEdges()) {
                if (edge.getDirection() == Direction.FORWARDS) {
                    float tmp = nodeMap.get(edge.from()).getDistance() + edge.getWeight();
                    if (tmp < nodeMap.get(edge.to()).getDistance()) {
                        nodeMap.get(edge.to()).setDistance(tmp);
                        nodeMap.get(edge.to()).setPrevious(Optional.of(edge.from()));
                    }
                } else if (edge.getDirection() == Direction.BACKWARDS) {
                    float tmp = nodeMap.get(edge.to()).getDistance() + edge.getWeight();
                    if (tmp < nodeMap.get(edge.from()).getDistance()) {
                        nodeMap.get(edge.from()).setDistance(tmp);
                        nodeMap.get(edge.from()).setPrevious(Optional.of(edge.from()));
                    }
                }
            }
        }

        // search for negative weight cycles.
        for (E edge : graph.getEdges()) {

            float tmp = nodeMap.get(edge.from()).getDistance() + edge.getWeight();
            if (tmp < nodeMap.get(edge.to()).getDistance()) {
                throw new AlgorithmException("Negative cycle found.");
            }
        }


        N p = (N)nodeMap.get(nodeF).getPrevious().get();
        path.add((N)nodeF);
        while (p != null && p != nodeMap.get(p).getPrevious().orElse(null)) {
            path.add(p);
            p = nodeMap.get(p).getPrevious().orElse(null);
        }
        return path;
    }
}
