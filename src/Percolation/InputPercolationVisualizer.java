package Percolation;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class InputPercolationVisualizer extends PercolationVisualizer{
    private static final int DELAY = 100;

    public static void main(String args[]) {
        In in = new In();             // input file
        int n = in.readInt();         // n-by-n percolation system

        // turn on animation mode
        StdDraw.enableDoubleBuffering();

        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(n);
        draw(perc, n);
        StdDraw.show();
        StdDraw.pause(DELAY);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            System.out.println(i + " " + j);
            draw(perc, n);
            StdDraw.show();
            StdDraw.pause(DELAY);
        }
    }
}
