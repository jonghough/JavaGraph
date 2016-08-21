import jgh.javagraph.Edge;
import jgh.javagraph.Graph;
import jgh.javagraph.algorithms.Connectivity;
import jgh.javagraph.generation.CompleteGeneration;
import jgh.javagraph.generation.NodeGeneration;
import org.junit.Test;

import java.util.HashSet;


public class SpeedTest {

    @Test
    public void testConnectivitySpeed(){

        HashSet<NodeGeneration.BasicNode> nodes = (HashSet< NodeGeneration.BasicNode>)NodeGeneration.generateNodes(2);
        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> K2 = CompleteGeneration.create(new HashSet<NodeGeneration.BasicNode>(nodes));

        long startTime = System.nanoTime();
        Connectivity.isConnected(K2);
        long endTime = System.nanoTime();
        float duration = (endTime - startTime) *1f / 1000000;
        System.out.println("K2 connectivity test took "+duration+" ms");

        /*
         * Here, connectivity tests are performed by DFS. DFS time complexity is
         * O(N+E), for complete graphs ~O(N^2).
         */
//        nodes = (HashSet< NodeGeneration.BasicNode>)NodeGeneration.generateNodes(5);
//        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> K5 = CompleteGeneration.create(new HashSet<NodeGeneration.BasicNode>(nodes));
//
//        startTime = System.nanoTime();
//        Connectivity.isConnected(K5);
//        endTime = System.nanoTime();
//        duration = (endTime - startTime) *1f / 1000000;
//        System.out.println("K5 connectivity test took "+duration+" ms");
//
//        nodes = NodeGeneration.generateNodes(10);
//        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> K10 = CompleteGeneration.create(new HashSet<NodeGeneration.BasicNode>(nodes));
//
//        startTime = System.nanoTime();
//        Connectivity.isConnected(K10);
//        endTime = System.nanoTime();
//        duration = (endTime - startTime) *1f / 1000000;
//        System.out.println("K10 connectivity test took "+duration+" ms");
//
//
//        nodes = NodeGeneration.generateNodes(20);
//        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> K20 = CompleteGeneration.create(new HashSet<NodeGeneration.BasicNode>(nodes));
//
//        startTime = System.nanoTime();
//        Connectivity.isConnected(K20);
//        endTime = System.nanoTime();
//        duration = (endTime - startTime) *1f / 1000000;
//        System.out.println("K20 connectivity test took "+duration+" ms");
//
//        nodes = NodeGeneration.generateNodes(40);
//        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> K40 = CompleteGeneration.create(new HashSet<NodeGeneration.BasicNode>(nodes));
//
//        startTime = System.nanoTime();
//        Connectivity.isConnected(K40);
//        endTime = System.nanoTime();
//        duration = (endTime - startTime) *1f / 1000000;
//        System.out.println("K40 connectivity test took "+duration+" ms");
//
//        nodes = NodeGeneration.generateNodes(80);
//        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> K80 = CompleteGeneration.create(new HashSet<NodeGeneration.BasicNode>(nodes));
//
//        startTime = System.nanoTime();
//        Connectivity.isConnected(K80);
//        endTime = System.nanoTime();
//        duration = (endTime - startTime) *1f / 1000000;
//        System.out.println("K80 connectivity test took "+duration+" ms");
//
//        nodes = NodeGeneration.generateNodes(160);
//        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> K160 = CompleteGeneration.create(new HashSet<NodeGeneration.BasicNode>(nodes));
//
//        startTime = System.nanoTime();
//        Connectivity.isConnected(K160);
//        endTime = System.nanoTime();
//        duration = (endTime - startTime) *1f / 1000000;
//        System.out.println("K160 connectivity test took "+duration+" ms");
//
//        nodes = NodeGeneration.generateNodes(320);
//        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> K320 = CompleteGeneration.create(new HashSet<NodeGeneration.BasicNode>(nodes));
//
//        startTime = System.nanoTime();
//        Connectivity.isConnected(K320);
//        endTime = System.nanoTime();
//        duration = (endTime - startTime) *1f / 1000000;
//        System.out.println("K320 connectivity test took "+duration+" ms");

//        nodes = (HashSet< NodeGeneration.BasicNode>)NodeGeneration.generateNodes(640);
//        Graph<Edge> K640 = CompleteGeneration.create(new HashSet<INode>(nodes));
//
//        startTime = System.nanoTime();
//        Connectivity.isConnected(K640);
//        endTime = System.nanoTime();
//        duration = (endTime - startTime) *1f / 1000000;
//        System.out.println("K640 connectivity test took "+duration+" ms");
    }
}
