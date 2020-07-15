public class RabinKarpAlgorithm {

    /**
     * This algorithm returns the starting index of the matching substring.
     * This method will return -1 if no matching substring is found, or if the input is invalid.
     */
    public static int rabinKarp(String input, String pattern) {
        if (input.length() < pattern.length()) {
            return -1;
        }
        int pl = pattern.length();
        RollingString patternRS = new RollingString(pattern, pl);
        RollingString inputRS = new RollingString(input.substring(0, pl), pl);
        int ph = patternRS.hashCode();
        for (int i = 0; i < input.length() - pl; i++) {
            if (inputRS.hashCode() == ph) {
                if (inputRS.equals(patternRS)) {
                    return i;
                }
            }
            inputRS.addChar(input.charAt(pl + i));
        }
        return -1;
    }

}
