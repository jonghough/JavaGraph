import jgh.javagraph.Edge;
import jgh.javagraph.Graph;
import jgh.javagraph.INode;
import jgh.javagraph.algorithms.AlgorithmException;
import jgh.javagraph.generation.CompleteGeneration;
import jgh.javagraph.generation.CycleGeneration;
import jgh.javagraph.generation.NodeGeneration;
import jgh.javagraph.generation.SpecialGeneration;
import jgh.javagraph.mincut.MinCut;
import jgh.javagraph.mincut.ModifiableEdge;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

public class MinCutTest {

    @Test
    public void MinCutCycleGraphTest() {
        HashSet<NodeGeneration.BasicNode> nodes = NodeGeneration.generateNodes(5);
        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> C5 = CycleGeneration.generate(new HashSet<NodeGeneration.BasicNode>(nodes));

        ArrayList<ModifiableEdge<NodeGeneration.BasicNode>> cutEdges = MinCut.findMinCut(C5);
        Assert.assertTrue(cutEdges.size() >= 1);

    }

    @Test
    public void MinCutCompleteGraphTest() {
        /* test we can find the minimum cut of a complete graph. After find a set of
         * edges that may be a cut, test that by removing these edges the graph is not connected.
         */
        HashSet<NodeGeneration.BasicNode> nodes = NodeGeneration.generateNodes(10);
        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> K10 = CompleteGeneration.create(new HashSet<NodeGeneration.BasicNode>(nodes));

        ArrayList<ModifiableEdge<NodeGeneration.BasicNode>> cutEdges = MinCut.findMinCut(K10);
        Assert.assertTrue(cutEdges.size() >= 9);
    }

    @Test
    public void MinCutGridGraphTest() {
        HashSet<NodeGeneration.BasicNode> nodes = NodeGeneration.generateNodes(100);
        try {
            Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> Grid10x10 = SpecialGeneration.generateGrid(new HashSet<NodeGeneration.BasicNode>(nodes), 10);
            ArrayList<ModifiableEdge<NodeGeneration.BasicNode>> cutEdges = MinCut.findMinCut(Grid10x10);
            Assert.assertTrue(cutEdges.size() >= 2); /* corner nodes can be cut by cutting two edges */

        } catch (AlgorithmException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}
