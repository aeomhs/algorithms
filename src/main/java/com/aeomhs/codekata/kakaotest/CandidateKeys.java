package com.aeomhs.codekata.kakaotest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class KeyResolverTest {

    public int solution(String[][] relation) {
        KeyResolver keyResolver = new KeyResolver(relation);
        return keyResolver.getCandidateKeys().size();
    }

    @Test
    public void solutionTest() {
        KeyResolverTest solution = new KeyResolverTest();
        System.out.println("=====Test Case =====");
        Assertions.assertEquals(2, solution.solution(
                new String[][] {
                        {"100","ryan","music","2"},
                        {"200","apeach","math","2"},
                        {"300","tube","computer","3"},
                        {"400","con","computer","4"},
                        {"500","muzi","music","3"},
                        {"600","apeach","music","2"}
                }
        ));
        System.out.println("=====Test Case =====");
        Assertions.assertEquals(3, solution.solution(
                new String[][] {
                        {"a","aa","aaa","1"},
                        {"b","bb","bbb","2"},
                        {"c","cc","ccc","3"},
                        {"a","cc","ddd","4"},
                        {"b","aa","eee","5"},
                        {"c","bb","fff","6"}
                }
        ));
        System.out.println("=====Test Case =====");
        Assertions.assertEquals(1, solution.solution(
                new String[][] {
                        {"a"},
                        {"b"},
                        {"c"},
                        {"aa"},
                        {"aaa"},
                }
        ));
        System.out.println("=====Test Case =====");
        Assertions.assertEquals(2, solution.solution(
                new String[][] {
                        {"a", "b", "ab"},
                        {"b", "c", "bc"},
                        {"a", "c", "ac"},
                }
        ));
        System.out.println("=====Test Case =====");
        Assertions.assertEquals(2, solution.solution(
                new String[][] {
                        {"a", "b", "ab"},
                        {"b", "c", "bc"},
                        {"a", "c", "ac"},
                }
        ));
        System.out.println("=====Test Case =====");
        Assertions.assertEquals(0, solution.solution(
                new String[][] {
                        {"xa", "b", "1"},
                        {"bx", "c", "1"},
                        {"cx", "c", "1"},
                        {"bx", "b", "1"},
                        {"c", "xc", "1"},
                }
        ));
        System.out.println("=====Test Case =====");
        Assertions.assertEquals(3, solution.solution(
                new String[][] {
                        {"a", "bbc", "5", "4"},
                        {"b", "aab", "4", "4"},
                        {"c", "bab", "3", "4"},
                        {"a", "baa", "2", "1"},
                        {"b", "bbb", "1", "1"},
                }
        ));
        System.out.println("=====Test Case =====");
        Assertions.assertEquals(4, solution.solution(
                new String[][] {
                        {"1", "0", "0", "0"},
                        {"0", "2", "0", "0"},
                        {"0", "0", "3", "0"},
                        {"0", "0", "0", "4"},
                }
        ));
    }
}

class KeyResolver {

    // (field == Attr)
    private Attr[] fields;

    private List<Key> keys;

    private List<SuperKey> superKeys;

    private List<CandidateKey> candidateKeys;

    KeyResolver(String[][] relation) {
        fields = new Attr[relation[0].length];

        for (int j = 0; j < relation[0].length ; j++) {
            String[] column = new String[relation.length];
            for (int i = 0; i < relation.length; i++) {
                column[i] = relation[i][j];
            }
            fields[j] = new Attr(column);
        }

        generateKeys();
        System.out.println("keys");
        System.out.println(keys);
        generateSuperKeys();
        System.out.println("superKeys");
        System.out.println(superKeys);
        generateCandidateKeys();
        System.out.println("candidateKeys");
        System.out.println(candidateKeys);
    }

    /**
     * building All of case can be key
     */
    private void generateKeys() {
        if (fields == null || fields.length == 0)
            return;

        keys = new LinkedList<>();
        List<Attr> temp = new LinkedList<>();
        for (int i = 1; i <= fields.length; i++) {
            comb(keys, temp, fields, 0, i, 0);
        }
    }

    /**
     * n개 중 r개를 뽑는다.
     */
    private void comb(List<Key> keys, List<Attr> temp, Attr[] fields, int index, int n, int r) {
        // Done
        if (n == r) {
            List<Attr> newAttrList = new LinkedList<>(temp);
            keys.add(new Key(newAttrList));
            return;
        }

        // Failed
        if (index > fields.length - 1)
            return;

        comb(keys, temp, fields, index+1, n, r);
        temp.add(fields[index]);
        comb(keys, temp, fields, index+1, n, r+1);
        temp.remove(fields[index]);
    }

    /**
     * 존재하는 모든 Key에 대해 super key 리스트를 생성한다.
     */
    private void generateSuperKeys() {
        if (keys == null)
            return;

        superKeys = new LinkedList<>();

        for (Key key : keys) {
            if (isSuperKey(key))
                superKeys.add(new SuperKey(key.attrs));
        }
    }

    private boolean isSuperKey(Key key) {
        HashSet<String> records = new HashSet<>();
        for (String record : key.records) {
            if (records.contains(record)) {
                return false;
            }
            records.add(record);
        }

        return true;
    }

    /**
     * 존재하는 모든 super key에 대해 candidate key 리스트를 생성한다.
     */
    private void generateCandidateKeys() {
        if (superKeys == null)
            return;

        candidateKeys = new LinkedList<>();

        for (SuperKey superKey : superKeys) {
            if (isCandidateKey(superKey))
                candidateKeys.add(new CandidateKey(superKey.attrs));
        }
    }


    private boolean isCandidateKey(SuperKey superKey) {
        List<Attr> temp = new LinkedList<>();

        Attr[] fields = new Attr[superKey.attrs.size()];
        for (int i = 0; i < fields.length; i++)
            fields[i] = superKey.attrs.get(i).clone();

        for (int i = 1; i < fields.length; i++) {
            List<Key> subKeys = new LinkedList<>();
            comb(subKeys, temp, fields, 0, i, 0);
            for (Key subKey : subKeys) {
                for (SuperKey existKey : superKeys){
                    if (existKey.equals(subKey)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public Attr[] getFields() {
        return fields;
    }

    public List<Key> getKeys() {
        return keys;
    }

    public List<SuperKey> getSuperKeys() {
        return superKeys;
    }

    public List<CandidateKey> getCandidateKeys() {
        return candidateKeys;
    }

    /**
     * Column
     */
    static class Attr {

        static int attr_count = 0b1;

        final int id;

        final String[] records;

        Attr(String[] records) {
            this.id = attr_count;
            attr_count = attr_count << 1;
            this.records = records;
        }

        private Attr(int id, String[] records) {
            this.id = id;
            this.records = records;
        }

        public boolean equals(Attr other) {
            return this.id == other.id;
        }

        public String toString() {
            return "["+ id +"]" + Arrays.toString(records);
        }

        public Attr clone() {
            return new Attr(this.id, this.records);
        }
    }

    /**
     * Mapped Attrs
     * (= Columns)
     */
    static class Key {

        final int attrBitMask;

        final List<Attr> attrs;

        final String[] records;

        Key(List<Attr> attrs) {
            this.attrs = attrs;

            if (attrs.size() == 0) {
                throw new IllegalArgumentException("Cannot Generate Key : attr list's size = 0");
            }
            else if (attrs.size() == 1){
                this.records = attrs.get(0).records;
                this.attrBitMask = attrs.get(0).id;
            }
            else {
                StringBuilder[] temps = new StringBuilder[attrs.get(0).records.length];
                int bitMask = 0b0;
                for (Attr attr : attrs) {
                    for (int i = 0; i < attr.records.length; i++) {
                        if (temps[i] == null)
                            temps[i] = new StringBuilder();
                        temps[i].append(attr.records[i]);
                    }
                    bitMask |= attr.id;
                }

                this.records = new String[temps.length];
                for (int i = 0; i < temps.length; i++) {
                    this.records[i] = temps[i].toString();
                }
                this.attrBitMask = bitMask;
            }
        }

        /**
         * 같은 attr의 조합이면 같은 key이다.
         */
        public boolean equals(Key other) {
            return (this.attrBitMask ^ other.attrBitMask) == 0;
        }

        public String toString() {
            return "[" + Integer.toBinaryString(attrBitMask) + "]" + Arrays.toString(records);
        }
    }

    static class SuperKey extends Key {

        SuperKey(List<Attr> attrs) {
            super(attrs);
        }
    }

    static class CandidateKey extends SuperKey {

        CandidateKey(List<Attr> attrs) {
            super(attrs);
        }
    }
}