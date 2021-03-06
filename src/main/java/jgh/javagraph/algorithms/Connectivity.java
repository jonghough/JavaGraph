package jgh.javagraph.algorithms;

import jgh.javagraph.*;

import java.util.*;
import java.util.stream.Stream;

/**
 * Methods related to testing connectivity of graphs, subgraphs,
 * and connected components of graphs.
 */
public class Connectivity {


    /**
     * Determines if the graph is connected, by doing a <i>BFS</i> through the nodes and
     * counting how many nodes have been visited. Total number of visited nodes should be the same as the
     * node set size, for a connected graph.
     *
     * @param graph Graph
     * @param <E>   Edge type
     * @return True if the graph is connected, false otherwise.
     */
    public static <N, E extends IEdge<N>> boolean isConnected(Graph<N,E> graph) {

        final int[] dummy = new int[1];
        dummy[0] = 0;

        GraphSearch.INextNode<N,E> nextNode = new GraphSearch.INextNode<N, E>() {
            @Override
            public void onNextNode(IGraph<N, E> graph, N previous, N current) {
                int counter = dummy[0];
                dummy[0] = counter + 1;
            }

            @Override
            public boolean forceStop() {
                return false;
            }
        };

        GraphSearch.searchBreadthFirst(graph, nextNode);

        return graph.getNodes().size() == dummy[0];
    }


    /**
     * Returns the connected components of a graph. Each connected component
     * is represented as an <code>ArrayList</code> of nodes. Clearly the lists
     * are pairwise disjoint.
     *
     * @return List of connected components
     */
    public static <N, E extends IEdge<N>> ArrayList<ArrayList<N>> getConnectedComponents(Graph<N,E> graph) {
        ArrayList<ArrayList<N>> connectedComponents = new ArrayList<ArrayList<N>>();

        HashMap<N, NodeData<N>> nodeMap = new HashMap<>();
        for(N n : graph.getNodes()){
            nodeMap.put(n, new NodeData<N>(n));
        }
        N n = getFirstUnvisitedNode(graph, nodeMap);
        while (true) {

            ArrayList<N> conn = getConnected(graph, n);
            conn.add(n);
            connectedComponents.add(conn);
            n = getFirstUnvisitedNode(graph, nodeMap);
            if (n == null)
                break;
        }

        return connectedComponents;
    }

    /**
     * Returns a list of the <i>maximally connected</i> subgraphs of the given graph. That is, each subgraph
     * in the list is completely disjoint from all other subgraphs. Any node in the given graph will be contained
     * in exactly one subgraph in the list.
     *
     * @param graph Graph to partition.
     * @param <E>   Edge type
     * @return list of subgraphs.
     */
    public static <N, E extends IEdge<N>> ArrayList<Graph<N,E>> getMaximalConnectedSubgraphs(Graph<N,E> graph) {
        ArrayList<ArrayList<N>> connectedNodes = getConnectedComponents(graph);
        ArrayList<E> edgeList = new ArrayList<E>(graph.getEdges());
        ArrayList<Graph<N,E>> graphList = new ArrayList<Graph<N,E>>();
        for (ArrayList<N> nodeList : connectedNodes) {
            HashSet<E> edgeSet = new HashSet<E>();
            for (N node : nodeList) {
                edgeSet.addAll(Utilities.getIncidentEdges(edgeList, node));
            }
            graphList.add(new Graph(new ArrayList<E>(edgeSet), new HashSet<N>(nodeList)));
        }
        return graphList;
    }



    /**
     * Searches the graph's node set, in an unspecified order, and retrieves
     * the first unvisited node found. If no such node exists then this method
     * returns <code>null</code>.
     *
     * @param graph Graph to consider
     * @param <E>   Edge type
     * @return An unvisited node if one exists, otherwise returns <code>null</code>.
     */
    private static <N, E extends IEdge<N>> N getFirstUnvisitedNode(Graph<N,E> graph, HashMap<N, NodeData<N>> nodeMap) {

        Iterator<Map.Entry<N,NodeData<N>>> iterator = nodeMap.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<N,NodeData<N>> npair = iterator.next();
            if (npair.getValue().isVisited() == false)
                return npair.getKey();
        }
        return null;
    }

    /**
     * Gets the edges which are connected to the given edge. i.e. they lie on the same
     * connected component in the Graph.
     *
     * @param graph Graph with edges to check for connectedness
     * @param edge  The current edge
     * @param <E>   Edge type, extends IEdge interface
     * @return ArrayList of Edges in the connected component containing <i>edge</i>.
     */
    public static <N, E extends IEdge<N>> ArrayList<E> getConnected(Graph<N,E> graph, E edge) {
        HashMap<E, EdgeData<E>> edgeMap = new HashMap<>();
        for(E e : graph.getEdges()) {
            EdgeData<E> ed = new EdgeData<>(e);
            if(e == edge) {
                ed.setVisited(true);
            }

            edgeMap.put(e,ed);
        }

        Collection<E> nextArr = Utilities.getIncidentEdges(new ArrayList(graph.getEdges()), edge);
        ArrayList<E> connList = new ArrayList<E>();
        connList.add(edge);
        for (E e : nextArr) {
            if (!edgeMap.get(e).isVisited()) {
                connList.addAll(getConnected(graph, e));
            }
        }
        return connList;
    }

    /**
     * Returns the nodes connected to the given node, on the given graph. Nodes are considered
     * connected if there is a path on the graph connecting them.
     *
     * @param graph Graph to consider
     * @param node  The node to consider
     * @param <E>   The edge type
     * @return <code>ArrayList</code> of nodes connected to the given node. If the node is not
     * connected to any other node then the list will be empty.
     */
    public static <N, E extends IEdge<N>> ArrayList<N> getConnected(Graph<N,E> graph,  N node) {

        HashMap<N, NodeData<N>> nodeMap = new HashMap<>();
        for(N n : graph.getNodes()){
            nodeMap.put(n, new NodeData<N>(n));
        }
        nodeMap.get(node).setVisited(true);
        return getConnected(graph, nodeMap, node);
    }

    private static <N, E extends IEdge<N>> ArrayList<N> getConnected(Graph<N,E> graph,
                                                                                  HashMap<N, NodeData<N>> nodeMap,  N node) {
        nodeMap.get(node).setVisited(true);
        ArrayList<N> connList = new ArrayList<N>();
        ArrayList<N> adjNodes = Utilities.getAdjacentUnvisited(nodeMap, new ArrayList(graph.getEdges()), node);
        connList.addAll(adjNodes);
        for (N t : adjNodes) {
            nodeMap.get(t).setVisited(true);
        }
        for (N t : adjNodes) {
            connList.addAll(getConnected(graph, nodeMap, t));
        }

        return connList;
    }

}
