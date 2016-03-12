package jgh.javagraph;

/**
 *
 */
public class DirectedEdgeConversionVisitor implements IEdgeVisitor {


    /**
     *
     */
    private DirectedWeightedEdge mEdge = null;

    private Direction mDirection = Direction.FORWARDS;

    /**
     * @return
     */
    public DirectedWeightedEdge getEdge() {
        return mEdge;
    }


    @Override
    public void visit(Edge edge) {
        //convert edge to weighted edge
        mEdge = new DirectedWeightedEdge(edge, 0, mDirection);
    }


    @Override
    public void visit(WeightedEdge edge) {
        mEdge = new DirectedWeightedEdge(edge, 0, mDirection);
    }
}
