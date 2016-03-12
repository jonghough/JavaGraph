import jgh.javagraph.Edge;
import jgh.javagraph.Graph;
import jgh.javagraph.INode;
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
}
