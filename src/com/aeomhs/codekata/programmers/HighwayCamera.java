package com.aeomhs.codekata.programmers;

import java.util.Arrays;

public class HighwayCamera {
    public static void main(String[] args) {
        HighwayCamera test = new HighwayCamera();
        int[][] routes = new int[][] {
            {-20,15},
            {-14,-5},
            {-18,-13},
            {-5,-3}
        };
        int result = test.solution(routes);
        System.out.println(result);

        routes = new int[][] {
                {0,0},
                {0,0},
                {-1,1},
                {-2,2}
        };
        result = test.solution(routes);
        System.out.println(result);

        routes = new int[][] {
                {0,1},
                {1,2},
                {2,3},
                {3,4}
        };
        result = test.solution(routes);
        System.out.println(result);

        routes = new int[][] {
                {0,10}, // 0, 10
                {-5,5}, // 0, 5
                {6,15}, // 6, 15
                {5,6},  // -2, -1
                {-2, -1}
        };
        result = test.solution(routes);
        System.out.println(result);
    }

    public int solution(int[][] routes) {
        Arrays.sort(routes, (o1, o2) -> {
            if (o1[0] < o2[0])
                return -1;
            else if (o1[0] > o2[0])
                return 1;
            return Integer.compare(Math.abs(o1[0]-o1[1]), Math.abs(o2[0]-o2[1]));
        });

        Camera installed = new Camera(routes[0][1]);
        int count = 1;
        for (int[] route : routes) {
            int in = route[0];
            int out = route[1];

            if (!installed.include(in, out)){
                installed.x = out;
                count++;
            }
        }

        return count;
    }

    static class Camera {
        int x;

        Camera(int x) {
            this.x = x;
        }

        public boolean include(int p, int q) {
            if (p <= x) {
                if (q < x) {
                    this.x = q;
                }
                return true;
            } else {
                return false;
            }
        }
    }
}
