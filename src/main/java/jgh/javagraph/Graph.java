package jgh.javagraph;

import jgh.javagraph.algorithms.Utilities;

import java.util.*;

/**
 * Basic Graph class. This class implements <code>IGraph</code>, and guarantees only
 * single edges per node pair.
 *
 * @param <E> The edge type.
 */
public class Graph<N extends INode, E extends IEdge<N>> implements IGraph<N,E> {

    ArrayList<E> mEdges;
    Set<N> mNodes;

    /**
     * Constructor for standard <i>Graph</i> object. This Graph guarantees that no two edges share the same two
     * nodes (i.e. cannot be a multigraph etc). If multiple edges share the same <i>(u,v)</i> node pair then
     * only the last indexed edge will be accepted by the graph.
     *
     * @param edges
     */
    public Graph(ArrayList<E> edges) {

        // Standard Graph must guarantee no two edges have the same two nodes.
        // To guarantee this, we merge out duplicates with the following
        // map object.
        HashMap<Integer, E> edgeMap = new HashMap<>();
        for (E e : edges) {
            edgeMap.put(e.from().hashCode() * -11 + 110451 * e.to().hashCode(), e);
        }

        //Guarantee only one edge per (u,v) node pair.
        mEdges = new ArrayList<E>(edgeMap.values());

        // get the nodes, add them to the node set.
        mNodes = new HashSet<N>();
        for (E e : edges) {
            Set<N> n = e.nodes();
            mNodes.addAll(n);
        }
    }

    /**
     * @param edges
     * @param nodes
     */
    public Graph(ArrayList<E> edges, Set<N> nodes) {
        this(edges);
        mNodes.addAll(nodes);
    }

    @Override
    public ArrayList<E> getEdges() {
        return mEdges;
    }

    @Override
    public Set<N> getNodes() {
        return mNodes;
    }


    /**
     * Returns the degree of the given node. If the node does not exist on the graph then returns
     * -1.
     *
     * @param node
     * @return degree of the node, or -1, if the node is not in the graph's node set.
     */
    public int degree(INode node) {
        if (mNodes.contains(node) == false) {
            return -1;
        } else {
            return (int) mEdges.stream().filter(e -> e.nodes().contains(node)).count();
        }
    }


    /**
     * Checks if the (undirected) graph contains a Eulerian cycle, a cycle visiting all nodes, only traversing
     * any edge once. Returns true <i>iff</i> all nodes have even degree.
     *
     * @return true if the graph contains a Eulerian cycle, false otherwise.
     */
    public boolean eulerian() {
        int evenNodes = (int) mNodes.stream().map(node -> degree(node)).filter(i -> i % 2 == 0).count();
        return evenNodes == mNodes.size();
    }

    /**
     * Gets all the nodes which have maximum degree on the graph.
     *
     * @return ArrayList of maximum degree nodes.
     */
    public ArrayList<N> getMaxDegreeNode() {
        ArrayList<N> max = new ArrayList<>();
        ArrayList<N> nodes = new ArrayList<>(getNodes());
        INode m = nodes.get(0);
        HashMap<N, Integer> degCount = new HashMap<>();
        for (E e : getEdges()) {
            N f = e.from();
            if (degCount.containsKey(f)) {
                degCount.put(f, degCount.get(f) + 1);
                int d = degCount.get(f);
            } else {
                degCount.put(f, 1);
            }
            N t = e.to();
            if (degCount.containsKey(t)) {
                degCount.put(t, degCount.get(t) + 1);
                int d = degCount.get(f);
            } else {
                degCount.put(t, 1);
            }
        }

        Optional<Integer> maxDegOption = degCount.values().stream().max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2) return 1;
                else if (o1 < o2) return -1;
                else return 0;
            }
        });
        if (maxDegOption.isPresent() == false)
            return null;
        Iterator<Map.Entry<N, Integer>> iter = degCount.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<N, Integer> entry = iter.next();
            if (entry.getValue() == maxDegOption.get())
                max.add(entry.getKey());
        }
        return max;
    }

    /**
     * Gets all the nodes which have minimum degree on the graph.
     *
     * @return ArrayList of minimum degree nodes.
     */
    public ArrayList<N> getMinDegreeNode() {
        ArrayList<N> min = new ArrayList<>();
        ArrayList<N> nodes = new ArrayList<>(getNodes());
        INode m = nodes.get(0);
        HashMap<N, Integer> degCount = new HashMap<>();
        for (E e : getEdges()) {
            N f = e.from();
            if (degCount.containsKey(f)) {
                degCount.put(f, degCount.get(f) + 1);
                int d = degCount.get(f);
            } else {
                degCount.put(f, 1);
            }

            N t = e.to();
            if (degCount.containsKey(t)) {
                degCount.put(t, degCount.get(t) + 1);
                int d = degCount.get(f);
            } else {
                degCount.put(t, 1);
            }
        }
        Optional<Integer> minDegOption = degCount.values().stream().min(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2) return 1;
                else if (o1 < o2) return -1;
                else return 0;
            }
        });
        if (minDegOption.isPresent() == false)
            return null;
        Iterator<Map.Entry<N, Integer>> iter = degCount.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<N, Integer> entry = iter.next();
            if (entry.getValue() == minDegOption.get())
                min.add(entry.getKey());
        }
        return min;
    }


    /**
     * Removes the edge from the graph. If the edge does not exist on the graph then the
     * function will return false.
     *
     * @param edge edge to remove.
     * @return false if the edge does not exist on the graph, true otherwise.
     */
    public boolean removeEdge(E edge) {
        if (!getEdges().contains(edge))
            return false;
        getEdges().remove(edge);
        return true;
    }


    /**
     * Adds the given edge to the graphs edge list. If the edge already exists on the graph
     * then the function will simply return <b>false</b>.
     *
     * @param edge edge to add to the graph
     * @return false if the edge is already on the graph, otherwise true.
     */
    public boolean addEdge(E edge) {
        if (getEdges().contains(edge))
            return false;
        getEdges().add(edge);
        return true;
    }


    /**
     * Removes the nodes from the graph. Returns false if the node does not exist
     * on the graph. Otherwise, removes the node and all incident edges from the
     * node set and edge set respectively.
     *
     * @param node node to remove
     * @return True if node exists on the graph, and was removed, false otherwise.
     */
    public boolean removeNode(N node) {
        if (!mNodes.contains(node))
            return false;
        Collection<E> incidentEdges = Utilities.getIncidentEdges(mEdges, node);
        mEdges.removeAll(incidentEdges);
        mNodes.remove(node);
        return true;
    }

    public boolean contractEdge(E edge) {
        if (!mEdges.contains(edge))
            return false;
        return false;
    }
}
