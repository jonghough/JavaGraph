package jgh.javagraph.generation;


import jgh.javagraph.INode;

import java.util.HashSet;
import java.util.Set;

/**
 * Static methods for generating nodes.
 */
public class NodeGeneration {


    /**
     * Generates Basic Nodes. THis is really used for testing purposes, to quickly generate some nodes
     * for various graphs.
     *
     * @param number the number of nodes to generate, must be positive.
     * @return Set of nodes (<code>BasicNode</code>s).
     */
    public static HashSet<BasicNode> generateNodes(int number) {
        if (number <= 0) throw new IllegalArgumentException("Cannot generate a non-positive number of nodes.");
        HashSet<BasicNode> nodes = new HashSet<>();
        int i = number;
        while (i-- > 0) {
            BasicNode b = new BasicNode("Node_" + i);
            nodes.add(b);
        }
        return nodes;
    }


    /**
     * Basic Node, used for generation purposes and testing.
     */
    public static class BasicNode implements INode {

        private String mLabel;
        private float mDistance;
        private boolean mVisited;
        private INode mPrevious;

        public BasicNode(String label) {
            mLabel = label;
        }

        @Override
        public String getLabel() {
            return mLabel;
        }

        @Override
        public void setLabel(String label) {
            mLabel = label;
        }

        @Override
        public float getDistance() {
            return mDistance;
        }

        @Override
        public void setDistance(float t) {
            mDistance = t;
        }

        @Override
        public boolean isVisited() {
            return mVisited;
        }

        @Override
        public void setVisited(boolean v) {
            mVisited = v;
        }

        @Override
        public INode getPrevious() {
            return mPrevious;
        }

        @Override
        public void setPrevious(INode node) {
            mPrevious = node;
        }

        @Override
        public int compareTo(INode o) {
            return mLabel.compareTo(o.getLabel());
        }
    }
}
