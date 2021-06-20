package com.aeomhs.codekata.programmers;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PairCharacter {
    public static void main(String[] args) {
        PairCharacter test = new PairCharacter();
        String s = "abababbababa";
        System.out.println(test.solution(s));
    }

    public int solution(String s) {
        StringBuilder builder = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        stack.push(s.charAt(0));

        for (int i = 1; i < s.length(); i++) {
            if (!stack.isEmpty() && stack.peek() == s.charAt(i)) {
                stack.pop();
            } else {
                stack.push(s.charAt(i));
            }
        }

        stack.forEach(builder::append);

        if (builder.length() == 0)
            return 1;
        else
            return 0;
    }

    public int solutionRegex(String s) {
        String regex = "(.*)([a-z])(\\2)(.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            System.out.println(matcher.group(1) + "," + matcher.group(2) + "," + matcher.group(3) + "," + matcher.group(4));
            s = matcher.group(1) + matcher.group(4);
            matcher = pattern.matcher(s);
        }

        if (s.length() == 0) {
            return 1;
        } else
            return 0;
    }
}
