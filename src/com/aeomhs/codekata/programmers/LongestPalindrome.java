package com.aeomhs.codekata.programmers;

public class LongestPalindrome {
    public static void main(String[] args) {
        LongestPalindrome test = new LongestPalindrome();
        System.out.println(test.solution("abababa"));
        System.out.println(test.solution("abaascd"));
        System.out.println(test.solution("abcdcba"));
        System.out.println(test.solution("abacde"));
    }

    public int solution(String s) {
        int len = 0;

        char[] str = s.toCharArray();

        for (len = str.length; len > 0; len--) {
            for (int j = 0; j+len <= str.length; j++) {
                if (isPalindrome(str, j, j+len)) {
                    return len;
                }
            }
        }

        return len;
    }

    private boolean isPalindrome(char[] s, int start, int end) {
        for (int i = start, j = end-1; i < j; i++, j--) {
            if (s[i] != s[j]) {
                return false;
            }
        }
        return true;
    }

    private boolean isPalindrome(String s) {
        for (int i = 0, j = s.length()-1; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }
}
