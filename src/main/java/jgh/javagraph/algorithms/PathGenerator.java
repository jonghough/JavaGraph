package jgh.javagraph.algorithms;

import jgh.javagraph.IEdge;
import jgh.javagraph.INode;

import java.util.HashSet;


public class PathGenerator<N extends INode, E extends IEdge<N>> {

    private HashSet<HashSet<E>> mBiconnectedComponents;
    public PathGenerator(HashSet<HashSet<E>> biconnectedComponents){
        mBiconnectedComponents = biconnectedComponents;
    }
}
