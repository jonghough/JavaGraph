package jgh.javagraph.algorithms;

import jgh.javagraph.*;
import jgh.javagraph.flownetwork.CapacityEdge;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Partial implementation of Ford-Fulkerson aglorithm for finding the maximum flow from
 * <i>source</i> nodes to <i>sink</i> node in a <i>Flow Network</i> graph. The implementation is partial
 * in that it assumes:<br>
 * flow is one way on each edge of the network;<br>
 * the path finding algorithm is not optimal, in that it does not look for the shortest path first, but finds
 * any arbitrary path from sink to source;<br>
 * the implementation, in general, needs refactoring later.<br>
 */
public class FordFulkerson {

    /**
     * Calculates the maximum possible flow on the <i>Flow Netowrk</i> graph, from the source, <i>startNode</i>,
     * to the sink, <i>finishNode</i>. The flow values are assigned to each edge on the graph. If no path can be found
     * from source to sink then this method will return false.
     * The algorithm performs a <i>DFS</i> of routes from source to sink, changing the flow values of edges
     * with each step.
     *
     * @param graph      Flow network graph
     * @param startNode  source node
     * @param finishNode sink node
     * @return True if flow found, false otherwise.
     */
    public static <N> boolean findMaxFlow(Graph<N,CapacityEdge<N>> graph, N startNode, N finishNode) {
        // find some initial path.
        HashMap<N,NodeData<N>> nodeMap = new HashMap<N,NodeData<N>>();
        for (N n : graph.getNodes()) {
            NodeData<N> nd = new NodeData<N>(n);
            nodeMap.put(n, nd);
            if(n == startNode){
                nd.setVisited(true);
            }
        }
        for (CapacityEdge e : graph.getEdges()) {
            e.setFlowDirection(Direction.FORWARDS);
        }

        ArrayList<CapacityEdge<N>> allEdges = new ArrayList<CapacityEdge<N>>(graph.getEdges());
        ArrayList<CapacityEdge<N>> initialPath = findPath(nodeMap, allEdges,
                startNode, finishNode);

        while (initialPath != null) {
            System.out.println("looping");
            N last = startNode;
            float minFlow = initialPath.stream().map(e -> e.getResidualFlow()).min(Comparator.<Float>naturalOrder()).get();
            List<CapacityEdge> minNode = initialPath.stream().filter(
                    e -> e.getResidualFlow() == minFlow).collect(Collectors.toList());

            for (CapacityEdge e : initialPath) {
                if (e.getFlowDirection() == Direction.FORWARDS)
                    e.setFlow(minFlow + e.getFlow());
                else
                    e.setFlow(e.getFlow() - minFlow);

            }

            for (N n : graph.getNodes()) {
                nodeMap.get(n).setVisited(false);
            }
            for (CapacityEdge e : graph.getEdges()) {
                e.setFlowDirection(Direction.FORWARDS);
            }
            new NodeData<N>(startNode).setVisited(true);
            //augment the path.
            initialPath.clear();
            allEdges.clear();
            allEdges = new ArrayList<CapacityEdge<N>>(graph.getEdges());
            allEdges.remove(minNode.get(0));
            initialPath = findPath(nodeMap, allEdges,
                    startNode, finishNode);
        }

        return false;
    }

    /**
     * Will find a path from the <i>start</i> node to the <i>finish</i> node on the graph. The method assumes
     * such a path exists, and that the graph is <i>connected</i>.
     * The path is not guaranteed to be optimal in any way. THe method uses a <i>DFS</i> to find any path, returning
     * the first path found.
     *
     * @param edges  List of directed edges, with capacity allowances.
     * @param start  the start node
     * @param finish the finish node
     * @return a list of <code>CapacityEdge</code>s from the start node to the finish node,
     * if such a path can be found. Otherwise, returns null.
     */
    private static <N> ArrayList<CapacityEdge<N>> findPath(HashMap<N, NodeData<N>> nodeMap,
                                                                         ArrayList<CapacityEdge<N>> edges, N start, N finish) {

        //filter only the edges coming from the start node
        Stream<CapacityEdge<N>> edgeStream = Utilities.getIncidentEdges(edges, start).stream().filter(e -> {
            if (e.from() == start && !nodeMap.get(e.to()).isVisited())
                return true;
            else if (e.to() == start && !nodeMap.get(e.from()).isVisited())
                return true;

            else return false;
        });

        // For each outgoing edge, do a depth first search for any path terminating at the
        // finish node. If no such path can be found, then there is no route from start to finish.
        Iterator<CapacityEdge<N>> iterator = edgeStream.iterator();
        while (iterator.hasNext()) {
            CapacityEdge<N> selectedEdge = iterator.next();

            if (selectedEdge != null) {
                //only want edges with positive residual flow.
                if (selectedEdge.getResidualFlow() <= 0) {
                    continue;
                }
                nodeMap.get(selectedEdge.to()).setVisited(true);

                // Flow direction is backwards if going from from() to to()
                // nodes. This changes the residual capacity calculation.
                if (selectedEdge.to() == start) {
                    selectedEdge.setFlowDirection(Direction.BACKWARDS);
                }
                if (selectedEdge.to() == finish) {
                    ArrayList<CapacityEdge<N>> path = new ArrayList<CapacityEdge<N>>();

                    path.add(selectedEdge);

                    return path;
                } else {
                    ArrayList<CapacityEdge<N>> edgesCopy = new ArrayList<CapacityEdge<N>>(edges);
                    edgesCopy.remove(selectedEdge);
                    N next = selectedEdge.to();
                    if (next == start) {
                        next = selectedEdge.from();
                    }
                    ArrayList<CapacityEdge<N>> subPath = findPath(nodeMap, edgesCopy, next, finish);

                    if (subPath != null && !subPath.isEmpty()) {
                        ArrayList<CapacityEdge<N>> path = new ArrayList<CapacityEdge<N>>();

                        path.addAll(subPath);
                        path.add(0, selectedEdge);

                        return path;
                    } else continue;
                }
            } else continue;
        }
        // Return null, only if all possible paths are exhausted
        // and no path to the finish node has been found.
        return null;
    }
}
