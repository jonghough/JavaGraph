package jgh.javagraph.generation;

import jgh.javagraph.Edge;
import jgh.javagraph.Graph;
import jgh.javagraph.IEdge;
import jgh.javagraph.INode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

/**
 *
 */
public class RandomGeneration {

    /**
     * Generates a random graph on the given nodes. The expectation of number of
     * edges is <i>n*(n-1)*0.5*p</i>, where <i>n</i> is the number of nodes,
     * <i>p</i> is the probability any given edge exists (i.e. an exist between
     * any pair of nodes exists).
     *
     * @param nodes    Set of nodes to use.
     * @param edgeProb probability that an edge will be included. Clamped
     *                 to be in range [0,1]
     * @return randomly generated graph on the given nodes.
     */
    public static <N extends INode> Graph<N,Edge<N>> generateRandomGraph(HashSet<N> nodes, float edgeProb) {
        float ep = edgeProb;
        ep = ep < 0 ? 0 : ep > 1.0f ? 1.0f : ep;
        Graph<N,Edge<N>> compG = CompleteGeneration.create(nodes);
        Random rand = new Random(System.currentTimeMillis());

        Iterator<Edge<N>> it = compG.getEdges().iterator();
        while (it.hasNext()) {
            it.next();
            float r = rand.nextFloat();
            if (r > ep) {
                it.remove();
            }
        }

        // include the possibility nodes have no edges, which would have
        // been removed automatically by CompleteGeneration.create(),
        // put them back here.
        return new Graph<N,Edge<N>>(new ArrayList<Edge<N>>(compG.getEdges()), nodes);
    }
}
