package com.aeomhs.codekata.programmers;

import java.util.HashSet;

public class BraceBuilder {
    private HashSet<String> set;
    private int count;

    public static void main(String[] args) {
        BraceBuilder test = new BraceBuilder();
        System.out.println(test.solution(1));
        System.out.println(test.solution(2));
        System.out.println(test.solution(3));
        System.out.println(test.solution(4));
        System.out.println(test.solution(5));
        System.out.println(test.solution(6));
        System.out.println(test.solution(7));
        System.out.println(test.solution(8));
        System.out.println(test.solution(12) + " 208012");
        System.out.println(test.solution(13) + " 742900");
        System.out.println(test.solution(14) + " 2674440");
    }

    public int solution(int n) {
        char[] braces = new char[n*2];
        for (int i = 0; i < n*2; i += 2) {
            braces[i] =   '(';
            braces[i+1] = ')';
        }
        set = new HashSet<>();
        count = 0;
        build(braces);

        return count;
    }

    public int solutionStringBuilder(int n) {

        String brace = "()";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            builder.append(brace);
        }

        set = new HashSet<>();
        count = 0;
        build(builder.toString());

        return count;
    }

    private void build(String braces) {
        set.add(braces);
        count++;

        String swap = ")(";
        int idx = braces.indexOf(swap);

        StringBuilder builder = new StringBuilder(braces);
        while (idx >= 0) {
            builder.replace(idx, idx+2, "()");
//            System.out.println(builder.toString());
            if (!set.contains(builder.toString())) {
                build(builder.toString());
            }
            builder.replace(idx, idx+2, ")(");
            idx = braces.indexOf(swap, idx+1);
        }
    }

    private void build(char[] braces) {
        set.add(String.valueOf(braces));
        count++;

        for (int i = 0; i < braces.length-1; i++) {
            if (braces[i] == ')' && braces[i+1] == '(') {
                swap(braces, i, i+1);
                if (!set.contains(String.valueOf(braces))) {
                    build(braces);
                }
                swap(braces, i, i+1);
            }
        }
    }

    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
