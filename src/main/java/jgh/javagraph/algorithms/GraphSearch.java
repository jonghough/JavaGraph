package jgh.javagraph.algorithms;

import jgh.javagraph.*;
import jgh.javagraph.IEdge;

import java.util.*;

/**
 * Implementation of Search Algorithms for a Graph. Includes <i>Depth First Search</i>
 * and <i>Breadth First Search.</i>
 */
public class GraphSearch {


    /**
     * Performs a <i>breadth first search</i> on the nodes of the given graph. This search picks an
     * arbitrary starting node and will search through adjacent nodes, breadth first.
     * When reaching an unvisited node, the <code>INextNode.nextNode(...)</code> callback will be called.
     * <br>
     * This search will only visit every node of the graph if the graph is <i>connected.</i> Otherwise, it
     * will only visited a connected component of the graph which includes the initial, arbitrary node.
     *
     * @param graph    Graph to search
     * @param nextNode Callback interface.
     * @param <E>      Edge type
     */
    public static <N extends INode, E extends IEdge<N>> void searchBreadthFirst(Graph<N,E> graph, INextNode nextNode) {
        N current = null;
        boolean currentSelected = false;
        //find an initial node, and set all nodes to not visited.
        for (N node : graph.getNodes()) {
            if (!currentSelected) {
                currentSelected = true;
                current = node;
            }
            node.setVisited(false);
        }

        LinkedList<N> nodeQueue = new LinkedList<N>();
        nodeQueue.addFirst(current);

        //apply mNext node function to the initial node.
        nextNode.onNextNode(graph, current, current);
        current.setVisited(true);

        while (!nodeQueue.isEmpty()) {
            N n = nodeQueue.pollFirst();
            //get all unvisited adjacent nodes.
            ArrayList<N> adjNodes = Utilities.getAdjacentUnvisited(graph.getEdges(), n);

            for (N m : adjNodes) {
                if (m.isVisited()) {
                    continue;
                } else {
                    nextNode.onNextNode(graph, n, m);
                    m.setVisited(true);
                    nodeQueue.addLast(m);

                    if (nextNode.forceStop())
                        break;
                }
            }

        }
    }


    /**
     * Performs a <i>breadth first search</i> on the edges of the given graph. This search picks an
     * arbitrary starting edge and will search through incident edges, breadth first. Function is not recursive.
     * When reaching an unvisited node, the <code>INextEdge.nextEdge(...)</code> callback will be called.
     * <br>
     * This search will only visit every node of the graph if the graph is <i>connected.</i> Otherwise, it
     * will only visited a connected component of the graph which includes the initial, arbitrary edge.
     *
     * @param graph    graph
     * @param nextEdge implementation of <code>INextEdge</code> interface.
     * @param <E>      THe edge type.
     */
    public static <N extends INode, E extends IEdge<N>> void searchEdgesBreadthFirst(Graph<N,E> graph, INextEdge<N, E> nextEdge) {
        E current = null;
        boolean currentSelected = false;
        //find an initial node, and set all nodes to not visited.
        for (E edge : graph.getEdges()) {
            if (!currentSelected) {
                currentSelected = true;
                current = edge;
            }
            edge.setVisited(false);
        }

        LinkedList<E> edgeQueue = new LinkedList<E>();
        edgeQueue.addFirst(current);

        //apply mNext node function to the initial node.
        nextEdge.onNextEdge(graph, current, current);
        current.setVisited(true);

        while (!edgeQueue.isEmpty()) {
            E e = edgeQueue.pollFirst();
            //get all unvisited adjacent nodes.
            ArrayList<E> incEdges = Utilities.getIncidentEdges(graph.getEdges(), e);

            for (E next : incEdges) {
                if (next.isVisited()) {
                    continue;
                } else {
                    nextEdge.onNextEdge(graph, e, next);
                    next.setVisited(true);
                    edgeQueue.addLast(next);

                    if (nextEdge.forceStop())
                        break;
                }
            }
        }
    }

    /**
     * Performs a <i>depth first search</i> on the nodes of the given graph. This search picks an
     * arbitrary starting node and will search through adjacent nodes, depth first. Function is not recursive.
     * When reaching an unvisited node, the <code>INextNode.nextNode(...)</code> callback will be called.
     * <br>
     * This search will only visit every node of the graph if the graph is <i>connected.</i> Otherwise, it
     * will only visited a connected component of the graph which includes the initial, arbitrary node.
     *
     * @param graph    Graph to search
     * @param nextNode Callback interface.
     * @param <E>      Edge type
     */
    public static <N extends INode,E extends IEdge<N>> void searchDepthFirst(Graph<N,E> graph, INextNode nextNode) {
        N current = null;
        boolean currentSelected = false;
        //find an initial node, and set all nodes to not visited.
        for (N node : graph.getNodes()) {
            if (!currentSelected) {
                currentSelected = true;
                current = node;

            }
            node.setVisited(false);
        }

        //DFS
        Stack<N> nodeStack = new Stack<N>();
        nodeStack.push(current);

        iterateDepthFirst(graph, nodeStack, nextNode);
    }

    /**
     * @param graph
     * @param startNode
     * @param nextNode
     * @param <E>
     */
    public static <N extends INode, E extends IEdge<N>> void searchDepthFirst(Graph<N,E> graph, N startNode, INextNode nextNode) {
        //DFS
        Stack<N> nodeStack = new Stack<N>();
        nodeStack.push(startNode);

        iterateDepthFirst(graph, nodeStack, nextNode);
    }

    /**
     * Iterate through the nodes of the graph depth first. Stack contains the current stack
     * used for DFS nodes ot be searched.
     *
     * @param graph     graph to search
     * @param nodeStack stack of nodes to be searched
     * @param nextNode  interface for callbacks per node visit.
     * @param <E>       edge type
     */
    private static <N extends INode, E extends IEdge<N>> void iterateDepthFirst(Graph<N,E> graph,
                                                            Stack<N> nodeStack, INextNode nextNode) {
        while (nodeStack.size() > 0) {
            N n = nodeStack.pop();

            if (!n.isVisited()) {
                //Call delegate function here.
                nextNode.onNextNode(graph, n.getPrevious(), n);

                n.setVisited(true);
                List<N> adjNodes = Utilities.getAdjacentUnvisited(graph.getEdges(), n);
                for (N m : adjNodes) {
                    m.setPrevious(n);
                    nodeStack.push(m);
                }

                if (nextNode.forceStop())
                    break;
            }

        }

    }

    /**
     * Performs a <i>depth first search</i> on the edges of the given graph. This search picks an
     * arbitrary starting edge and will search through incident edges, depth first. Function is not recursive.
     * When reaching an unvisited node, the <code>INextEdge.nextEdge(...)</code> callback will be called.
     * The <code>mPrevious</code> parameter is always <code>null</code>.
     * <br>
     * This search will only visit every node of the graph if the graph is <i>connected.</i> Otherwise, it
     * will only visited a connected component of the graph which includes the initial, arbitrary edge.
     *
     * @param graph    graph
     * @param nextEdge implementation of <code>INextEdge</code> interface.
     * @param <E>      THe edge type.
     */
    public static <N extends INode, E extends IEdge<N>> void searchEdgesDepthFirst(Graph<N,E> graph, INextEdge nextEdge) {
        E current = null;
        boolean currentSelected = false;
        //find an initial node, and set all nodes to not visited.
        for (E edge : graph.getEdges()) {
            if (!currentSelected) {
                currentSelected = true;
                current = edge;
            }
            edge.setVisited(false);
        }

        //DFS
        Stack<E> nodeStack = new Stack<E>();
        nodeStack.push(current);
        while (nodeStack.size() > 0) {
            E e = nodeStack.pop();

            if (!e.isVisited()) {
                //Call delegate function here.
                nextEdge.onNextEdge(graph, null, e);

                e.setVisited(true);
                ArrayList<E> incEdges = Utilities.getIncidentEdges(graph.getEdges(), e);
                for (E next : incEdges) {
                    nodeStack.push(next);
                }

                if (nextEdge.forceStop())
                    break;
            }


        }
    }


    /**
     * Callback to be called when depth first search or breadth first search hit a new, unvisited
     * node. Uses the <code>getPrevious()</code> and <code>setPrevious()</code> methods for
     * each node.
     *
     * @param <E> Edge type.
     */
    public interface INextNode<N extends INode, E extends IEdge<N>> {

        /**
         * Called when each node is visited during a <i>BFS</i> or <i>DFS</i>.
         *
         * @param graph    the graph
         * @param previous the mPrevious node.
         * @param current  the currently visited node.
         */
        void onNextNode(IGraph<N,E> graph, N previous, N current);

        /**
         * Force the search to terminate. This is called on each successive iteraiton
         * of the <i>BFS</i> or <i>DFS</i>. If it returns true then the search will
         * immediately halt.
         *
         * @return true if the search is to halt immediately, false to keep searching.
         */
        boolean forceStop();
    }

    /**
     * Callback to be called when DFS or BFS hit a new, unvisited edge.
     *
     * @param <E> Edge type.
     */
    public interface INextEdge<N extends INode, E extends IEdge<N>> {

        /**
         * Called when each edge is visited during a <i>BFS</i> or <i>DFS</i>.
         *
         * @param graph    the graph
         * @param previous the mPrevious edge. For <i>DFS</i> this is <code>null</code>.
         * @param current  the currently visited edge.
         */
        void onNextEdge(IGraph<N,E> graph, E previous, E current);

        /**
         * Force the search to terminate. This is called on each successive iteraiton
         * of the <i>BFS</i> or <i>DFS</i>. If it returns true then the search will
         * immediately halt.<br>
         * The purpose of this function is, if <code>onNextEdge</code> gives the wanted
         * result (whatever that may be), there is no point in continuing to search, so
         * this function will force the search to stop.
         *
         * @return true if the search is to halt immediately, false to keep searching.
         */
        boolean forceStop();
    }
}
