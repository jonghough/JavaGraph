import jgh.javagraph.Graph;
import jgh.javagraph.WeightedEdge;
import jgh.javagraph.algorithms.Dijkstra;
import jgh.javagraph.generation.NodeGeneration;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Tests for Dijkstra's algorithm.
 */
public class DijkstraTest {

    @Test
    public void AStarTest1(){

        // A -> B -> F should be shortest path from A to F.
        NodeGeneration.BasicNode b1 = new NodeGeneration.BasicNode("A");
        NodeGeneration.BasicNode b2 = new NodeGeneration.BasicNode("B");
        NodeGeneration.BasicNode b3 = new NodeGeneration.BasicNode("C");
        NodeGeneration.BasicNode b4 = new NodeGeneration.BasicNode("D");
        NodeGeneration.BasicNode b5 = new NodeGeneration.BasicNode("E");
        NodeGeneration.BasicNode b6 = new NodeGeneration.BasicNode("F");

        WeightedEdge<NodeGeneration.BasicNode> eAB = new WeightedEdge<>(b1,b2, 3);
        WeightedEdge<NodeGeneration.BasicNode> eAC = new WeightedEdge<>(b1,b3, 6);
        WeightedEdge<NodeGeneration.BasicNode> eCD = new WeightedEdge<>(b3,b4, 1);
        WeightedEdge<NodeGeneration.BasicNode> eDE = new WeightedEdge<>(b4,b5, 10);
        WeightedEdge<NodeGeneration.BasicNode> eAF = new WeightedEdge<>(b1,b6, 12);
        WeightedEdge<NodeGeneration.BasicNode> eBF = new WeightedEdge<>(b2,b6, 3.5f);
        WeightedEdge<NodeGeneration.BasicNode> eDF = new WeightedEdge<>(b4,b6, 6.5f);

        ArrayList<WeightedEdge<NodeGeneration.BasicNode>> list = new ArrayList<>();
        list.add(eAB);
        list.add(eAC);
        list.add(eCD);
        list.add(eDE);
        list.add(eAF);
        list.add(eBF);
        list.add(eDF);

        Graph<NodeGeneration.BasicNode, WeightedEdge<NodeGeneration.BasicNode>> g = new Graph<NodeGeneration.BasicNode, WeightedEdge<NodeGeneration.BasicNode>>(list);
        ArrayList<NodeGeneration.BasicNode> path = Dijkstra.findMinPath(g, b1, b6);
//        System.out.println("path len  "+path.size());
//        for(NodeGeneration.BasicNode n : path){
//            System.out.println("node in path   "+n.getLabel());
//        }
        Assert.assertTrue(path.contains(b2));
    }

    @Test
    public void AStarTest2(){

        // A -> F -> D -> H should be shortest path from A to H
        NodeGeneration.BasicNode b1 = new NodeGeneration.BasicNode("A");
        NodeGeneration.BasicNode b2 = new NodeGeneration.BasicNode("B");
        NodeGeneration.BasicNode b3 = new NodeGeneration.BasicNode("C");
        NodeGeneration.BasicNode b4 = new NodeGeneration.BasicNode("D");
        NodeGeneration.BasicNode b5 = new NodeGeneration.BasicNode("E");
        NodeGeneration.BasicNode b6 = new NodeGeneration.BasicNode("F");
        NodeGeneration.BasicNode b7 = new NodeGeneration.BasicNode("G");
        NodeGeneration.BasicNode b8 = new NodeGeneration.BasicNode("H");

        WeightedEdge<NodeGeneration.BasicNode> eAB = new WeightedEdge<>(b1,b2, 10);
        WeightedEdge<NodeGeneration.BasicNode> eAG = new WeightedEdge<>(b1,b7, 14);
        WeightedEdge<NodeGeneration.BasicNode> eCD = new WeightedEdge<>(b3,b4, 5);
        WeightedEdge<NodeGeneration.BasicNode> eDH = new WeightedEdge<>(b4,b8, 5.5f);
        WeightedEdge<NodeGeneration.BasicNode> eAF = new WeightedEdge<>(b1,b6, 12);
        WeightedEdge<NodeGeneration.BasicNode> eBF = new WeightedEdge<>(b2,b6, 3.5f);
        WeightedEdge<NodeGeneration.BasicNode> eDF = new WeightedEdge<>(b4,b6, 1.5f);
        WeightedEdge<NodeGeneration.BasicNode> eCG = new WeightedEdge<>(b3,b7, 11.5f);
        WeightedEdge<NodeGeneration.BasicNode> eBC = new WeightedEdge<>(b2,b3, 6.5f);
        WeightedEdge<NodeGeneration.BasicNode> eCE = new WeightedEdge<>(b3,b5, 6.5f);

        ArrayList<WeightedEdge<NodeGeneration.BasicNode>> list = new ArrayList<>();
        list.add(eAB);
        list.add(eAG);
        list.add(eCD);
        list.add(eDH);
        list.add(eAF);
        list.add(eBF);
        list.add(eDF);
        list.add(eCG);
        list.add(eBC);
        list.add(eCE);

        Graph<NodeGeneration.BasicNode, WeightedEdge<NodeGeneration.BasicNode>> g = new Graph<NodeGeneration.BasicNode, WeightedEdge<NodeGeneration.BasicNode>>(list);
        ArrayList<NodeGeneration.BasicNode> path = Dijkstra.findMinPath(g, b1, b8);

        Assert.assertTrue("contains A D F H and size is 4", path.contains(b1) && path.contains(b4) &&
                path.contains(b6) && path.contains(b8) && path.size() == 4);
//        System.out.println("path len  "+path.size());
//        for(NodeGeneration.BasicNode n : path){
//            System.out.println("node in path   "+n.getLabel());
//        }
    }


    @Test
    public void AStarTest3(){

        // A ->B should be shortest path from A to B
        NodeGeneration.BasicNode b1 = new NodeGeneration.BasicNode("A");
        NodeGeneration.BasicNode b2 = new NodeGeneration.BasicNode("B");

        WeightedEdge<NodeGeneration.BasicNode> eAB = new WeightedEdge<>(b1,b2, 10);

        ArrayList<WeightedEdge<NodeGeneration.BasicNode>> list = new ArrayList<>();
        list.add(eAB);

        Graph<NodeGeneration.BasicNode, WeightedEdge<NodeGeneration.BasicNode>> g = new Graph<NodeGeneration.BasicNode, WeightedEdge<NodeGeneration.BasicNode>>(list);
        ArrayList<NodeGeneration.BasicNode> path = Dijkstra.findMinPath(g, b1, b2);

        Assert.assertTrue("contains A D F H and size is 4", path.contains(b1) && path.contains(b2) && path.size() == 2);
//        System.out.println("path len  "+path.size());
//        for(NodeGeneration.BasicNode n : path){
//            System.out.println("node in path   "+n.getLabel());
//        }
    }
}
