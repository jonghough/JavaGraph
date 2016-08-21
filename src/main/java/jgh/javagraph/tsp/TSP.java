package jgh.javagraph.tsp;

import jgh.javagraph.*;
import jgh.javagraph.algorithms.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Collection of static methods with connections to <i>Hamiltonian cycles, paths</i> and
 * the <i> Travelling Salesman Problem.</i>
 */
public class TSP {

    /**
     * Determines whether the graph contains the ore property. The ore preoprty states that
     * if the number of nodes on the graph is greater than 2, and  the sum of the degrees
     * of any two non-adjacent nodes is at least the number of nodes on the graph, <br>
     * ie. <i> deg(u) + deg(v) >= N, for non-adjacent u,v and where N is number of nodes.</i>
     * <br>
     * If the graph has the ore property then the existence of a <i>Hamiltonian cycle</i> is
     * guaranteed. The converse is nt true.
     *
     * @param graph Graph to search
     * @param <E>   Edge type
     * @return true if property holds, false otherwise.
     */
    public static <N, E extends IEdge<N>> boolean hasOreProperty(Graph<N,E> graph) {
        if (graph.getNodes().size() < 3)
            return false;

        HashMap<N,NodeData<N>> nodeMap = new HashMap<N,NodeData<N>>();
        for (N n : graph.getNodes()) {
            nodeMap.put(n, new NodeData<N>(n));
        }


        ArrayList<N> nodes = new ArrayList<N>(graph.getNodes());
        for (int i = 0; i < nodes.size(); i++) {
            if (nodeMap.get(nodes.get(i)).isVisited())
                continue;
            nodeMap.get(nodes.get(i)).setVisited(true);
            int d1 = graph.degree(nodes.get(i));
            HashSet<N> adj = new HashSet<N>(Utilities.getAdjacentUnvisited(nodeMap, graph.getEdges(), nodes.get(i)));
            HashSet<N> notAdj = new HashSet<N>(graph.getNodes());
            notAdj.removeAll(adj);
            for (N n : notAdj) {
                nodeMap.get(n).setVisited(true);
                int d2 = graph.degree(n);
                if (d1 + d2 < graph.getNodes().size())
                    return false;
            }
        }
        return true;
    }

    /**
     * Determines whether the graph contains the Dirac property. The ore property states that
     * if the minimum degree of all nodes on a graph on at least 3 nodes is greater than
     * or equal to <i>N/2</i>, where <i>N</i> is the total number of nodes, then the graph
     * contains a Hamiltonian cycle. The converse is not true.
     *
     * @param graph Graph to search
     * @param <E>   Edge type
     * @return true if property holds, false otherwise.
     */
    public static <N, E extends IEdge<N>> boolean hasDiracProperty(Graph<N,E> graph) {
        if (graph.getNodes().size() < 3)
            return false;

        int minDegree = Integer.MAX_VALUE;
        for (N n : graph.getNodes()) {
            int d = graph.degree(n);
            if (d < minDegree)
                minDegree = d;
        }

        return minDegree >= graph.getNodes().size() * 0.5f;
    }
}
