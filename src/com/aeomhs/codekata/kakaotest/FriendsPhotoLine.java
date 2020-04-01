package com.aeomhs.codekata.kakaotest;

import java.util.HashMap;
import java.util.Map;

public class FriendsPhotoLine {

    public static void main(String[] args) {
        FriendsPhotoLine test = new FriendsPhotoLine();
        int n = 2;
        String[] data = new String[] {
                "N~F=0", "R~T>2"
        };
        int expected = 3648;
        int result = test.solution(n, data);
        System.out.println(expected+","+result);
    }

    private Condition[] conditions;

    private int count;

    public int solution(int n, String[] data) {
        int[] pos = new int[KakaoFriends.getSize()];

        conditions = new Condition[n];
        for (int i = 0; i < n; i++)
            conditions[i] = new Condition(data[i]);

        for (int i = 0; i < pos.length; i++) {
            pos[i] = i+1;
        }

        count = 0;
        perm(pos, pos.length, 0, pos.length);

        return count;
    }

    // n 개 중에 r개를 뽑는다.
    private void perm(int[] arr, int n, int index, int r) {
        if (index == r) {
            if (satisfyAllCondition(arr))
                count++;
        }

        for (int i = index; i < n; i++) {
            swap(arr, i, index);
            perm(arr, n, index+1, r);
            swap(arr, i, index);
        }
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private boolean satisfyAllCondition(int[] pos) {
        for (Condition condition : conditions) {
            if (!condition.satisfyCondition(pos[condition.f1.getValue()], pos[condition.f2.getValue()]))
                return false;
        }
        return true;
    }

    static class Condition {
        KakaoFriends f1, f2;

        char relation;

        int dist;

        Condition(KakaoFriends f1, KakaoFriends f2, char relation, int dist) {
            this.f1 = f1;
            this.f2 = f2;
            this.relation = relation;
            this.dist = dist;
        }

        Condition(String condition) {
            this(KakaoFriends.valueOf(condition.charAt(0)),
                 KakaoFriends.valueOf(condition.charAt(2)),
                 condition.charAt(3),
                 condition.charAt(4)-'0');
        }

        boolean satisfyCondition(int f1pos, int f2pos) {
            if (relation == '=') {
                return Math.abs(f1pos - f2pos) - 1 == dist;
            } else if (relation == '<') {
                return Math.abs(f1pos - f2pos) - 1 < dist;
            } else if (relation == '>') {
                return Math.abs(f1pos - f2pos) - 1 > dist;
            } else
                throw new IllegalArgumentException("Invalid Relationship");
        }

        @Override
        public String toString() {
            return "Condition{" +
                    "f1=" + f1.getValue() +
                    ", f2=" + f2.getValue() +
                    ", relation=" + relation +
                    ", dist=" + dist +
                    '}';
        }
    }
}


enum KakaoFriends {

    A(0), C(1), F(2), J(3), M(4), N(5), R(6), T(7);

    private final int value;

    private static Map<Integer, KakaoFriends> map = new HashMap<>();

    private static final int size = 8;

    static {
        for (KakaoFriends kakaoFriends : KakaoFriends.values()) {
            map.put(kakaoFriends.value, kakaoFriends);
        }
    }

    KakaoFriends(int value) {
        this.value = value;
    }

    /* https://codingexplained.com/coding/java/enum-to-integer-and-integer-to-enum */
    public static KakaoFriends valueOf(char c) {
        int value = KakaoFriends.valueOf(String.valueOf(c)).value;
        return map.get(value);
    }

    public int getValue() {
        return value;
    }

    public static int getSize() {
        return size;
    }
}
