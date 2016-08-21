package jgh.javagraph.generation;


import java.util.HashSet;

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
    public static class BasicNode {

        private String mLabel;

        public BasicNode(String label) {
            mLabel = label;
        }

        public String getLabel() {
            return mLabel;
        }

        public void setLabel(String label) {
            mLabel = label;
        }

    }
}
