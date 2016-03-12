import jgh.javagraph.geometry.voronoi.CircleEvent;
import jgh.javagraph.geometry.voronoi.TreeItem;
import jgh.javagraph.geometry.voronoi.Node2d;
import org.junit.Assert;
import org.junit.Test;

public class VoronoiTest {

    @Test
    public void CircleEventTest1(){
        Node2d n1 = new Node2d(0, 10);
        Node2d n2 = new Node2d(5, 10);
        Node2d n3 = new Node2d(10, 0);

        CircleEvent ce = new CircleEvent(new TreeItem(n1), new TreeItem(n2), new TreeItem(n3));
        ce.calculateCircle();
        Assert.assertTrue(Math.abs(ce.getR() - 7.9) < 0.1f);
    }

    @Test
    public void CircleEventTest2(){
        Node2d n1 = new Node2d(0, 10);
        Node2d n2 = new Node2d(0, 11);
        Node2d n3 = new Node2d(16, 34);

        CircleEvent ce = new CircleEvent(new TreeItem(n1), new TreeItem(n2), new TreeItem(n3));
        ce.calculateCircle();
        Assert.assertTrue(Math.abs(ce.getR() - 25.25495) < 0.1f);
    }


    @Test
    public void CircleEventTest3(){
        //all points are collinear and x values are all equal
        Node2d n1 = new Node2d(0, 10);
        Node2d n2 = new Node2d(0, 10);
        Node2d n3 = new Node2d(0, 10);

        CircleEvent ce = new CircleEvent(new TreeItem(n1), new TreeItem(n2), new TreeItem(n3));
        boolean canCalculate = ce.calculateCircle();
        Assert.assertTrue(canCalculate == false);
    }

    @Test
    public void CircleEventTest4(){
        // all points are collinear
        Node2d n1 = new Node2d(2, 10);
        Node2d n2 = new Node2d(10, 50);
        Node2d n3 = new Node2d(26, 130);

        CircleEvent ce = new CircleEvent(new TreeItem(n1), new TreeItem(n2), new TreeItem(n3));
        boolean canCalculate = ce.calculateCircle();
        Assert.assertTrue(canCalculate == false);
    }

    @Test
    public void CircleEventTest5(){
        // all points are ALMOST collinear, but not quite. This should
        // give a huge circle with a radius very far from all points.
        Node2d n1 = new Node2d(2, 10);
        Node2d n2 = new Node2d(10.1f, 50);
        Node2d n3 = new Node2d(26.2f, 130.05f);

        CircleEvent ce = new CircleEvent(new TreeItem(n1), new TreeItem(n2), new TreeItem(n3));
        boolean canCalculate = ce.calculateCircle();
        Assert.assertTrue(canCalculate == true);
        Assert.assertTrue(Math.abs(ce.getR() - 46322.7) < 1f);
    }

}
