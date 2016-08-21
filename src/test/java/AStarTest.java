import jgh.javagraph.Graph;
import jgh.javagraph.WeightedEdge;
import jgh.javagraph.algorithms.AStar;
import jgh.javagraph.generation.NodeGeneration;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;


public class AStarTest {

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
        ArrayList<NodeGeneration.BasicNode> path = AStar.findMinPath(g, b1, b6, new AStar.IAStarHeuristic<NodeGeneration.BasicNode>() {
            @Override
            public float getHeuristic(NodeGeneration.BasicNode t, NodeGeneration.BasicNode goal) {
                return 15;
            }
        });
//        System.out.println("path len  "+path.size());
//        for(NodeGeneration.BasicNode n : path){
//            System.out.println("node in path   "+n.getLabel());
//        }

        Assert.assertTrue("contains A B F and size is 3", path.contains(b1) &&
                path.contains(b2) && path.contains(b6) && path.size() == 3);
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
        ArrayList<NodeGeneration.BasicNode> path = AStar.findMinPath(g, b1, b8, new AStar.IAStarHeuristic<NodeGeneration.BasicNode>() {
            @Override
            public float getHeuristic(NodeGeneration.BasicNode t, NodeGeneration.BasicNode goal) {
                return 5;
            }
        });

        Assert.assertTrue("contains A D F H and size is 4", path.contains(b1) && path.contains(b4) &&
                path.contains(b6) && path.contains(b8) && path.size() == 4);
//        System.out.println("path len  " + path.size());
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
        ArrayList<NodeGeneration.BasicNode> path = AStar.findMinPath(g, b1, b2, new AStar.IAStarHeuristic<NodeGeneration.BasicNode>() {
            @Override
            public float getHeuristic(NodeGeneration.BasicNode t, NodeGeneration.BasicNode goal) {
                return 5;
            }
        });

        Assert.assertTrue("contains A B and size is 2", path.contains(b1) && path.contains(b2) && path.size() == 2);
//        System.out.println("path len  "+path.size());
//        for(NodeGeneration.BasicNode n : path){
//            System.out.println("node in path   "+n.getLabel());
//        }
    }

    @Test
    public void AStarTest4(){

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

        WeightedEdge<NodeGeneration.BasicNode> eAB = new WeightedEdge<>(b1,b2, 2.3f);
        WeightedEdge<NodeGeneration.BasicNode> eBC = new WeightedEdge<>(b2,b3, 4.5f);
        WeightedEdge<NodeGeneration.BasicNode> eCD = new WeightedEdge<>(b3,b4, 1.3f);
        WeightedEdge<NodeGeneration.BasicNode> eDE = new WeightedEdge<>(b4,b5, 6.5f);
        WeightedEdge<NodeGeneration.BasicNode> eEF = new WeightedEdge<>(b5,b6, 0.5f);
        WeightedEdge<NodeGeneration.BasicNode> eFG = new WeightedEdge<>(b6,b7, 3.5f);
        WeightedEdge<NodeGeneration.BasicNode> eGH = new WeightedEdge<>(b7,b8, 1.5f);
        WeightedEdge<NodeGeneration.BasicNode> eHA = new WeightedEdge<>(b8,b1, 53.5f);

        ArrayList<WeightedEdge<NodeGeneration.BasicNode>> list = new ArrayList<>();
        list.add(eAB);
        list.add(eBC);
        list.add(eCD);
        list.add(eDE);
        list.add(eEF);
        list.add(eFG);
        list.add(eGH);
        list.add(eHA);

        Graph<NodeGeneration.BasicNode, WeightedEdge<NodeGeneration.BasicNode>> g = new Graph<NodeGeneration.BasicNode, WeightedEdge<NodeGeneration.BasicNode>>(list);
        ArrayList<NodeGeneration.BasicNode> path = AStar.findMinPath(g, b1, b8, new AStar.IAStarHeuristic<NodeGeneration.BasicNode>() {
            @Override
            public float getHeuristic(NodeGeneration.BasicNode t, NodeGeneration.BasicNode goal) {
                return 5;
            }
        });

        Assert.assertTrue("contains A B C D E F G H and size is 8", path.contains(b1) && path.contains(b2) &&
                path.contains(b3) && path.contains(b4) && path.contains(b5) && path.contains(b6) && path.contains(b7)
                && path.contains(b8) && path.size() == 8);
//        System.out.println("path len  "+path.size());
//        for(NodeGeneration.BasicNode n : path){
//            System.out.println("node in path   "+n.getLabel());
//        }
    }


    @Test
    public void AStarTest5(){

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
        NodeGeneration.BasicNode b9 = new NodeGeneration.BasicNode("I");
        NodeGeneration.BasicNode b10 = new NodeGeneration.BasicNode("J");

        ArrayList<WeightedEdge<NodeGeneration.BasicNode>> list = new ArrayList<>();

        WeightedEdge<NodeGeneration.BasicNode> eAB = new WeightedEdge<>(b1,b2, 1.4f);
        list.add(eAB);
        WeightedEdge<NodeGeneration.BasicNode> eAC = new WeightedEdge<>(b1,b3, 43.5f);
        list.add(eAC);
        WeightedEdge<NodeGeneration.BasicNode> eAE = new WeightedEdge<>(b1,b5, 22.6f);
        list.add(eAE);
        WeightedEdge<NodeGeneration.BasicNode> eAH = new WeightedEdge<>(b1,b8, 56.0f);
        list.add(eAH);
        WeightedEdge<NodeGeneration.BasicNode> eBC = new WeightedEdge<>(b2,b3, 1.9f);
        list.add(eBC);
        WeightedEdge<NodeGeneration.BasicNode> eBD = new WeightedEdge<>(b2,b4, 12.3f);
        list.add(eBD);
        WeightedEdge<NodeGeneration.BasicNode> eBE = new WeightedEdge<>(b2,b5, 19.5f);
        list.add(eBE);
        WeightedEdge<NodeGeneration.BasicNode> eCD = new WeightedEdge<>(b3,b4, 2.6f);
        list.add(eCD);
        WeightedEdge<NodeGeneration.BasicNode> eCF = new WeightedEdge<>(b3,b6, 88.9f);
        list.add(eCF);
        WeightedEdge<NodeGeneration.BasicNode> eCG = new WeightedEdge<>(b3,b7, 59.0f);
        list.add(eCG);
        WeightedEdge<NodeGeneration.BasicNode> eCH = new WeightedEdge<>(b3,b8, 76.3f);
        list.add(eCH);
        WeightedEdge<NodeGeneration.BasicNode> eCI = new WeightedEdge<>(b3,b9, 38.1f);
        list.add(eCI);
        WeightedEdge<NodeGeneration.BasicNode> eCJ = new WeightedEdge<>(b3,b10, 44.2f);
        list.add(eCJ);
        WeightedEdge<NodeGeneration.BasicNode> eDE = new WeightedEdge<>(b4,b5, 2.1f);
        list.add(eDE);
        WeightedEdge<NodeGeneration.BasicNode> eDG = new WeightedEdge<>(b4,b7, 80.4f);
        list.add(eDG);
        WeightedEdge<NodeGeneration.BasicNode> eDH = new WeightedEdge<>(b4,b8, 90.4f);
        list.add(eDH);
        WeightedEdge<NodeGeneration.BasicNode> eDJ = new WeightedEdge<>(b4,b10, 55.2f);
        list.add(eDJ);
        WeightedEdge<NodeGeneration.BasicNode> eEF = new WeightedEdge<>(b5,b6, 1.3f);
        list.add(eEF);
        WeightedEdge<NodeGeneration.BasicNode> eEG = new WeightedEdge<>(b5,b7, 40.3f);
        list.add(eEG);
        WeightedEdge<NodeGeneration.BasicNode> eEI = new WeightedEdge<>(b5,b9, 44.3f);
        list.add(eEI);
        WeightedEdge<NodeGeneration.BasicNode> eEJ = new WeightedEdge<>(b5,b10, 190.2f);
        list.add(eEJ);
        WeightedEdge<NodeGeneration.BasicNode> eFG = new WeightedEdge<>(b6,b7, 2.1f);
        list.add(eFG);
        WeightedEdge<NodeGeneration.BasicNode> eFH = new WeightedEdge<>(b6,b8, 19.4f);
        list.add(eFH);
        WeightedEdge<NodeGeneration.BasicNode> eFI = new WeightedEdge<>(b6,b9, 55.1f);
        list.add(eFI);
        WeightedEdge<NodeGeneration.BasicNode> eGH = new WeightedEdge<>(b7,b8, 2.9f);
        list.add(eGH);
        WeightedEdge<NodeGeneration.BasicNode> eGI = new WeightedEdge<>(b7,b9, 19.0f);
        list.add(eGI);
        WeightedEdge<NodeGeneration.BasicNode> eHI = new WeightedEdge<>(b8,b9, 1.6f);
        list.add(eHI);
        WeightedEdge<NodeGeneration.BasicNode> eHJ = new WeightedEdge<>(b8,b10, 33.2f);
        list.add(eHJ);
        WeightedEdge<NodeGeneration.BasicNode> eIJ = new WeightedEdge<>(b9,b10, 1.3f);
        list.add(eIJ);


        Graph<NodeGeneration.BasicNode, WeightedEdge<NodeGeneration.BasicNode>> g = new Graph<NodeGeneration.BasicNode, WeightedEdge<NodeGeneration.BasicNode>>(list);
        ArrayList<NodeGeneration.BasicNode> path = AStar.findMinPath(g, b1, b10, new AStar.IAStarHeuristic<NodeGeneration.BasicNode>() {
            @Override
            public float getHeuristic(NodeGeneration.BasicNode t, NodeGeneration.BasicNode goal) {
                return 5;
            }
        });

        Assert.assertTrue("contains A B C D E F G H I J and size is 10", path.contains(b1) && path.contains(b2) &&
                path.contains(b3) && path.contains(b4) && path.contains(b5) && path.contains(b6) && path.contains(b7)
                && path.contains(b8) && path.contains(b9) && path.contains(b10) && path.size() == 10);
//        System.out.println("path len  "+path.size());
//        for(NodeGeneration.BasicNode n : path){
//            System.out.println("node in path   "+n.getLabel());
//        }
    }

    @Test
    public void AStarTest6(){

        // There is NO PATH from A to B
        NodeGeneration.BasicNode b1 = new NodeGeneration.BasicNode("A");
        NodeGeneration.BasicNode b2 = new NodeGeneration.BasicNode("B");
        HashSet<NodeGeneration.BasicNode> nodeSet = new HashSet<>();
        nodeSet.add(b1);
        nodeSet.add(b2);
        //WeightedEdge<NodeGeneration.BasicNode> eAB = new WeightedEdge<>(b1,b2, 10);


        ArrayList<WeightedEdge<NodeGeneration.BasicNode>> list = new ArrayList<>();
        //list.add(eAB);

        Graph<NodeGeneration.BasicNode, WeightedEdge<NodeGeneration.BasicNode>> g = new Graph<NodeGeneration.BasicNode, WeightedEdge<NodeGeneration.BasicNode>>(list, nodeSet);
        ArrayList<NodeGeneration.BasicNode> path = AStar.findMinPath(g, b1, b2, new AStar.IAStarHeuristic<NodeGeneration.BasicNode>() {
            @Override
            public float getHeuristic(NodeGeneration.BasicNode t, NodeGeneration.BasicNode goal) {
                return 5;
            }
        });

        Assert.assertTrue("path is empty (except for start node)", path.size() == 1);

    }

}
