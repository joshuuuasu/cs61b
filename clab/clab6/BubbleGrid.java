/**
 * @source: https://github.com/zangsy/cs61b_sp19/blob/master/clab/clab6/BubbleGrid.java
 *
 * Calculate connecting items after cutting one item.
 * Connecting stuff --> disjoint set;
 * Since ds only has union function (no disconnect function), we should do it reversely, cutting all the ones that are
 * going to be cut all at once, and then adding back the items.
 *
 * key point here: all the non-falling bubbles are connected with the CEILING!
 */
public class BubbleGrid {
    private int[][] grid;
    private int rowNum;
    private int colNum;
    private int ceiling = 0; // stored in the 0-th item of UnionFind array.
    private int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
        rowNum = grid.length;
        colNum = grid[0].length;
    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        UnionFind uf = new UnionFind(rowNum * colNum + 1);
        // the 0th item in the array denotes the ceiling.

        // Mark the bubbles that will be hit by darts, set the corresponding grid value to 2.
        for (int[] dart: darts) {
            if (grid[dart[0]][dart[1]] == 1) {
                grid[dart[0]][dart[1]] = 2;
            }
        }

        // Union the rest bubbles that will not be hit.
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                if (grid[i][j] == 1) {
                    unionNeighbors(i, j, grid, uf);
                }
            }
        }

        int[] result = new int[darts.length];
        int count = uf.sizeOf(ceiling);
        // Counting the number of bubbles that are connected with ceilling.

        //Reversely put bubble(1) back to their positions.
        for (int i = darts.length - 1; i >= 0; i--) {
            int row = darts[i][0];
            int col = darts[i][1];
            if (grid[row][col] == 2) {
                grid[row][col] = 1;
                unionNeighbors(row, col, grid, uf);
            }
            int newCount = uf.sizeOf(ceiling);
            result[i] = newCount > count ? newCount - count - 1 : 0;
            // -1 denotes the one that is being hit just pops and does not fall!
            // max comes here because if the grid being hit does not contain a bubble.
            // A ? B : C ---> if A then B else C
            count = newCount;
        }
        return result;
    }

    // Union a given bubble (row, col) with all neighboring bubbles.
    private void unionNeighbors(int row, int col,int[][] grid, UnionFind uf) {
        if (row == 0) {
            uf.union(xyTo1D(row, col), ceiling);
        }
        for (int[] dir: dirs) {
            int adjRow = row + dir[0];
            int adjCol = col + dir[1];
            if (adjRow < 0 || adjRow == rowNum || adjCol < 0 || adjCol == colNum || grid[adjRow][adjCol] != 1) {
                continue;
            }
            uf.union(xyTo1D(row, col), xyTo1D(adjRow, adjCol));
        }
    }

    private int xyTo1D(int row, int col) {
        // Turn (row, col) to uf index.
        return row * colNum + col + 1;
    }
}
