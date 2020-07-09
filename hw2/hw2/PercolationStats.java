package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private double mean;
    private double sd;      //standard deviation
    private double[] ci;    // confidence interval

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        double[] fraction = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int x = StdRandom.uniform(N * N);
                int[] pos = oneDToXY(x, N);
                p.open(pos[0], pos[1]);
            }
            fraction[i] = ((double) p.numberOfOpenSites()) / (N * N);
        }

        mean = StdStats.mean(fraction);
        sd = StdStats.stddev(fraction);
        ci = new double[]{mean - 1.96 * sd / Math.sqrt(T), mean + 1.96 * sd / Math.sqrt(T)};
    }

    private int[] oneDToXY(int n, int colNum) {
        int col = n % colNum;
        int row = (n - col) / colNum;
        int[] res = {row, col};
        return res;
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return sd;
    }

    public double confidenceLow() {
        return ci[0];
    }

    public double confidenceHigh() {
        return ci[1];
    }
}
