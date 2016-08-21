import jgh.javagraph.Edge;
import jgh.javagraph.Graph;
import jgh.javagraph.generation.CompleteGeneration;
import jgh.javagraph.generation.NodeGeneration;
import jgh.javagraph.linegraph.LineGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

public class LineGraphTest {

    @Test
    public void TestLineGraphOfComplete(){
        HashSet<NodeGeneration.BasicNode> nodes = NodeGeneration.generateNodes(7);
        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> K7 = CompleteGeneration.create(new HashSet<NodeGeneration.BasicNode>(nodes));

        Graph<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>> lineGraph = LineGraph.createLineGraph(K7, new LineGraph.ILineGraphBuilder<NodeGeneration.BasicNode,Edge<NodeGeneration.BasicNode>>() {
            @Override
            public NodeGeneration.BasicNode createNode(Edge<NodeGeneration.BasicNode> edge) {
                NodeGeneration.BasicNode bn = new NodeGeneration.BasicNode("("+edge.from().getLabel()+", "+edge.to().getLabel()+")");
                return bn;
            }

            @Override
            public Edge<NodeGeneration.BasicNode> createEdge(NodeGeneration.BasicNode nodeFrom, NodeGeneration.BasicNode nodeTo) {
                return new Edge(nodeFrom, nodeTo);
            }
        });

        Assert.assertEquals(21 * 10 / 2 , lineGraph.getEdges().size());

    }
}
