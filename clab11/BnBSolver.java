import java.util.ArrayList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {

    private List<Bear> solvedBears = new ArrayList<>();
    private List<Bed> solvedBeds = new ArrayList<>();

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        Pair<List<Bear>, List<Bed>> res = quickSort(bears, beds);
        solvedBears = res.first();
        solvedBeds = res.second();
    }

    private Pair<List<Bear>, List<Bed>> quickSort(List<Bear> bears, List<Bed> beds) {
        if (bears.size() < 2 || beds.size() < 2) {
            return new Pair<>(bears, beds);
        }

        Bear pivotBear = bears.get(0);
        List<Bed> lessBeds = new ArrayList<>();
        List<Bed> equalBeds = new ArrayList<>();
        List<Bed> greaterBeds = new ArrayList<>();
        partitionBeds(pivotBear, beds, lessBeds, equalBeds, greaterBeds);

        Bed pivotBed = equalBeds.get(0);
        List<Bear> lessBears = new ArrayList<>();
        List<Bear> equalBears = new ArrayList<>();
        List<Bear> greaterBears = new ArrayList<>();
        partitionBears(pivotBed, bears, lessBears, equalBears, greaterBears);

        Pair<List<Bear>, List<Bed>> less = quickSort(lessBears, lessBeds);
        List<Bear> sortedLessBears = less.first();
        List<Bed> sortedLessBeds = less.second();
        Pair<List<Bear>, List<Bed>> greater = quickSort(greaterBears, greaterBeds);
        List<Bear> sortedGreaterBears = greater.first();
        List<Bed> sortedGreaterBeds = greater.second();

        List<Bear> sortedBears = catenate(sortedLessBears, equalBears, sortedGreaterBears);
        List<Bed> sortedBeds = catenate(sortedLessBeds, equalBeds, sortedGreaterBeds);
        return new Pair<>(sortedBears, sortedBeds);
    }

    private void partitionBeds(Bear pivotBear, List<Bed> beds, List<Bed> less,
                               List<Bed> equal, List<Bed> greater) {
        for (Bed bed : beds) {
            if (bed.compareTo(pivotBear) < 0) {
                less.add(bed);
            } else if (bed.compareTo(pivotBear) == 0) {
                equal.add(bed);
            } else {
                greater.add(bed);
            }
        }
    }

    private void partitionBears(Bed pivotBed, List<Bear> bears, List<Bear> less,
                                List<Bear> equal, List<Bear> greater) {
        for (Bear bear : bears) {
            if (bear.compareTo(pivotBed) < 0) {
                less.add(bear);
            } else if (bear.compareTo(pivotBed) == 0) {
                equal.add(bear);
            } else {
                greater.add(bear);
            }
        }
    }

    private <T extends HiddenComparable> List<T> catenate(List<T> l1, List<T> l2, List<T> l3) {
        List<T> res = new ArrayList<>();
        for (T t : l1) {
            res.add(t);
        }
        for (T t : l2) {
            res.add(t);
        }
        for (T t : l3) {
            res.add(t);
        }
        return res;
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        return solvedBears;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        return solvedBeds;
    }
}
