package jgh.javagraph;

import java.util.Set;

/**
 * Edge base class.
 * @param <N> THe node type
 */
public abstract class IEdge<N> {

    /**
     * Constructs the instance of <code>IEdge</code>
     *
     * @param from the from node
     * @param to   the to node.
     */
    public IEdge(N from, N to) {
        //do nothing
    }

    /**
     * The "from" node. For undirected graphs this should hold meaning,
     * but for directed graphs, indicates the origin of the edge.
     *
     * @return The <i>from</i> node
     */
    public abstract N from();

    /**
     * The "to" node. For undirected graphs this should hold meaning,
     * but for directed graphs, indicates the destination of the edge.
     *
     * @return The <i>to</i> node
     */
    public abstract N to();

    /**
     * Returns all (both) nodes.
     *
     * @return both nodes.
     */
    public abstract Set<N> nodes();

    /**
     * Accept the <code>IEdgeVisitor</code>
     *
     * @param visitor
     */
    public abstract void accept(IEdgeVisitor visitor);

}

