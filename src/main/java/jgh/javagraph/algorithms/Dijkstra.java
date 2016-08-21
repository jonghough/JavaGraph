package jgh.javagraph.algorithms;

import jgh.javagraph.*;

import java.util.*;

/**
 * Implementation of <i>Dijkstra's Algorithm</i> to find the shortest path between two
 * nodes in a graph.
 */
public class Dijkstra {


    /**
     * Finds the minimum weight path between two nodes, <code>start</code>, and <code>finish</code>.
     * The weighted graph's edge weights must be non-negative.
     *
     * @param weightedGraph Weighted Graph
     * @param start         the initial node
     * @param finish        the goal node.
     * @param <N>           Node type
     * @return List of <code>INode</code>s representing the minimum path from <code>start</code> to
     * <code>finish</code>.
     */
    public static <N, E extends WeightedEdge<N>> ArrayList<N> findMinPath(Graph<N, E> weightedGraph, N start, N finish) {
        ArrayList<N> minPath = new ArrayList<N>();
        ArrayList<N> nodes = new ArrayList<N>(weightedGraph.getNodes());
        HashMap<N,NodeData<N>> nodeMap = new HashMap<N,NodeData<N>>();
        // set the initial conditions. The start node (start) has a temp
        // distance of 0
        // and all other nodes have a temp distance of max possible.
        for (N inode : nodes) {
            NodeData<N> nd = new NodeData<>(inode);
            if (inode == start) {
                nd.setDistance(0);
            } else {
                nd.setDistance(Float.MAX_VALUE);
            }
            nodeMap.put(inode, nd);
        }

        // loop until complete.
        while (true) {
            N min = null;
            // try to find an unvisited node.
            for (N t : nodes) {
                if (nodeMap.get(t).isVisited() == false) {
                    min = t;
                    break;
                }
            }
            if (min == null) {
                break;
            }


            // find the minimum node of all unvisited nodes.
            for (N t : nodes) {
                if (nodeMap.get(t).isVisited() == false && nodeMap.get(t).getDistance() <= nodeMap.get(min).getDistance()) {
                    min = t;
                }
            }

            nodeMap.get(min).setVisited(true);

            // update the distance of adjacent nodes if necessary.
            HashMap<N, Float> adjacent = getAdjacentNodesWithWeights(weightedGraph, min);
            Iterator<Map.Entry<N, Float>> it = adjacent.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<N, Float> entry = it.next();
                if (nodeMap.get(entry.getKey()).isVisited()) {
                    continue;
                }
                float poss = entry.getValue() + nodeMap.get(min).getDistance();

                if (poss < nodeMap.get(entry.getKey()).getDistance()) {
                    nodeMap.get(entry.getKey()).setDistance(poss);
                    nodeMap.get(entry.getKey()).setPrevious(Optional.of(min));

                }
            }
        }

        //build the path list.
        N p = nodeMap.get(finish).getPrevious().get(); //mPrevious
        N c = finish;               //current
        minPath.add(c);
        while (p != null && p != c) {
            minPath.add(p);
            c = p;
            Optional<N> on = nodeMap.get(p).getPrevious();
            p =  on.orElse(null);
        }
        return minPath;
    }

    /**
     * Returns the nodes adjacent to the given node on the graph. The returned nodes are given
     * as a HashMap of the node and edge weight for the weight of the edge incident with both nodes.
     *
     * @param graph Graph to search.
     * @param node  node to search from.
     * @param <N>   Node type.
     * @return hashmap of adjacent nodes and edge weights.
     */
    private static <N, E extends WeightedEdge<N>> HashMap<N, Float> getAdjacentNodesWithWeights(Graph<N, E> graph, N node) {
        HashMap<N, Float> adj = new HashMap<N, Float>();
        for (WeightedEdge<N> e : graph.getEdges()) {

            if (e.from() == node) {
                adj.put(e.to(), e.getWeight());
            } else if (e.to() == node) {
                adj.put(e.from(), e.getWeight());
            }
        }

        return adj;
    }
}
