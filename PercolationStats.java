import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] treshold;
    private int t;
    private final double CONFIDENCE_95=1.96;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N < 1 || T < 1) {
            throw new IllegalArgumentException();
        }
        this.t = T;
        this.treshold = new double[t];

        for (int i = 0; i < treshold.length; i++) {
            treshold[i] = calcTreshold(N);
        }
    }

    private double calcTreshold(int n) {
        double count = 0;
        int random1, random2;
        Percolation perc = new Percolation(n);
        while (!perc.percolates()) {
            random1 = StdRandom.uniform(n) + 1;
            random2 = StdRandom.uniform(n) + 1;
            if (!perc.isOpen(random1, random2)) {
                count++;
                perc.open(random1, random2);
            }
        }
        return count / (n * n);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.treshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.treshold);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return this.mean() - (this.CONFIDENCE_95 * stddev()) / (Math.sqrt(t));
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return this.mean() + (this.CONFIDENCE_95 * stddev()) / (Math.sqrt(t));
    }

    // test client, described below
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(100, 50);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() +
                                   ", " + stats.confidenceHi());

    }
}
