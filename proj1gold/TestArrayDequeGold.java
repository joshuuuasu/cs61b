import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDequeGold {

    @Test
    public void ArrayDequeTest() {
        /** @source: StudentArrayDequeLauncher.java */

        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        String sequence = "\n";

        for (int i = 0; i < 100; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.25) {
                sad.addFirst(i);
                ads.addFirst(i);
                sequence += String.format("addFirst(%s)\n", i);
            } else if (numberBetweenZeroAndOne < 0.5) {
                sad.addLast(i);
                ads.addLast(i);
                sequence += String.format("addLast(%s)\n", i);
            } else if (sad.size() == 0 || ads.size() == 0) {
                continue;
            } else if (numberBetweenZeroAndOne < 0.75) {
                Integer actual = sad.removeFirst();
                Integer expected = ads.removeFirst();
                sequence += "removeFirst()\n";
                assertEquals(sequence, expected, actual);
            } else {
                Integer actual = sad.removeLast();
                Integer expected = ads.removeLast();
                sequence += "removeLast()\n";
                assertEquals(sequence, expected, actual);
            }
        }
    }
}
