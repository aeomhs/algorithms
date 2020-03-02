package com.aeomhs.util;

import java.util.HashMap;

public class SymbolGraph {

    private HashMap<String, Integer> symbolTable;   // 문자열 -> 인덱스
    private String[] keys;                          // 인덱스 -> 문자열
    private Graph graph;                            // 그래프 객체

    public SymbolGraph(String[] stream) {
        symbolTable = new HashMap<>();

        for (String line : stream) {
            String[] linkedEntries = line.split(" ");
            for (String entry : linkedEntries) {
                if (!symbolTable.containsKey(entry))
                    symbolTable.put(entry, symbolTable.size());
            }
        }

        keys = new String[symbolTable.size()];
        for (String key : symbolTable.keySet())
            keys[symbolTable.get(key)] = key;

        graph = new Graph(symbolTable.size());
        for (String line : stream) {
            String[] linkedEntries = line.split(" ");
            int v = symbolTable.get(linkedEntries[0]);
            for (int i = 1; i < linkedEntries.length; i++)
                graph.addEdge(v, symbolTable.get(linkedEntries[i]));
        }
    }

    public boolean contains(String key) {
        return symbolTable.containsKey(key);
    }

    public int index(String key) {
        return symbolTable.get(key);
    }

    public String name(int v) {
        return keys[v];
    }

    public Graph getGraph() {
        return graph;
    }
}

class DegreesOfSeparation {
    public static void main(String[] args) {
        SymbolGraph symbolGraph = new SymbolGraph(new String[] {
                "Movie1 actor1 actor2 actor3 actor4 actor5 actor6",
                "Movie2 actor1 actor10 actor11 actor12 actor13 actor14",
                "Movie3 actor2 actor4 actor10 actor20 actor21 actor22",
                "Movie4 actor3 actor4 actor21 actor7 actor8 actor9",
        });

        Graph graph = symbolGraph.getGraph();


        // actor1로부터 얼마나 떨어져있는지 확인한다.
        String actor = "actor1";
        String[] actors = new String[] {
                "actor10", "actor4", "actor7", "actor14"
        };
        if (!symbolGraph.contains(actor))
            return;

        int symbol = symbolGraph.index(actor);
        BreadthFirstPaths breadthFirstPaths = new BreadthFirstPaths(graph, symbol);

        for (String target : actors) {
            if (symbolGraph.contains(target)) {
                int targetIndex = symbolGraph.index(target);
                if (breadthFirstPaths.hasPathTo(targetIndex)) {
                    for (int v : breadthFirstPaths.pathTo(targetIndex)) {
                        System.out.print(" " + symbolGraph.name(v));
                    }
                    System.out.println();
                }
                else {
                    System.out.println("No Connected");
                }
            }
            else {
                System.out.println("No Data");
            }
        }

    }
}