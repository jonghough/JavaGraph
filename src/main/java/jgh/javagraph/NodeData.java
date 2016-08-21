package jgh.javagraph;


import java.util.Optional;

public final class NodeData<N>{

    private N mParent;
    private float mDistance = 0.0f;
    private boolean mVisited = false;
    private Optional<N> mPrevious = Optional.empty();

    public NodeData(N node){
        mParent = node;
        mVisited = false;
    }

    public N getNode(){
        return mParent;
    }

    public float getDistance() {
        return mDistance;
    }


    public void setDistance(float t) {
        mDistance = t;
    }


    public boolean isVisited() {
        return mVisited;
    }


    public void setVisited(boolean v) {
        mVisited = v;
    }


    public Optional<N> getPrevious() {
        return mPrevious;
    }


    public void setPrevious(Optional<N> node) {
        mPrevious = node;
    }


}
