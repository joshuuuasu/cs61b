import java.util.Arrays;

public class ArrayDequeTest {
    /** preliminary test for ArrayDeque. */
    public static void main(String[] args) {
        ArrayDeque<Integer> adq1 = new ArrayDeque<>(5);
        adq1.addFirst(4);
        ArrayDeque<String> adq2 = new ArrayDeque<>();
        ArrayDeque<String> adq3 = new ArrayDeque<>(adq2);
        System.out.println(adq1.get(0));
        adq2.addLast("testaddlast");
        adq2.resize(16);
        adq3.addFirst("testaddfirst");
        adq3.printDeque();
        adq2.printDeque();
        System.out.println(adq1.size);
        System.out.println(Arrays.toString(adq2.items));
        System.out.println(adq2.size);
        adq3.removeLast();
        adq2.removeFirst(); //test halve
    }
}
