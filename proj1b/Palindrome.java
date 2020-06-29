public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> result = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            result.addLast(word.charAt(i));
        }
        return result;
    }

    private boolean isPalindrome(Deque<Character> dqc) {
        if (dqc.size() < 2) {
            return true;
        }
        char first = dqc.removeFirst();
        char last = dqc.removeLast();
        if (first != last) {
            return false;
        }
        return isPalindrome(dqc);
    }

    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindrome(wordDeque);
    }

    private boolean isPalindrome(Deque<Character> dqc, CharacterComparator cc) {
        if (dqc.size() < 2) {
            return true;
        }
        char first = dqc.removeFirst();
        char last = dqc.removeLast();
        if (!cc.equalChars(first, last)) {
            return false;
        }
        return isPalindrome(dqc, cc);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindrome(wordDeque, cc);
    }
}
