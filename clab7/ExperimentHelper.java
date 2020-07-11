import java.util.Random;

/**
 * Created by hug.
 */
public class ExperimentHelper {

    /** Returns the internal path length for an optimum binary search tree of
     *  size N. Examples:
     *  N = 1, OIPL: 0
     *  N = 2, OIPL: 1
     *  N = 3, OIPL: 2
     *  N = 4, OIPL: 4
     *  N = 5, OIPL: 6
     *  N = 6, OIPL: 8
     *  N = 7, OIPL: 10
     *  N = 8, OIPL: 13
     */
    public static int optimalIPL(int N) {
        int result = 0;
        int count = 0;
        int temp = N;
        while (temp != 1) {
            count++;
            temp = temp / 2;
        }
        for (int i = 0; i < count; i++) {
            result = result + (int) Math.pow(2, i) * i;
        }
        result = result + (N - (int) Math.pow(2, count) + 1) * count;
        return result;
    }

    //The recursion method below is way to slow!
    /*{
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        if (N == 1) {
            return 0;
        } else if (N == 2) {
            return 1;
        } else if ((N & (N - 1)) == 0) {
            return 2 * optimalIPL(N-1) - optimalIPL(N-2) + 1;
        }
        return 2 * optimalIPL(N-1) - optimalIPL(N-2);
    } */

    /** Returns the average depth for nodes in an optimal BST of
     *  size N.
     *  Examples:
     *  N = 1, OAD: 0
     *  N = 5, OAD: 1.2
     *  N = 8, OAD: 1.625
     * @return
     */

    public static double optimalAverageDepth(int N) {
        return ((double) optimalIPL(N)) / N;
    }

    public static void randomInsert(BST bst) {
        int x = RandomGenerator.getRandomInt(1000000);
        while (true) {
            if (!bst.contains(x)) {
                bst.add(x);
                break;
            } else {
                x = RandomGenerator.getRandomInt(1000000);
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 8; i++) {
            System.out.println(optimalIPL(i));
        }
        for (int i = 1; i <= 8; i++) {
            System.out.println(optimalAverageDepth(i));
        }
    }
}
