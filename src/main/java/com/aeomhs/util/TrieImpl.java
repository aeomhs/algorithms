package com.aeomhs.util;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Robert Sedgewick. Algorithms. 4th Edition. Chapter 5. Trie
 */
public class TrieImpl<V> {

    private static int LOWERCASE_NUM = 26;

    private Node root = new Node();

    private static class Node {
        private Object value;
        private Node[] next = new Node[LOWERCASE_NUM];
    }

    public V get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (V) x.value;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        int index = key.charAt(d) - 'a';
        return get(x.next[index], key, d+1);
    }

    public void put(String key, V value) {
        root = put(root, key, value, 0);
    }

    private Node put(Node x, String key, V value, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.value = value;
            return x;
        }
        int index = key.charAt(d) - 'a';
        x.next[index] = put(x.next[index], key, value, d+1);
        return x;
    }

    public Iterable<String> keys() {
        return keyWithPrefix("");
    }

    public Iterable<String> keyWithPrefix(String pre) {
        LinkedList<String> list = new LinkedList<>();
        collect(get(root, pre, 0), pre, list);
        return list;
    }

    private void collect(Node x, String pre, LinkedList<String> list) {
        if (x == null)
            return;

        if (x.value != null)
            list.add(pre);

        for (int c = 0; c < LOWERCASE_NUM; c++)
            collect(x.next[c], pre + (char) (c + 'a'), list);
    }

    public Iterable<String> keysThatMatch(String pattern) {
        LinkedList<String> list = new LinkedList<>();
        collect(root, "", pattern, list);
        return list;
    }

    private void collect(Node x, String pre, String pattern, LinkedList<String> list) {
        int d = pre.length();
        if (x == null)
            return;

        if (d == pattern.length() && x.value != null)
            list.add(pre);

        if (d == pattern.length())
            return;

        int next = pattern.charAt(d) - 'a';
        for (int c = 0; c < LOWERCASE_NUM; c++) {
            if (next == '?'-'a' || next == c)
                collect(x.next[c], pre + (char) (c + 'a'), pattern, list);
        }
    }
}

/**
 * Programmers 가사검색 문제 적용.
 * 더 느려짐.
 */
class TrieImplTest {
    public int[] solution(String[] words, String[] queries) {
        TrieForLyricsSearch trie = new TrieForLyricsSearch();

        // words.length 만큼 반복
        for (String word : words)
            trie.put(word);

        int[] answer = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            answer[i] = trie.NumberOfkeysThatMatch(queries[i]);
        }

        return answer;
    }

    public static void main(String[] args) {
        TrieImplTest test = new TrieImplTest();
        int[] result = test.solution(
                new String[] {
                        "frodo", "front", "frost", "frozen", "frame", "kakao"
                },
                new String[] {
                        "fro??", "????o", "fr???", "fro???", "pro?", "?????"
                }
        );
        int[] expected = new int[] {3, 2, 4, 1, 0};

        System.out.println("Expected : "+ Arrays.toString(expected));
        System.out.println("Actual : "+Arrays.toString(result));
    }
}

/**
 * LyricsSearch
 */
class TrieForLyricsSearch {

    private static int LOWERCASE_NUM = 26;

    private Node root = new Node();

    private static class Node {
        private String value;
        private Node[] next = new Node[LOWERCASE_NUM];
    }

    public void put(String key) {
        root = put(root, key, 0);
    }

    private Node put(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.value = key;
            return x;
        }
        int index = key.charAt(d) - 'a';
        x.next[index] = put(x.next[index], key, d+1);
        return x;
    }

    public int NumberOfkeysThatMatch(String pattern) {
        LinkedList<String> list = new LinkedList<>();
        collect(root, "", pattern, list);
        return list.size();
    }

    private void collect(Node x, String pre, String pattern, LinkedList<String> list) {
        int d = pre.length();
        if (x == null)
            return;

        if (d == pattern.length() && x.value != null)
            list.add(pre);

        if (d == pattern.length())
            return;

        int next = pattern.charAt(d) - 'a';
        for (int c = 0; c < LOWERCASE_NUM; c++) {
            if (next == '?'-'a' || next == c)
                collect(x.next[c], pre + (char) (c + 'a'), pattern, list);
        }
    }
}