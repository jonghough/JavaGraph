import jgh.javagraph.Graph;
import jgh.javagraph.algorithms.FordFulkerson;
import jgh.javagraph.flownetwork.CapacityEdge;
import jgh.javagraph.generation.NodeGeneration;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;

/**
 * Test for Ford Fulkerson Algorithm for finding the maximum flow in a
 * flow network.
 */
public class FordFulkersonTest {


    @Test
    public void TestFF1() {
        /*
         * This test is a copy of this problem:
         * https://en.wikipedia.org/wiki/Edmonds%E2%80%93Karp_algorithm
         */
        ArrayList<NodeGeneration.BasicNode> nodes = new ArrayList<>(NodeGeneration.generateNodes(7));
        for (int i = 0; i < nodes.size(); i++) {

        }
        nodes.get(0).setLabel("A");
        nodes.get(1).setLabel("B");
        nodes.get(2).setLabel("C");
        nodes.get(3).setLabel("D");
        nodes.get(4).setLabel("E");
        nodes.get(5).setLabel("F");
        nodes.get(6).setLabel("G");
        CapacityEdge e1 = new CapacityEdge(nodes.get(0), nodes.get(1), 3);
        CapacityEdge e2 = new CapacityEdge(nodes.get(0), nodes.get(3), 3);

        CapacityEdge e3 = new CapacityEdge(nodes.get(1), nodes.get(2), 4);
        CapacityEdge e3x = new CapacityEdge(nodes.get(1), nodes.get(4), 1);
        CapacityEdge e4 = new CapacityEdge(nodes.get(2), nodes.get(0), 3);
        CapacityEdge e5 = new CapacityEdge(nodes.get(2), nodes.get(3), 1);
        CapacityEdge e6 = new CapacityEdge(nodes.get(2), nodes.get(4), 2);
        CapacityEdge e7 = new CapacityEdge(nodes.get(3), nodes.get(5), 6);
        CapacityEdge e8 = new CapacityEdge(nodes.get(3), nodes.get(4), 2);
        CapacityEdge e9 = new CapacityEdge(nodes.get(4), nodes.get(6), 1);
        CapacityEdge e10 = new CapacityEdge(nodes.get(5), nodes.get(6), 9);

//        CapacityEdge f1 = new CapacityEdge(nodes.get(0), nodes.get(1), 3);
//        CapacityEdge f2 = new CapacityEdge(nodes.get(0), nodes.get(2), 2);
//        CapacityEdge f3 = new CapacityEdge(nodes.get(0), nodes.get(3), 1);
//        CapacityEdge f4 = new CapacityEdge(nodes.get(1), nodes.get(2), 5);
//        CapacityEdge f5 = new CapacityEdge(nodes.get(3), nodes.get(4), 1);
//        CapacityEdge f6 = new CapacityEdge(nodes.get(2), nodes.get(4), 2);
//        CapacityEdge f7 = new CapacityEdge(nodes.get(4), nodes.get(5), 6);
//        CapacityEdge f8 = new CapacityEdge(nodes.get(3), nodes.get(6), 1);
//
//        CapacityEdge f9 = new CapacityEdge(nodes.get(5), nodes.get(6), 10);
//        CapacityEdge f10 = new CapacityEdge(nodes.get(1), nodes.get(6), 5);


        ArrayList<CapacityEdge<NodeGeneration.BasicNode>> edges = new ArrayList<>();
        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.add(e4);
        edges.add(e3x);
        edges.add(e5);
        edges.add(e6);
        edges.add(e7);
        edges.add(e8);
        edges.add(e9);
        edges.add(e10);
//        edges.add(f1);
//        edges.add(f2);
//        edges.add(f3);
//        edges.add(f4);
//        edges.add(f5);
//        edges.add(f6);
//        edges.add(f7);
//        edges.add(f8);
//        edges.add(f9);
//        edges.add(f10);

        Graph<NodeGeneration.BasicNode,CapacityEdge<NodeGeneration.BasicNode>> g = new Graph<NodeGeneration.BasicNode,CapacityEdge<NodeGeneration.BasicNode>>(edges);
        FordFulkerson.findMaxFlow(g, nodes.get(0), nodes.get(6));

//        for (CapacityEdge e : edges) {
//            System.out.println("capacity edge " + e.from().getLabel() + "->" + e.to().getLabel() + ", " + e.getFlow() + "/" + e.getCapacity());
//        }

        Assert.assertEquals(9, e10.getCapacity(), 0.1);
    }
}
