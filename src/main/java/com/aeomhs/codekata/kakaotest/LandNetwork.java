package com.aeomhs.codekata.kakaotest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Union - Find , Quick Find 사용
 * Quick Union 으로 시도했지만 지속적으로 실패.
 * 약 2시간 소요
 */
public class LandNetwork {

    /**
     * Land ID
     * 격자 id - 소속된 Land id
     */
    private int[] landId;

    private int landCount;

    private int N;

    private List<Link> priceLinks;

    private List<Link> freeLinks;

    LandNetwork(int[][] land, int height) {
        N = land.length;

        landCount = N * N;
        landId = new int[landCount];
        for (int i = 0; i < landCount; i++)
            landId[i] = i;

        priceLinks = new LinkedList<>();
        freeLinks = new LinkedList<>();
        getAllLinks(land, height);

        for (Link link : freeLinks) {
            link(link.x, link.y);
        }

        for (int i = 0; i < N*N; i++) {
            if (i % N == 0)
                System.out.println();
            System.out.print(String.format("%2d",landId[i])+" ");
        }
        System.out.println();


        System.out.println(freeLinks);
        System.out.println(priceLinks);
    }

    private void getAllLinks(int[][] land, int height) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                boolean edgeOfRight = j == N-1;
                boolean edgeOfBottom = i == N-1;

                if (!edgeOfRight) {
                    int priceToLink = Math.abs(land[i][j] - land[i][j+1]);
                    if (priceToLink <= height) {
                        freeLinks.add(new Link(i * N + j, i * N + (j+1), priceToLink));
                    } else {
                        priceLinks.add(new Link(i * N + j, i * N + (j+1), priceToLink));
                    }
                }
                if (!edgeOfBottom) {
                    int priceToLink = Math.abs(land[i][j] - land[i+1][j]);
                    if (priceToLink <= height) {
                        freeLinks.add(new Link(i * N + j, (i+1) * N + j, priceToLink));
                    } else {
                        priceLinks.add(new Link(i * N + j, (i+1) * N + j, priceToLink));
                    }
                }
            }
        }
    }

    private void link(int x, int y) {
        int landIdX = landId[x];
        int landIdY = landId[y];

        if (landIdX == landIdY)
            return;

        for (int i = 0; i < landId.length; i++) {
            if (landId[i] == landIdY)
                landId[i] = landIdX;
        }

        landCount--;
    }

    public int getMinPriceToAllLink() {
        int price = 0;
        Collections.sort(priceLinks);

        while (landCount != 1) {
            Link link = priceLinks.remove(0);
            // is Land are Linked?
            if (!isLinked(link.x, link.y)) {
                price += link.price;
                link(link.x, link.y);
            }
        }

        return price;
    }

    private boolean isLinked(int x, int y) {
        return getLandId(x) == getLandId(y);
    }

    private int getLandId(int x) {
        return landId[x];
    }

    static class Link implements Comparable<Link> {
        int x;
        int y;
        int price;
        Link(int x, int y, int price) {
            this.x = x;
            this.y = y;
            this.price = price;
        }

        @Override
        public int compareTo(Link o) {
            return Integer.compare(this.price, o.price);
        }

        @Override
        public String toString() {
            return x + " to " + y + " : " + price;
        }
    }
}

class LandNetworkTest {
    int[][] land;
    int height;
    int result, expected;

    public int solution(int[][] land, int height) {
        LandNetwork landNetwork = new LandNetwork(land, height);

        return landNetwork.getMinPriceToAllLink();
    }

    @Test
    public void testCase1() {
        land = new int[][] {
                {1, 4, 8, 10},
                {5, 5, 5, 5},
                {10, 10, 10, 10},
                {10, 10, 10, 20}
        };
        height = 3;
        expected = 15;
        result = solution(land, height);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testCase2() {
        land = new int[][] {
                {10, 11, 10, 11},
                {2, 21, 20, 10},
                {1, 20, 21, 11},
                {2, 1, 2, 1}
        };
        height = 1;
        expected = 18;
        result = solution(land, height);
        Assertions.assertEquals(expected, result);
    }
}