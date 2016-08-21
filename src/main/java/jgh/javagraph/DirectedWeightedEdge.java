package jgh.javagraph;

/**
 * Implementation of a Directed, Weighted Edge. Direction can either be <i>forwards</i>, from <i>from</i> to
 * <i>to</i> nodes, <i>backwards</i> (opposite direction), or <i>both</i>, bi-directional.
 */
public final class DirectedWeightedEdge<N> extends WeightedEdge<N> {

    /**
     * Direction of the edge, either <code>FORWARDS</code>, <code>BACKWARDS</code>, or
     * <code>BOTH</code>.
     */
    private Direction mDirection;

    /**
     * <code>DirectedWeightedEdge</code> constructor with the <i>from</i> and <i>to</i>
     * nodes, weight, and direction parameters.
     *
     * @param from
     * @param to
     * @param weight
     * @param direction
     */
    public DirectedWeightedEdge(N from, N to, float weight, Direction direction) {
        super(from, to, weight);
        mDirection = direction;
    }

    /**
     * <code>DirectedWeightedEdge</code> constructor with base <code>IEdge</code>, weight, and direction parameters.
     *
     * @param edge
     * @param weight
     * @param direction
     */
    public DirectedWeightedEdge(IEdge edge, float weight, Direction direction) {
        super(edge, weight);
        mDirection = direction;
    }

    /**
     * <code>DirectedWeightedEdge</code> constructor with base <code>WeightedEdge</code>,
     * weight, and direction parameters.
     *
     * @param edge
     * @param weight
     * @param direction
     */
    public DirectedWeightedEdge(WeightedEdge edge, float weight, Direction direction) {
        super(edge, weight);
        mDirection = direction;
    }

    /**
     * Gets the direction of the edge.
     *
     * @return
     */
    public Direction getDirection() {
        return mDirection;
    }

    /**
     * Sets the direction of the edge.
     *
     * @param direction
     */
    public void setDirection(Direction direction) {
        mDirection = direction;
    }


}
