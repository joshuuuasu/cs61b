import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void isPalindromeTest() {
        assertTrue(palindrome.isPalindrome("wow"));
        assertTrue(palindrome.isPalindrome("anna"));
        assertTrue(palindrome.isPalindrome(" "));
        assertFalse(palindrome.isPalindrome("Anna"));
        assertTrue(palindrome.isPalindrome("a"));
    }

    @Test
    public void isPalindromeCcTest() {
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake", cc));
        assertTrue(palindrome.isPalindrome("%avub&", cc));
        assertTrue(palindrome.isPalindrome("q", cc));
        assertTrue(palindrome.isPalindrome(" ", cc));
        assertFalse(palindrome.isPalindrome("Annie", cc));
        assertFalse(palindrome.isPalindrome("Ana", cc));
    }
}
