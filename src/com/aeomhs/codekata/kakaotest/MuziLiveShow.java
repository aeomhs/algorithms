package com.aeomhs.codekata.kakaotest;

import java.util.*;

public class MuziLiveShow {
    public static void main(String[] args) {
        MuziLiveShow test = new MuziLiveShow();
        int[] food_times = new int[] {
                3,1,2
        };
        long k = 5;
        int expected = 1;
        int result = test.solution(food_times, k);
        System.out.println(expected + " ? " + result);
        assert expected == result;

        food_times = new int[] {
                5, 1, 3, 1, 2
        };
        k = 7;
        expected = 5;
        result = test.solution(food_times, k);
        System.out.println(expected + " ? " + result);
        assert expected == result;

        food_times = new int[] {
                3,3,3,2
        };
        k = 7;
        expected = 4;
        result = test.solution(food_times, k);
        System.out.println(expected + " ? " + result);
        assert expected == result;

        food_times = new int[] {
                1
        };
        k = 1;
        expected = -1;
        result = test.solution(food_times, k);
        System.out.println(expected + " ? " + result);
        assert expected == result;

        food_times = new int[] {
                2
        };
        k = 1;
        expected = 1;
        result = test.solution(food_times, k);
        System.out.println(expected + " ? " + result);
        assert expected == result;

        food_times = new int[] {
                1, 1, 1, 1
        };
        k = 4;
        expected = -1;
        result = test.solution(food_times, k);
        System.out.println(expected + " ? " + result);
        assert expected == result;

        food_times = new int[] {
                1, 1, 1, 1
        };
        k = 3;
        expected = 4;
        result = test.solution(food_times, k);
        System.out.println(expected + " ? " + result);
        assert expected == result;

        food_times = new int[] {
                1, 1, 1, 2
        };
        k = 4;
        expected = 4;
        result = test.solution(food_times, k);
        System.out.println(expected + " ? " + result);
        assert expected == result;

        food_times = new int[] {
                5, 1, 1, 2
        };
        k = 7;
        expected = 1;
        result = test.solution(food_times, k);
        System.out.println(expected + " ? " + result);
        assert expected == result;
    }

    public int solution(int[] food_times, long k) {
        int N = food_times.length;
        FoodItem[] foods = new FoodItem[N];

        for (int i = 0; i < N; i++) {
            foods[i] = new FoodItem(i+1, food_times[i]);
        }

        Arrays.sort(foods);

        long cycle = 0;
        for (int i = 0; i < N; i++) {
            long T = N - i;
            long min = foods[i].foods;
            if (k >= T * (min-cycle)) {
                for (; i+1 < N && foods[i].foods == foods[i+1].foods; i++);
                k -= T * (min-cycle);
                cycle = min;
            }
            else {
                FoodItem[] rest = new FoodItem[(int) T];
                System.arraycopy(foods, i, rest, 0, (int) T);
                Arrays.sort(rest, Comparator.comparingInt(o1->o1.id));

                return rest[(int) (k%T)].id;
            }
        }
        return -1;
    }

    public int solution2(int[] food_times, long k) {
        PriorityQueue<FoodItem> foods = new PriorityQueue<>(food_times.length);
        ArrayList<FoodItem> foodIds = new ArrayList<>(food_times.length);

        for (int i = 0; i < food_times.length; i++) {
            FoodItem foodItem = new FoodItem(i+1, food_times[i]);
            foods.add(foodItem);
            foodIds.add(foodItem);
        }

        int T = foods.size();// 1 바퀴 도는데 필요한 시간

        while (T > 0) {
            FoodItem food = foods.poll();
            int min = food.foods;
            if (k >= (long)T * (long)min) {
                foodIds.removeIf(foodItem -> foodItem.eat(min));
                k -= ((long)T * (long)min);

                T = foods.size();
            } else {
                break;
            }
        }

        if (foodIds.size() == 0)
            return -1;

        return foodIds.get((int) (k % foodIds.size())).id;

    }

    static class FoodItem implements Comparable<FoodItem> {
        int id;
        int foods;

        /**
         * @param id 음식 아이디
         * @param foods 음식량
         */
        FoodItem(int id, int foods) {
            assert id > 0 && foods > 0;
            this.id = id;
            this.foods = foods;
        }

        public boolean eat(int foods) {
            assert this.foods >= foods;
            this.foods -= foods;

            return this.foods == 0;
        }

        @Override
        public int compareTo(FoodItem o) {
            return Integer.compare(this.foods, o.foods);
        }

        @Override
        public String toString() {
            return "["+id+"]"+foods+"";
        }
    }
}
