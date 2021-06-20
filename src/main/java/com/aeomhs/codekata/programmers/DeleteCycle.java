package com.aeomhs.codekata.programmers;

import java.util.*;

/**
 * 1. 유니온 파인드를 이용한 cycle check
 *  - 새롭게 추가하는 Edge(v,w)의 w가 이미 같은 tree에 속한다면 cycle 발생!
 * -> 역이용, 노드를 하나씩 제거하면서 cycle 검사를 하는 것보다,
 * -> 1~n 노드 중 i 노드를 제외하고 edge를 추가하면서 cycle이 발생하면 return
 * -> 끝까지 cycle이 생기지 않는다면 count
 * !!!!!@#!@#@!#!@#!!
 */
public class DeleteCycle {
    public static void main(String[] args) {
        DeleteCycle test = new DeleteCycle();
        int result = test.solution(8, new int[][] {
                {1,2},
                {1,3},
                {1,4},
                {3,5},
                {4,5},
                {2,7},
                {5,6},
                {6,7},
                {8,7},
        });
        System.out.println(result);

        result = test.solution(8, new int[][] {
                {1,2},
                {1,3},
                {1,4},
                {3,4},
                {2,4},
                {2,5},
                {3,4},
                {5,4},
        });
        System.out.println(result);

        result = test.solution(8, new int[][] {
                {1,2},
                {2,3},
                {3,4},
                {4,5},
                {5,6},
                {6,7},
                {7,8},
                {8,1},
                {2,7},
                {3,6}
        });
        System.out.println(result);
    }

    public int solution(int n, int[][] edges) {
        int answer = 0;

        // 노드를 하나씩 제외한 상태에서 그래프를 연결한다.
        // i : 5000
        // 5000 개의 노드에 대한 모든 검사를 할 필요가 있을까?
        // i * j = 5000 * 2500만...
        for (int i = 0; i < n; i++) {
            System.out.println("Draw Tree Without " + i);
            UnionFind uf = new UnionFind(n);
            boolean isCycle = false;

            // 제외하는 노드를 포함하지 않는 모든 엣지를 추가한다.
            // j : 1 ~ 5000^2(25 000 000)
            // 비순환 그래프에서 Edge 수는 V-1 이다.
            // V-1 이상이면 순환이 생길 수밖에 없다.
            // 어차피 그 전에 Connect 검사로 발견될테니 개수 비교는 무의미하다.
            for (int[] edge : edges) {
                int v = edge[0] - 1;
                int w = edge[1] - 1;

                System.out.println(v + ", " + w);

                if (v != i && w != i) {
                    // 사이클 탐색
                    // log n
                    if (uf.isConnected(v, w)) {
                        System.out.println("Detected Cycle!");
                        isCycle = true;
                        break;
                    }

                    // 엣지 추가
                    uf.union(v, w);
                }
            }

            if (!isCycle)
                answer += (i+1);
        }

        return answer;
    }
}

class UnionFind implements Cloneable {

    private int N;

    private int[] id;

    private int[] sz;

    UnionFind(int n) {
        N = n;
        id = new int[N];
        sz = new int[N];

        for (int i = 0; i < N; i++){
            id[i] = i;
            sz[i] = 1;
        }
    }

    public boolean isConnected(int v, int w) {
        return find(v) == find(w);
    }

    public int find(int v) {
        int vi = id[v];
        while (vi != id[vi])
            vi = id[vi];

        return vi;
    }

    // Quick Union
    public void union(int v, int w) {
        int iv = find(v);
        int iw = find(w);

        if (iv == iw)
            return;

        // Weight Quick Union
        if (sz[iv] > sz[iw]) {
            id[iw] = id[iv];
            sz[iv] += sz[iw];
        } else {
            id[iv] = id[iw];
            sz[iw] += sz[iv];
        }
    }

    // v가 루트 노드일 경우, v를 부모로 하는 모든 노드의 id를 자기 자신으로 바꾼다.
    // 아니면, v의 부모 노드로 향하게 한다.
    public void delete(int v) {
        int vi = find(v);

        boolean isRoot = v == vi;

        for (int i = 0; i < N; i++) {
            if (id[i] == v) {
                if (isRoot) {
                    id[i] = i;
                }
                else {
                    id[i] = vi;
                }
            }
        }

        sz[vi]--;
    }

    @Override
    public Object clone() {
        UnionFind clone = new UnionFind(N);

        System.arraycopy(id, 0, clone.id, 0, id.length);
        System.arraycopy(sz, 0, clone.sz, 0, sz.length);

        return clone;
    }
}