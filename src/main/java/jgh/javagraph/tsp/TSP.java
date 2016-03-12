package jgh.javagraph.tsp;

import jgh.javagraph.Graph;
import jgh.javagraph.IEdge;
import jgh.javagraph.INode;
import jgh.javagraph.WeightedEdge;
import jgh.javagraph.algorithms.Utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * Collection of static methods with connections to <i>Hamiltonian cycles, paths</i> and
 * the <i> Travelling Salesman Problem.</i>
 */
public class TSP {

    /**
     * Finds a Hamiltonian Cycle in the given weighted graph. The cycle found, will almost certainly
     * not be optimal (minimum weight), and is simply the first cycle found, if one exists. If no such
     * cycle exists, then returns null.
     *
     * @param graph graph to search for hamiltonian cycle on.
     * @param <W>   Weighted edge type
     * @return ArrayList of nodes in the cycle (in order) if such a cycle exists, null otherwise.
     */
    public static <N extends INode, W extends WeightedEdge<N>> ArrayList<N> FindHamiltonianCycle(Graph<N,W> graph) {
        for (N n : graph.getNodes()) {
            n.setVisited(false);
        }
        for (W e : graph.getEdges()) {
            e.setVisited(false);
        }


        // Get the initial start node. Search for cycles from this node.
        // Because the graph is connected we will be able to find all possible
        // cycles.
        N first = null;

        for (N n : graph.getNodes()) {
            first = n;
            break;
        }


        ArrayList<N> adjacent = Utilities.getAdjacent(graph.getEdges(), first);

        // begin the cycle search
        for (N adj : adjacent) {
            Stack<N> path = new Stack<N>();
            path.push(first);
            ArrayList<N> cycle = findCycle(graph, first, adj, path);
            if (cycle != null)
                return cycle;
        }
        return null;
    }


    /**
     * Recursive cycle finding method.
     *
     * @param graph    graph to search
     * @param previous mPrevious node
     * @param current  current node
     * @param path     current path
     * @param <E>      edge type
     * @return cycle, as a list.
     */
    private static <N extends INode, E extends IEdge<N>> ArrayList<N> findCycle(Graph<N,E> graph, N previous, N current, Stack<N> path) {


        for (int i = 1; i < path.size(); i++) {
            if (path.get(i) == current) {

                return null;
            }
        }
        if (path.get(0) == current) {
            if (path.size() == graph.getNodes().size()) {
                ArrayList<N> cycle = new ArrayList<N>(path);
                return cycle;
            } else {
                return null;
            }
        }


        ArrayList<E> edgeList = new ArrayList<E>(graph.getEdges());
        ArrayList<E> incident = Utilities.getIncidentEdges(edgeList, current);

        path.push(current);
        HashSet<N> currentHS = new HashSet<N>();
        currentHS.add(current);
        for (E e : incident) {
            if (e.isVisited() == false) {

                e.setVisited(true);
                HashSet<N> searchNodes = new HashSet<N>(e.nodes());
                searchNodes.remove(current);

                for (N n : searchNodes) {
                    if (n != previous) {

                        ArrayList<N> cycle = findCycle(graph, current, n, path);
                        if (cycle != null)
                            return cycle;
                    }
                }
            }
            e.setVisited(false);
        }

        path.pop();
        return null;
    }


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
    public static <N extends INode, E extends IEdge<N>> boolean hasOreProperty(Graph<N,E> graph) {
        if (graph.getNodes().size() < 3)
            return false;

        //set all nodes to unvisitied
        for (N n : graph.getNodes()) {
            n.setVisited(false);
        }

        ArrayList<N> nodes = new ArrayList<N>(graph.getNodes());
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).isVisited())
                continue;
            nodes.get(i).setVisited(true);
            int d1 = graph.degree(nodes.get(i));
            HashSet<N> adj = new HashSet<N>(Utilities.getAdjacentUnvisited(graph.getEdges(), nodes.get(i)));
            HashSet<N> notAdj = new HashSet<N>(graph.getNodes());
            notAdj.removeAll(adj);
            for (N n : notAdj) {
                n.setVisited(true);
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
    public static <N extends INode, E extends IEdge<N>> boolean hasDiracProperty(Graph<N,E> graph) {
        if (graph.getNodes().size() < 3)
            return false;

        int minDegree = Integer.MAX_VALUE;
        for (INode n : graph.getNodes()) {
            int d = graph.degree(n);
            if (d < minDegree)
                minDegree = d;
        }

        return minDegree >= graph.getNodes().size() * 0.5f;
    }
}
