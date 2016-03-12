import jgh.javagraph.Edge;
import jgh.javagraph.Graph;
import jgh.javagraph.INode;
import jgh.javagraph.algorithms.FordFulkerson;
import jgh.javagraph.cliques.Cliques;
import jgh.javagraph.colorings.Chromatic;
import jgh.javagraph.flownetwork.CapacityEdge;
import jgh.javagraph.generation.CompleteGeneration;
import jgh.javagraph.generation.NodeGeneration;
import jgh.javagraph.generation.SpecialGeneration;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Test functions for cliques.
 */
public class CliqueTest {

    @Test
    public void cliqueTest1() {
        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> g = SpecialGeneration.generateBarbellGraph(10);
        ArrayList<HashSet<NodeGeneration.BasicNode>> cliques = Cliques.findMaximalCliques(g);
    }

    @Test
    public void cliqueTest2() {
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
        ArrayList<HashSet<NodeGeneration.BasicNode>> cliques = Cliques.findMaximalCliques(g);
    }
}
