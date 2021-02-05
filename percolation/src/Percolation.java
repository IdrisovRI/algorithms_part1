import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {

    private boolean[][] field;
    private int N;
    private boolean[][] fullFields;

    private void checkN(int n){
        if (n <= 0) throw new IllegalArgumentException("n must be > 0");
    }

    private void checkD(int value){
        if ((value <= 0) || (value > N)) throw new IllegalArgumentException("value must be in defined range=" + N);
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
//        StdOut.println("Size of entered field is " + n);
//        if (n <= 0) throw new IllegalArgumentException("n must be > 0");
        checkN(n);

        field = new boolean[n+1][n+1];
        fullFields = new boolean[n + 1][n + 1];
        N = n;

        for (int row = 1; row <= n; row++){
            for (int col = 1; col <= n; col++){
                field[row][col] = false;
                fullFields[row][col] = false;
            }
        }
    }

    private void fullField(int row, int col){
        if(field[row][col]){
            if (row == 1)
                fullFields[row][col] = true;
            else{
                if(row-1>0)
                    if(fullFields[row-1][col])
                        fullFields[row][col] = true;
                if(row+1<=N)
                    if(fullFields[row+1][col])
                        fullFields[row][col] = true;
                if(col-1>0)
                    if(fullFields[row][col-1])
                        fullFields[row][col] = true;
                if(col+1<=N)
                    if(fullFields[row][col+1])
                        fullFields[row][col] = true;
            }
        }
//            StdOut.println("fullFields[" + row + "][" + col + "]=" + fullFields[row][col]);
        if(fullFields[row][col]) {
            if (row - 1 > 0)
                if ((field[row - 1][col]) && (!fullFields[row - 1][col]))
                    fullField(row - 1, col);
            if (row + 1 <= N)
                if ((field[row + 1][col]) && (!fullFields[row + 1][col]))
                    fullField(row + 1, col);
            if (col - 1 > 0)
                if ((field[row][col - 1]) && (!fullFields[row][col - 1]))
                    fullField(row, col - 1);
            if (col + 1 <= N)
                if ((field[row][col + 1]) && (!fullFields[row][col + 1]))
                    fullField(row, col + 1);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        checkD(row);
        checkD(col);

        if (!field[row][col]) {
            field[row][col] = true;
            fullField(row, col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        checkD(row);
        checkD(col);
        return field[row][col];
    }

    private boolean[][] getFullField(){
        return fullFields;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        boolean[][] fFields = getFullField();
        return fFields[row][col];
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        int num = 0;
        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= N; col++) {
                if (field[row][col])
                    num++;
            }
        }
        return num;
    }

    // does the system percolate?
    public boolean percolates(){
        boolean percolate = false;
        boolean[][] fFields = getFullField();
        for(int col = 1; col <= N; col++){
            if(fFields[N][col]){
                percolate = true;
            }
        }
        return percolate;
    }

    // test client (optional)
    public static void main(String[] args){
        StdOut.println("Start main");
        int n = StdIn.readInt();
        Percolation pn = new Percolation(n);
//        while (!StdIn.isEmpty()) {
        while (true) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            //Extra exit
            if (row==0 && col==0) break;
            pn.open(row, col);
            StdOut.println("Was opened row=" + row + " col=" + col);
            StdOut.println("Number of opened sides=" + pn.numberOfOpenSites());
            StdOut.println("Is full=" + pn.isFull(row, col));
            StdOut.println("Percolate=" + pn.percolates());
        }
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                StdOut.println("Check opened point row=" + row + " col=" + col + " open=" + pn.isOpen(row, col));
            }
        }
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                StdOut.println("Check fulled point row=" + row + " col=" + col + " full=" + pn.isFull(row, col));
            }
        }
        StdOut.println("numberOfOpenSites= " + pn.numberOfOpenSites());
        StdOut.println("Percolates= " + pn.percolates());

        StdOut.println("End");
    }
}
