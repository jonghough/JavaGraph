# Java Graph Library

## A collection of algorithms and datatypes for working with Graphs

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

* ![Pretty Voronoi](https://bitbucket.org/jghough/javagraph2/raw/master/images/prettypattern1.png)
* ![Pretty Voronoi](https://bitbucket.org/jghough/javagraph2/raw/master/images/prettypattern2.png)
* ![Pretty Voronoi](https://bitbucket.org/jghough/javagraph2/raw/master/images/random_with_triangulation.png)
* ![Pretty Voronoi](https://bitbucket.org/jghough/javagraph2/raw/master/images/random1.png)

### Graph structure

* Construction of various graph types of given size.
* Chromatic number estimations
* Graph colorizable algorithm
* Chromatic polynomials for certain graph types
* Construct Line graph **L(G)** of graph

### Others
* Genetic algorithm solution to TSP problem for a given **complete** graph.