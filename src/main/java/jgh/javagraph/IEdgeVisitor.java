package jgh.javagraph;

/**
 * Visitor for edges
 */
public interface IEdgeVisitor {

    /**
     * @param edge
     */
    void visit(Edge edge);

    /**
     * @param edge
     */
    void visit(WeightedEdge edge);
}