import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.math.*;

public class PercolationStats {
    private Percolation percolation;
    private double [] threshold; // opened sites when percolates
    private int T;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        T = trials;
        threshold = new double[T];
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n)+1;
                int col = StdRandom.uniform(n)+1;
                if (percolation.isOpen(row,col))
                    continue;
                percolation.open(row,col);
            }
            System.out.println("Percolates " + percolation.numberOfOpenSites());
            threshold[i] = ((double) percolation.numberOfOpenSites()/(n*n));
        }
    }

    public double mean() {
        return StdStats.mean(threshold);
    }

    public double stddev() {
        if (T==1)
            return Double.NaN;
        return StdStats.stddev(threshold);
    }

    public double confidenceLo() {
        return (mean() - (1.96*stddev()/Math.sqrt(T)));
    }

    public double confidenceHi() {
        return (mean() + (1.96*stddev()/Math.sqrt(T)));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);

        PercolationStats pStats = new PercolationStats(n,trails);

        System.out.println("mean\t= "+pStats.mean());
        System.out.println("stddev\t= "+pStats.stddev());
        System.out.println("95% confidence interval\t= ["+pStats.confidenceLo()+", "+pStats.confidenceHi()+"]");
    }

}
