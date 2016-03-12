package jgh.javagraph.geometry.voronoi;

public class HalfEdge {

    private HalfEdge mNext = null;
    private HalfEdge mPrevious = null;
    private HalfEdge mTwin = null;
    private CircleEvent mTarget = null;
    private Node2d mFace = null;

    public HalfEdge(Node2d face){
        setFace(face);
    }

    public Node2d getFace(){
        return mFace;
    }

    public void setFace(Node2d node){
        mFace = node;
    }

    public HalfEdge twin(){
        return mTwin;
    }

    public void setTwin(HalfEdge edge){
        mTwin = edge;
    }

    public HalfEdge next(){
        return mNext;
    }

    public void setNext(HalfEdge edge){
        mNext = edge;
    }

    public HalfEdge previous(){
        return mPrevious;
    }

    public void setPrevious(HalfEdge edge){
        mPrevious = edge;
    }

    public CircleEvent getTarget(){
        return mTarget;
    }

    public void setTarget(CircleEvent circleEvent){
        mTarget = circleEvent;
    }

    public String printit(){
        return "{ "+ mFace.getLabel()+" ,  "+ mTwin.mFace.getLabel()+" }";
    }
}
