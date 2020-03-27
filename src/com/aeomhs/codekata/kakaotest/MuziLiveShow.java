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
    }

    public int solution(int[] food_times, long k) {
        PriorityQueue<FoodItem> foods = new PriorityQueue<>();

        for (int i = 0; i < food_times.length; i++) {
            foods.offer(new FoodItem(i+1, food_times[i]));
        }

        while (!foods.isEmpty()) {
            int cycle = foods.size();
            FoodItem min = foods.poll();

            if (k > cycle * min.foods) {
                foods.removeIf(foodItem -> foodItem.eat(min.foods));
                k -= cycle * min.foods;
            }
            else {
                foods.offer(min);
                break;
            }
        }

        if (foods.size() == 1) {
            if (foods.peek().foods <= k)
                return -1;
            else
                return foods.peek().id;
        }

//        while (k > foods.size()) {
//            foods.removeIf(foodItem -> foodItem.eat(1));
//            k -= foods.size();
//        }

        int cycle = (int) (k / foods.size()); // k == 10, size == 4, [4, 4, 4, 4] -> 2 cycle
        foods.removeIf(foodItem -> foodItem.eat(cycle));
        k -= cycle * foods.size();    // k == 2

        List<FoodItem> foodItemList = new ArrayList<>(foods);
        foodItemList.sort(Comparator.comparingInt((FoodItem food) -> food.id));

        System.out.println(foods);
        System.out.println(foodItemList);
        return foodItemList.get((int) (k%foods.size())).id;
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
