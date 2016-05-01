import jgh.javagraph.Edge;
import jgh.javagraph.Graph;
import jgh.javagraph.INode;
import jgh.javagraph.WeightedEdge;
import jgh.javagraph.trees.SpanningTree;
import jgh.javagraph.generation.CompleteGeneration;
import jgh.javagraph.generation.NodeGeneration;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;


public class SpanningTreeTest {

    @Test
    public void completeGraphSpanningTreeTest(){
        HashSet<NodeGeneration.BasicNode> nodes = NodeGeneration.generateNodes(17);
        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> K6 = CompleteGeneration.create(new HashSet<NodeGeneration.BasicNode>(nodes));
        ArrayList<Edge<NodeGeneration.BasicNode>> spanningTree = SpanningTree.generateSpanningTree(K6);
        Assert.assertEquals(16, spanningTree.size());
    }


    @Test
    public void spanningTreeOfSpanningTreeTest(){
        HashSet<NodeGeneration.BasicNode> nodeSet = NodeGeneration.generateNodes(7);
        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> K7 = CompleteGeneration.create(new HashSet<NodeGeneration.BasicNode>(nodeSet));
        ArrayList<Edge<NodeGeneration.BasicNode>> spanningTree = SpanningTree.generateSpanningTree(K7);
       Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> spanGraph = new Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>>(spanningTree);

       ArrayList<Edge<NodeGeneration.BasicNode>> spanningTree2 = SpanningTree.generateSpanningTree(spanGraph);
        Assert.assertEquals(6 , spanningTree2.size());


    }

    @Test
    public void minSpanningTreeTest1(){
        NodeGeneration.BasicNode bn1 = new NodeGeneration.BasicNode("1");
        NodeGeneration.BasicNode bn2 = new NodeGeneration.BasicNode("2");
        NodeGeneration.BasicNode bn3 = new NodeGeneration.BasicNode("3");
        NodeGeneration.BasicNode bn4 = new NodeGeneration.BasicNode("4");
        NodeGeneration.BasicNode bn5 = new NodeGeneration.BasicNode("5");


        WeightedEdge<NodeGeneration.BasicNode> wedge1 = new WeightedEdge<NodeGeneration.BasicNode>(bn1, bn3,4.2f );
        WeightedEdge<NodeGeneration.BasicNode> wedge2 = new WeightedEdge<NodeGeneration.BasicNode>(bn1, bn4,24.12f );
        WeightedEdge<NodeGeneration.BasicNode> wedge3 = new WeightedEdge<NodeGeneration.BasicNode>(bn2, bn3,10.2f );
        WeightedEdge<NodeGeneration.BasicNode> wedge4 = new WeightedEdge<NodeGeneration.BasicNode>(bn2, bn4,16.8f );
        WeightedEdge<NodeGeneration.BasicNode> wedge5 = new WeightedEdge<NodeGeneration.BasicNode>(bn2, bn5,23.7f );
        WeightedEdge<NodeGeneration.BasicNode> wedge6 = new WeightedEdge<NodeGeneration.BasicNode>(bn3, bn4,107.7f );
        WeightedEdge<NodeGeneration.BasicNode> wedge7 = new WeightedEdge<NodeGeneration.BasicNode>(bn4, bn5,8.88f );

        ArrayList<WeightedEdge<NodeGeneration.BasicNode>> edges = new ArrayList<>();

        edges.add(wedge1);
        edges.add(wedge2);
        edges.add(wedge3);
        edges.add(wedge4);
        edges.add(wedge5);
        edges.add(wedge6);
        edges.add(wedge7);

        ArrayList<WeightedEdge<NodeGeneration.BasicNode>> spt = SpanningTree.generateMinimumSpanningTree(
                new Graph<NodeGeneration.BasicNode, WeightedEdge<NodeGeneration.BasicNode>>(edges));

        Assert.assertTrue(spt.size()==4);
    }

    @Test
    public void minSpanningTreeTest2(){
        NodeGeneration.BasicNode bn1 = new NodeGeneration.BasicNode("1");
        NodeGeneration.BasicNode bn2 = new NodeGeneration.BasicNode("2");
        NodeGeneration.BasicNode bn3 = new NodeGeneration.BasicNode("3");
        NodeGeneration.BasicNode bn4 = new NodeGeneration.BasicNode("4");
        NodeGeneration.BasicNode bn5 = new NodeGeneration.BasicNode("5");
        NodeGeneration.BasicNode bn6 = new NodeGeneration.BasicNode("6");
        NodeGeneration.BasicNode bn7 = new NodeGeneration.BasicNode("7");
        NodeGeneration.BasicNode bn8 = new NodeGeneration.BasicNode("8");

        HashSet<NodeGeneration.BasicNode> nodes = new HashSet<>();
        nodes.add(bn1);
        nodes.add(bn2);
        nodes.add(bn3);
        nodes.add(bn4);
        nodes.add(bn5);
        nodes.add(bn6);
        nodes.add(bn7);
        nodes.add(bn8);

        WeightedEdge<NodeGeneration.BasicNode> wedge1 = new WeightedEdge<NodeGeneration.BasicNode>(bn1, bn2,34.2f );
        WeightedEdge<NodeGeneration.BasicNode> wedge2 = new WeightedEdge<NodeGeneration.BasicNode>(bn1, bn3,34.2f );
        WeightedEdge<NodeGeneration.BasicNode> wedge3 = new WeightedEdge<NodeGeneration.BasicNode>(bn1, bn8,100.7f );
        WeightedEdge<NodeGeneration.BasicNode> wedge4 = new WeightedEdge<NodeGeneration.BasicNode>(bn2, bn4,11.2f );
        WeightedEdge<NodeGeneration.BasicNode> wedge5 = new WeightedEdge<NodeGeneration.BasicNode>(bn2, bn7,81.28f );
        WeightedEdge<NodeGeneration.BasicNode> wedge6 = new WeightedEdge<NodeGeneration.BasicNode>(bn2, bn8,93.52f );
        WeightedEdge<NodeGeneration.BasicNode> wedge7 = new WeightedEdge<NodeGeneration.BasicNode>(bn3, bn5,23.52f );
        WeightedEdge<NodeGeneration.BasicNode> wedge8 = new WeightedEdge<NodeGeneration.BasicNode>(bn4, bn5,3.52f );
        WeightedEdge<NodeGeneration.BasicNode> wedge9 = new WeightedEdge<NodeGeneration.BasicNode>(bn4, bn6,33.7f );
        WeightedEdge<NodeGeneration.BasicNode> wedge10 = new WeightedEdge<NodeGeneration.BasicNode>(bn5, bn6,5.909f );
        WeightedEdge<NodeGeneration.BasicNode> wedge11 = new WeightedEdge<NodeGeneration.BasicNode>(bn5, bn7,22.98f );
        WeightedEdge<NodeGeneration.BasicNode> wedge12 = new WeightedEdge<NodeGeneration.BasicNode>(bn5, bn8,66.74f );

        ArrayList<WeightedEdge<NodeGeneration.BasicNode>> edges = new ArrayList<>();

        edges.add(wedge1);
        edges.add(wedge2);
        edges.add(wedge3);
        edges.add(wedge4);
        edges.add(wedge5);
        edges.add(wedge6);
        edges.add(wedge7);
        edges.add(wedge8);
        edges.add(wedge9);
        edges.add(wedge10);
        edges.add(wedge11);
        edges.add(wedge12);

        ArrayList<WeightedEdge<NodeGeneration.BasicNode>> spt = SpanningTree.generateMinimumSpanningTree(
                new Graph<NodeGeneration.BasicNode, WeightedEdge<NodeGeneration.BasicNode>>(edges));



        Assert.assertTrue(spt.size()== 7);
    }
}
