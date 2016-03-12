package jgh.javagraph;

import java.util.Set;

/**
 * Implementation of weighted edge. Edge has a weight of float type.
 */
public class WeightedEdge<N extends INode> extends IEdge<N> implements Comparable<WeightedEdge<N>> {

    /**
     * the weight of the edge.
     */
    protected float mWeight;

    /**
     * The underlying <code>IEdge</code> implementation.
     */
    protected Edge<N> mEdge;

    /**
     * Creates instance of <code>WeightedEdge</code> and initializes
     * the edge's weight to zero.
     * @param from <i>from</i> node
     * @param to <i>to</i> node
     */
    public WeightedEdge(N from, N to) {
        super(from, to);
        mEdge = new Edge(from, to);
        mWeight = 0;
    }

    /**
     * Creates an instance of <code>WeightedEdge</code> with the given weight.
     * @param from <i>from</i> node
     * @param to <i>to</i> node
     * @param weight weight
     */
    public WeightedEdge(N from, N to, float weight) {
        super(from, to);
        mEdge = new Edge(from, to);
        mWeight = weight;
    }

    /**
     * Creates an instance of <code>WeightedEdge</code> with the given weight, from
     * an original <code>IEdge</code> edge. The weighted Edge will use this edge's
     * <i>to</i> and <i>from</i> nodes as its own nodes.
     * @param edge original edge to use to create the weighted edge.
     * @param weight weight of the edge.
     */
    public WeightedEdge(IEdge<N> edge, float weight) {
        super(edge.from(), edge.to());
        mWeight = weight;
        mEdge = new Edge(edge.from(), edge.to());
    }

    @Override
    public N from() {
        return mEdge.from();
    }

    @Override
    public N to() {
        return mEdge.to();
    }

    @Override
    public Set<N> nodes() {
        return mEdge.nodes();
    }

    @Override
    public void accept(IEdgeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isVisited() {
        return mEdge.isVisited();
    }

    @Override
    public void setVisited(boolean visited) {
        mEdge.setVisited(visited);
    }

    public float getWeight() {
        return mWeight;
    }

    public void setWeight(float weight) {
        mWeight = weight;
    }


    @Override
    public int compareTo(WeightedEdge o) {
        if (this.getWeight() > o.getWeight()) return 1;
        else if (this.getWeight() == o.getWeight()) return 0;
        else return -1;
    }
}
