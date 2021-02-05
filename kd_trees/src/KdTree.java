import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;


public class KdTree {
    private Node root;
    private int size = 0;
    private final static boolean RED = false;
    private final static boolean BLUE = true;



    private static class Node
    {
        private final Point2D point;
        private final boolean colour;
        private Node left=null, right=null; // links to subtrees

        private boolean getNextColour(boolean colour){
            if(colour==KdTree.RED)
                return KdTree.BLUE;
            else
                return KdTree.RED;
        }

        public Node(Point2D point, boolean colour)
        { this.point = point;this.colour = colour;}

        public Point2D getPoint(){
            return this.point;
        }
        public boolean getColour(){
            return this.colour;
        }

        public void setLeftByPoint(Point2D point){
            this.left=new Node(point, getNextColour(this.colour));
        }
        public void setRightByPoint(Point2D point){
            this.right=new Node(point, getNextColour(this.colour));
        }
        public Node getLeft(){
            return this.left;
        }
        public Node getRight(){
            return this.right;
        }

        public void drawAll(){
            drawNodeAndSubNodes(this);
        }

        public void drawNodeAndSubNodes(Node node){
            //draw point
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.BLACK);
            node.getPoint().draw();

            //draw RED line
            if(node.getColour()==RED) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(node.getPoint().x(),0,node.getPoint().x(),1);
            }

            //draw BLUE line
            if(node.getColour()==BLUE) {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(0,node.getPoint().y(),1,node.getPoint().y());
            }

            if(this.getLeft()!=null){
                drawNodeAndSubNodes(this.getLeft());
            }
            if(this.getRight()!=null){
                drawNodeAndSubNodes(this.getRight());
            }
        }
    }



    // construct an empty set of points
    public KdTree(){
        this.root = null;
    }

    // is the set empty?
    public boolean isEmpty(){
        return (this.root==null);
    }

    // number of points in the set
    public int size(){
        return this.size;
    }

    // provide aplha
    //if alpha < 0 => go left else go right
    private double getAlpha(Node currentNode, Point2D point){
        Point2D curNodePoint = currentNode.getPoint();
        double alpha;
        if(currentNode.getColour()==RED)
            alpha = point.x() - curNodePoint.x();
        else
            alpha = point.y() - curNodePoint.y();
        return alpha;
    }


    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p){
        if(p==null){
            throw new IllegalArgumentException("Null point insertion");
        }

        if (this.root==null){
            this.root = new Node(p, RED);
            this.size++;
        }
        else{
            Node curNode = this.root;
            Point2D curNodePoint;

            while(true){
                curNodePoint = curNode.getPoint();
                if(p.x() == curNodePoint.x() && p.y() == curNodePoint.y()){
                    //already was inserted
                    break;
                }

                //if alpha<0 => current point greater than new one => go left
                if (getAlpha(curNode, p) < 0) {
                    if(curNode.getLeft()==null){
                        curNode.setLeftByPoint(p);
                        this.size++;
                        break;
                    }
                    else{
                        curNode = curNode.getLeft();
                    }
                }
                else{
                    if(curNode.getRight()==null){
                        curNode.setRightByPoint(p);
                        this.size++;
                        break;
                    }
                    else{
                        curNode = curNode.getRight();
                    }
                }
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p){
        if(p==null){
            throw new IllegalArgumentException("Null point contains");
        }

        Node curNode = this.root;
        Point2D curNodePoint;

        while(true) {
            curNodePoint = curNode.getPoint();
            if (p.x() == curNodePoint.x() && p.y() == curNodePoint.y()) {
                //already was inserted
                return true;
            }

            //if alpha<0 => current point greater than new one => go left
            if (getAlpha(curNode, p) < 0) {
                if(curNode.getLeft()==null){
                    break;
                }
                else{
                    curNode = curNode.getLeft();
                }
            }
            else{
                if(curNode.getRight()==null){
                    break;
                }
                else{
                    curNode = curNode.getRight();
                }
            }
        }
        return false;
    }

    // draw all points to standard draw
    public void draw(){
        this.root.drawAll();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect){
        if(rect == null){
            throw new IllegalArgumentException("Null rectangle in range");
        }

        ArrayList<Point2D> points = new ArrayList<>();

        addNodesFromSubTree(points, this.root, rect);

        return points;
    }

    private void addNodesFromSubTree(ArrayList<Point2D> resultPoints, Node nodeSubTree, RectHV rect){
        if(nodeSubTree!=null) {
            if(rect.contains(nodeSubTree.getPoint())){
                resultPoints.add(nodeSubTree.getPoint());
            }

//            StdOut.println("x="+nodeSubTree.getPoint().x()+" y="+nodeSubTree.getPoint().y()+" color="+nodeSubTree.getColour());

            if(nodeSubTree.getColour()==RED){
//                StdOut.println("RED");
                double nodePointX = nodeSubTree.getPoint().x();
                if(nodePointX>rect.xmin())
                    addNodesFromSubTree(resultPoints, nodeSubTree.getLeft(), rect);
                if(nodePointX<rect.xmax())
                    addNodesFromSubTree(resultPoints, nodeSubTree.getRight(), rect);
            }
            if(nodeSubTree.getColour()==BLUE){
//                StdOut.println("BLUE");
                double nodePointY = nodeSubTree.getPoint().y();
                if(nodePointY>rect.ymin())
                    addNodesFromSubTree(resultPoints, nodeSubTree.getLeft(), rect);
                if(nodePointY<rect.ymax())
                    addNodesFromSubTree(resultPoints, nodeSubTree.getRight(), rect);
            }
        }
    }


    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p){
        if(p==null){
            throw new IllegalArgumentException("Null point contains");
        }

        return getBestPoint(p, this.root.getPoint(), this.root);

    }

    private Point2D getBestPoint(Point2D queryPoint, Point2D bestPoint, Node subNode){
        if (subNode==null){
            return bestPoint;
        }

        if(subNode.getPoint().distanceTo(queryPoint)<bestPoint.distanceTo(queryPoint))
            bestPoint = subNode.getPoint();

        double alpha;
        Point2D heightPoint;
        Node sameSideNode;
        Node otherSideNode;

        if(subNode.getColour()==RED){
            heightPoint = new Point2D(subNode.getPoint().x(), queryPoint.y());
            alpha = queryPoint.x()-subNode.getPoint().x();
        }
        else{
            heightPoint = new Point2D(queryPoint.x(), subNode.getPoint().y());
            alpha = queryPoint.y()-subNode.getPoint().y();
        }
        if(alpha<0){
            sameSideNode = subNode.getLeft();
            otherSideNode = subNode.getRight();
        }
        else{
            sameSideNode = subNode.getRight();
            otherSideNode = subNode.getLeft();
        }

        bestPoint = getBestPoint(queryPoint, bestPoint, sameSideNode);
        if(heightPoint.distanceTo(queryPoint)<bestPoint.distanceTo(queryPoint))
            bestPoint = getBestPoint(queryPoint, bestPoint, otherSideNode);

        return bestPoint;
    }


    // unit testing of the methods (optional)
    public static void main(String[] args){
        KdTree kd = new KdTree();
        kd.insert(new Point2D(0.372, 0.497));
        kd.insert(new Point2D(0.564, 0.413));
        kd.insert(new Point2D(0.226, 0.577));
        kd.insert(new Point2D(0.144, 0.179));
        kd.insert(new Point2D(0.083, 0.51));
        kd.insert(new Point2D(0.32, 0.708));
        kd.insert(new Point2D(0.417, 0.362));
        kd.insert(new Point2D(0.862, 0.825));
        kd.insert(new Point2D(0.785, 0.725));
        kd.insert(new Point2D(0.499, 0.208));
        StdOut.println(kd.size());
//        kd.draw();
        StdOut.println(kd.root.getPoint());
        StdOut.println(kd.root.getRight().getPoint());
        StdOut.println(kd.root.getLeft().getPoint());
        StdOut.println(kd.range(new RectHV(0.256, 0.681, 0.422, 0.987)));
    }
}
