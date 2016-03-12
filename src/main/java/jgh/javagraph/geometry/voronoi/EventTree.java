package jgh.javagraph.geometry.voronoi;

import java.util.ArrayList;

/**
 *
 */
public class EventTree {

    private double maxY;
    private TreeNode mRoot = null;

    public ArrayList<HalfEdge> halfEdges = new ArrayList<>();


    public EventTree() {/* empty */}

    public TreeNode getRoot() {
        return mRoot;
    }

    public void setRoot(TreeNode root) {
        mRoot = root;
        mRoot.setParent(null);
    }

    public void setMaxY(double y) {
        maxY = y;
    }

    /**
     * Gets the mNext node on the event tree.
     * This could be a leaf node or an inner node.
     *
     * @param current the current node on the tree.
     * @return the mNext node. Returns <code>null</code> if no such
     * node exists.
     */
    public TreeNode getNext(TreeNode current) {
        if (current.rChild() != null) {
            TreeNode next = current.rChild();
            while (next.lChild() != null) {
                next = next.lChild();
            }
            return next;
        } else if (current.parent() != null && current.parent().lChild() == current) {
            return current.parent();
        } else if (current.parent() != null) {
            TreeNode next = current.parent();
            while (next.parent() != null) {
                if (next.parent().lChild() == next)
                    return next.parent();
                next = next.parent();
            }
            return null;
        } else return null;
    }

    /**
     * Gets the mNext leaf in the event tree from the current node.
     *
     * @param current
     * @return the mNext leaf node. Returns <code>null</code> if no such
     * node exists.
     */
    public TreeNode getNextLeaf(TreeNode current) {
        TreeNode next = getNext(current);
        while (next != null) {
            if (next instanceof LeafNode) {
                return next;
            }
            next = getNext(next);
        }
        return null;
    }

    /**
     * Gets the mPrevious node on the event tree.
     * This could be a leaf node or an inner node.
     *
     * @param current the current node on the tree.
     * @return the mPrevious node. Returns <code>null</code> if no such
     * node exists.
     */
    public TreeNode getPrevious(TreeNode current) {
        if (current.lChild() != null) {
            TreeNode prev = current.lChild();

            while (prev.rChild() != null) {
                prev = prev.rChild();
            }
            return prev;
        } else if (current.parent() != null && current.parent().rChild() == current) {

            return current.parent();

        } else if (current.parent() != null) {
            TreeNode prev = current.parent();
            while (prev.parent() != null) {
                if (prev.parent().rChild() == prev)
                    return prev.parent();
                prev = prev.parent();
            }
            return null;
        } else return null;
    }

    /**
     * Gets the mPrevious leaf in the event tree from the current node.
     *
     * @param current
     * @return the mPrevious leaf node. Returns <code>null</code> if no such
     * node exists.
     */
    public TreeNode getPreviousLeaf(TreeNode current) {
        TreeNode prev = getPrevious(current);
        while (prev != null) {
            if (prev instanceof LeafNode) {
                return prev;
            }
            prev = getPrevious(prev);
        }
        return null;
    }

    /**
     * Gets the closest node to the given site event. This is done using a binary search, hence
     * <i>O(log n)</i> time complexity, which in turn allows the algorithm as a whole to be <i>O(n log n)</i>
     * in time, i.e. faster than you'd think.
     *
     * @param current   the current node to start the search from.
     * @param siteEvent The given site event.
     * @return The closest leaf node of the event tree to the site event.
     */
    public TreeNode getClosest(VoronoiGenerator vg, TreeNode current, SiteEvent siteEvent) {
        double x = siteEvent.getX();
        double y = siteEvent.getY();
        if ((int) y == (int) maxY) {
            TreeItem le = new TreeItem(siteEvent.node);
            LeafNode event = new LeafNode(le);
            TreeNode next = mRoot;
            while (getNextLeaf(next) != null) {
                next = getNextLeaf(next);
            }

            HalfEdge h1 = new HalfEdge(((LeafNode) next).getListItem().getNode());
            HalfEdge h2 = new HalfEdge(le.getNode());

            h1.setTwin(h2);
            h2.setTwin(h1);
            halfEdges.add(h1);
            halfEdges.add(h2);

            if(((LeafNode) next).getListItem().getNode().halfEdge == null){
                ((LeafNode) next).getListItem().getNode().halfEdge = h1;
            }
            else{
                HalfEdge n = ((LeafNode) next).getListItem().getNode().halfEdge;
                while(n.next() != null){
                    n = n.next();
                }
                n.setNext(h1);
            }

            ((LeafNode) next).getListItem().setHalfEdge(h1);
            le.setHalfEdge(h1);

            le.getNode().halfEdge = h2;

            Breakpoint b0;
            b0 = new Breakpoint(((LeafNode) next).getListItem(), event.getListItem());
            BreakpointNode bpNode0 = new BreakpointNode(b0);
            ((LeafNode) next).setBreakpointNode(bpNode0);
            if (next == mRoot) {
                next.setParent(bpNode0);
                mRoot = bpNode0;
                bpNode0.setLChild(next);
                next.setParent(bpNode0);
                bpNode0.setRChild(event);
                event.setParent(bpNode0);
            } else {
                TreeNode parent = next.parent();
                parent.setRChild(bpNode0);
                bpNode0.setParent(parent);
                bpNode0.setLChild(next);
                next.setParent(bpNode0);
                bpNode0.setRChild(event);
                event.setParent(bpNode0);

            }

            b0.calculateBreakpoint(y);
            b0.setX((x + next.getX()) / 2);
            b0.setY(y + 9000000 /* close to infinity */);

            CircleEvent ce = new CircleEvent(b0.getX(), b0.getY());

            h1.setTarget(ce);
            ce.halfEdge = h1;

            vg.getAllCircleEvents().add(ce);

            return null;

        } else {

            current.calculatePosition(y - 0.0001);

            if (x > current.getX()) {
                if (current.rChild() != null) {
                    return getClosest(vg, current.rChild(), siteEvent);
                } else return current;
            } else if (x < current.getX()) {
                if (current.lChild() != null) {
                    return getClosest(vg, current.lChild(), siteEvent);
                } else return current;
            } else return current;
        }
    }

    /**
     * Inserts a new site event into the binary tree. Essentially this creates four new nodes on the tree,
     * (two inner nodes, representing the breakpoints between the newest node and the current node,
     * and two new leaf nodes.
     *
     * @param current  the current (closest) leaf node
     * @param listItem the list item representing the newest leaf node.
     * @return Circle events formed from the breakpoints, <code>null</code> if no such
     * circle events exist.
     */
    public ArrayList<CircleEvent> insertNewSiteEvent(LeafNode current, TreeItem listItem) {

        ArrayList<CircleEvent> circleEvents = new ArrayList<>();
        if (!(current instanceof LeafNode)) {
            System.out.println("oh crap!");
        }

        //==================== half edge =================
        Node2d currentNode2d = current.getListItem().getNode();
        Node2d newNode2d = listItem.getNode();
        HalfEdge h1 = new HalfEdge(currentNode2d);
        HalfEdge h2 = new HalfEdge(newNode2d);
        h1.setTwin(h2);
        h2.setTwin(h1);
        halfEdges.add(h1);
        halfEdges.add(h2);
        if (currentNode2d.halfEdge == null) {
            currentNode2d.halfEdge = h1;

        }
        else{
            HalfEdge n = currentNode2d.halfEdge;
        }

        //of course the new node doesnt have an edge yet.
        newNode2d.halfEdge = h2;

        HalfEdge oldHE = current.getListItem().getHalfEdge();
        current.getListItem().setHalfEdge(h1);
        listItem.setHalfEdge(h1);

        TreeNode prev = getPreviousLeaf(current);
        TreeNode next = getNextLeaf(current);

        LeafNode newNode = new LeafNode(listItem);
        TreeItem copy = ((LeafNode) current).getListItem().copy();
        LeafNode copyNode = new LeafNode(copy);

        Breakpoint bp1 = new Breakpoint(((LeafNode) current).getListItem(), listItem);
        bp1.calculateBreakpoint(listItem.getNode().getY() - 1);


        Breakpoint bp2 = new Breakpoint(listItem, copy);
        bp2.calculateBreakpoint(listItem.getNode().getY() - 1);

        // add two new inner nodes.
        BreakpointNode bp1Node = new BreakpointNode(bp1);
        BreakpointNode bp2Node = new BreakpointNode(bp2);


        current.setBreakpointNode(bp1Node);
        newNode.setBreakpointNode(bp2Node);

        //case 1, current has a parent
        if (current.parent() != null) {
            if (current == current.parent().lChild()) {
                current.parent().setLChild(bp1Node);
                bp1Node.setParent(current.parent());
            } else {
                current.parent().setRChild(bp1Node);
                bp1Node.setParent(current.parent());
            }
        } else {
            setRoot(bp1Node);
            mRoot.setParent(null);
        }

        bp1Node.setLChild(current);
        current.setParent(bp1Node);

        bp1Node.setRChild(bp2Node);
        bp2Node.setParent(bp1Node);

        bp2Node.setLChild(newNode);
        newNode.setParent(bp2Node);

        bp2Node.setRChild(copyNode);
        copyNode.setParent(bp2Node);

        if (oldHE != null) {
            copyNode.getListItem().setHalfEdge(oldHE);
            //copyNode.getListItem().rightHalfEdge = oldHE.mTwin;
            HalfEdge pre = copyNode.getListItem().getHalfEdge();

            if (pre.getFace() != currentNode2d) {
                pre = pre.twin();
            }


            HalfEdge onext = pre.next();
            pre.setNext(h1);
            h1.setPrevious(pre);
            if (onext != null) {
                onext.setPrevious(h1);
                h1.setNext(onext);
            }
        }


        /*
         *   We must attempt to find circle event formed from the triples:
         *   (mPrevious, current, newnode)
         *   and
         *   (newnode, copynode, mNext)
         *   where current and copynode are the nodes that will be removed once the circle
         *   event (if it exists) has been processed, respectively.
         */
        if (prev != null) {
            CircleEvent ce = new CircleEvent(((LeafNode) prev), ((LeafNode) current), newNode);
            boolean canCalculateCircle = ce.calculateCircle();
            Breakpoint bpP = new Breakpoint(((LeafNode) prev).getListItem(), current.getListItem());

            ((LeafNode) prev).getBreakpointNode().setBreakpoint(bpP);

            bp1Node.getBreakpoint().calculateBreakpoint(ce.getDistanceToLine());
            bpP.calculateBreakpoint(ce.getDistanceToLine());

            if (canCalculateCircle && CircleEvent.doBreakpointsConverge(ce)) {
                current.setDisappearEvent(ce);
                circleEvents.add(ce);
            }
        }

        if (next != null) {
            CircleEvent ce = new CircleEvent(newNode, copyNode, ((LeafNode) next));
            boolean canCalculateCircle = ce.calculateCircle();

            Breakpoint bpN = new Breakpoint(copy, ((LeafNode) next).getListItem());
            copyNode.setBreakpointNode((BreakpointNode) getNext(copyNode));
            copyNode.getBreakpointNode().setBreakpoint(bpN);

            bp2Node.getBreakpoint().calculateBreakpoint(ce.getDistanceToLine());
            bpN.calculateBreakpoint(ce.getDistanceToLine());

            if (canCalculateCircle && CircleEvent.doBreakpointsConverge(ce)) {
                copyNode.setDisappearEvent(ce);
                circleEvents.add(ce);
            }

        } else {
            copyNode.getListItem().setHalfEdge(h1);

        }

        return circleEvents;
    }


    /**
     * Removes a node from the event tree. This <i>only</i> happens when a circle event is processed.
     * The removal of the node requires movingf around some of the other nodes, but follows a similar method
     * to the <code>insertNewSiteNode</code> algorithm.
     *
     * @param prev       the mPrevious node
     * @param removeNode the current node, to be removed.
     * @param next       the mNext node
     * @return Circle events, formed from the newly resturctured event tree,
     * if any such events can be found, otherwise returns <code>null</code>.
     */
    public ArrayList<CircleEvent> removeNode(CircleEvent circleEventx, LeafNode prev, LeafNode removeNode, LeafNode next) {
        ArrayList<CircleEvent> circleEvents = new ArrayList<>();

        if (prev == null || next == null) {
            //if you reach this point, you're in trouble.
            System.out.println("oh crap. Null prev or mNext! " + (prev == null) + ", " + (next == null));

        }

        //==================== half edge =================
        Node2d currentNode2d = prev.getListItem().getNode();
        Node2d newNode2d = next.getListItem().getNode();
        HalfEdge h1 = new HalfEdge(currentNode2d);
        HalfEdge h2 = new HalfEdge(newNode2d);
        h1.setTwin(h2);
        h2.setTwin(h1);
        halfEdges.add(h1);
        halfEdges.add(h2);


        h1.setTarget(circleEventx);
        Breakpoint bp1 = new Breakpoint(prev.getListItem(), next.getListItem());

        if (prev.getBreakpointNode() != null && prev.getBreakpointNode() == removeNode.parent()) {
            prev.setBreakpointNode(removeNode.getBreakpointNode());
        }
        prev.getBreakpointNode().setBreakpoint(bp1);


        HalfEdge oldEdgeP = prev.getListItem().getHalfEdge();
        if (oldEdgeP.getFace() != prev.getListItem().getNode()) {
            oldEdgeP = oldEdgeP.twin();
        }
        HalfEdge n = oldEdgeP.next();
        oldEdgeP.setNext(h1);
        h1.setPrevious(oldEdgeP);
        if (n != null) {
            h1.setNext(n);
            n.setPrevious(h1);
        }

        HalfEdge oldEdgeN = next.getListItem().getHalfEdge();
        if (oldEdgeN.getFace() != next.getListItem().getNode()) {
            oldEdgeN = oldEdgeN.twin();
        }

        n = oldEdgeN.next();

        oldEdgeN.setNext(h2);
        h2.setPrevious(oldEdgeN);
        if (n != null) {
            h2.setNext(n);
            n.setPrevious(h2);

        }


        prev.getListItem().setHalfEdge(h1);

        //case where there is no grandparent
        if (removeNode.parent().parent() == null) {
            //must be root
            if (removeNode.parent().lChild() == removeNode) {
                mRoot = removeNode.parent().rChild();

            } else
                mRoot = removeNode.parent().lChild();

            mRoot.setParent(null);
        } else {
            TreeNode grandparent = removeNode.parent().parent();
            TreeNode parent = removeNode.parent();
            if (removeNode == removeNode.parent().lChild()) {
                if (removeNode.parent() == grandparent.lChild()) {
                    grandparent.setLChild(parent.rChild());
                    parent.rChild().setParent(grandparent);
                } else {
                    grandparent.setRChild(parent.rChild());
                    parent.rChild().setParent(grandparent);
                }
            }

            //remove node is right child of parent...
            else {
                if (removeNode.parent() == grandparent.lChild()) {
                    grandparent.setLChild(parent.lChild());
                    parent.lChild().setParent(grandparent);
                } else {
                    grandparent.setRChild(parent.lChild());
                    parent.lChild().setParent(grandparent);

                }
            }
            removeNode.setParent(null);
        }


        TreeNode nextNextTree = getNextLeaf(next);
        if (nextNextTree != null) {
            LeafNode nextNext = (LeafNode) nextNextTree;
            CircleEvent circleEvent = new CircleEvent(prev, next, nextNext);
            boolean canCalculateCircle = circleEvent.calculateCircle();

            Breakpoint b2;
            b2 = new Breakpoint(next.getListItem(), nextNext.getListItem());

            next.getBreakpointNode().setBreakpoint(b2);

            bp1.calculateBreakpoint(circleEvent.getDistanceToLine());

            b2.calculateBreakpoint(circleEvent.getDistanceToLine());
            if (canCalculateCircle && CircleEvent.doBreakpointsConverge(circleEvent)) {

                next.setDisappearEvent(circleEvent);

                circleEvents.add(circleEvent);
            }
        }

        TreeNode prevPrevTree = getPreviousLeaf(prev);
        if (prevPrevTree != null) {
            LeafNode prevPrev = (LeafNode) prevPrevTree;

            CircleEvent circleEvent = new CircleEvent(prevPrev, prev, next);
            boolean canCalculateCircle = circleEvent.calculateCircle();

            Breakpoint b2;
            b2 = new Breakpoint(prevPrev.getListItem(), prev.getListItem());
            prevPrev.getBreakpointNode().setBreakpoint(b2);
            bp1.calculateBreakpoint(circleEvent.getDistanceToLine());

            b2.calculateBreakpoint(circleEvent.getDistanceToLine());
            if (canCalculateCircle && CircleEvent.doBreakpointsConverge(circleEvent)) {

                prev.setDisappearEvent(circleEvent);

                circleEvents.add(circleEvent);
            }
        }

        return circleEvents;
    }

    /* default */ interface TreeNode {
        double getX();

        double getY();

        TreeNode lChild();

        void setLChild(TreeNode child);

        void setRChild(TreeNode child);

        TreeNode rChild();

        TreeNode parent();

        void setParent(TreeNode parent);

        void calculatePosition(double sweepLine);

    }

    /* default */ class BreakpointNode implements TreeNode {

        private Breakpoint mBreakpoint;
        private TreeNode mLeftChild;
        private TreeNode mRightChild;
        private TreeNode mParent = null;

        public BreakpointNode(Breakpoint breakpoint) {
            mBreakpoint = breakpoint;
        }

        public Breakpoint getBreakpoint() {
            return mBreakpoint;
        }

        public void setBreakpoint(Breakpoint bp) {
            mBreakpoint = bp;
        }

        @Override
        public double getX() {
            return mBreakpoint.getX();
        }

        @Override
        public double getY() {
            return mBreakpoint.getY();
        }

        @Override
        public TreeNode lChild() {
            return mLeftChild;
        }

        @Override
        public void setLChild(TreeNode child) {
            mLeftChild = child;
        }

        @Override
        public void setRChild(TreeNode child) {
            mRightChild = child;
        }

        @Override
        public TreeNode rChild() {
            return mRightChild;
        }

        @Override
        public TreeNode parent() {
            return mParent;
        }

        @Override
        public void setParent(TreeNode parent) {
            mParent = parent;
        }

        @Override
        public void calculatePosition(double sweepLine) {
            mBreakpoint.calculateBreakpoint(sweepLine);
        }

    }

    /* default */ class LeafNode implements TreeNode {

        private TreeItem mListItem;
        private CircleEvent mDisappearEvent;
        private TreeNode mParent;
        private BreakpointNode mBreakpoint;

        public LeafNode(TreeItem item) {
            mListItem = item;
        }

        public TreeItem getListItem() {
            return mListItem;
        }

        public void setBreakpointNode(BreakpointNode bpn) {
            mBreakpoint = bpn;
        }

        public BreakpointNode getBreakpointNode() {
            return mBreakpoint;
        }

        public void setDisappearEvent(CircleEvent event) {
            mDisappearEvent = event;
        }

        public CircleEvent getDisappearEvent() {
            return mDisappearEvent;
        }

        @Override
        public double getX() {
            return mListItem.getNode().getX();
        }

        @Override
        public double getY() {
            return mListItem.getNode().getY();
        }

        @Override
        public TreeNode lChild() {
            return null;
        }

        @Override
        public void setLChild(TreeNode child) {
            // do nothing.
        }

        @Override
        public void setRChild(TreeNode child) {
            // do nothing.
        }

        @Override
        public TreeNode rChild() {
            return null;
        }

        @Override
        public TreeNode parent() {
            return mParent;
        }

        @Override
        public void setParent(TreeNode parent) {
            mParent = parent;
        }

        @Override
        public void calculatePosition(double sweepLine) {
            // do nothing
        }
    }
}
