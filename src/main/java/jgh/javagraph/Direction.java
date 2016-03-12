package jgh.javagraph;

/**
 * Direction enum is for directed graphs to give a consistent meaning
 * to the direction of digraph edges.
 */
public enum Direction {
    /**
     * Forwards direction is from the <code>from()</code> node to the <code>to()</code>
     * node.
     */
    FORWARDS,
    /**
     * Backwards direction is from the <code>to()</code> node to the <code>from()</code>
     * node.
     */
    BACKWARDS,
    /**
     * Direction is two-way.
     */
    BOTH
}
