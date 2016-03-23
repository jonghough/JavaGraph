package jgh.javagraph.tsp;

import jgh.javagraph.Graph;
import jgh.javagraph.INode;
import jgh.javagraph.WeightedEdge;
import jgh.javagraph.algorithms.AlgorithmException;
import jgh.javagraph.algorithms.Utilities;

import java.util.*;

/**
 * A <i>Travelling Salesman Problem</i> solver. Uses  a genetic algorithm to
 * find the probable best solution, i.e. the minimum weight Hamiltonian Path
 * on a <b>complete graph</b>.
 * <br>
 * This is based on the Genetic Algorithm example in <i>Introduction to Neural Networks with Java</i>.
 */
public class TspSolver<N extends INode, E extends WeightedEdge<N>> {

    // graph to solve.
    private Graph<N,E> mGraph;
    private int mNodeCount;
    private int mPopulation;
    private int mMatingPopulation;
    private int mSelectedPopulation;

    private ArrayList<Chromosome<N,E>> mChromosomes;
    private int mGeneration = 0;
    private float mCost = Float.MAX_VALUE;

    private ArrayList<N> mNodeList;

    /**
     * Instantiates an instance of a TspSolver.
     *
     * @param graph complete graph.
     * @throws AlgorithmException
     */
    public TspSolver(Graph<N,E> graph) throws AlgorithmException {
        //if the graph is not complete throw up
        if (!Utilities.isComplete(graph))
            throw new AlgorithmException("TspSolver only accepts complete graphs. This graph is not complete.");
        mGraph = graph;
        mNodeList = new ArrayList<>(mGraph.getNodes());
        mNodeCount = graph.getNodes().size();
        mPopulation = 800;
        mMatingPopulation = (int) (mPopulation * 0.5f);

        mSelectedPopulation = (int) (mMatingPopulation * 0.5f);

        mChromosomes = new ArrayList<Chromosome<N,E>>(mPopulation);
        for (int i = 0; i < mPopulation; i++) {
            mChromosomes.add(new Chromosome(graph, mNodeList));
        }

        Collections.sort(mChromosomes, new ChromosomeComparator());
    }

    /**
     * Solve the <i>TSP</i> problem, heuristically.
     */
    public void solve() {
        float cachedCost = 0f;
        while (mGeneration < 50) {
            int iOffset = mMatingPopulation;
            int mutated = 0;
            for (int i = 0; i < mSelectedPopulation; i++) {
                Chromosome mother = mChromosomes.get(i);
                int f = (int) (0.999f * Math.random() * mMatingPopulation);

                Chromosome father = mChromosomes.get(f);
                mutated += mother.mate(father, mChromosomes.get(iOffset), mChromosomes.get(iOffset + 1));
                iOffset += 2;
            }

            Collections.sort(mChromosomes, new ChromosomeComparator());
            mChromosomes.get(0).calculateCost(mGraph);
            float cost = mChromosomes.get(0).getCost();
            float costmax = mChromosomes.get(mChromosomes.size() - 1).getCost();
            float diff = Math.abs(cost - mCost);
            mCost = cost;
            float mRate = 100 * mutated * 1f / mMatingPopulation;

            if ((int) mCost == (int) cachedCost) {
                mGeneration++;
            } else {
                mGeneration = 0;
                cachedCost = mCost;
            }
        }

        int[] bestPath = mChromosomes.get(0).getAllNodes();
        for (int nodeIndex : bestPath) {
            System.out.println("node " + mNodeList.get(nodeIndex).getLabel());
        }
        System.out.println("Cost is "+mChromosomes.get(0).getCost());
    }


    private final class ChromosomeComparator implements Comparator<Chromosome<N,E>> {

        @Override
        public int compare(Chromosome o1, Chromosome o2) {
            return o1.compareTo(o2);
        }
    }

}
