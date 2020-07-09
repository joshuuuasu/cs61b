package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int[][] grid;
    private int openSize;
    private WeightedQuickUnionUF uf, uf2;

    private int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N should be positive integer");
        }
        grid = new int[N][N];
        openSize = 0;

        uf = new WeightedQuickUnionUF(N * N + 2);
        uf2 = new WeightedQuickUnionUF(N * N + 1);
        // uf2 is used for avoiding backwash (no bottom end point.)

        for (int i = 1; i <= N; i++) {
            uf.union(0, i);
            uf2.union(0, i);
        }
        for (int i = N * N - N + 1; i <= N * N; i++) {
            uf.union(N * N + 1, i);
        }
    }

    public void open(int row, int col) {
        if (row >= grid.length || col >= grid[0].length || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("index out of bounds for open()");
        }
        if (isOpen(row, col)) {
            return;
        }
        grid[row][col] = 1; // 1 denotes open, 0 (default) otherwise.
        unionNeighbors(row, col);
        openSize += 1;
    }

    private void unionNeighbors(int row, int col) {
        for (int[] dir: dirs) {
            int[] adj = {row + dir[0], col + dir[1]};
            if (adj[0] >= 0 && adj[0] < grid.length && adj[1] >= 0 && adj[1] < grid[0].length
                && grid[adj[0]][adj[1]] == 1) {
                uf.union(xyTo1D(row, col), xyTo1D(adj[0], adj[1]));
                uf2.union(xyTo1D(row, col), xyTo1D(adj[0], adj[1]));
            }
        }
    }

    private int xyTo1D(int row, int col) {
        return row * grid[0].length + col + 1;
    }

    public boolean isOpen(int row, int col) {
        if (row >= grid.length || col >= grid[0].length || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("index out of bounds for isOpen()");
        }
        return grid[row][col] == 1;
    }

    public boolean isFull(int row, int col) {
        if (row >= grid.length || col >= grid[0].length || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("index out of bounds for isFull()");
        }
        return uf2.connected(0, xyTo1D(row, col)) && isOpen(row, col);
    }

    public int numberOfOpenSites() {
        return openSize;
    }

    public boolean percolates() {
        return uf.connected(0, grid.length * grid[0].length + 1) && numberOfOpenSites() > 0;
    }

    public static void main(String[] args) {
        Percolation x = new Percolation(10);
        System.out.println(x.grid[0][0]);
    }
}
