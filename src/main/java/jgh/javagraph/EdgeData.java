package jgh.javagraph;


public class EdgeData<E extends IEdge> {
    private boolean mVisited = false;
    private E mEdge;

    public EdgeData(E e){
        mEdge = e;
    }

    public E getEdge(){
        return mEdge;
    }

    public boolean isVisited() {
        return mVisited;
    }

    public void setVisited(boolean visited) {
        mVisited = visited;
    }
}
