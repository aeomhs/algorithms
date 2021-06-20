package com.aeomhs.codekata.programmers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MakeJadenCase {

    public static void main(String[] args) {
        MakeJadenCase test = new MakeJadenCase();
        String s = " a  ";
        System.out.println("result ["+test.solution(s)+"]");
    }

    public String solution(String s) {
        StringBuilder builder = new StringBuilder();

        Pattern pattern = Pattern.compile("[a-zA-Z0-9]+[\\s]*");
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();

            System.out.println("["+s.substring(start, end)+"]("+start+","+end+")");
            builder.append(s.substring(start, start + 1).toUpperCase())
                    .append(s.substring(start + 1, end).toLowerCase());
        }

        return builder.toString();
    }
}
