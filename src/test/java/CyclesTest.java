import jgh.javagraph.Edge;
import jgh.javagraph.Graph;
import jgh.javagraph.algorithms.Cycles;
import jgh.javagraph.generation.CompleteGeneration;
import jgh.javagraph.generation.NodeGeneration;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class CyclesTest {

    @Test
    public void CycleTest1(){
        ArrayList<NodeGeneration.BasicNode> nodes =
                new ArrayList<NodeGeneration.BasicNode> (NodeGeneration.generateNodes(6));

        Edge e1 = new Edge(nodes.get(4), nodes.get(1));
        Edge e2 = new Edge(nodes.get(4), nodes.get(0));
        Edge e3 = new Edge(nodes.get(0), nodes.get(1));
        Edge e4 = new Edge(nodes.get(1), nodes.get(2));
        Edge e5 = new Edge(nodes.get(2), nodes.get(3));
        Edge e6 = new Edge(nodes.get(3), nodes.get(4));
        Edge e7 = new Edge(nodes.get(3), nodes.get(5));

        ArrayList<Edge<NodeGeneration.BasicNode>> edges = new ArrayList<>();
        edges.add(e1);
        edges.add(e2);edges.add(e3);edges.add(e4);edges.add(e5);edges.add(e6);edges.add(e7);

        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> g = new Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>>(edges);

        // cycles:
        // 4 1 0
        // 4 1 2 3
        // 4 0 1 2 3
        int i = Cycles.findAllCyclesInGraph(g).size();
        System.out.println("NUMBER OF CYCLES:   "+i);
        Assert.assertEquals(3, i);
    }

    @Test
    public void CycleTest2(){
        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> g = CompleteGeneration.create(NodeGeneration.generateNodes(4));

        // Graph k4 has 7 cycles
        int i = Cycles.findAllCyclesInGraph(g).size();
        System.out.println("NUMBER OF CYCLES:   "+i);
        Assert.assertEquals(7, i);
    }
}
