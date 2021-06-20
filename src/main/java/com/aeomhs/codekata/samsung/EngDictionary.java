package com.aeomhs.codekata.samsung;

import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

class Solution {

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);

        EngDictionary dictManager = new EngDictionary();

        for (int TestCase = in.nextInt(), tc = 1; tc <= TestCase; tc = tc + 1) {

            int Query_N = in.nextInt();

            out.print("#" + tc);

            dictManager.init();

            for (int i = 1; i <= Query_N; i++) {
                int type = in.nextInt();

                if (type == 1) {
                    String buf = in.next();
                    dictManager.insert(buf.length(), buf);
                }
                else {
                    String buf = in.next();
                    int answer = dictManager.query(buf.length(), buf);
                    out.print(" " + answer);
                }
            }
            out.println("");
        }
        out.close();
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
}

public class EngDictionary {
    private Trie trie;

    public void init() {
        trie = new Trie();
    }

    public void insert(int buffer_size, String buf) {
        trie.insert(buf);
    }

    public int query(int buffer_size, String buf) {
        try {
            return trie.startOf(buf);
        } catch (NullPointerException notFound) {
            return 0;
        }
    }
}

class Trie {

    private final static int R = 26;

    private final Node root;

    public Trie() {
        this.root = new Node('\n');
    }

    public void insert(String word) {
        insert(root, 0, word);
    }

    private void insert(Node node, int i, String word) {
        if (i == word.length()) {
            node.value = word;
            return;
        }

        int index = (word.charAt(i) - 'a') % R;
        if (node.childs[index] == null) {
            node.childs[index] = new Node(word.charAt(i));
        } else {
            node.childs[index].countOfStart++;
        }

        insert(node.childs[index], i+1, word);
    }

    public int startOf(String subString) throws NullPointerException {

        return find(root, 0, subString).countOfStart;
    }

    public Node find(Node node, int i, String subString) {
        if (i == subString.length()) {
            return node;
        }

        int index = (subString.charAt(i) - 'a') % R;

        if (node.childs[index] == null)
            return null;

        return find(node.childs[index], i+1, subString);
    }

    static class Node {

        Node[] childs;

        char c;

        String value;

        int countOfStart;

        Node(char c) {
            this.c = c;
            this.value = "";
            this.childs = new Node[R];
            this.countOfStart = 1;
        }
    }
}

