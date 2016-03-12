package jgh.javagraph.cliques;


import jgh.javagraph.Graph;
import jgh.javagraph.IEdge;
import jgh.javagraph.INode;
import jgh.javagraph.algorithms.Utilities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;


/**
 * Collection of functions for handling <i>cliques</i>, where a <i>clique, C,</i> of a <i>Graph, G,</i> is defined
 * as a subgraph of <i>G</i> which is complete (i.e. all nodes of the subgraph are pairwise adjacent).
 */
public class Cliques {

    /**
     * Finds all the maximal cliques of the graph. That is, all cliques that are not subgraphs of another clique.
     *
     * @param graph Graph
     * @param <E>   Edge type
     * @return List of sets of nodes, where each set represents a clique.
     */
    public static <N extends INode, E extends IEdge<N>> ArrayList<HashSet<N>> findMaximalCliques(Graph<N,E> graph) {
        HashSet<N> R = new HashSet<>();
        HashSet<N> X = new HashSet<>();
        HashSet<N> P = new HashSet<>(graph.getNodes());

        ArrayList<HashSet<N>> cliques = findCliques(graph, R, X, P);
        return cliques;


    }

    /**
     * Returns the clique number of the graph. The <i>clique number</i> is the
     * size of the largest clique contained in the graph.
     *
     * @param graph graph
     * @param <E>   Edge type
     * @return clique number.
     */
    public static <N extends INode, E extends IEdge<N>> int getCliqueNumber(Graph<N,E> graph) {
        ArrayList<HashSet<N>> cliques = findMaximalCliques(graph);
        return cliques.stream().max(new Comparator<HashSet<N>>() {
            @Override
            public int compare(HashSet<N> o1, HashSet<N> o2) {
                if (o1.size() > o2.size()) return 1;
                else if (o1.size() < o2.size()) return -1;
                else return 0;
            }
        }).get().size();
    }


    private static <N extends INode, E extends IEdge<N>> ArrayList<HashSet<N>> findCliques(Graph<N,E> graph,
                                                                           HashSet<N> R, HashSet<N> X, HashSet<N> P) {
        ArrayList<HashSet<N>> nodeSetList = new ArrayList<>();
        if (P.isEmpty() && X.isEmpty()) {
            nodeSetList.add(R);
            return nodeSetList;
        } else {
            HashSet<N> used = new HashSet<>();
            for (N n : P) {
                if (used.contains(n))
                    continue;
                HashSet<N> R_ = new HashSet<>(R);
                HashSet<N> X_ = new HashSet<>(X);
                HashSet<N> P_ = new HashSet<>(Utilities.getAdjacent(graph.getEdges(), n));
                P_.removeAll(used);
                P_.retainAll(P);
                R_.add(n);
                X_.addAll(used);
                X_.retainAll(Utilities.getAdjacent(graph.getEdges(), n));
                nodeSetList.addAll(findCliques(graph, R_, X_, P_));
                used.add(n);

            }
        }
        return nodeSetList;
    }
}
