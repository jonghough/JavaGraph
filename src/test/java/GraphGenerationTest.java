import jgh.javagraph.Edge;
import jgh.javagraph.Graph;
import jgh.javagraph.algorithms.AlgorithmException;
import jgh.javagraph.generation.CompleteGeneration;
import jgh.javagraph.generation.NodeGeneration;
import jgh.javagraph.generation.SpecialGeneration;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;


public class GraphGenerationTest {

    @Test
    public void testCompleteGraph1(){
        HashSet<NodeGeneration.BasicNode> nodes = NodeGeneration.generateNodes(10);
        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> K10 =
                CompleteGeneration.create(new HashSet<NodeGeneration.BasicNode>(nodes));
        Assert.assertEquals(10 * 9 / 2, K10.getEdges().size());

        Assert.assertEquals(9, K10.degree(K10.getMinDegreeNode().get(0)));
    }

    @Test
    public void testCompleteGraph2(){
        HashSet<NodeGeneration.BasicNode> nodes = NodeGeneration.generateNodes(37);
        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> K37 = CompleteGeneration.create(new HashSet<NodeGeneration.BasicNode>(nodes));
        Assert.assertEquals(37 * 36 / 2, K37.getEdges().size());
    }


    @Test
    public void testGridGraph1(){
        HashSet<NodeGeneration.BasicNode> nodes = NodeGeneration.generateNodes(20);
        try {
            Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> Grid_5x4 = SpecialGeneration.generateGrid(new HashSet<NodeGeneration.BasicNode>(nodes), 4);
            Assert.assertEquals(4 * (5 - 1) + 5 * (4 -1 ), Grid_5x4.getEdges().size());
        } catch (AlgorithmException e) {
            Assert.fail("Exception: "+e.getMessage());
        }
    }


    @Test
    public void testGridGraph2(){
        HashSet<NodeGeneration.BasicNode> nodes = NodeGeneration.generateNodes(80);
        try {
            Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> Grid_5x4 = SpecialGeneration.generateGrid3D(new HashSet<NodeGeneration.BasicNode>(nodes), 5, 4);
            Assert.assertEquals(184, Grid_5x4.getEdges().size());
        } catch (AlgorithmException e) {
            Assert.fail("Exception: "+e.getMessage());
        }
    }


    @Test
    public void testGearGraph1(){
        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> gear1 = SpecialGeneration.generateGearGraph(4);
        Assert.assertEquals(9, gear1.getNodes().size());
        Assert.assertEquals(12, gear1.getEdges().size());
    }

    @Test
    public void testGearGraph2(){
        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> gear2 = SpecialGeneration.generateGearGraph(10);
        Assert.assertEquals(21, gear2.getNodes().size());
        Assert.assertEquals(30, gear2.getEdges().size());
    }

    @Test
    public void testHelmGraph1(){
        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> helm = SpecialGeneration.generateHelmGraph(4);
        Assert.assertEquals(9, helm.getNodes().size());
        Assert.assertEquals(12, helm.getEdges().size());
    }

    @Test
    public void testHelmGraph2(){
        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> helm = SpecialGeneration.generateHelmGraph(10);
        Assert.assertEquals(21, helm.getNodes().size());
        Assert.assertEquals(30, helm.getEdges().size());
    }
}

