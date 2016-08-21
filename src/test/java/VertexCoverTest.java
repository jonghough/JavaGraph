import jgh.javagraph.Edge;
import jgh.javagraph.Graph;
import jgh.javagraph.algorithms.VertexCover;
import jgh.javagraph.generation.CompleteGeneration;
import jgh.javagraph.generation.NodeGeneration;
import junit.framework.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class VertexCoverTest {

    @Test
    public void vertexCoverTest1(){
        HashSet<NodeGeneration.BasicNode> hs = NodeGeneration.generateNodes(5);
        Graph<NodeGeneration.BasicNode, Edge<NodeGeneration.BasicNode>> g = CompleteGeneration.create(hs);

        Set<NodeGeneration.BasicNode> cover = VertexCover.generateVertexCover(g);
        org.junit.Assert.assertTrue(cover.size() == 2);

    }
}
