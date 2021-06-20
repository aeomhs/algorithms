package com.aeomhs.codekata.kakaotest;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NTuple {

    public static void main(String[] args) {
        NTuple test = new NTuple();

        String s = "{{2},{2,1},{2,1,3},{2,1,3,4}}";
        int[] expected = new int[] {
                2, 1, 3, 4
        };
        int[] result = test.solution(s);
        System.out.println(Arrays.toString(expected) + " , " + Arrays.toString(result));
        assert Arrays.equals(expected, result);


        s = "{{1,2,3},{2,1},{1,2,4,3},{2}}";
        expected = new int[] {
                2, 1, 3, 4
        };
        result = test.solution(s);
        System.out.println(Arrays.toString(expected) + " , " + Arrays.toString(result));
        assert Arrays.equals(expected, result);

        s = "{{20,111},{111}}";
        expected = new int[] {
                111, 20
        };
        result = test.solution(s);
        System.out.println(Arrays.toString(expected) + " , " + Arrays.toString(result));
        assert Arrays.equals(expected, result);
    }

    public int[] solution(String s) {

        List<SubSet> subSets = parseToSubSets(s);
        Map<Integer, Integer> hashMap = new LinkedHashMap<>();

        for (SubSet subSet : subSets) {
            for (int attr : subSet.attrs) {
                hashMap.put(attr, hashMap.getOrDefault(attr, 0) + 1);
            }
        }

        int[] answer = new int[subSets.size()];

        for (Map.Entry<Integer, Integer> entries : hashMap.entrySet()) {
            // {1}, {1,2}, {1,2,3} -> 1 : 3, 2 : 2, 3 : 1 -> [size-count] = attr
            answer[subSets.size()-entries.getValue()] = entries.getKey();
        }
        return answer;
    }

    private List<SubSet> parseToSubSets(String s) {
        String excludeLastBrace = s.substring(1, s.length()-1);
        String subsetRegex = "\\{[0-9,]+}";
        Matcher finder = Pattern.compile(subsetRegex).matcher(excludeLastBrace);

        List<SubSet> subSets = new ArrayList<>();
        while (finder.find()) {
            int start = finder.start()+1;
            int end = finder.end()-1;
            System.out.println("SubSet : " + excludeLastBrace.substring(start, end));
            String[] attrs = excludeLastBrace.substring(start, end).split(",");

            subSets.add(new SubSet(attrs));
        }

        return subSets;
    }

    static class SubSet {
        List<Integer> attrs;
        SubSet(String[] attrs) {
            this.attrs = new LinkedList<>();
            for (String attr : attrs) {
                this.attrs.add(Integer.parseInt(attr));
            }
        }
    }
}
