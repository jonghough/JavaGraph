package jgh.javagraph.linegraph;

import jgh.javagraph.Graph;
import jgh.javagraph.IEdge;
import jgh.javagraph.IGraph;
import jgh.javagraph.INode;
import jgh.javagraph.algorithms.GraphSearch;
import jgh.javagraph.algorithms.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * <i>Line Graph, L(G),</i> of a graph <i>G</i>, defined to be the graph created by
 * considering adjacent edges as nodes, where adjacent edges are edges sharing
 * a common node. Any pair of adjacent edges creates a unique node.
 */
public final class LineGraph<E extends IEdge> {

    /**
     * Creates a Graph instance isomorphic to the Line Graph, <i>L(G)</i> of the
     * original graph, <i>G</i>. The nodes of <i>L(G)</i> are in a 1-to-1 correspondence
     * with the edges of <i>G</i>. The edges of <i>L(G)</i> are in a 1-to-1 correspondence
     * with every pair <i>(e1,e2)</i> of <b>incident edges</b>, <i>e1</i> and <i>e2</i> of
     * the original graph, <i>G</i>.
     *
     * @param graph   the graph, <i>G</i>
     * @param builder interface for creating the nodes and edges of the new graph. The methods
     *                of the interface must be defined in the implementation.
     * @param <E>     the edge type.
     * @return Line Graph, <i>L(G)</i>, of the original graph, <i>G</i>.
     */
    public static <N extends INode, E extends IEdge<N>> Graph<N,E> createLineGraph(Graph<N,E> graph, ILineGraphBuilder<N,E> builder) {

        final HashMap<E, N> edgeNodeMap = new HashMap<E, N>();
        final HashMap<Integer, E> edgeHashMap = new HashMap<Integer, E>();
        /*
         * perform a BFS through the edge set of the original graph and
         * create new nodes and edges of the line graph.
         */
        GraphSearch.searchEdgesBreadthFirst(graph, new GraphSearch.INextEdge<N,E>() {
            @Override
            public void onNextEdge(IGraph<N,E> graph, E previous, E current) {
                N newNode = builder.createNode(current);
                edgeNodeMap.put(current, newNode);

                ArrayList<E> incident = Utilities.getIncidentEdges(graph.getEdges(), current);
                for (E inc : incident) {
                    if (edgeNodeMap.containsKey(inc) && edgeNodeMap.get(inc) != newNode) {
                        E edge = builder.createEdge(edgeNodeMap.get(inc), newNode);
                        edgeHashMap.put((current.hashCode() + inc.hashCode()) * 7927 + 11 * current.hashCode() * inc.hashCode(), edge);
                    }
                }
            }

            @Override
            public boolean forceStop() {
                return false;
            }
        });

        return new Graph<N,E>(new ArrayList<E>(edgeHashMap.values()), new HashSet<N>(edgeNodeMap.values()));
    }


    /**
     * Interface for building the edges and nodes of a <code>LineGraph</code>.
     *
     * @param <E> edge type
     */
    public interface ILineGraphBuilder<N extends INode, E extends IEdge<N>> {

        /**
         * Creates a node of the LineGraph from the given edge. The edges of the
         * original graph are 1-to-1 with the nodes of the LineGraph.
         *
         * @param edge edge of original graph.
         * @return a new node object.
         */
        N createNode(E edge);

        /**
         * Creates an edge of the LineGraph from two adjacent nodes of the LineGraph.
         * The nodes will have been, in turn , created from the edges of the
         * original graph. The <i>from</i> node and the <i>to</i> node positions
         * are arbitrary.
         *
         * @param nodeFrom from node
         * @param nodeTo   tp node
         * @return a new Edge instance of the LineGraph.
         */
        E createEdge(N nodeFrom, N nodeTo);
    }
}
