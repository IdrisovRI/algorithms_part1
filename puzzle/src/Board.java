import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;


public class Board {
    private final int size;
    private int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        size = tiles[0].length;
        this.tiles = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                this.tiles[i][j] = tiles[i][j];
    }

    // string representation of this board
    public String toString(){
        StringBuilder stringResult = new StringBuilder(this.size + "\n");
        for (int i = 0; i < size; i++){
            String rowStr="" + (this.tiles[i][0]);
            for (int j = 1; j < size; j++){
                rowStr = rowStr + " " + this.tiles[i][j];
            }
            stringResult.append(rowStr).append("\n");
        }
        return stringResult.toString();
    }

    // board dimension n
    public int dimension(){
        return this.size;
    }

    private int getTargetRow(int tile){
        return (tile-1)/this.size;
    }

    private int getTargetCol(int tile){
        return (tile-1)%size;
    }

    // number of tiles out of place
    public int hamming(){
        int hamming = 0;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++){
                if (this.tiles[i][j]!=0) {
                    int tile = this.tiles[i][j];
                    int target_row = getTargetRow(tile);
                    int target_col = getTargetCol(tile);
                    if(target_row != i || target_col != j)
                        hamming++;
                }
            }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int manhattan = 0;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++){
                if (this.tiles[i][j]!=0) {
                    int tile = this.tiles[i][j];

                    int rowDelta = i - getTargetRow(tile);
                    if (rowDelta < 0)
                        rowDelta = rowDelta * -1;
                    int colDelta = j - getTargetCol(tile);
                    if (colDelta < 0)
                        colDelta = colDelta * -1;
//                    StdOut.println("Manhattan val=" + manhattan + " i=" + i + " j=" + j);
                    manhattan = manhattan + rowDelta + colDelta;
                }
            }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal(){
        return 0 == manhattan();
    }

    // does this board equal y?
    public boolean equals(Object y){
        if(y==null)
            return false;

        if (y.getClass() != this.getClass()){
            return false;
        }
        Board boardY = (Board) y;

        if (this.dimension() != boardY.dimension())
            return false;

        if(Arrays.deepEquals(this.tiles, boardY.tiles))
            return true;

        return false;
    }

    private Board getBoardCopy(){
        Board newBoard = new Board(this.tiles);
        return newBoard;
    }

    private Board getExchangedBoard(int r1, int c1, int r2, int c2){
        Board boardCopy = getBoardCopy();

        int buff = boardCopy.tiles[r1][c1];
        boardCopy.tiles[r1][c1] = boardCopy.tiles[r2][c2];
        boardCopy.tiles[r2][c2] = buff;
        return boardCopy;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        ArrayList<Board> neighbours= new ArrayList<>();

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++){
                if(this.tiles[i][j] == 0){
                    if(i-1 >= 0)
                        neighbours.add(getExchangedBoard(i,j,i-1,j));
                    if(i+1 < size)
                        neighbours.add(getExchangedBoard(i,j,i+1,j));
                    if(j-1 >= 0)
                        neighbours.add(getExchangedBoard(i,j,i,j-1));
                    if(j+1 < size)
                        neighbours.add(getExchangedBoard(i,j,i,j+1));
                }
            }
        return neighbours;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        Board twinBoard = getBoardCopy();
        for (int i = 0; i < size; i++)
            for (int j = 0; i < size-1; i++) {
                if (this.tiles[i][j] != 0 && this.tiles[i][j+1] != 0 && this.tiles[i][j] != ((i * size) + j + 1)) {
                    int buff = twinBoard.tiles[i][j + 1];
                    twinBoard.tiles[i][j + 1] = twinBoard.tiles[i][j];
                    twinBoard.tiles[i][j] = buff;
                    return twinBoard;
                }
            }

        return twinBoard;
    }

    // unit testing (not graded)
    public static void main(String[] args){
        int[][] tiles = new int[3][3];
        tiles[0][0] = 5;tiles[0][1] = 0;tiles[0][2] = 4;
        tiles[1][0] = 2;tiles[1][1] = 3;tiles[1][2] = 8;
        tiles[2][0] = 7;tiles[2][1] = 1;tiles[2][2] = 6;
        int[][] tiles2 = new int[3][3];
        tiles2[0][0] = 1;tiles2[0][1] = 2;tiles2[0][2] = 3;
        tiles2[1][0] = 4;tiles2[1][1] = 5;tiles2[1][2] = 6;
        tiles2[2][0] = 7;tiles2[2][1] = 8;tiles2[2][2] = 0;

        Board b1 = new Board(tiles);
        Board b2 = new Board(tiles2);
        StdOut.println("Hamming="+b1.hamming());
//        StdOut.println("Manhattan="+b1.manhattan());
        StdOut.println("------------------------------");
        StdOut.println("Manhattan2="+b2.manhattan());
        StdOut.println("------------------------------");
        StdOut.println("Dimension=" + b1.dimension());
        StdOut.println(b1.toString());
        StdOut.println("");
        StdOut.println("isGoal="+b1.isGoal());
        StdOut.println(b1.equals(b1.twin()));
        StdOut.println("Neighbors:");
        for(Board b: b1.neighbors())
            StdOut.println(b.toString());


    }

}
