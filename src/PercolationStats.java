import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // opened sites when percolates
    private final double [] threshold;
    private final int trials;
    private final double factorForConfidence = 1.96;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        this.trials = trials;
        threshold = new double[this.trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n)+1;
                int col = StdRandom.uniform(n)+1;
                if (percolation.isOpen(row,col))
                    continue;
                percolation.open(row,col);
            }
            // System.out.println("Percolates " + percolation.numberOfOpenSites());
            threshold[i] = ((double) percolation.numberOfOpenSites()/(n*n));
        }
    }

    public double mean() {
        return StdStats.mean(threshold);
    }

    public double stddev() {
        if (trials ==1)
            return Double.NaN;
        return StdStats.stddev(threshold);
    }

    public double confidenceLo() {
        return (mean() - (factorForConfidence*stddev()/Math.sqrt(trials)));
    }

    public double confidenceHi() {
        return (mean() + (factorForConfidence*stddev()/Math.sqrt(trials)));
    }

    public final void show() {
        StdOut.printf("mean\t\t\t\t\t= %f\nstddev\t\t\t\t\t= %f\n95%% confidence interval\t= %f, %f\n",
                mean(), stddev(), confidenceLo(), confidenceHi());
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);

        PercolationStats pStats = new PercolationStats(n,trails);
        pStats.show();

    }

}
