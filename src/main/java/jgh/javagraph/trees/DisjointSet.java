package jgh.javagraph.trees;

import jgh.javagraph.IEdge;
import jgh.javagraph.INode;

import java.util.Collection;
import java.util.HashSet;



public class DisjointSet<N extends INode, E extends IEdge<N>> {


    private HashSet<LinkedNode> linkedSets;
    public DisjointSet(Collection<E> edges){
        linkedSets = new HashSet<>();
        for(E edge : edges){
            LinkedNode<N,E> ln = new LinkedNode<N,E>();
            ln.previous = null;
            ln.next = null;
            ln.root = ln;
            ln.edge = edge;
            linkedSets.add(ln);
        }
    }

    public boolean canAddEdge(E otherEdge){
        int connectedCount = 0;
        LinkedNode<N,E> potentialParent1 = null;
        LinkedNode<N,E> potentialParent2 = null;
        for(LinkedNode<N,E> ln : linkedSets){
            int c = ln.getConnectedCount(otherEdge);
            if(c > 1) {

                return false;
            }
            else if(c == 1){
                if(potentialParent1 == null) {
                    potentialParent1 = ln;
                }
                else{
                    potentialParent2 = ln;
                }
            }
        }

        if(potentialParent1 == null){
            LinkedNode<N,E> ln = new LinkedNode<N,E>();
            ln.previous = null;
            ln.next = null;
            ln.root = ln;
            ln.edge = otherEdge;
            linkedSets.add(ln);
        }
        else{
            LinkedNode<N,E> ln = new LinkedNode<N,E>();
            ln.previous = null;
            ln.next = null;
            ln.root = ln;
            ln.edge = otherEdge;
            if(potentialParent2 != null){
                union(potentialParent1, potentialParent2);
            }

            union(potentialParent1, ln);

        }

        return true;

    }

    public void union(LinkedNode set1Root, LinkedNode set2Root){

        LinkedNode ln = set1Root.next;
        set2Root.previous = set1Root;
        set1Root.next = set2Root;
        LinkedNode next = set2Root;
        set2Root.root = set1Root;
        while(next != null) {
            if(next.next == null) {
                next.next = ln;
                if(ln != null)
                    ln.previous = next.next;
                break;
            }
            else
                next = next.next;
        }

        linkedSets.remove(set2Root);


    }

    public class LinkedNode<N extends INode, E extends IEdge<N>>{
        public E edge;
        public LinkedNode<N,E> root;
        public LinkedNode<N,E> previous;
        public LinkedNode<N,E> next;


        public int getConnectedCount(E otherEdge){
            HashSet<N> incidentNodes = new HashSet<>();

            if(otherEdge.nodes().contains(edge.from())) {
                incidentNodes.add(edge.from());
            }
            else if(otherEdge.nodes().contains(edge.to())) {
                incidentNodes.add(edge.to());
            }

            LinkedNode nextNode = next;
            while(nextNode != null){

                if(otherEdge.nodes().contains(nextNode.edge.from()))
                    incidentNodes.add((N)nextNode.edge.from());
                else if(otherEdge.nodes().contains(nextNode.edge.to()))
                    incidentNodes.add((N)nextNode.edge.to());
                nextNode = nextNode.next;
            }

            return incidentNodes.size();
        }

    }
}
