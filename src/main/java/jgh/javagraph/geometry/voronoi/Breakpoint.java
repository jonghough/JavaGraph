package jgh.javagraph.geometry.voronoi;

/**
 * Breakpoint on the beach line. This is a point of incidence between two
 * parabolas defined by the sweepline and site events. Breakpoints <i>(x, y)</i>
 * coordinates move as the sweepline moves, so it is necessary, when calculating
 * <i>(x,y)</i> to know the position of the sweepline.
 */
public class Breakpoint {

    private TreeItem mLeftListEvent;
    private TreeItem mRightListEvent;
    private double mX;
    private double mY;


    /**
     * Instantiates a Breakpoint instance with the given <code>TreeItem</code>s,
     * representing <i>site events</i>.
     * @param listEvent1 left site event
     * @param listEvent2 right site event
     */
    public Breakpoint(TreeItem listEvent1, TreeItem listEvent2) {
        mLeftListEvent = listEvent1;
        mRightListEvent = listEvent2;
    }

    public TreeItem getLeftListEvent(){
        return mLeftListEvent;
    }

    public TreeItem getRightListEvent(){
        return mRightListEvent;
    }

    public double getX(){
        return mX;
    }

    public void setX(double x){
        mX = x;
    }

    public double getY(){
        return mY;
    }

    public void setY(double y){
        mY = y;
    }


    public String printit() {
        return "~~ " +this.hashCode();// "( " + x + ", " + y + " )  < "+leftListEvent.hashCode()+" | "+rightListEvent.hashCode()+" >";
    }

    /**
     * Calculates the <i>(x,y)</i> coordinates of the breakpoint, given the <i>sweepLine</i>.
     * This is the point at which the two parabolas defined by the left and right nodes meet.
     * i.e. this is simply solving an equation <i>f(x) = g(x)</i>, for x, where <i>f(x)</i> and
     * <i>g(x)</i> are the parabolas.
     * @param sweepLine
     * @return <code>true</code> if the calculation was possible, <code>false</code> otherwise.
     */
    public boolean calculateBreakpoint(double sweepLine){

        Node2d p = mLeftListEvent.getNode();
        Node2d q = mRightListEvent.getNode();

        double alpha = 0.5 / (p.getY() - sweepLine);
        double beta = 0.5 / (q.getY() - sweepLine);
        double A = alpha - beta;
        double B = (-2.0 * (alpha * p.getX() - beta * q.getX()));
        double C = (alpha * (p.getY() * p.getY() + p.getX() * p.getX() - sweepLine * sweepLine) - beta * (q.getY()*q.getY()
                + q.getX()*q.getX() - sweepLine * sweepLine));

        if(p.getY() == q.getY()){
            mX = -C / B;
            mY = (1.0 / (2.0 * (p.getY() - sweepLine))) *  (((mX - p.getX()) * (mX - p.getX())) +
                    p.getY() * p.getY() - sweepLine * sweepLine);

        }
        else {

            double disc = B * B - 4 * A * C;
            if (disc < 0) {
                return false;
            }
            mX = (-B + 1.0 * Math.sqrt(B * B - 4 * A * C)) / (2 * A);
            mY = (1.0f / (2.0 * (p.getY() - sweepLine))) * (((mX - p.getX()) * (mX - p.getX())) +
                    p.getY() * p.getY() - sweepLine * sweepLine);

        }

        return true;

    }


}
