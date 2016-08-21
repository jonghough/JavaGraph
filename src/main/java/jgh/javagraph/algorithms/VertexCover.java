package jgh.javagraph.algorithms;


import jgh.javagraph.EdgeData;
import jgh.javagraph.Graph;
import jgh.javagraph.IEdge;
import jgh.javagraph.generation.NodeGeneration;

import java.util.*;

public class VertexCover {

    public static <N, E extends IEdge<N>> Set<N> generateVertexCover(Graph<N, E> graph) {
        HashSet<N> hashSet = new HashSet<>();
        HashMap<E, EdgeData<E>> edgeList = new HashMap<>();
        for (E e : graph.getEdges()) {
            edgeList.put(e, new EdgeData<E>(e));
        }

        Iterator<Map.Entry<E, EdgeData<E>>> it = edgeList.entrySet().iterator();
        while (it.hasNext()) {
            if (!it.next().getValue().isVisited()) {
                E e = it.next().getKey();
                hashSet.add(e.from());
                hashSet.add(e.to());
                Iterator<Map.Entry<E, EdgeData<E>>> it2 = edgeList.entrySet().iterator();
                while (it2.hasNext()) {
                    if (it2.next().getKey().to() == e.to() || it2.next().getKey().to() == e.from()
                            || it2.next().getKey().from() == e.to() || it2.next().getKey().from() == e.from()) {
                        it2.next().getValue().setVisited(true);
                    }
                }
                break;
            }
        }
        return hashSet;
    }
}
