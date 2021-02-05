import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Permutation {
    public static void main(String[] args) {
        if (args.length > 0) {
            int k = Integer.parseInt(args[0]);
//            StdOut.println("k=" + k);

            RandomizedQueue<String> rq = new RandomizedQueue<String>();

            while (!StdIn.isEmpty()) {
                String row = StdIn.readString();
                rq.enqueue(row);
            }

            for(int i=0; i<k; i++){
                StdOut.println(rq.dequeue());
            }
        }
    }
}
