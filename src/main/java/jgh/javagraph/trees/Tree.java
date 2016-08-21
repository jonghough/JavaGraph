package jgh.javagraph.trees;

import jgh.javagraph.Graph;
import jgh.javagraph.IEdge;
import jgh.javagraph.IGraph;
import jgh.javagraph.algorithms.Connectivity;
import jgh.javagraph.algorithms.GraphSearch;

import java.util.*;

/**
 * Collection of static methods for generating trees and related functions.
 */
public class Tree {

    /**
     * Determines whether the given <code>Graph</code> is a tree. Tree's being defined as
     * connected graphs with no cycles.
     *
     * @param graph Graph to test.
     * @param <E>   Edge type
     * @return True if the graph is a tree, false otherwise.
     */
    public static <N, E extends IEdge<N>> boolean isTree(Graph<N,E> graph) {
        boolean graphIsTree = false;
        // iff connected graph and size of edge set is size of node set minus one,
        // the graph is a tree.
        if (Connectivity.getConnectedComponents(graph).size() == 1
                && graph.getEdges().size() == graph.getNodes().size() - 1)
            graphIsTree = true;
        return graphIsTree;
    }


    /**
     * Creates a <i>Tremaux Tree</i> graph of the original graph, using the same edges and nodes.
     * A <i>Tremaux Tree</i> is a spanning tree of the original graph such that any two adjacent
     * nodes are related to each other as parent and child in a depth first search generated sequence.
     *
     * @param graph
     * @param baseNode
     * @param <E>
     * @return
     */
    public static <N, E extends IEdge<N>> Graph<N,E> generateTremauxTree(Graph<N,E> graph, N baseNode) {
        ArrayList<E> edges = new ArrayList<>();

        final ArrayList<N> store = new ArrayList<>();
        store.add(baseNode);
        GraphSearch.searchDepthFirst(graph, baseNode, new GraphSearch.INextNode<N,E>() {
            @Override
            public void onNextNode(IGraph<N,E> g, N p, N c) {
                N lastNode = store.get(0);
                //add the edge connecting last edge to this current edge.
                for (E e : graph.getEdges()) {
                    if ((e.from() == c && e.to() == lastNode)
                            || e.from() == lastNode && e.from() == c) {
                        edges.add(e);
                        break;
                    }
                }

                store.add(0, c);
            }

            @Override
            public boolean forceStop() {
                return false;
            }
        });

        return new Graph<N,E>(edges, graph.getNodes());
    }


}
