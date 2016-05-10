package jgh.javagraph.generation;

import jgh.javagraph.*;
import jgh.javagraph.algorithms.AlgorithmException;
import jgh.javagraph.algorithms.GraphSearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Generate special graphs.
 */
public class SpecialGeneration {


    /**
     * Generates a petersen graph - a graph of 10 vertices.
     *
     * @return <code>Graph</code>
     */
    public static  Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> generatePetersenGraph() {
        Set<NodeGeneration.BasicNode> nodes = NodeGeneration.generateNodes(10);

        ArrayList<NodeGeneration.BasicNode> nodeList = new ArrayList<NodeGeneration.BasicNode>(nodes);
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for (int i = 0; i < 5; i++) {
            Edge e = new Edge(nodeList.get(i), nodeList.get((i + 2) % 5));
            edges.add(e);
        }

        for (int i = 5; i < 10; i++) {
            Edge inner = new Edge(nodeList.get(i), nodeList.get(i % 5));
            Edge outer = new Edge(nodeList.get(i), nodeList.get(((i + 1) % 5) + 5));

            edges.add(inner);
            edges.add(outer);
        }

        return new Graph(edges);
    }


    /**
     * Generates a <i>Star graph</i>, where all nodes are adjacent to a single central
     * node and not adjacent to any other node.
     *
     * @param points number of points on the star. Total number of nodes will be this plus one.
     * @return Star Graph
     */
    public static Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> generateStarGraph(int points) {
        HashSet<NodeGeneration.BasicNode> nodes = new HashSet<>(NodeGeneration.generateNodes(points));
        NodeGeneration.BasicNode center = NodeGeneration.generateNodes(1).iterator().next();
        ArrayList<Edge<NodeGeneration.BasicNode>> edgeList = new ArrayList<Edge<NodeGeneration.BasicNode>>();
        for (INode n : nodes) {
            edgeList.add(new Edge(center, n));
        }
        return new Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>>(edgeList);
    }


    /**
     * Generates a <i>wheel graph</i>, where each node in a cyclic graph is connected
     * to a unique <i>central</i> node.
     * @param cycleVertexCount the number of nodes in the cycle subgraph of the
     *                         graph.
     * @return Wheel graph
     */
    public static Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> generateWheelGraph(int cycleVertexCount){
        final int count = cycleVertexCount;
        HashSet<NodeGeneration.BasicNode> nodes = NodeGeneration.generateNodes(count);

        final NodeGeneration.BasicNode bn = new NodeGeneration.BasicNode("center");

        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> cycle = CycleGeneration.generate(nodes);
        final ArrayList<Edge<NodeGeneration.BasicNode>> allEdges = new ArrayList<>(cycle.getEdges());
        GraphSearch.searchBreadthFirst(cycle, new GraphSearch.INextNode() {
            @Override
            public void onNextNode(IGraph graph, INode previous, INode current) {
                allEdges.add( new Edge(bn, current));
            }

            @Override
            public boolean forceStop() {
                return false;
            }
        });

        return new Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>>(allEdges);
    }


    /**
     * Generates a <i>gear graph</i> of the given order.
     * @param order
     * @return Graph object, a gear graph of the given order
     */
    public static Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> generateGearGraph(int order){
        int count = 2 * order;
        HashSet<NodeGeneration.BasicNode> nodes = NodeGeneration.generateNodes(count);
        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> cycle = CycleGeneration.generate(nodes);

        final ArrayList<Edge<NodeGeneration.BasicNode>> spokes = new ArrayList<>();
        final NodeGeneration.BasicNode bn = new NodeGeneration.BasicNode("center");
        final boolean[] flag = new boolean[1];
        flag[0] = true;
        GraphSearch.searchDepthFirst(cycle, new GraphSearch.INextNode<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>>() {
            @Override
            public void onNextNode(IGraph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> graph, NodeGeneration.BasicNode previous, NodeGeneration.BasicNode current) {
                if(flag[0]){
                    spokes.add(new Edge(bn, current));
                }
                flag[0] = !flag[0];
            }

            @Override
            public boolean forceStop() {
                return false;
            }
        });

        ArrayList<Edge<NodeGeneration.BasicNode>> allEdges = new ArrayList<>(cycle.getEdges());
        allEdges.addAll(spokes);

        return new Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>>(allEdges);
    }

    /**
     * Generates a <i>Helm Graph, H(n)</i> where <i>n</i> is the order of the graph.
     * In general, <i>H(n)</i> has <i>2*n+1</i> nodes.
     * @param order the order of the helm graph
     * @return helm graph
     */
    public static Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> generateHelmGraph(int order){
        final int count = order;
        HashSet<NodeGeneration.BasicNode> nodes = NodeGeneration.generateNodes(count);
        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> cycle = CycleGeneration.generate(nodes);

        final ArrayList<Edge<NodeGeneration.BasicNode>> spokes = new ArrayList<>();
        final NodeGeneration.BasicNode bn = new NodeGeneration.BasicNode("center");

        GraphSearch.searchDepthFirst(cycle, new GraphSearch.INextNode<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>>() {
            @Override
            public void onNextNode(IGraph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> graph, NodeGeneration.BasicNode previous, NodeGeneration.BasicNode current) {
                spokes.add(new Edge<NodeGeneration.BasicNode>(bn, current));
                NodeGeneration.BasicNode nodeExtend = new NodeGeneration.BasicNode("extend_"+current.getLabel());
                spokes.add(new Edge(nodeExtend, current));
            }

            @Override
            public boolean forceStop() {
                return false;
            }
        });

        ArrayList<Edge<NodeGeneration.BasicNode>> allEdges = new ArrayList<>(cycle.getEdges());
        allEdges.addAll(spokes);

        return new Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>>(allEdges);
    }


    /**
     * Generates a barbell graph - two complete graphs connected by a single edge.
     *
     * @param endSize the order of the complete graphs making the two ends of the barbell.
     * @return Barbell graph.
     */
    public static Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> generateBarbellGraph(int endSize) {
        if (endSize <= 0)
            throw new IllegalArgumentException("Parameters must be positive.");

        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> left = CompleteGeneration.create(
                new HashSet<NodeGeneration.BasicNode>(NodeGeneration.generateNodes(endSize)));
        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> right = CompleteGeneration.create(
                new HashSet<NodeGeneration.BasicNode>(NodeGeneration.generateNodes(endSize)));
        for (NodeGeneration.BasicNode n : right.getNodes()) {
            n.setLabel("RIGHT_" + n.getLabel());
        }
        NodeGeneration.BasicNode lnode = left.getNodes().iterator().next();
        NodeGeneration.BasicNode rnode = right.getNodes().iterator().next();
        Edge<NodeGeneration.BasicNode> middleEdge = new Edge(lnode, rnode);

        ArrayList<Edge<NodeGeneration.BasicNode>> allEdges = (ArrayList<Edge<NodeGeneration.BasicNode>>) left.getEdges();
        allEdges.addAll(right.getEdges());
        allEdges.add(middleEdge);
        return new Graph(allEdges);
    }


    /**
     * Generates a grid graph where the dimensions of the grid are <code>width * nodeSet.size() / width</code>.
     * The size of the node set must, therefore, be a multiple of <code>width</code>, otherwise an exception
     * will be thrown.
     * The resulting graph forms a grid, where node at position <i>(x,y)</i> is connected by an edge to nodes differing
     * in position horizontally or vertically by one.
     *
     * @param nodeSet set of nodes to form the graph.
     * @param width   The width of the graph, must be a divisor of the size of the node set.
     * @param <E>     Edge type
     * @return Grid graph.
     * @throws AlgorithmException
     */
    public static <N extends INode, E extends Edge<N>> Graph<N,E> generateGrid(HashSet<N> nodeSet, int width) throws AlgorithmException {

        if (nodeSet.size() % width != 0)
            throw new AlgorithmException("Number of nodes must be a multiple of width.");
        int height = nodeSet.size() / width;
        ArrayList<E> edgeList = new ArrayList<E>();
        ArrayList<N> nodeList = new ArrayList<N>(nodeSet);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height - 1; j++) {
                E e = (E) new Edge(nodeList.get(i + j * width), nodeList.get(i + (j + 1) * width));
                edgeList.add(e);
            }

        }
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height; j++) {
                E e = (E) new Edge(nodeList.get(i + j * width), nodeList.get(i + j * width + 1));
                edgeList.add(e);
            }
        }

        return new Graph<N,E>(edgeList);
    }

    /**
     * Generates a 3D grid graph with dimensions <code>width * depth * (nodeSet.size() / (width * depth))</code>.
     * The size of the node set must be a multiple of <code>width * depth</code>, else an exception
     * will be thrown.
     * <br>
     * The number of edges in the returned graph is
     * <br>
     * <code>d * (w * (h - 1) + h * (w - 1)) + (d - 1) * w * h </code>
     * <br>
     * where <code>d</code> is the depth, <code>w</code> is the width, <code>h</code> is the height.
     *
     * @param nodeSet Set of nodes to be nodes of the returned graph.
     * @param width   width of the grid
     * @param depth   depth of the grid
     * @param <E>     Edge type
     * @return Graph on the given nodes, forming a 3D grid.
     * @throws AlgorithmException
     */
    public static <N extends INode, E extends Edge<N>> Graph<N,E> generateGrid3D(HashSet<N> nodeSet, int width, int depth)
            throws AlgorithmException {

        if (nodeSet.size() % (width * depth) != 0)
            throw new AlgorithmException("Number of nodes must be a multiple of width * depth.");
        int height = nodeSet.size() / (width * depth);

        ArrayList<ArrayList<E>> edgeLists = new ArrayList<ArrayList<E>>();
        ArrayList<N> allNodeList = new ArrayList<N>(nodeSet);
        N[][] nodePartition = (N[][])new INode[depth][width * height];
        for (int i = 0; i < depth; i++) {
            HashSet<N> grid2dNodes = new HashSet<N>();
            N[] nArr = (N[])new INode[width * height];
            for (int j = 0; j < width * height; j++) {
                grid2dNodes.add(allNodeList.get(width * height * i + j));

                nArr[j] = allNodeList.get(width * height * i + j);

            }
            Graph<N,E> g = generateGrid(grid2dNodes, width);
            edgeLists.add(g.getEdges());
            nodePartition[i] = nArr;
        }

        ArrayList<E> edges = new ArrayList<E>();

        for (int i = 0; i < depth - 1; i++) {
            edges.addAll(edgeLists.get(i));
            for (int j = 0; j < width * height; j++) {
                edges.add((E) new Edge(nodePartition[i][j], nodePartition[i + 1][j]));
            }
        }
        //add the last grid
        edges.addAll(edgeLists.get(edgeLists.size() - 1));
        return new Graph<N,E>(edges);
    }
}
