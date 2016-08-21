package jgh.javagraph.mincut;


import jgh.javagraph.Graph;
import jgh.javagraph.IEdge;
import jgh.javagraph.algorithms.Utilities;

import java.util.ArrayList;
import java.util.Random;

public class MinCut {

    /**
     * Finds a <i>minimum cut</i> of the given connected graph. The method uses a
     * randomized algorithm with s probability that the returned cut will not
     * be minimum.
     *
     * @param graph Graph object
     * @param <E>   Edge type
     * @return List of edges in the (minimum) cut.
     */
    public static <N, E extends IEdge<N>> ArrayList<ModifiableEdge<N>> findMinCut(Graph<N,E> graph) {
        ArrayList<ModifiableEdge<N>> edgeList = new ArrayList<ModifiableEdge<N>>();
        for (E e : graph.getEdges()) {
            ModifiableEdge me = new ModifiableEdge(e.from(), e.to());
            edgeList.add(me);
        }


        Graph<N,ModifiableEdge<N>> modGraph = new Graph<N,ModifiableEdge<N>>(edgeList, graph.getNodes());
        Random r = new Random();
        while (modGraph.getNodes().size() > 2) {

            int next = r.nextInt(modGraph.getEdges().size());
            ModifiableEdge<N> removeEdge = modGraph.getEdges().get(next);
            contractEdge(modGraph, removeEdge);
        }

        return modGraph.getEdges();
    }

    /**
     * Contract the given edge on the graph. If the edge does not exist on the graph this method will
     * throw an <code>IllegalArgumentException</code>.
     *
     * @param graph
     * @param edge
     * @param <E>
     */
    private static <N, E extends ModifiableEdge<N>> void contractEdge(Graph<N,E> graph, E edge) {
        if (graph.getEdges().contains(edge) == false)
            throw new IllegalArgumentException("Edge does not exist on this graph.");
        //arbitrary, choose the form node to make the new node.
        N newNode = edge.from();
        ArrayList<E> incident = Utilities.getIncidentEdges(graph.getEdges(), edge.to());
        ArrayList<E> toRemove = new ArrayList<>();
        toRemove.add(edge);
        for (E e : incident) {
            if (e == edge)
                continue;
            else if (e.from() == edge.to()) {
                if (e.to() == newNode) {
                    toRemove.add(e);
                    continue;
                }
                e.setFrom(newNode);
            } else if (e.to() == edge.to()) {
                if (e.from() == newNode) {
                    toRemove.add(e);
                    continue;
                }
                e.setTo(newNode);
            }
        }

        graph.getNodes().remove(edge.to());
        graph.getEdges().removeAll(toRemove);
    }
}
