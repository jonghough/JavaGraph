package jgh.javagraph.geometry.voronoi;

/**
 * Element class of the <code>EventTree</code>. Instances of this class have
 * references to the <i>Node2d</i> point, from which a Site Event was formed.
 * <br>
 * Each instance also has a possible reference to a <i> Circle Event</i>,
 * the circle event for which this list item should be removed from the linked list.
 *
 */
public class TreeItem {

    private Node2d mPoint;

    private HalfEdge mIncidentHalfEdge;

    public TreeItem(Node2d node){
        this.mPoint = node;
    }

    public Node2d getNode(){
        return mPoint;
    }

    public TreeItem copy(){
        TreeItem ev = new TreeItem(mPoint);
        ev.mIncidentHalfEdge = mIncidentHalfEdge;
        return ev;
    }

    public HalfEdge getHalfEdge(){
        return mIncidentHalfEdge;
    }

    public void setHalfEdge(HalfEdge halfEdge){
        mIncidentHalfEdge = halfEdge;
    }
}
