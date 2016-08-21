package jgh.javagraph.flownetwork;


import jgh.javagraph.*;
import jgh.javagraph.algorithms.Dijkstra;

import java.util.Set;

/**
 * This class is an implementation of the <code>IEdge</code> base class, for
 * edges which hold a <i>capacity</i> and a <i>flow</i> value, and a flow <i>direction</i>.
 * Instances of this edge type are used for <i>Flow Network</i> algorithms.
 */
public class CapacityEdge<N> extends IEdge<N> implements Comparable<CapacityEdge> {

    /**
     * The underlying edge
     */
    private Edge<N> mEdge;

    /**
     * The capacity of this edge. The flow on the edge should never exceed this value.
     */
    private float mCapacity;

    /**
     * The flow along this edge, in the forward direction.
     */
    private float mFlow;

    /**
     * The flow along this edge in the backward direction.
     */
    private float mReverseFlow;

    /**
     * Flow direction.
     */
    private Direction mFlowDirection = Direction.FORWARDS;

    public CapacityEdge(N from, N to) {
        super(from, to);
        mEdge = new Edge(from, to);
    }


    public CapacityEdge(N from, N to, float capacity) {
        super(from, to);
        mCapacity = capacity;
        mEdge = new Edge(from, to);
    }

    public CapacityEdge(IEdge<N> edge, float capacity, Direction direction) {
        super(edge.from(), edge.to());
        mCapacity = capacity;
        mEdge = new Edge<N>(edge.from(), edge.to());
    }

    @Override
    public int compareTo(CapacityEdge o) {
        return 0;
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
        //nothing
    }


    public float getCapacity() {
        return mCapacity;
    }

    public void setCapacity(float capacity) {
        mCapacity = capacity;
    }

    public float getFlow() {
        return mFlow;
    }


    public void setFlow(float flow) {

        mFlow = flow;
    }

    public float getReverseFlow() {
        return mReverseFlow;
    }


    public void setReverseFlow(float flow) {

        mReverseFlow = flow;
    }

    public Direction getFlowDirection() {
        return mFlowDirection;
    }

    public void setFlowDirection(Direction flowDirection) {
        mFlowDirection = flowDirection;
    }

    public float getResidualFlow() {
        int mul = 1;
        if (mFlowDirection == Direction.BACKWARDS)
            return 0 + mFlow;
        else return (mCapacity - mFlow);
    }
}
