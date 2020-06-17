public class TestBody {

    public static void main(String[] args) {
        Body b1 = new Body(0, 0, 1, 4, 50, "jupiter.gif");
        Body b2 = new Body(0, 5, 3, 5, 60, "jupiter.gif");
        System.out.println( b1.calcForceExertedBy(b2) );
    }
}