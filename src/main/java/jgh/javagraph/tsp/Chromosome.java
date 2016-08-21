package jgh.javagraph.tsp;


import jgh.javagraph.Graph;
import jgh.javagraph.WeightedEdge;

import java.util.ArrayList;

public class Chromosome<N, W extends WeightedEdge<N>> implements Comparable<Chromosome<N,W>> {

    /**
     * Array of the indices of the nodes in the original
     * ArrayList.
     */
    private int[] mNodes;

    /**
     * Cost value, aim is to minimize this.
     */
    private float mCost;

    /**
     * Rate of mutations per generation.
     */
    private float mMutationRate = 0.1f;


    private int mCut;

    private Graph<N,W> mGraph;
    private ArrayList<N> mOrderedNodeList;

    /**
     * Instantiate a single chromosome.
     *
     * @param graph
     */
    public Chromosome(Graph<N,W> graph, ArrayList<N> orderedNodeList) {
        //ArrayList<N> nodes = new ArrayList<>(graph.getNodes());
        mOrderedNodeList = orderedNodeList;
        mGraph = graph;
        mNodes = new int[mOrderedNodeList.size()];

        boolean taken[] = new boolean[mNodes.length];
        mCost = 0;

        for (int i = 0; i < mOrderedNodeList.size(); i++) {
            int candidate;
            do {
                candidate = (int) (0.99999f * Math.random() * mOrderedNodeList.size());
            } while (taken[candidate]);
            mNodes[i] = candidate;
            taken[candidate] = true;
            if (i == mNodes.length - 2) {
                candidate = 0;
                while (taken[candidate]) candidate++;
                mNodes[i + 1] = candidate;
            }
        }
        mCut = 25;
    }

    /**
     * Calculates the cost function for this chromosome. This is the distance necessary
     * to travel in a hamiltonian cycle around the graph.
     *
     * @param graph    Complete Graph
     */
    public void calculateCost(Graph<N,W> graph) {
        mCost = 0;
        for (int i = 0; i < mOrderedNodeList.size() - 1; i++) {
            N n = mOrderedNodeList.get(mNodes[i]);
            N m = mOrderedNodeList.get(mNodes[i + 1]);

            for (W edge : graph.getEdges()) {
                if (edge.from() == n && edge.to() == m) {
                    mCost += edge.getWeight();
                    break;
                } else if (edge.to() == n && edge.from() == m) {
                    mCost += edge.getWeight();
                    break;
                }

            }
        }

        //cycle finding so need to add the distance from last node to first node.
        N n = mOrderedNodeList.get(mNodes[mOrderedNodeList.size() - 1]);
        N m = mOrderedNodeList.get(mNodes[0]);

        for (W edge : graph.getEdges()) {
            if (edge.from() == n && edge.to() == m) {
                mCost += edge.getWeight();
                break;
            } else if (edge.to() == n && edge.from() == m) {
                mCost += edge.getWeight();
                break;
            }

        }

    }

    /**
     * Returns the index of the <i>INode</i> node at the given
     * chromosome index.
     *
     * @param i index of chromosome array
     * @return index of node array
     */
    public int getNode(int i) {
        return mNodes[i];
    }

    /**
     * Sets this chromosome's node order to the given
     * order of the array.
     *
     * @param nodes array of indices of the nodes.
     */
    public void setNodes(int[] nodes) {
        for (int i = 0; i < mNodes.length; i++) {
            mNodes[i] = nodes[i];
        }
    }

    /**
     * Gets the cost result for the current sequence of
     * nodes.
     *
     * @return
     */
    public float getCost() {


        calculateCost(mGraph);
        return mCost;
    }

    /**
     * Sets the cut.
     *
     * @param len
     */
    public void setCut(int len) {
        mCut = len;
    }

    /**
     * Returns the node sequence order as an int array.
     *
     * @return
     */
    public int[] getAllNodes() {
        return mNodes;
    }

    /**
     * Mate with another chromosome to modify child chromosomes
     *
     * @param fatherChromosome The father chromosome to mate with
     * @param child1           child to modify
     * @param child2           child to modify
     * @return mutation count
     */
    public int mate(Chromosome<N,W> fatherChromosome, Chromosome<N,W> child1, Chromosome<N,W> child2) {
        int cutPoint1 = (int) (0.999f * Math.random() * (mNodes.length - mCut));
        int cutPoint2 = cutPoint1 + mCut;

        boolean[] taken1 = new boolean[mNodes.length];
        boolean[] taken2 = new boolean[mNodes.length];

        for (int i = 0; i < mNodes.length; i++) {
            taken1[i] = false;
            taken2[i] = false;

        }

        int[] off1 = new int[mNodes.length];
        int[] off2 = new int[mNodes.length];
        for (int i = 0; i < mNodes.length; i++) {
            if (i < cutPoint1 || i >= cutPoint2) {
                off1[i] = -1;
                off2[i] = -1;
            } else {
                int mother = mNodes[i];
                int father = fatherChromosome.getNode(i);
                off1[i] = father;
                off2[i] = mother;
                taken1[father] = true;
                taken2[mother] = true;
            }
        }

        for (int i = 0; i < cutPoint1; i++) {
            if (off1[i] == -1) {
                for (int j = 0; j < mNodes.length; j++) {
                    int mother = mNodes[j];
                    if (!taken1[mother]) {
                        off1[i] = mother;
                        taken1[mother] = true;
                        break;
                    }
                }
            }

            if (off2[i] == -1) {
                for (int j = 0; j < mNodes.length; j++) {
                    int father = fatherChromosome.getNode(j);
                    if (!taken2[father]) {
                        off2[i] = father;
                        taken2[father] = true;
                        break;
                    }
                }
            }
        }

        for (int i = mNodes.length - 1; i >= cutPoint2; i--) {
            if (off1[i] == -1) {
                for (int j = mNodes.length - 1; j >= 0; j--) {
                    int mother = mNodes[j];
                    if (!taken1[mother]) {
                        off1[i] = mother;
                        taken1[mother] = true;
                        break;
                    }
                }
            }

            if (off2[i] == -1) {
                for (int j = mNodes.length - 1; j >= 0; j--) {
                    int father = fatherChromosome.getNode(j);
                    if (!taken2[father]) {
                        off2[i] = father;
                        taken2[father] = true;
                        break;
                    }
                }
            }
        }


        child1.setNodes(off1);
        child2.setNodes(off2);

        int mutate = 0;

        if (Math.random() < mMutationRate) {
            int swap1 = (int) (0.999f * Math.random() * mNodes.length);
            int swap2 = (int) (0.999f * Math.random() * mNodes.length);
            int i = off1[swap1];
            off1[swap1] = off1[swap2];
            off1[swap2] = i;
            mutate++;
        }
        if (Math.random() < mMutationRate) {
            int swap1 = (int) (0.999f * Math.random() * mNodes.length);
            int swap2 = (int) (0.999f * Math.random() * mNodes.length);
            int i = off2[swap1];
            off2[swap1] = off2[swap2];
            off2[swap2] = i;
            mutate++;
        }

        return mutate;
    }


    @Override
    public int compareTo(Chromosome<N,W> o) {
        if (this.getCost() > o.getCost()) return 1;
        else if (this.getCost() < o.getCost()) return -1;
        else return 0;
    }
}