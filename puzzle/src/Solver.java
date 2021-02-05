import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;


public class Solver {

    private ArrayList<Board> solution = new ArrayList<>();


    private class BoardStep implements Comparable<BoardStep>{
        private Board board;
        private BoardStep prevBoard;
        private int moves;
        private int manhattan;
        private int priority;

        public BoardStep(Board board, BoardStep prevBoard, int moves){
            this.board = board;
            this.prevBoard=prevBoard;
            this.moves = moves;
            this.manhattan = board.manhattan();
            this.priority = board.manhattan() + moves;
        }

        public boolean isGoal(){
            return this.board.isGoal();
        }

        public Board getBoard(){
            return this.board;
        }

        public int getManhattan(){
            return this.manhattan;
        }

        public BoardStep getPrevBoard(){
            return this.prevBoard;
        }

        public int getMoves(){
            return this.moves;
        }

        public ArrayList<Board> getSolution(){
            ArrayList<Board> solution = new ArrayList<>();
            solution.add(this.board);
            BoardStep prev = this.getPrevBoard();

            while(prev != null){
                solution.add(0, prev.board);
                prev = prev.getPrevBoard();
            }

            return solution;
        }

        public int compareTo(BoardStep bs){
            int result = 0;
            if(this.priority > bs.priority)
                result = 1;
            if(this.priority < bs.priority)
                result = -1;
            return result;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        if(initial == null)
            throw new IllegalArgumentException();

        MinPQ<BoardStep> priorityQueue = new MinPQ<>();
        BoardStep firstStep = new BoardStep(initial, null, 0);
        priorityQueue.insert(firstStep);

        while(priorityQueue.size()>0){
            BoardStep bs = priorityQueue.delMin();

            if (bs.getBoard().isGoal()){
                this.solution = bs.getSolution();
                break;
            }

            for(Board neighbor: bs.getBoard().neighbors()){
                if(bs.getPrevBoard()==null || !neighbor.equals(bs.getPrevBoard().getBoard())){
                    BoardStep newStep = new BoardStep(neighbor, bs, bs.getMoves()+1);
                    priorityQueue.insert(newStep);
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return (this.solution.size() > 0);
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        return (this.solution.size() - 1);
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        return this.solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
