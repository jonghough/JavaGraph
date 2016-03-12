package jgh.javagraph.geometry.voronoi;


/**
 * Class to implement the <code>IEvent</code> interface, holding
 * a single 2d node object. It needs to implement <code>IEvent</code>
 * to be placed into the <code>PriorityQueue</code> in the
 * <code>VoronoiGenerator</code> class.
 */
public class SiteEvent implements IEvent {


    public Node2d node;

    public SiteEvent(Node2d node){
        this.node = node;
    }

    @Override
    public double getX() {
        return node.getX();
    }

    @Override
    public double getY() {
        return node.getY();
    }

    @Override
    public double getDistanceToLine() {
        return node.getY();
    }
}
