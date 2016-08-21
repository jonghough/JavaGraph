package jgh.javagraph.trees;

import jgh.javagraph.*;
import jgh.javagraph.algorithms.GraphSearch;
import jgh.javagraph.algorithms.Utilities;

import java.util.*;

/**
 * Algorithms for finding the minimum spanning tree of graphs.
 */
public class SpanningTree {

    /**
     * Generates the minimum spanning tree of the <b><i>connected</i></b>, weighted graph,
     * using an implementation of <i>Kruskal's algorithm</i>.
     * If the graph is not connected then the result will be wrong.
     *
     * @param weightedGraph connected, weighted graph
     * @return List of edges in minimum spanning tree.
     */
    public static <N> ArrayList<WeightedEdge<N>> generateMinimumSpanningTree(Graph<N,WeightedEdge<N>> weightedGraph) {

        ArrayList<WeightedEdge<N>> gcopy = new ArrayList<WeightedEdge<N>>(weightedGraph.getEdges());
        Collections.sort(gcopy);
        ArrayList<WeightedEdge<N>> stEdges = new ArrayList<WeightedEdge<N>>();

        DisjointSet<N,WeightedEdge<N>> disjointSet = new DisjointSet<N,WeightedEdge<N>>(stEdges);
        while (gcopy.isEmpty() == false) {
            WeightedEdge<N> first = gcopy.get(0);

            if(disjointSet.canAddEdge(first)){

                stEdges.add(first);
            }
            gcopy.remove(0);
        }
        return stEdges;
    }

    /**
     * Generates some spanning tree for the <i>connected<i> graph. The graph is assumed to be connected,
     * so if an unconnected graph is used as an argument, the algorithm will return a list of
     * edges forming a spanning tree for some connected subgraph of the original graph.
     *
     * @param graph graph
     * @param <E> edge type
     * @return list of edges forming a spanning tree
     */
    public static <N, E extends IEdge<N>> ArrayList<E> generateSpanningTree(Graph<N,E> graph) {

        final ArrayList<E> stEdges = new ArrayList<E>();
        final DisjointSet<N,E> disjointSet = new DisjointSet<N,E>(stEdges);
        GraphSearch.searchEdgesDepthFirst(graph, new GraphSearch.INextEdge<N,E>() {
            @Override
            public void onNextEdge(IGraph<N,E> graph, E previous, E current) {
                if(disjointSet.canAddEdge(current)) {

                    stEdges.add(current);
                }
            }

            @Override
            public boolean forceStop() {
                return false;
            }
        });

        return stEdges;
    }


    /**
     * Generates a spanning tree of the given edge list, if one is possible. If the edge
     * list is not a list of connected edges then the returned list will be some connected
     * subset of the original list, forming a partial spanning tree.
     * @param edgeList list of edges to find a spannign tree from.
     * @param <E> edge type
     * @return list of edges forming a spanning tree
     */
    public static <N, E extends IEdge<N>> ArrayList<E> generateSpanningTree(List<E> edgeList) {
        ArrayList<E> gcopy = new ArrayList<E>(edgeList);
        ArrayList<E> stEdges = new ArrayList<E>();
        DisjointSet<N,E> disjointSet = new DisjointSet<N,E>(stEdges);
        for (E e : gcopy) {
            if(disjointSet.canAddEdge(e)) {

                stEdges.add(e);
            }
        }
        return stEdges;
    }
}
