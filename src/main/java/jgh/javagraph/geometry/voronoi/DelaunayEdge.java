package jgh.javagraph.geometry.voronoi;

import jgh.javagraph.Edge;
import jgh.javagraph.IEdge;
import jgh.javagraph.IEdgeVisitor;

import java.util.Set;


public class DelaunayEdge extends IEdge<Node2d> {

    /**
     * The underlying <code>IEdge</code> implementation.
     */
    protected Edge<Node2d> mEdge;

    public DelaunayEdge(Node2d from, Node2d to){
        super(from, to);
        mEdge = new Edge(from, to);
    }

    @Override
    public Node2d from() {
        return mEdge.from();
    }

    @Override
    public Node2d to() {
        return mEdge.to();
    }

    @Override
    public Set<Node2d> nodes() {
        return mEdge.nodes();
    }

    @Override
    public void accept(IEdgeVisitor visitor) {

    }

    @Override
    public boolean isVisited() {
        return mEdge.isVisited();
    }

    @Override
    public void setVisited(boolean visited) {
        mEdge.setVisited(visited);
    }
}
