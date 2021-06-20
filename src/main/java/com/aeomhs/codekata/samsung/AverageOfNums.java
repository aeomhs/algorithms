package com.aeomhs.codekata.samsung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

@FunctionalInterface
interface AverageFunction {
    int averageOf(int[] nums);
}

@FunctionalInterface
interface ConvertInteger {
    int[] convert(String[] nums);
}

public class AverageOfNums {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int T = Integer.parseInt(br.readLine());
            int[] answers = new int[T];

            AverageFunction function = nums -> (int) Math.round((double) Arrays.stream(nums).sum()/ (double) nums.length);
            ConvertInteger converter = nums -> Arrays.stream(nums).mapToInt(Integer::parseInt).toArray();

            for (int t = 1; t <= T; t++) {
                String[] inputs = br.readLine().split(" ");
                answers[t-1] = function.averageOf(converter.convert(inputs));
            }

//            System.out.println(Arrays.toString(answers));

            for (int t = 1; t <= T; t++) {
                System.out.println("#"+t+" "+answers[t-1]);
            }

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
}
