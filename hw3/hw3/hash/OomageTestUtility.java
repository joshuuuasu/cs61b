package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */

        List<List<Oomage>> buckets = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            List<Oomage> bucket = new ArrayList<>();
            buckets.add(bucket);
        }
        for (Oomage o: oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            if (!buckets.get(bucketNum).contains(o)) {
                buckets.get(bucketNum).add(o);
            }
        }
        int N = oomages.size();
        for (int i = 0; i < M; i++) {
            int x = buckets.get(i).size();
            if (x < N / 50.0 || x > N / 2.5) {
                return false;
            }
        }
        return true;
    }
}
