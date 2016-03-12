import jgh.javagraph.*;
import jgh.javagraph.algorithms.AStar;
import jgh.javagraph.algorithms.AlgorithmException;
import jgh.javagraph.algorithms.BellmanFord;
import jgh.javagraph.generation.NodeGeneration;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;


public class BellmanFordTest {

    @Test
    public void BellmanFordTest1(){

        // A -> B -> C -> D - E -> F -> G -> H should be shortest path from A to H,
        // i.e faster than just going A -> H
        NodeGeneration.BasicNode b1 = new NodeGeneration.BasicNode("A");
        NodeGeneration.BasicNode b2 = new NodeGeneration.BasicNode("B");
        NodeGeneration.BasicNode b3 = new NodeGeneration.BasicNode("C");
        NodeGeneration.BasicNode b4 = new NodeGeneration.BasicNode("D");
        NodeGeneration.BasicNode b5 = new NodeGeneration.BasicNode("E");
        NodeGeneration.BasicNode b6 = new NodeGeneration.BasicNode("F");
        NodeGeneration.BasicNode b7 = new NodeGeneration.BasicNode("G");
        NodeGeneration.BasicNode b8 = new NodeGeneration.BasicNode("H");

        DirectedWeightedEdge<NodeGeneration.BasicNode> eAB = new DirectedWeightedEdge<>(b1,b2, 2.3f, Direction.FORWARDS);
        DirectedWeightedEdge<NodeGeneration.BasicNode> eBC = new DirectedWeightedEdge<>(b2,b3, 5.5f, Direction.FORWARDS);
        DirectedWeightedEdge<NodeGeneration.BasicNode> eCD = new DirectedWeightedEdge<>(b3,b4, 1.3f, Direction.FORWARDS);
        DirectedWeightedEdge<NodeGeneration.BasicNode> eDE = new DirectedWeightedEdge<>(b4,b5, -6.5f, Direction.FORWARDS);
        DirectedWeightedEdge<NodeGeneration.BasicNode> eEF = new DirectedWeightedEdge<>(b5,b6, 0.5f, Direction.FORWARDS);
        DirectedWeightedEdge<NodeGeneration.BasicNode> eFG = new DirectedWeightedEdge<>(b6,b7, 3.5f, Direction.FORWARDS);
        DirectedWeightedEdge<NodeGeneration.BasicNode> eGH = new DirectedWeightedEdge<>(b7,b8, 1.5f, Direction.FORWARDS);
        DirectedWeightedEdge<NodeGeneration.BasicNode> eHA = new DirectedWeightedEdge<>(b8,b1, 10.5f, Direction.FORWARDS);

        ArrayList<DirectedWeightedEdge<NodeGeneration.BasicNode>> list = new ArrayList<>();
        list.add(eAB);
        list.add(eBC);
        list.add(eCD);
        list.add(eDE);
        list.add(eEF);
        list.add(eFG);
        list.add(eGH);
       // list.add(eHA);

        Graph<NodeGeneration.BasicNode, DirectedWeightedEdge<NodeGeneration.BasicNode>> g = new Graph<NodeGeneration.BasicNode, DirectedWeightedEdge<NodeGeneration.BasicNode>>(list);
        ArrayList<NodeGeneration.BasicNode> path = null;
        try {
            path = BellmanFord.findMinPath(g, b1, b8);


        Assert.assertTrue("contains A B C D E F G H and size is 8", path.contains(b1) && path.contains(b2) &&
                path.contains(b3) && path.contains(b4) && path.contains(b5) && path.contains(b6) && path.contains(b7)
                && path.contains(b8) && path.size() == 8);
        System.out.println("path len  "+path.size());
        for(NodeGeneration.BasicNode n : path){
            System.out.println("node in path   "+n.getLabel());
        }
        } catch (AlgorithmException e) {
            Assert.fail("Failure: Bellman Ford test algorithm exception, "+e.getMessage());
        }
    }
}
