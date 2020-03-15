package com.aeomhs.codekata.kakaotest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

public class CompressionLZW {
    public int[] solution(String msg) {
        TrieLZW trie = new TrieLZW();

        int[] answer = trie.encode(msg);

        return answer;
    }

    @Test
    public void compressionLZWTest() {
        CompressionLZW module = new CompressionLZW();
        int[] result = module.solution("KAKAO");
        int[] expected = {11, 1, 27, 15};
        Assertions.assertArrayEquals(expected, result);

        result = module.solution("TOBEORNOTTOBEORTOBEORNOT");
        expected = new int[]{20, 15, 2, 5, 15, 18, 14, 15, 20, 27, 29, 31, 36, 30, 32, 34};
        Assertions.assertArrayEquals(expected, result);

        result = module.solution("ABABABABABABABAB");
        expected = new int[]{1, 2, 27, 29, 28, 31, 30};
        Assertions.assertArrayEquals(expected, result);
    }
}

// using TRIE
class TrieLZW {

    // 대문자 영어 알파벳 개수
    private static final int R = 26;

    private Node root = new Node();

    private HashMap<String, Integer> symbolTable = new HashMap<>();

    private int combineWordIndex = 1;

    static class Node {

        String value;

        Node[] next;

        Node() {
            next = new Node[R];
        }
    }

    public int[] encode(String string) {
        LinkedList<Integer> codeList = new LinkedList<>();
        StringBuilder encoder = new StringBuilder(string);

        while (encoder.length() > 0) {
            // find Longest Substring
            String longestSubString = findLongestSubString(encoder.toString());

            // pre view and registry substring
            if (encoder.length() > longestSubString.length()) {
                char preWord = encoder.charAt(longestSubString.length());
                put(longestSubString+preWord);
            }

            // encode longest substring
            codeList.addLast(symbolTable.get(longestSubString));
            encoder.delete(0, longestSubString.length());

        }

        int[] codeWords = new int[codeList.size()];
        for (int i = 0; i < codeWords.length; i++)
            codeWords[i] = codeList.get(i);

        return codeWords;
    }

    private void put(String string) {
        put(root, string, 0);
    }

    private void put(Node node, String string, int depth) {

        if (depth == string.length()) {
            node.value = string;

            if (depth == 1)
                symbolTable.put(string, string.charAt(0)-'A'+1);
            else
                symbolTable.put(string, R + combineWordIndex++);

            return;
        }

        int index = string.charAt(depth)-'A';

        if (node.next[index] == null)
            node.next[index] = new Node();

        put(node.next[index], string, depth+1);

    }

    private String findLongestSubString(String string) {
        int index = string.charAt(0) - 'A';

        if (root.next[index] == null) {
            put(string.substring(0,1));
            return string.substring(0, 1);
        }

        return findLongestSubString(root.next[index], string, 1);
    }

    private String findLongestSubString(Node node, String string, int index) {

        // longest Substring
        if (string.length() == index || node.next[string.charAt(index)-'A'] == null) {
           return node.value;
        }

        return findLongestSubString(node.next[string.charAt(index)-'A'], string, index+1);
    }


}