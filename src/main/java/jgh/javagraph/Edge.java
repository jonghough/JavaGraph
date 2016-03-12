package jgh.javagraph;

import java.util.HashSet;
import java.util.Set;

/**
 * Edge type. This is the basic edge type.
 */
public class Edge<N extends INode> extends IEdge<N> {

    /**
     * The <i>from</i> node. For undirected edges the
     * <i>from</i> and <i>to</i> nodes are arbitrary.
     * For directed graphs the <code>FORWARDS</code> direction
     * is from <i>from</i> to <i>to</i>.
     */
    protected N mFrom;

    /**
     * The <i>to</i> node. For undirected edges the
     * <i>from</i> and <i>to</i> nodes are arbitrary.
     * For directed graphs the <code>FORWARDS</code> direction
     * is from <i>from</i> to <i>to</i>.
     */
    protected N mTo;

    /**
     * HashSet of the <i>from</i> and <i>to</i> nodes.
     */
    protected HashSet<N> mNodes;

    /**
     * Visited flag. Flag is set with <code>setVisited(boolean visited)</code> method.
     * The meaning of the flag can be implementation and algorithm dependent. e.g. for
     * Dijkstra's algorithm an edge is visited after the algorithm passes it once.
     */
    protected boolean mVisited = false;

    /**
     * Constructor for <code>Edge</code> taking the <i>from</i> and <i>to</i> nodes
     * as parameters.
     *
     * @param from from node
     * @param to   to node
     */
    public Edge(N from, N to) {
        super(from, to);
        mFrom = from;
        mTo = to;
        mNodes = new HashSet<>();
        mNodes.add(mFrom);
        mNodes.add(mTo);
    }

    @Override
    public N from() {
        return mFrom;
    }

    @Override
    public N to() {
        return mTo;
    }

    @Override
    public Set<N> nodes() {
        return mNodes;
    }

    @Override
    public void accept(IEdgeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isVisited() {
        return mVisited;
    }

    @Override
    public void setVisited(boolean visited) {
        mVisited = visited;
    }
}
