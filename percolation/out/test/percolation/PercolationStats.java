import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class PercolationStats {
    private double[] xi;
    private double x;
    private double s2;
    private int TRIALS;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if ((n <= 0) || (trials <= 0)) throw new IllegalArgumentException("n and trials must be > 0");
        xi = new double[trials];
        TRIALS = trials;

        double xiSum = 0;
        for(int t = 0; t < trials; t++){
            Percolation pn = new Percolation(n);
            while (!pn.percolates()){
                int row = 1 + StdRandom.uniform(n);
                int col = 1 + StdRandom.uniform(n);
                pn.open(row, col);
            }
            xi[t] = (double) pn.numberOfOpenSites() /(n*n);

            xiSum += xi[t];

        }
        x = xiSum/trials;
        s2 = 0;
        for (int t = 0; t < trials; t++){
            s2 += Math.pow(x-xi[t], 2);
        }
        s2 = s2/(trials-1);
    }

    // sample mean of percolation threshold
    public double mean(){
        return x;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return Math.pow(s2, 0.5);
    }

    private double delta(){
        return 1.96*Math.pow(s2, 0.5)/Math.pow(TRIALS, 0.5);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return x - delta();
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return x + delta();
    }

    // test client (see below)
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
//        StdOut.println("n=" + n + " trials =" + trials);
        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.println("mean=" + ps.mean());
        StdOut.println("stddev=" + ps.stddev());
        StdOut.println("95% confidence interval=[" + ps.confidenceHi() + ", " + ps.confidenceLo() + "]");
    }

}
