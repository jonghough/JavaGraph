package jgh.javagraph.algorithms;

import jgh.javagraph.Graph;
import jgh.javagraph.IEdge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


/**
 * Random walk on graph, Markov Chains etc.
 */
public class RandomWalk {

    /**
     *
     * @param graph
     * @param steps
     * @param <E>
     * @return
     */
    public static <N, E extends IEdge<N>> ArrayList<N> generateRandomWalk(Graph<N, E> graph, int steps) {
        Iterator<N> iterator = graph.getNodes().iterator();
        iterator.next();
        N start = iterator.next();
        ArrayList<N> randomPath = new ArrayList<N>();
        Random random = new Random();
        int s = steps;
        N current = start;
        while (s-- >= 0) {
            randomPath.add(current);
            ArrayList<N> adj = Utilities.getAdjacent(graph.getEdges(), current);

            int nextRandom = random.nextInt(adj.size());
            current = adj.get(nextRandom);
        }
        return randomPath;
    }


//    public void buildMarkovChain(){
//        Matrix m = new Matrix(1);
//    }
}
