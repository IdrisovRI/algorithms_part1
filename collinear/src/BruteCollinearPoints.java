import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] segments = new LineSegment[1];;

    private void AddLineSegment(LineSegment newSegm){
//        StdOut.println("NewSegm="+newSegm.toString());
        if (segments[0]!=null) {
            LineSegment[] newSegments = new LineSegment[segments.length + 1];
            for (int i = 0; i < segments.length; i++)
                newSegments[i] = segments[i];
            newSegments[newSegments.length - 1] = newSegm;
            segments = newSegments;
        }
        else{
            segments[0]=newSegm;
        }
    }

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

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Points is null");
        for (Point p : points)
            if (p == null)
                throw new IllegalArgumentException("Null point was detected");
        for (int i = 0; i < points.length - 1; i++)
            for (int j = i + 1; j < points.length; j++)
                if (points[i].equals(points[j]))
                    throw new IllegalArgumentException("Equals points was detected");

        int size = points.length;
        double slope1, slope2, slope3;
        Point[] segmentPoints = new Point[4];
//        LineSegment[] segments = new LineSegment[1];

        for (int p = 0; p < size - 3; p++) {
            for (int q = p+1; q < size - 2; q++) {
                slope1 = points[p].slopeTo(points[q]);
                for (int r = q+1; r < size - 1; r++) {
                    slope2 = points[p].slopeTo(points[r]);
                    if (slope1 != slope2)
                        continue;
                    for (int s = r+1; s < size; s++) {
                        slope3 = points[p].slopeTo(points[s]);

                        if (slope1 == slope3) {
//                            StdOut.println("AddLineSegment p=" + p + " q=" + q + " r=" + r + " s=" + s + " slope1=" + slope1 + " slope2=" + slope2 + " slope3=" + slope3);

                            segmentPoints[0] = points[p];
                            segmentPoints[1] = points[q];
                            segmentPoints[2] = points[r];
                            segmentPoints[3] = points[s];

                            AddLineSegment(BuildLineSegmentByPoints(segmentPoints));
                        }
                    }
                }
            }
        }
        if (segments[0]==null)
            segments = new LineSegment[0];
    }
    // the number of line segments
    public           int numberOfSegments()       {
        return segments.length;
    }
    // the line segments
    public LineSegment[] segments(){
        return segments;
    }

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
