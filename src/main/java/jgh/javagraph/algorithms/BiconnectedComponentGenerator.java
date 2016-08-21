package jgh.javagraph.algorithms;


import jgh.javagraph.Graph;
import jgh.javagraph.IEdge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;


/**
 * Class to generate <i>Biconnected components</i> of a <code>Graph</code>, where
 * a biconnected component is defined as a maximal subset of the edges of the graph such that
 * removing any edge from the subset will still leave a connected graph.
 *
 */
public class BiconnectedComponentGenerator<N, E extends IEdge<N>> {

    private Stack<E> mEdgeStack;
    private HashMap<N,NodeData<N>> mDataMap;
    private Graph<N,E> mGraph;
    private HashSet<HashSet<E>> mConnectedSets;
    private int mCounter = 0;

    private class NodeData<N>{
        public boolean visited = false;
        public N parent = null;
        public int depth = 0;
        public int low = 0;
        public NodeData(){}
    }

    public BiconnectedComponentGenerator(Graph<N,E> graph){
        mGraph = graph;
        mEdgeStack = new Stack<>();
        mDataMap = new HashMap<>();
        mCounter = 0;
        mConnectedSets = new HashSet<>();
    }

    public HashSet<HashSet<E>> getBiconnectedComponents(){
        return mConnectedSets;
    }

    public void findBiconnectedComponents(){

        for(N node : mGraph.getNodes()){
            //node.setVisited(false);
            mDataMap.put(node, new NodeData<N>()); //initialize null parent
        }

        for(N node : mGraph.getNodes()){
            if(!mDataMap.get(node).visited){
                visitNode(node);
            }
        }

    }

    private void visitNode(N node){
        mDataMap.get(node).visited = true;
        mCounter ++;
        mDataMap.get(node).depth = mCounter;
        mDataMap.get(node).low = mCounter;
        for(E nextEdge : Utilities.getIncidentEdges(mGraph.getEdges(), node)){
            N nextNode = nextEdge.from() == node ? nextEdge.to() : nextEdge.from();
            if(!mDataMap.get(nextNode).visited) {

                mEdgeStack.push(nextEdge);
                mDataMap.get(nextNode).parent = node;
                visitNode(nextNode);
                if(mDataMap.get(nextNode).low >= mDataMap.get(node).depth){
                    mConnectedSets.add(getBCNodes(nextEdge));
                }
                mDataMap.get(node).low = Math.min(mDataMap.get(nextNode).low,mDataMap.get(node).low);
            }
            else if(mDataMap.get(node).parent != nextNode && mDataMap.get(node).depth > mDataMap.get(nextNode).depth){

                mEdgeStack.push(nextEdge);
                mDataMap.get(node).low = Math.min(mDataMap.get(nextNode).depth,mDataMap.get(node).low);
            }
        }
    }

    private HashSet<E> getBCNodes(E lastEdge){
        HashSet<E> nl = new HashSet<>();

        while(true){
            E edge = mEdgeStack.pop();
            nl.add(edge);
            if(lastEdge == edge)
                break;

        }
        return nl;
    }

}
