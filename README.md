[![Build Status](https://travis-ci.org/jonghough/JavaGraph.svg?branch=master)](https://travis-ci.org/jonghough/JavaGraph) [![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/hyperium/hyper/master/LICENSE)
# Java Graph Library

## A collection of algorithms and datatypes for working with Graphs

### Build
`mvn clean install`
### Run tests
`mvn test`

## Overview
### Searching, Spanning, Cutting

* Dijkstra's algorithm for undirected graphs
* A*-algorithm for undirected graphs
* Bellman-Ford algorithm for directed graphs
* Ford-Fulkerson algorithm, for flow networks
* Minimum spanning tree algorithm
* Cycle finding algorithm
* MinCut algorithm to find minimum cut

### Geometry
#### Voronoi Diagrams
The library contains classes to construct Voronoi Diagrams from arbitray collections of 2D points. To create Voronoi diagrams
an implementation of *Fortune's algorithm* is used, with time complexity *~O(n log n)*.

#### Examples
It is easy to create a voronoi diagram, for example, using `JavaFX`:
```
public class VoronoiViewer extends Application {

    static ArrayList<Node2d> nodeList;
    public static VoronoiGenerator voronoi;

    public static void main(String[] args) {


        Random r = new Random();


        nodeList = new ArrayList<Node2d>();

        for(int j = 0; j < 34; j++){
            for(int k = 0; k < 14; k++){
                nodeList.add(new Node2d(3 + j*25 + 0.000*r.nextInt(663), 4 + k * 30+ j*j*0.25 + 0*r.nextInt(470)));
            }
        }
        
        voronoi = new VoronoiGenerator(nodeList);
        voronoi.createDiagram();
        
        launch(args);
   }
  
   @Override
   public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Voronoi Test");


        Group root = new Group();
        Canvas canvas = new Canvas(800, 750);
        GraphicsContext gc = canvas.getGraphicsContext2D();


        gc.setStroke(Color.MAROON);

        int total = 0;
        double r = 0.5;
        double g = 0.0;
        double b = 1.0;
        for(int i = 0; i < voronoi.getNodes().size(); i ++){
            Node2d ce = voronoi.getNodes().get(i);
            HalfEdge edge= ce.halfEdge;

            HalfEdge f = edge;

            gc.setStroke(Color.color(r,g,b));
            gc.beginPath();
            while(edge != null){
                total++;
                gc.setStroke(Color.color(r,g,b));
                gc.beginPath();
                gc.moveTo(edge.twin().getTarget().getX(), edge.twin().getTarget().getY());
                gc.lineTo(edge.getTarget().getX(), edge.getTarget().getY());

                gc.stroke();
                edge = edge.next();
                if(edge == null || f == edge)
                    break;
            }

        }

        gc.setFill(Color.FIREBRICK);
        for (CircleEvent ce : voronoi.getAllCircleEvents()) {
            gc.setFill(Color.DEEPPINK);
            gc.fillOval(ce.getX()-2, ce.getY()-2, 4, 4);
            gc.setStroke(Color.BLACK);

        }
        gc.setFill(Color.RED);

        for (Node2d n : nodeList) {
            gc.fillOval(n.getX() - 5f / 2, n.getY() - 5f / 2, 5, 5);

        }

        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        scene.setFill(Color.OLDLACE);

        primaryStage.setScene(scene);
        primaryStage.show();
    } 
 }
```

 ![Pretty Voronoi](/images/prettypattern1.png)
 ![Pretty Voronoi](/images/prettypattern2.png)
 ![Pretty Voronoi](/images/random_with_triangulation.png)
 ![Pretty Voronoi](/images/random1.png)

### Graph structure

* Construction of various graph types of given size.
* Chromatic number estimations
* Graph colorizable algorithm
* Chromatic polynomials for certain graph types
* Construct Line graph **L(G)** of graph

### Others
* Genetic algorithm solution to TSP problem for a given **complete** graph.
