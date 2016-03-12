package jgh.javagraph.algorithms;


import jgh.javagraph.*;

import java.util.ArrayList;

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
    public static <N extends INode, E extends DirectedWeightedEdge<N>> ArrayList<N> findMinPath(Graph<N, E> graph,
                                                                                                INode nodeS, INode nodeF)
            throws AlgorithmException {
        ArrayList<N> path = new ArrayList<N>();
        ArrayList<N> nodes = new ArrayList<N>(graph.getNodes());
        for (N t : nodes) {
            if (t == nodeS)
                t.setDistance(0);
            else
                t.setDistance(Float.MAX_VALUE);// essentially infinity
            // all mPrevious are null
            t.setPrevious(null);
        }

        ArrayList<N> edgeNodes = new ArrayList<N>();
        for (int i = 0; i < nodes.size(); i++) {
            for (E edge : graph.getEdges()) {
                if (edge.getDirection() == Direction.FORWARDS) {
                    float tmp = edge.from().getDistance() + edge.getWeight();
                    if (tmp < edge.to().getDistance()) {
                        edge.to().setDistance(tmp);
                        edge.to().setPrevious(edge.from());
                    }
                } else if (edge.getDirection() == Direction.BACKWARDS) {
                    float tmp = edge.to().getDistance() + edge.getWeight();
                    if (tmp < edge.from().getDistance()) {
                        edge.from().setDistance(tmp);
                        edge.from().setPrevious(edge.from());
                    }
                }
            }
        }

        // search for negative weight cycles.
        for (E edge : graph.getEdges()) {

            float tmp = edge.from().getDistance() + edge.getWeight();
            if (tmp < edge.to().getDistance()) {
                throw new AlgorithmException("Negative cycle "+edge.from().getLabel()+", "+
                        edge.to().getLabel()+", weight:\n "+edge.getWeight()+", distances\n"+
                        edge.from().getDistance()+", "+edge.to().getDistance());
            }
        }


        N p = (N)nodeF.getPrevious();
        path.add((N)nodeF);
        while (p != null && p != p.getPrevious()) {
            path.add(p);
            p = (N)p.getPrevious();
        }
        return path;
    }
}
