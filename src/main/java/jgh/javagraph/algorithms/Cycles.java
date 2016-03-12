package jgh.javagraph.algorithms;

import jgh.javagraph.Graph;
import jgh.javagraph.IEdge;
import jgh.javagraph.IGraph;
import jgh.javagraph.INode;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Some cycle finding algorithms.
 */
public class Cycles {


    /**
     * Finds all the cycles in the given graph, via a brute force search. The graph need not be connected.
     * This method uses a very slow algorithm, searching repetitively for all possible cycles with time cost
     * <i> ~O(N!)</i>, in the worst case (Complete graph, K(N): number of hamiltonian cycles is (N-1)!, whereas this
     * method searches for <i>all</i> cycles, not only the hamiltonian cycles). <br>
     * The cycles are found by performing a depth first search at an arbitrary node
     * in each connected subgraph. For all but small graphs this is not a suitable approach. Once all the
     * cycles have been found for a given subgraph, they need to be condensed, as cycles may have been counted
     * more than once, depending on the order of traversal.
     *
     * @param graph Graph to consider
     * @param <E>   Edge type
     * @return List of list of nodes, in cyclic order.
     */
    public static <N extends INode, E extends IEdge<N>> ArrayList<ArrayList<N>> findAllCyclesInGraph(Graph<N,E> graph) {
        //get the maximally connected subgraphs and find cycles.
        ArrayList<Graph<N,E>> graphList = Connectivity.getMaximalConnectedSubgraphs(graph);
        ArrayList<ArrayList<N>> cycleList = new ArrayList<ArrayList<N>>();
        for (Graph<N,E> g : graphList) {
            cycleList.addAll(findAllCycles(g));
        }
        return cycleList;
    }

    /**
     * Finds all the cycles in the <code>Graph<E></E></code>, graph. The graph is assumed to be <i>connected</i>, if not
     * it will give wrong results. This method is particularly slow in time, i.e. ~O(2^N), in the worst case,
     * where N is the number of nodes.
     *
     * @param graph Graph to search
     * @param <E>   Edge type
     * @return List of cycles
     */
    private static <N extends INode, E extends IEdge<N>> ArrayList<ArrayList<N>> findAllCycles(Graph<N,E> graph) {


        //set the nodes
        for (N n : graph.getNodes()) {
            n.setVisited(false);
        }

        //get the edges and nodes. We choose an arbitrary node as the first node.
        ArrayList<N> nodeQ = new ArrayList<N>(graph.getNodes());
        ArrayList<E> edgeList = new ArrayList<E>(graph.getEdges());
        N first = nodeQ.get(0);

        ArrayList<ArrayList<N>> cycleList = new ArrayList<ArrayList<N>>();
        ArrayList<N> adjacent = Utilities.getAdjacent(edgeList, first);

        // begin the cycle search...
        for (N adj : adjacent) {
            Stack<N> path = new Stack<N>();
            path.add(first);
            cycleList.addAll(addToPath(graph, first, adj, path));
        }

        // Transform the list of cycles into wrapped lists, merge the duplicates,
        // and transform back into array lists. This is necessary because we have almost certainly counted
        // duplicate cycles.
        List<ArrayList<N>> wrappedCycles = cycleList.stream().map(lst ->
                new Cycles().new ListWrapper<N>(lst)).distinct().map(wrapped ->
                wrapped.data).collect(Collectors.toList());

        cycleList.clear();
        cycleList.addAll(wrappedCycles);
        return cycleList;
    }

    /**
     * Add the current node to the current path, and search for cycles from adjacent nodes.
     *
     * @param graph
     * @param previous
     * @param current
     * @param path
     * @param <E>
     * @return
     */
    private static <N extends INode, E extends IEdge<N>> ArrayList<ArrayList<N>> addToPath(Graph<N,E> graph, INode previous,
                                                                           N current, Stack<N> path) {

        ArrayList<ArrayList<N>> cycleList = new ArrayList<ArrayList<N>>();

        for (int i = 0; i < path.size() - 1; i++) {
            if (path.get(i) == current) {
                ArrayList<N> cycle = new ArrayList<N>();

                for (int j = i; j < path.size(); j++) {
                    cycle.add(path.get(j));
                }

                cycleList.add(cycle);
                return cycleList;
            }
        }

        //get the adjacent nodes to the current nodes
        Collection<E> edgeList = new ArrayList<E>(graph.getEdges());
        Collection<E> incident = Utilities.getIncidentEdges(edgeList, current);
        //add the current node to the stack and search.
        path.push(current);
        for (E e : incident) {
            if (e.isVisited() == false) {
                e.setVisited(true);
                HashSet<N> searchNodes = new HashSet<>(e.nodes());
                searchNodes.remove(current);
                for (N n : searchNodes) {
                    if (n != previous) {
                        cycleList.addAll(addToPath(graph, current, n, path));
                    }
                }
            }
            e.setVisited(false);
        }
        // pop current node off stack.
        path.pop();
        return cycleList;
    }

    /**
     * Class for wrapping lists. This is for ease of removing duplicate lists, by utilizing a custom
     * <code>hashCode</code> method.
     *
     * @param <T> Type of list
     */
    private class ListWrapper<T> {

        public ArrayList<T> data;

        public ListWrapper(ArrayList<T> d) {
            data = d;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                // If it is null then it is not equal to this instance.
                return false;
            }

            return this.hashCode() == other.hashCode();
        }

        @Override
        public int hashCode() {
            // The hashcode function needs to be unique for a sequence of items in a given order (but not distinct
            // for rotations of that order). e.g. {1,2,3,4} should have the same hash as {2,3,4,1}, but not the same
            // hash as {1,3,2,4}. Each term of the following sum takes the mPrevious term into account, so any
            // two rotations of the same sequence will give the same output, but a sequence that is not a rotation
            // will give a different output. The result is, lists which are rotations of each other can be
            // merged into a single list in a time-efficient way.
            int hash = 0;
            for (int i = 0; i < data.size(); i++) {
                int last = 0;
                int next = 0;
                if (i == 0)
                    last = data.size() - 1;
                else last = i - 1;
                if (i == data.size() - 1)
                    next = 0;
                else next = i + 1;
                hash += data.get(i).hashCode() * data.get(i).hashCode() * data.get(last).hashCode()
                        * data.get(next).hashCode();
            }
            return hash;
        }
    }
}
