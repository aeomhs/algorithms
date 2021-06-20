package com.aeomhs.codekata.beakjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class P7568 {
    public static void main(String[] args) throws IOException {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int N = Integer.parseInt(br.readLine());
            Entry[] entries = new Entry[N];
            for (int i = 0; i < N; i++) {
                String[] row = br.readLine().split(" ");
                entries[i] = new Entry(Integer.parseInt(row[0]), Integer.parseInt(row[1]));
                entries[i].rank = 1;
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (i == j)
                        continue;

                    if (entries[j].biggerThan(entries[i]))
                        entries[i].rank++;
                }
            }

            for (Entry entry : entries)
                System.out.print(entry.rank + " ");

        }
    }

    static class Entry {
        private int weight;
        private int height;
        private int rank;

        Entry(int weight, int height) {
            this.weight = weight;
            this.height = height;
        }

        public boolean biggerThan(Entry o) {
            return this.weight > o.weight && this.height > o.height;
        }
    }
}
