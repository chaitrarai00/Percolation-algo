import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private int top;
    private int bottom;
    private WeightedQuickUnionUF unionfindorig;
    //private WeightedQuickUnionUF unionfindmodify;
    private int[] indicator; //  0-->closed,1-->open

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (checkargs(n)) {
            this.n = n;
            unionfindorig = new WeightedQuickUnionUF(n * n + 2);
//            unionfindmodify = new WeightedQuickUnionUF(n * n + 2);
            top = 0;
            bottom = n * n + 1;
            indicator = new int[n*n];

        }

        // for (int i = 0; i < indicator.length; i++) {
        //     indicator[i] = false; // all blocks are initially blocked
        // }
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {


        if (isOpen(row, col)) {
            return;
        }
        int pos = coordinateforarray(row, col);
        this.indicator[pos] = 1;

        if (row == 1 && !(unionfindorig.find(pos) == unionfindorig.find(top))) {
            unionfindorig.union(pos, top);
//            unionfindmodify.union(pos, top);
        }
        if (row == this.n) {
            unionfindorig.union(pos, bottom);
//            unionfindmodify.union(pos, top);
        }
        if (row < this.n) {
            if (isOpen(row + 1, col)) {
                unionfindorig.union(pos, coordinateforarray(row + 1, col));
//                unionfindmodify.union(pos, coordinateforarray(row + 1, col));
            }
        }

        if (row > 1) {
            if (isOpen(row - 1, col)) {
                unionfindorig.union(pos, coordinateforarray(row - 1, col));
//                unionfindmodify.union(pos, coordinateforarray(row - 1, col));
            }
        }

        if (col < this.n) {
            if (isOpen(row, col + 1)) {
                unionfindorig.union(pos, coordinateforarray(row, col + 1));
//                unionfindmodify.union(pos, coordinateforarray(row, col + 1));
            }
        }

        if (col > 1) {
            if (isOpen(row, col - 1)) {
                unionfindorig.union(pos, coordinateforarray(row, col - 1));
//                unionfindmodify.union(pos, coordinateforarray(row, col - 1));
            }
        }


    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    int pos = coordinateforarray(row, col);
        if ((row < 1 || row > this.n) && (col < 1 || col > this.n)) {
            throw new IllegalArgumentException();
        }
        if (this.indicator[pos] == 1) {
              return true;
        }
            else
                return false;
        }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int pos = coordinateforarray(row, col);
            if ((row < 1 || row > this.n) && (col < 1 || col > this.n)) {
                throw new IllegalArgumentException();
            }
        if (!isOpen(row, col))
            return false;

         if (unionfindorig.find(top)==unionfindorig.find(pos)){
             return true;
         }
         return false;

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < indicator.length; i++) {
            if (indicator[i] == 1)
                count++;
        }
        return count;

    }

    // check for proper arguments
    private boolean checkargs(int val) {
        if (val < 1 ) {
            throw new IllegalArgumentException();
        }
        return true;
    }

    // convert to coordinates
    private int coordinateforarray (int row, int col) {
        int coordinates = (row - 1) * this.n + (col - 1); //-1 since indexing starts with 0 //input to a 1d array
        return coordinates;
    }

    // does the system percolate?
    public boolean percolates() {
        if (unionfindorig.find(top) == unionfindorig.find(bottom)) {
            return true;
        }
        return false;

    }


    //     // test client (optional)
    //     public static void main(String[] args){
    // }

}
