package com.aeomhs.codekata.kakaotest;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


// TODO Not Solved
public class LyricsSearch {

    public int[] solution(String[] words, String[] queries) {

        Trie trie = new Trie();

        // words.length 만큼 반복
        for (String word : words)
            trie.addWord(word);

        int[] answer = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            answer[i] = trie.countWordWithQueries(queries[i]);
        }

        return answer;
    }

    public static void main(String[] args) {
        LyricsSearch test = new LyricsSearch();
        int[] result = test.solution(
                new String[] {
                        "frodo", "front", "frost", "frozen", "frame", "kakao"
                },
                new String[] {
                        "fro??", "????o", "fr???", "fro???", "pro?", "?????"
                }
        );
        int[] expected = new int[] {3, 2, 4, 1, 0};

        System.out.println("Expected : "+Arrays.toString(expected));
        System.out.println("Actual : "+Arrays.toString(result));
    }
}

class Trie {

    private final Node root;

    private List<Node> leafs;

    Trie() {
        this.root = new Node(null, null, '\n');
        this.leafs = new LinkedList<>();
    }

    static class Node {

        private final Node parent;

        private List<Node> childs;

        private String word;

        private char value;

        Node(Node parent, String word, char value) {
            this.parent = parent;
            this.word = word;
            this.value = value;
            this.childs = new LinkedList<>();
        }

        boolean isLeaf() {
            return this.childs.size() == 0;
        }

        int findValueInChild(char value) {
            int index = 0;
            for (Node child : this.childs) {
                if (child.value == value)
                    return index;
                index++;
            }
            return -1;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("{");
            for (Node child : this.childs)
                stringBuilder.append(child.toString());
            stringBuilder.append("}");

            return this.value + stringBuilder.toString();
        }
    }

    public boolean findWord(String word) {
        Node temp = root;

        for (int i = 0; i < word.length(); i++) {
            if (temp.isLeaf() || temp.findValueInChild(word.charAt(i)) < 0)
                return false;
            temp = temp.childs.get(temp.findValueInChild(word.charAt(i)));
        }

        return word.equals(temp.word);
    }

    public int countWordWithQueries(String queries) {
        if (queries.charAt(0) == '?'){
            StringBuilder builder = new StringBuilder(queries);
            queries = builder.reverse().toString();
            int count = 0;
            for (Node leaf : this.leafs) {
                if (leaf.word.length() == queries.length())
                    count += matchesFromLeaf(leaf, queries) ? 1 : 0;
            }
            return count;
        }
        else {
            int index = this.root.findValueInChild(queries.charAt(0));
            if (index < 0)
                return 0;
            return countWordWithQueries(this.root.childs.get(index), queries, 0);
        }
    }

    private boolean matchesFromLeaf(Node leaf, String queries) {
        Node temp = leaf;
        int index = 0;

        while (temp.parent != null) {
            if (queries.charAt(index) == '?')
                return true;
            if (temp.value != queries.charAt(index))
                return false;

            temp = temp.parent;
            index++;
        }

        return queries.length() == index;
    }

    private int countWordWithQueries(Node node, String queries, int index) {
        char query = queries.charAt(index);

        // 쿼리 검사가 끝나는 시점
        if (queries.length() - 1 == index){
            // 길이 일치하는 문자 없음
            if (node.word == null)
                return 0;
            // 길이 일치하는 문자 존재
            if (query == '?' || query == node.value)
                return 1;
            return 0;
        }

        // 쿼리문자열 길이와 다른 경우
        if (node.isLeaf())
            return 0;

        // 와일드 카드가 아니면서 문자가 다른 경우
        if (query != '?' && query != node.value)
            return 0;

        // 와일드 카드 시작, 이때부터는 길이만 검사하면 된다.
        if (query == '?') {
            return countChildsGroupByDepth(node, queries.length() - index - 1);
        }

        // 문자가 같은 경우 계속 검사한다.
        int count = 0;
        for (Node child : node.childs) {
            count += countWordWithQueries(child, queries, index + 1);
        }
        return count;
    }

    private int countChildsGroupByDepth(Node node, int depth) {
        if (depth == 0 && node.word != null)
            return 1;
        if (depth < 0 || node.isLeaf())
            return 0;

        int count = 0;
        for (Node child : node.childs) {
            count += countChildsGroupByDepth(child, depth - 1);
        }
        return count;
    }

    public void addWord(String word) {

        Node temp = root;

        for (int i = 0; i < word.length(); i++) {
            int index = temp.findValueInChild(word.charAt(i));
            if (index < 0) {
                Node childs = createWordEntries(temp, word, word.substring(i));
                temp.childs.add(childs);
                return;
            }
            temp = temp.childs.get(index);
        }

        temp.word = word;
        leafs.add(temp);
    }

    private Node createWordEntries(Node subRoot, String word, String subWord) {
        int len = subWord.length();
        Node topNode = new Node(subRoot,null, subWord.charAt(0));

        Node temp = topNode;
        for (int i = 1; i < len ; i++) {
            Node child = new Node(temp,null, subWord.charAt(i));
            temp.childs.add(child);
            temp = child;
        }
        temp.word = word;
        leafs.add(temp);

        return topNode;
    }

    @Override
    public String toString() {
        return this.root.toString() + "\n";
    }
}
