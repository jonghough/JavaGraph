package jgh.javagraph.geometry.voronoi;


import jgh.javagraph.Graph;
import jgh.javagraph.IEdge;

import java.util.*;

/**

 * Generates the vertices and edges of a <i>voronoi diagram</i> from an initial list of 2D points.
 * The resulting vertices and edges are given by <code>allCircleEvents</code> which contains all the
 * <i>circle events</i> of the voronoi sturcture, which correspond to vertices of the diagram.
 * <br>
 * Each <code>CircleEvent</code> contains a <code>HalfEdge</code> reference which gives all (half) edges
 * enclosing the face of the voronoi diagram containing the circle event.
 */
public class VoronoiGenerator{

    private ArrayList<Node2d> mNodeList;

    private PriorityQueue<IEvent> mEventQueue;
    private ArrayList<CircleEvent> mAllCircleEvents = new ArrayList<>();


    private double mOpenEdgeLength = 1000D;


    private EventTree mEventTree = new EventTree();


    public VoronoiGenerator(ArrayList<Node2d> nodeList){
        mNodeList = nodeList;

        //Definitely do this. Duplicate points are a big no-no.
        removeDuplicatePoints();


        mEventQueue = new PriorityQueue<IEvent>(new Comparator<IEvent>() {
            @Override
            public int compare(IEvent o1, IEvent o2) {
                if(o1.getDistanceToLine() < o2.getDistanceToLine()) return 1;
                else if(o1.getDistanceToLine() > o2.getDistanceToLine()) return -1;
                else {
                    if(o1.getX() < o2.getX() ) return -1;
                    else if(o1.getX() > o2.getX()) return 1;
                    else return 0;
                }
            }
        });

        for(Node2d node : mNodeList){
            mEventQueue.add(new SiteEvent(node));
        }

    }

    public ArrayList<Node2d> getNodes(){
        return mNodeList;
    }

    public ArrayList<CircleEvent> getAllCircleEvents(){
        return mAllCircleEvents;
    }

    public void setOpenEdgeLength(double length){
        mOpenEdgeLength = Math.abs(length);
    }

    /**
     * Creates the Voronoi Diagram, using an implementation of <i>Fprtune's Algorithm.</i>. The point set is already
     * in a priority queue, so this algorithm goes through each item of the queue (more queue items are added during processing).
     */
    public void createDiagram() {
        while (!mEventQueue.isEmpty()) {
            IEvent nextEvent = mEventQueue.poll();
            //create the initial node
            if(mEventTree.getRoot() == null){
                mEventTree.setRoot(mEventTree. new LeafNode(new TreeItem(((SiteEvent)nextEvent).node)));
                mEventTree.setMaxY(((SiteEvent) nextEvent).node.getY());

            }
            else{
                if (nextEvent instanceof SiteEvent) {
                    handleSiteEvent((SiteEvent) nextEvent);
                } else {

                   handleCircleEvent((CircleEvent) nextEvent);
                  mAllCircleEvents.add((CircleEvent) nextEvent);
                }
            }

            if(mEventTree.getRoot() != null) {

            }
        }

        //remaining breakpoints...
        finishUp();
    }


    private void handleSiteEvent(SiteEvent siteEvent){

        EventTree.TreeNode node = mEventTree.getClosest(this, mEventTree.getRoot(), siteEvent);


        if(node == null || node instanceof EventTree.BreakpointNode){
            return;
        }
        EventTree.LeafNode closest = (EventTree.LeafNode)node;

        if(closest.getDisappearEvent() != null){
            mEventQueue.remove(closest.getDisappearEvent());
            closest.setDisappearEvent(null);
        }

        ArrayList<CircleEvent> circleEvents2 = mEventTree.insertNewSiteEvent(closest, new TreeItem(siteEvent.node));

        mEventQueue.addAll(circleEvents2);
    }

    /**
     * Handles the case when the priority queue gives a <i>circle event</i>. In this case, we have
     * found a vertex of the <i>Voronoi Diagram</i>, which is the center of the circle. We can also
     * get some half-edges from each of the three points lying on the circumfrence of the circle
     * (i.e. the circle's left, center, right points).
     *
     * @param circleEvent Circle event to process.
     */
    private void handleCircleEvent(CircleEvent circleEvent){

        HalfEdge CL = circleEvent.L().getHalfEdge();

        if(CL.getFace() == circleEvent.L().getNode()){
            CL = CL.twin();
        }

        HalfEdge CR = circleEvent.C().getHalfEdge();
        if(CR.getFace() == circleEvent.R().getNode()){
            CR = CR.twin();

        }

        HalfEdge RC = CR.twin();
        RC.setTarget(circleEvent);
        CL.setTarget(circleEvent);

        circleEvent.halfEdge = CR;

        // the center node's mPrevious and mNext nodes need to have their associated circle event's removed, as they
        // are now invalid.
        EventTree.LeafNode prev = (EventTree.LeafNode) mEventTree.getPreviousLeaf(circleEvent.getCenter());
        EventTree.LeafNode next = (EventTree.LeafNode) mEventTree.getNextLeaf(circleEvent.getCenter());

        if(prev != null){
            if(prev.getDisappearEvent() != null){
                mEventQueue.remove(prev.getDisappearEvent());
                prev.setDisappearEvent(null);
            }
        }

        if(next != null){
            if(next.getDisappearEvent() != null){
                mEventQueue.remove(next.getDisappearEvent());
                next.setDisappearEvent(null);
            }
        }

        ArrayList<CircleEvent> newCircles = mEventTree.removeNode(circleEvent, prev, circleEvent.getCenter(), next);
        if(newCircles != null) {
            mEventQueue.addAll(newCircles);
        }

    }


    /**
     * Duplicate nodes need to be removed. A duplicate node is a node with the exact same
     * <i>(x,y)</i> coordinates. If two nodes with the same coordinates are used in the
     * algorithm, things really start to go wrong.
     */
    private void removeDuplicatePoints(){
        if(mNodeList == null)
            return;

        HashMap<Integer, Node2d> compressor = new HashMap<>();
        for(Node2d n : mNodeList){
            compressor.put(101371 * (int)n.getX() + 3 * (int)n.getY() - 11 * (int)(n.getX() * n.getY()), n);
        }
        mNodeList.clear();
        mNodeList.addAll(compressor.values());
    }

    private void finishUp(){
        getFinalNodePoint(mEventTree.getRoot());
    }


    private void getFinalNodePoint(EventTree.TreeNode node) {


        if (node instanceof EventTree.LeafNode) {
            if (((EventTree.LeafNode) node).getBreakpointNode() == null) return;
            Breakpoint b = ((EventTree.LeafNode) node).getBreakpointNode().getBreakpoint();


            Node2d n1 = b.getLeftListEvent().getHalfEdge().getFace();
            Node2d n2 = b.getLeftListEvent().getHalfEdge().twin().getFace();


            double centerx = 0.5 * (b.getLeftListEvent().getHalfEdge().getFace().getX() + b.getLeftListEvent().getHalfEdge().twin().getFace().getX());
            double centery = 0.5 * (b.getLeftListEvent().getHalfEdge().getFace().getY() + b.getLeftListEvent().getHalfEdge().twin().getFace().getY());

            if (n1.getY() == n2.getY()) {
                HalfEdge he = b.getLeftListEvent().getHalfEdge();
                CircleEvent ce = new CircleEvent(centerx, -mOpenEdgeLength /* neg infinity */);
                if (he.getTarget() == null) {
                    he.setTarget(ce);
                } else
                    he.twin().setTarget(ce);

                mAllCircleEvents.add(ce);
            } else {

                double grad = (n2.getY() - n1.getY()) * 1.0 / (n2.getX() - n1.getX());
                double realGrad = -1.0 / grad;

                double constant = centery - realGrad * centerx;

                double bpx = b.getX() - centerx;
                double bpy = b.getY() - centery;

                //if x = bpx...
                double testx = centerx + 10000;
                double testy = testx * realGrad + constant;
                CircleEvent ce;
                if (testx * bpx + testy * bpy > 0)
                    ce = new CircleEvent(testx, testy);
                else
                    ce = new CircleEvent(centerx - 10000, (centerx - 10000) * realGrad + constant);


                HalfEdge he = b.getLeftListEvent().getHalfEdge();
                if (he.getFace() != b.getLeftListEvent().getNode()) {
                    he = he.twin();
                }


                if (he.getTarget() == null) {
                    he.setTarget(ce);
                } else if (he.twin().getTarget() == null) {
                    he.twin().setTarget(ce);
                } else {
                    //big problem... should never happen
                }
                mAllCircleEvents.add(ce);
            }

            return;
        } else {
            Breakpoint b = ((EventTree.BreakpointNode) node).getBreakpoint();

            b.calculateBreakpoint(mOpenEdgeLength);


            if (node.lChild() != null) {
                getFinalNodePoint(node.lChild());

            }
            if (node.rChild() != null) {
                getFinalNodePoint(node.rChild());
            }
        }
    }

    public Graph<Node2d,DelaunayEdge> generateDelaunayGraph(){
        ArrayList<DelaunayEdge> dEdges = new ArrayList<>();

        for(HalfEdge he : mEventTree.halfEdges){
            if(he.getTarget().getX() >= he.twin().getTarget().getX() && he.getTarget().getY() >= he.getTarget().getY()){

                dEdges.add(new DelaunayEdge(he.getFace(), he.twin().getFace()));
            }

        }
        System.out.println("delaunay edge size  "+dEdges.size());
        return new Graph<Node2d,DelaunayEdge>(dEdges);
    }
}


