import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

public final class Percolation {
    // sizeN-by-sizeN grid
    private final int sizeN;
    private final int topSiteIndex;
    private final int bottomSiteIndex;
    // number of open sites
    private int numOpen;
    // 0 represents closed, 1 represents opened;
    private final boolean [] state;
    private final WeightedQuickUnionUF unionFind;
    private final WeightedQuickUnionUF subUnionFind;

    public Percolation(int sizeN) {
        if (sizeN <= 0)
            throw new IllegalArgumentException();

        this.sizeN = sizeN;
        numOpen = 0;
        state = new boolean[sizeN * sizeN +2];
        unionFind = new WeightedQuickUnionUF(this.sizeN * this.sizeN +2);
        subUnionFind = new WeightedQuickUnionUF(this.sizeN * this.sizeN +2);

        Arrays.fill(state, false);

        // open virtual sites;
        state[0] = true;
        state[this.sizeN * this.sizeN +1] = true;
        topSiteIndex = 0;
        bottomSiteIndex = state.length-1;
    }

    private int toIndex(int row, int col) {
        // considered virtual sites
        return ((row-1)* sizeN +col);
    }

    private boolean connected(WeightedQuickUnionUF UF, int p, int q) {
        return UF.find(p) == UF.find(q);
    }

    private void connectUP(int row, int col) {
        // if at first row, connect to TopSite;
        int index = toIndex(row, col);
        if (row == 1) {
        // if (row ==1) {
            unionFind.union(topSiteIndex,index);
            subUnionFind.union(topSiteIndex,index);
        } else {
            if (isOpen(row-1, col))
                unionFind.union(index, toIndex(row-1,col));
                subUnionFind.union(index, toIndex(row-1,col));
        }
    }

    private void connectDown(int row, int col) {
        // if at bottom row, connect to BottomSite
        int index = toIndex(row, col);
        if (row == sizeN) {
            unionFind.union(bottomSiteIndex, index);
        } else {
            if (isOpen(row+1, col)) {
                unionFind.union(index, toIndex(row+1, col));
                subUnionFind.union(index, toIndex(row+1, col));
            }
        }
    }

    private void connectLeft(int row, int col) {
        // attention to the most left border
        int index = toIndex(row, col);
        if (col == 1)
            return;
        if (isOpen(row, col-1)) {
            unionFind.union(index,toIndex(row, col-1));
            subUnionFind.union(index,toIndex(row, col-1));
        }
    }

    private  void connectRight(int row, int col) {
        // attention to the most right boarder
        int Index = toIndex(row,col);
        if (col == sizeN)
            return;
        if (isOpen(row,col+1)) {
            unionFind.union(Index,toIndex(row,col+1));
            subUnionFind.union(Index,toIndex(row,col+1));
        }
    }

    private void check(int row, int col) {
        if (row < 1 || row > sizeN || col < 1 || col > sizeN)
            throw new IllegalArgumentException();
    }

    public void open(int row, int col) {
        check(row,col);
        connectUP(row,col);
        connectDown(row,col);
        connectLeft(row,col);
        connectRight(row,col);

        state[toIndex(row,col)] = true;
        numOpen += 1;
    }

    public boolean isOpen(int row, int col) {
        check(row,col);
        return state[toIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        check(row,col);
        return connected(unionFind,topSiteIndex,toIndex(row, col))
                && connected(subUnionFind, topSiteIndex, toIndex(row, col));
    }

    public int numberOfOpenSites() {
        return numOpen;
    }

    public boolean percolates() {
        return unionFind.find(topSiteIndex) == unionFind.find(bottomSiteIndex);
    }
}
