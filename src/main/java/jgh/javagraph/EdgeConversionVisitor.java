package jgh.javagraph;


import java.util.ArrayList;

/**
 * Convert <code>IEdge</code> types into <cdoe>WeightedEdge</cdoe> types.
 */
public final class EdgeConversionVisitor implements IEdgeVisitor {


    /**
     *
     */
    private WeightedEdge mEdge = null;

    /**
     * @return
     */
    public WeightedEdge getEdge() {
        return mEdge;
    }


    @Override
    public void visit(Edge edge) {
        //convert edge to weighted edge
        mEdge = new WeightedEdge(edge, 0);
    }


    @Override
    public void visit(WeightedEdge edge) {
        //nothing.
    }
}
