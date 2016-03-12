package jgh.javagraph;

import jgh.javagraph.INode;

import java.util.Set;

/**
 * Edge base class.
 * @param <N> THe node type, an implementation of <code>INode</code>
 */
public abstract class IEdge<N extends INode> {

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

    /**
     * Returns true if this node has been visited,
     * false otherwise.
     *
     * @return visited flag
     */
    public abstract boolean isVisited();

    /**
     * Sets the visited flag.
     *
     * @param visited
     */
    public abstract void setVisited(boolean visited);
}

