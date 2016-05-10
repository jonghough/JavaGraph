import jgh.javagraph.Edge;
import jgh.javagraph.Graph;
import jgh.javagraph.algorithms.BiconnectedComponentGenerator;
import jgh.javagraph.generation.NodeGeneration;
import jgh.javagraph.generation.SpecialGeneration;
import org.junit.Test;

import java.util.HashSet;

/**
 * Created by jon on 16/05/04.
 */
public class BiconnectedComponentsTest {

    @Test
    public void biconnectedComponentTest1(){
        Graph<NodeGeneration.BasicNode, Edge<NodeGeneration.BasicNode>> graph
                = SpecialGeneration.generateBarbellGraph(7);

        BiconnectedComponentGenerator<NodeGeneration.BasicNode, Edge<NodeGeneration.BasicNode>> generstor =
                new BiconnectedComponentGenerator<>(graph);

        generstor.findBiconnectedComponents();
        HashSet<HashSet<Edge<NodeGeneration.BasicNode>>> comps = generstor.getBiconnectedComponents();
        System.out.println("biconnected compoents size is "+comps.size());
        for(HashSet<Edge<NodeGeneration.BasicNode>> set : comps){
            System.out.println("===============");
            for(Edge<NodeGeneration.BasicNode> edge : set){
                System.out.println("edge: "+edge.from().getLabel()+", "+edge.to().getLabel());
            }
        }
    }
}
