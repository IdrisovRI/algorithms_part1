import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> pointSet;

    // construct an empty set of points
    public  PointSET(){
        pointSet = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty(){
        return pointSet.isEmpty();
    }
    // number of points in the set
    public int size(){
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p){
        if(p==null){
            throw new IllegalArgumentException("Null point insertion");
        }

        pointSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p){
        if(p==null){
            throw new IllegalArgumentException("Null point containing");
        }

        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public              void draw(){
        for(Point2D p2: pointSet){
            p2.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect){
        ArrayList<Point2D> result = new ArrayList<>();

        if(rect==null){
            throw new IllegalArgumentException("Null rect range");
        }


        for(Point2D p: this.pointSet)
            if(rect.contains(p))
                result.add(p);

        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public           Point2D nearest(Point2D p){
        if(p==null){
            throw new IllegalArgumentException("Null point nearest");
        }

        Point2D result = null;
        double bd = Double.POSITIVE_INFINITY;;

        for(Point2D ps: this.pointSet) {
            double distance = ps.distanceTo(p);
            if (distance<bd) {
                result = ps;
                bd = distance;
            }
        }
        return result;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args){}
}
