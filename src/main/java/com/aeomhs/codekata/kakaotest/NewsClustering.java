package com.aeomhs.codekata.kakaotest;

import java.util.*;

public class NewsClustering {

    public int solution(String str1, String str2) {
        int answer = 0;

        List<String> set1 = buildMultipleSet(str1);
        List<String> set2 = buildMultipleSet(str2);

        System.out.println(set1);
        System.out.println(set2);

        JacquardSet jacquardSet1 = new JacquardSet(set1);
        JacquardSet jacquardSet2 = new JacquardSet(set2);

        Set<String> intersectionSet = JacquardSet.computeIntersectionSet(jacquardSet1, jacquardSet2);
        Set<String> unionSet = JacquardSet.computeUnionSet(jacquardSet1, jacquardSet2);

        int numberOfIntersectionSet = 0;
        for (String subSet : intersectionSet) {
            numberOfIntersectionSet += Math.min(jacquardSet1.numberOfEntry(subSet), jacquardSet2.numberOfEntry(subSet));
        }

        int numberOfUnionSet = 0;
        for (String subSet : unionSet) {
            numberOfUnionSet += Math.max(jacquardSet1.numberOfEntry(subSet), jacquardSet2.numberOfEntry(subSet));
        }

        if (numberOfUnionSet == 0 || numberOfIntersectionSet == 0)
            return 65536;

        double jacquard = (double) numberOfIntersectionSet / (double) numberOfUnionSet * 65536;

        return (int) jacquard;
    }

    public List<String> buildMultipleSet(String str) {
        List<String> set = new LinkedList<>();

        StringBuilder builder = new StringBuilder(str.toLowerCase());
        for (int i = 0; i < builder.length() - 1; i++) {
            if (builder.charAt(i) >= 'a' && builder.charAt(i) <= 'z' &&
                builder.charAt(i+1) >= 'a' && builder.charAt(i+1) <= 'z')
                set.add(builder.substring(i, i+2));
        }
        return set;
    }

    public static void main(String[] args) {
        NewsClustering test = new NewsClustering();
//        int result = test.solution("aa1+aa2", "AAAA12");
//        int result = test.solution("FRANCE", "FRENCH");
        int result = test.solution("handshake", "shake hands");
        int expected = 16384;
        System.out.println("Expected : " + expected);
        System.out.println("Actual : " + result);
    }
}

class JacquardSet {
    private List<String> multipleSet;

    private Map<String, Integer> entryMap;

    JacquardSet(List<String> multipleSet) {
        this.multipleSet = multipleSet;
        entryMap = new HashMap<>();
        for (String subSet : multipleSet) {
            entryMap.put(subSet, entryMap.getOrDefault(subSet, 0) + 1);
        }
    }

    public int numberOfEntry(String entry) {
        return entryMap.getOrDefault(entry, 0);
    }

    public static Set<String> computeIntersectionSet(JacquardSet set1, JacquardSet set2) {
        Set<String> intersectionSet = new HashSet<>();

        for (String key : set1.entryMap.keySet()) {
            if (set2.entryMap.containsKey(key))
                intersectionSet.add(key);
        }

        return intersectionSet;
    }

    public static Set<String> computeUnionSet(JacquardSet set1, JacquardSet set2) {
        Set<String> unionSet = new HashSet<>();

        unionSet.addAll(set1.entryMap.keySet());
        unionSet.addAll(set2.entryMap.keySet());

        return unionSet;
    }
}