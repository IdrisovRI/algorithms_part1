import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

class SlopeSegment{
    public Point p1;
    public Point p2;
    public double Slope;
    public SlopeSegment(Point p1, Point p2, double slope){
        this.p1 = p1;
        this.p2 = p2;
        this.Slope = slope;
    }

}

class Quick
{
    private static boolean less(SlopeSegment v, SlopeSegment w)
    { return v.Slope < w.Slope; }

    private static void exch(SlopeSegment[] a, int i, int j)
    {
//        StdOut.println("exch i="+i+" j="+j);
        SlopeSegment t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void sort(SlopeSegment[] a)
    {
        StdRandom.shuffle(a); // Eliminate dependence on input.
        sort(a, 0, a.length - 1);
    }
    private static int partition(SlopeSegment[] a, int lo, int hi)
    { // Partition into a[lo..i-1], a[i], a[i+1..hi].
        int i = lo, j = hi+1; // left and right scan indices
        SlopeSegment v = a[lo]; // partitioning item
        while (true)
        { // Scan right, scan left, check for scan complete, and exchange.
            while (less(a[++i], v)) if (i == hi) break;
            while (less(v, a[--j])) if (j == lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j); // Put v = a[j] into position
        return j; // with a[lo..j-1] <= a[j] <= a[j+1..hi].
    }
    private static void sort(SlopeSegment[] a, int lo, int hi)
    {
        if (hi <= lo) return;
        int j = partition(a, lo, hi); // Partition (see page 291).
        sort(a, lo, j-1); // Sort left part a[lo .. j-1].
        sort(a, j+1, hi); // Sort right part a[j+1 .. hi].
    }
}

public class FastCollinearPoints {
    private LineSegment[] segments = new LineSegment[1];

//    private void AddSegments(HashMap<Double, Point[]> slopeMapPoints, SlopeSegment[] slopeSegments){
//        int ss_len = slopeSegments.length;
//        ArrayList<SlopeSegment> segments = new ArrayList<SlopeSegment>();
//        segments.add(slopeSegments[0]);
//        double slopeVal = slopeSegments[0].Slope;
//
//        for(int i=1; i<ss_len; i++){
//            if (slopeVal==slopeSegments[i].Slope){
//                segments.add(slopeSegments[i]);
//            }
//            else{
//
//                if(segments.size() >= 3){
//                    if(slopeMapPoints.containsKey(slopeVal)){
//                        Point[] mapPoints = slopeMapPoints.get(slopeVal);
//                        boolean alreadyAdded = false;
//                        for(SlopeSegment ss: slopeSegments){
//                            for(Point point: mapPoints){
//                                if ()
//
//                            }
//                        }
//                    }
//                }
//            }
//
//        }
//
//
////        LineSegment[] newSegments = new LineSegment[segments.length+1];
////        for(int i=0; i<segments.length; i++)
////            newSegments[i] = segments[i];
////        newSegments[newSegments.length-1] = newSegm;
////        segments = newSegments;
//    }

    private LineSegment BuildLineSegmentByPoints(Point[] points){
        Point max_point = null;
        Point min_point = null;

        for(Point p: points){
            if(max_point == null){
                max_point = p;
                min_point = p;
                continue;
            }
            else {
                if(p.compareTo(max_point) > 0){
                    max_point = p;
                }
                if(p.compareTo(min_point) < 0){
                    min_point = p;
                }
            }
        }
        return new LineSegment(max_point, min_point);
    }

//    private void ProcessSegment(ArrayList<SlopeSegment> ss){}

    private void AddLineSegments(SlopeSegment[] slopeSegments){
        ArrayList<Point> points = new ArrayList<Point>();
        ArrayList<SlopeSegment> ss = new ArrayList<>();
        ss.add(slopeSegments[0]);
        double slopeVal = slopeSegments[0].Slope;


        if (slopeSegments.length > 0){
            for(int i=1;i<slopeSegments.length;i++){
//                StdOut.println("slope sg="+i);
                if(slopeVal==slopeSegments[i].Slope){
                    ss.add(slopeSegments[i]);
                }
                else{
                    if(ss.size()>=3){
                        //Add all points
                        points.add(ss.get(0).p1);
                        for(SlopeSegment s: ss){
                            points.add(s.p2);
                        }

                        LineSegment newLS= BuildLineSegmentByPoints(points.toArray(new Point[0]));
                        points.clear();
//                        int sLen = segments.length;
                        boolean alreadyAdded = false;
                        for(LineSegment lS: segments){
                            if (lS!=null)
                                if ((lS.toString()).equals(newLS.toString()))
                                    alreadyAdded = true;
                        }
                        if (!alreadyAdded) {
                            if(segments[0]==null){
                                segments[0]=newLS;
                            }
                            else{
                                LineSegment[] newSegments = new LineSegment[segments.length+1];
                                for(int j=0;j<segments.length;j++){
                                    newSegments[j]=segments[j];
                                }
                                newSegments[newSegments.length-1] = newLS;
                                segments = newSegments;
                            }

                        }
                    }
                    else {
                        slopeVal = slopeSegments[i].Slope;
                        ss.clear();
                        ss.add(slopeSegments[i]);
                    }
                }
            }
            if(ss.size()>=3){
                //Add all points
                points.add(ss.get(0).p1);
                for(SlopeSegment s: ss){
                    points.add(s.p2);
                }

                LineSegment newLS= BuildLineSegmentByPoints(points.toArray(new Point[0]));
                points.clear();
//                        int sLen = segments.length;
                boolean alreadyAdded = false;
                for(LineSegment lS: segments){
                    if (lS!=null) {
//                        StdOut.println("LS="+lS.toString()+" newLS="+newLS.toString());
//                        StdOut.println("LS equals="+(lS.toString()).equals(newLS.toString()));
                        if ((lS.toString()).equals(newLS.toString()))
                            alreadyAdded = true;
                    }
                }
                if (!alreadyAdded) {
                    if(segments[0]==null){
                        segments[0]=newLS;
                    }
                    else{
                        LineSegment[] newSegments = new LineSegment[segments.length+1];
                        for(int j=0;j<segments.length;j++){
                            newSegments[j]=segments[j];
                        }
                        newSegments[newSegments.length-1] = newLS;
                        segments = newSegments;
                    }

                }
            }


        }

    }

    public FastCollinearPoints(Point[] points){
        if (points == null)
            throw new IllegalArgumentException("Points is null");
        for (Point p : points)
            if (p == null)
                throw new IllegalArgumentException("Null point was detected");
//        for (int i = 0; i < points.length - 1; i++)
//            for (int j = i + 1; j < points.length; j++)
//                if (points[i].equals(points[j]))
//                    throw new IllegalArgumentException("Equals points was detected");


        int size = points.length;
        double slope;
        Point point1;
        Point point2;
//        HashMap<Double, Point[]> slopeMapPoints = new HashMap<>();

        for(int p=0; p<size; p++){
            point1 = points[p];
            SlopeSegment[] slopeSegments = new SlopeSegment[size-1];
//            StdOut.println("size="+size+" p="+p);

            int counter = -1;
            for(int q=0; q<size; q++) {
                if (p == q)
                    continue;
                point2 = points[q];

                slope = points[p].slopeTo(points[q]);
//                StdOut.println("1");
                counter = counter + 1;
                slopeSegments[counter] = new SlopeSegment(point1, point2, slope);
            }
//            StdOut.println("2");
//            for(int i=0;i< slopeSegments.length;i++)
//                StdOut.println("Slope segments before="+slopeSegments[i].Slope);
            Quick.sort(slopeSegments);


//            for(int i=0;i< slopeSegments.length;i++)
//                StdOut.println("Slope segments sorted="+slopeSegments[i].Slope);
            AddLineSegments(slopeSegments);

        }
//        if (segments[0]==null)
//            segments = new LineSegment[0];

    }     // finds all line segments containing 4 or more points

    public           int numberOfSegments(){
        return this.segments.length;
    }        // the number of line segments

    public LineSegment[] segments(){
        return this.segments;
    }                // the line segments
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
