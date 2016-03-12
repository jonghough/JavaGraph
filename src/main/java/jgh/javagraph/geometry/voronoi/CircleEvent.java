package jgh.javagraph.geometry.voronoi;

/**
 * Circle Events are caused by the conjunction of three
 * consecutive ListItems in the EventLinkedList, which together
 * form a Circle Event.
 * <br>
 * The circle event causes the removal of the SiteEvent represented by
 * the <i>center</i> list item form the beach line. This is due to the left and right
 * arcs overtaking the center arc. Such an event gives a point of the voronoi diagram.
 * Actually, any point of the voronoi diagram can <i>only</i> be caused by
 * a <i>Circle Event</i> like this.
 * <br>
 * It's worth noting that some <i>Circle Events</i> are "false alarms", in that
 * they are created when three consecutive List items are found to create one, but
 * it may not necessarily give a point of the voronoi diagram.
 */
public class CircleEvent implements IEvent {

    public HalfEdge halfEdge; //arbitrary half edge originating here.

    private double  mR; //radius
    private double mX;
    private double mY;
    private TreeItem mLeft, mCenter, mRight;

    private EventTree.LeafNode mLeftNode, mRightNode, mCenterNode;

    public CircleEvent(TreeItem left, TreeItem center, TreeItem right){
        this.mLeft = left;
        this.mRight = right;
        this.mCenter = center;

        calculateCircle();
    }

    public CircleEvent(double x, double y){
        mX = x;
        mY = y;
    }

    public CircleEvent(EventTree.LeafNode left, EventTree.LeafNode center, EventTree.LeafNode right){
        this.mLeftNode = left;
        this.mRightNode = right;
        this.mCenterNode = center;

        mLeft = mLeftNode.getListItem().copy();
        mRight = mRightNode.getListItem().copy();
        mCenter = mCenterNode.getListItem().copy();

        calculateCircle();
    }


    public TreeItem L(){
        return mLeft;
    }

    public TreeItem C(){
        return mCenter;
    }

    public TreeItem R(){
        return mRight;
    }

    public EventTree.LeafNode getLeft(){
        return mLeftNode;
    }

    public EventTree.LeafNode getCenter(){
        return mCenterNode;
    }

    public EventTree.LeafNode getRight(){
        return mRightNode;
    }

    public double getR(){
        return mR;
    }

    @Override
    public double getX() {
        return mX;
    }

    @Override
    public double getY() {
        return mY;
    }

    @Override
    public double getDistanceToLine() {
        // the bottom of the circle.
        return mY-mR;
    }

    public String printit(){
        return mLeft.getNode().getLabel()+"[ "+mLeftNode.hashCode()+ " ] / "+ mCenter.getNode().getLabel()+"[ "+mCenterNode.hashCode()+ " ] / "+mRight.getNode().getLabel()+"[ "+mRightNode.hashCode()+ " ]";
    }


    /**
     * From three non-colinear points find the unique circle circumscribing them.
     * @return true if circle can be found, false if points are colinear.
     */
    public boolean calculateCircle(){
        Node2d point1 = mLeft.getNode();
        Node2d point2 = mCenter.getNode();
        Node2d point3 = mRight.getNode();
        final double epsilon = 0.000001;

        if(point2.getX() == point3.getX()  && point2.getX() == point1.getX()){
            return false;
        }
        else{
            double grad12 = (point2.getY() - point1.getY()) *1f/ (point2.getX() - point1.getX());
            double grad23 = (point3.getY() - point2.getY()) *1f/ (point3.getX() - point2.getX());

            if(Math.abs(grad12 - grad23) < epsilon){
                return false;
            }
        }

        double normal12;
        double normal23;
        if(point2.getY() == point3.getY()) {
               point1 = mRight.getNode();
                point3 = mLeft.getNode();
        }
        if (Math.abs(point2.getX() - point1.getX()) < epsilon || Math.abs(point2.getY() - point1.getY()) < epsilon
                || Math.abs(point2.getX() - point3.getX()) < epsilon || Math.abs(point2.getY() - point3.getY()) < epsilon
                || Math.abs(point1.getX() - point3.getX()) < epsilon || Math.abs(point1.getY() - point3.getY()) < epsilon) {

            return rotateAndCalculateCircle(11.3);
        }

        else {
            if (Math.abs(point2.getX() - point1.getX()) < epsilon || Math.abs(point2.getY() - point1.getY()) < epsilon
                    || Math.abs(point2.getX() - point3.getX()) < epsilon || Math.abs(point2.getY() - point3.getY()) < epsilon
                    || Math.abs(point1.getX() - point3.getX()) < epsilon || Math.abs(point1.getY() - point3.getY()) < epsilon) {

                return rotateAndCalculateCircle(11.3);
            } else {
                // first pair perpendicular bisector
                double grad12 = (point2.getY() - point1.getY()) / (point2.getX() - point1.getX());
                normal12 = -1.0 / grad12;

                double grad23 = (point3.getY() - point2.getY()) / (point3.getX() - point2.getX());
                normal23 = -1.0 / grad23;

                double const12 = 0.5 * (point1.getY() + point2.getY()) - 0.5 * normal12 * (point1.getX() + point2.getX());
                double const23 = 0.5 * (point2.getY() + point3.getY()) - 0.5 * normal23 * (point2.getX() + point3.getX());


                double centerX = (const23 - const12) / (normal12 - normal23);
                double centerY = normal23 * centerX + const23;


                float radius1 = (float) Math.sqrt((point1.getX() - centerX) * (point1.getX() - centerX) +
                        (point1.getY() - centerY) * (point1.getY() - centerY));

                mR = radius1;
                mX = centerX;
                mY = centerY;
            }
        }

        return true;
    }


    /**
     * lazy way to find the circle when two points have the same y coords. Rotate a little and then
     * recalculate.
     * @param degrees
     * @return
     */
    public boolean rotateAndCalculateCircle(double degrees){
        double COS = Math.cos(degrees * Math.PI / 180);
        double SIN = Math.sin(degrees * Math.PI / 180);

        Node2d point1 = mLeft.getNode();
        Node2d point2 = mCenter.getNode();
        Node2d point3 = mRight.getNode();

        double p1x = COS * point1.getX() - SIN * point1.getY();
        double p1y = SIN * point1.getX() + COS * point1.getY();

        point1 = new Node2d(p1x, p1y);

        double p2x = COS * point2.getX() - SIN * point2.getY();
        double p2y = SIN * point2.getX() + COS * point2.getY();

        point2 = new Node2d(p2x, p2y);

        double p3x = COS * point3.getX() - SIN * point3.getY();
        double p3y = SIN * point3.getX() + COS * point3.getY();

        point3 = new Node2d(p3x, p3y);



        double normal12;
        double normal23;


        double grad12 = (point2.getY() - point1.getY()) / (point2.getX() - point1.getX());
            normal12 = -1.0 / grad12;


        double grad23 = (point3.getY() - point2.getY()) / (point3.getX() - point2.getX());
            normal23 = -1.0 / grad23;


        double const12 = 0.5 * (point1.getY() + point2.getY()) - 0.5 * normal12 * (point1.getX() + point2.getX());
        double const23 = 0.5 * (point2.getY() + point3.getY()) - 0.5 * normal23 * (point2.getX() + point3.getX());


        double centerX = (const23 - const12) / (normal12 - normal23);
        double centerY = normal23 * centerX + const23;


        float radius1 = (float) Math.sqrt((point1.getX() - centerX) * (point1.getX() - centerX) +
                (point1.getY() - centerY) * (point1.getY() - centerY));

        double centerX2 = COS * centerX + SIN * centerY;
        double centerY2 = -SIN * centerX + COS * centerY;
        mR = radius1;
        mX = centerX2;
        mY = centerY2;

        return true;
    }


    /**
     * Test if the potential circle event actually is a circle event. This only happens if the
     * two <code>Breakpoint</code>s defining the <code>CircleEvent</code> converge. Convergence
     * implies that the center arc, (i.e. the center node in this calculation) is eliminated.
     * @param circleEvent Potential circleEvent.
     * @return True if the circle event exists. False otherwise.
     */
    public static boolean doBreakpointsConverge(CircleEvent circleEvent){
        boolean clockwise = (circleEvent.L().getNode().getY() - circleEvent.C().getNode().getY())
                * (circleEvent.R().getNode().getX() - circleEvent.C().getNode().getX()) <=
                (circleEvent.L().getNode().getX() - circleEvent.C().getNode().getX())
                        * (circleEvent.R().getNode().getY() - circleEvent.C().getNode().getY());

        if(clockwise)
            return true;
        else
            return false;
    }
}