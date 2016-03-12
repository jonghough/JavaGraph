package jgh.javagraph.generation;

import jgh.javagraph.Edge;
import jgh.javagraph.Graph;
import jgh.javagraph.IEdge;
import jgh.javagraph.INode;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Generates a cyclic graph.
 */
public class CycleGeneration {

    /**
     * Generate a cyclic graph from the given node set. The neighbours of each node are arbitrary.
     *
     * @param nodeSet
     * @return
     */
    public static <N extends INode> Graph<N,Edge<N>> generate(HashSet<N> nodeSet) {
        if (nodeSet == null) throw new IllegalArgumentException("Cannot pass null node set.");
        else if (nodeSet.size() == 0) throw new IllegalArgumentException("Cannot pass empty set.");

        ArrayList<N> nodes = new ArrayList<N>(nodeSet);
        ArrayList<Edge<N>> edges = new ArrayList<Edge<N>>();

        for (int i = 0; i < nodes.size(); i++) {
            int j = (i + 1) % nodes.size();
            Edge e = new Edge(nodes.get(i), nodes.get(j));
            edges.add(e);
            System.out.println("connecting " + i + ", " + j);
        }
        System.out.println("edge count " + edges.size());
        Graph<N,Edge<N>> g = new Graph(edges);
        return g;
    }
}
