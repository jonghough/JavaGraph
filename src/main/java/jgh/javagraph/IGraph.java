package jgh.javagraph;


import java.util.Collection;
import java.util.Set;

/**
 * @param <E>
 */
public interface IGraph<N extends INode, E extends IEdge<N>> {

    /**
     * Returns a List of all edges on the graph.
     *
     * @return List of IEdge objects.
     */
    Collection<E> getEdges();

    /**
     * Gets all the nodes from the edge list.
     *
     * @return HashSet of all nodes.
     */
    Set<N> getNodes();

}
