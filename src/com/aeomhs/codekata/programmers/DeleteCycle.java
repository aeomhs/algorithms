package com.aeomhs.codekata.programmers;

import java.util.*;
import java.util.stream.Collectors;

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
    }

    public int solution(int n, int[][] edges) {
        int answer = 0;

        Graph graph = new Graph(n);

        for (int[] set : edges)
            graph.addEdge((set[0]-1), (set[1]-1));

        // 모든 싸이클이 제거되기 위해서는 최초 싸이클 중 하나의 원소를 하나라도 제거해서 성공해야한다.
        DfsForDeleteCycle cycleDfs = new DfsForDeleteCycle(graph);

        System.out.println(cycleDfs.getCycle());

        for (int i : cycleDfs.getCycle()) {
            System.out.println(i);
            Graph deleteNodeGraph = (Graph) graph.clone();
            deleteNodeGraph.deleteNode(i);
            DfsForDeleteCycle dfs = new DfsForDeleteCycle(deleteNodeGraph);
            if (!dfs.isCycle())
                answer += (i+1);

            int[] edgeTo = dfs.getEdgeTo();
            for (int w = 0; w < n; w++) {
                int v = w;
                System.out.print("To "+(v+1)+" : ");
                while (v != edgeTo[v]){
                    System.out.print((v+1) + ", ");
                    v = edgeTo[v];
                }
                System.out.println();
            }
        }

        return answer;
    }
}

class DfsForDeleteCycle {

    private boolean[] marked;

    private boolean[] cycleCheck;

    private int[] edgeTo;

    private HashMap<Integer, Integer> cycle;

    private boolean isCycle;

    // s로부터 DFS
    DfsForDeleteCycle(Graph graph) {
        this.marked = new boolean[graph.getV()];
        this.cycleCheck = new boolean[graph.getV()];
        this.edgeTo = new int[graph.getV()];
        cycle = new HashMap<>();
        isCycle = false;

        for (int i = 0; i < graph.getV(); i++) {
            if (!marked[i])
                dfs(graph, i);
        }

    }

    private void dfs(Graph graph, int v) {
        marked[v] = true;
        cycleCheck[v] = true;

        // v->w-> ..  -> v : cycle = marked,
        for (int w : graph.getAdj(v)) {
            if (!marked[w]){
                edgeTo[w] = v;
                dfs(graph, w);
            }
            else if (cycleCheck[w]) {
                // v -> w -> v << 양방향 그래프이므로 순환X
                if (edgeTo[v] != w) {
                    isCycle = true;

                    // 사이클마다 지나가는 정점의 회수를 알고, 가장 많이 지나가는 정점을 제거해야한다.
                    for (int x = v; x != w; x = edgeTo[x])
                        cycle.put(x, cycle.getOrDefault(x, 0)+1);
                    cycle.put(w, cycle.getOrDefault(w, 0)+1);
                }
            }
        }

        cycleCheck[v] = false;
    }

    public int[] getEdgeTo() {
        return edgeTo;
    }

    public boolean isCycle() {
        return isCycle;
    }

    public Iterable<Integer> getCycle() {
        // 가장 많이 등장한 사이클 정점

        int maxCounted = cycle.entrySet().stream()
                        .max(Map.Entry.comparingByValue()).get().getValue();

        return cycle.entrySet().stream()
                .filter(integerIntegerEntry -> integerIntegerEntry.getValue() >= maxCounted)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}

class Graph implements Cloneable {

    private int V;

    private int E;

    private Deque<Integer>[] adj;

    Graph(int V) {
        this.V = V;
        this.adj = new Deque[V];
        for (int i = 0; i < V; i++)
            adj[i] = new LinkedList<>();
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    // 그 노드로의 모든 이동을 제한한다.
    public void deleteNode(int v) {
        for (Deque<Integer> adjList : adj) {
            adjList.remove(v);
        }
        adj[v] = new LinkedList<>();
    }

    public int getV() {
        return V;
    }

    public Deque<Integer> getAdj(int v) {
        return adj[v];
    }

    @Override
    public Object clone() {
        Graph clone = new Graph(V);
        clone.E = E;
        clone.adj = new Deque[V];
        for (int i = 0; i < V; i++) {
            clone.adj[i] = new LinkedList<>();
            for (int c : adj[i])
                clone.adj[i].add(c);
        }
        return clone;
    }
}
