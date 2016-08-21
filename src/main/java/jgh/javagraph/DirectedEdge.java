package jgh.javagraph;

/**
 * Implementation of a <i> Directed edge</i>, an edge connecting two nodes, with a given direction,
 * either <i>forwards, backwards</i> or <i>both</i>, depending in which direction the edge may be traversed.
 */
public class DirectedEdge<N> extends Edge<N> {

    /**
     *
     */
    private Direction mDirection = Direction.FORWARDS;

    /**
     * @param from
     * @param to
     */
    public DirectedEdge(N from, N to, Direction direction) {
        super(from, to);
        mDirection = direction;
    }

    /**
     * @return
     */
    public Direction getDirection() {
        return mDirection;
    }

    /**
     * @param direction
     */
    public void setDirection(Direction direction) {
        mDirection = direction;
    }
}
