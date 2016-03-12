package jgh.javagraph;

/**
 * Node interface. All classes wishing to be nodes /  vertices of a
 * <code>Graph</code> should implement this interface.
 */
public interface INode extends Comparable<INode> {

    /**
     * Returns an identifying label for this node.
     *
     * @return string identifying label.
     */
    String getLabel();


    /**
     * Sets the node's label.
     */
    void setLabel(String label);


    /**
     * Returns a measure of a distance, the meaning of which
     * should be interpreted by implementing classes.
     *
     * @return distance measure
     */
    float getDistance();


    /**
     * Sets some measure of distance
     *
     * @param t
     */
    void setDistance(float t);


    /**
     * Returns true if this node has been visited.
     *
     * @return
     */
    boolean isVisited();


    /**
     * Sets the visited parameter.
     *
     * @param v
     */
    void setVisited(boolean v);


    /**
     * Gets the mPrevious node. Interpretation
     * of the meaning is left to implementation.
     *
     * @return
     */
    INode getPrevious();


    /**
     * Sets the mPrevious node. Interpretation
     * of the meaning is left to implementation.
     *
     * @param node
     */
    void setPrevious(INode node);
}
