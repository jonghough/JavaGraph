package jgh.javagraph.geometry.voronoi;

import jgh.javagraph.INode;

/**
 * Implementation of <code>INode</code> interface
 * containing <i>(x,y)</i> coordinates. We override
 * Hashcode and equals to make sure any two points with the same
 * coordinates are counted as the same point. The purpose for this is two-fold:
 * <br>
 * 1. Duplicate points mess up the algorithm implementation
 * 2. To convert the resulting set of edges and nodes to be used with <code>Graph</code>s
 *    the nodes with the same coordinates need to be seen as the same node, otherwise
 *    almost all <code>Graph</code> algorithms break down.
 */
public class Node2d implements INode {

    private String mLabel = null;
    private boolean mVisited = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node2d node2d = (Node2d) o;

        if (Double.compare(node2d.mX, mX) != 0) return false;
        if (Double.compare(node2d.mY, mY) != 0) return false;
        if (mLabel != null ? !mLabel.equals(node2d.mLabel) : node2d.mLabel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = mLabel != null ? mLabel.hashCode() : 0;
        temp = Double.doubleToLongBits(mX);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mY);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    private double mX, mY;

    public Node2d(double x, double y){
        mX = x;
        mY = y;
    }

    public HalfEdge halfEdge = null;

    public double getX(){
        return mX;
    }

    public double getY(){
        return mY;
    }

    public void setX(double x){
        mX = x;
    }

    public void setY(double y){
        mY = y;
    }

    @Override
    public String getLabel() {
        return "("+getX()+", "+getY()+")";
    }

    @Override
    public void setLabel(String label) {
        mLabel = label;
    }

    @Override
    public float getDistance() {
        return 0;
    }

    @Override
    public void setDistance(float t) {

    }

    @Override
    public boolean isVisited() {
        return mVisited;
    }

    @Override
    public void setVisited(boolean v) {
        mVisited = v;
    }

    @Override
    public INode getPrevious() {
        return null;
    }

    @Override
    public void setPrevious(INode node) {

    }

    @Override
    public int compareTo(INode o) {
        return 0;
    }
}
