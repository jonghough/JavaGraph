package jgh.javagraph.mincut;


import jgh.javagraph.Edge;
import jgh.javagraph.INode;

public class ModifiableEdge<N extends INode> extends Edge<N> {

    private N mOriginalFrom;
    private N mOriginalTo;

    /**
     * Constructor for <code>Edge</code> taking the <i>from</i> and <i>to</i> nodes
     * as parameters.
     *
     * @param from from node
     * @param to   to node
     */
    public ModifiableEdge(N from, N to) {
        super(from, to);
        mOriginalFrom = from;
        mOriginalTo = to;
    }

    public N getOriginalFrom() {
        return mOriginalFrom;
    }

    public N getOriginalTo() {
        return mOriginalTo;
    }

    public void setFrom(N node) {
        this.mFrom = node;
    }

    public void setTo(N node) {
        this.mTo = node;
    }
}
