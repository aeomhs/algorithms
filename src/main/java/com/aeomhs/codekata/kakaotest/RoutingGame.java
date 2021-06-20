package com.aeomhs.codekata.kakaotest;

import java.util.*;

public class RoutingGame {
    public static void main(String[] args) {
        RoutingGame test = new RoutingGame();
        int[][] nodeinfo = new int[][] {
                {5,3},{11,5},{13,3},{3,5},{6,1},{1,3},{8,6},{7,2},{2,2}
        };
        test.solution(nodeinfo);

    }

    public int[][] solution(int[][] nodeinfo) {

        int N = nodeinfo.length;

        Node[] nodes = new Node[N];
        for (int i = 0; i < N; i++) {
            nodes[i] = new Node(i+1, nodeinfo[i][0], nodeinfo[i][1]);
        }
        Arrays.sort(nodes);
        System.out.println(Arrays.toString(nodes));

        BinaryGraph binaryGraph = new BinaryGraph(nodes);

        int[][] answer = new int[2][N];

        answer[0] = binaryGraph.preOrder();
        System.out.println(Arrays.toString(answer[0]));
        answer[1] = binaryGraph.postOrder();
        System.out.println(Arrays.toString(answer[1]));

        return answer;
    }

    static class BinaryGraph {
        Node root;

        BinaryGraph(Node[] nodes) {
            root = nodes[0];
            for (int i = 1; i < nodes.length; i++)
                addNode(root, nodes[i]);
        }

        void addNode(Node parent, Node node) {
            if (parent.x > node.x) {
                if (parent.child[0] != null) {
                    addNode(parent.child[0], node);
                } else {
                    parent.child[0] = node;
                }
            } else {
                if (parent.child[1] != null) {
                    addNode(parent.child[1], node);
                } else {
                    parent.child[1] = node;
                }
            }
        }

        int[] preOrder() {
            ArrayList<Integer> list = new ArrayList<>();
            preOrder(root, list);
            int[] visitNodes = new int[list.size()];
            for (int i = 0; i < list.size(); i++)
                visitNodes[i] = list.get(i);

            return visitNodes;
        }

        int[] postOrder() {
            ArrayList<Integer> list = new ArrayList<>();
            postOrder(root, list);
            int[] visitNodes = new int[list.size()];
            for (int i = 0; i < list.size(); i++)
                visitNodes[i] = list.get(i);

            return visitNodes;
        }

        void preOrder(Node parent, List<Integer> visit) {
            visit.add(parent.id);
            if (parent.child[0] != null)
                preOrder(parent.child[0], visit);
            if (parent.child[1] != null)
                preOrder(parent.child[1], visit);
        }

        void postOrder(Node parent, List<Integer> visit) {
            if (parent.child[0] != null)
                postOrder(parent.child[0], visit);
            if (parent.child[1] != null)
                postOrder(parent.child[1], visit);
            visit.add(parent.id);
        }
    }

    static class Node implements Comparable<Node> {

        int id;
        int x, y;

        Node[] child;

        Node(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.child = new Node[2];
        }

        @Override
        public int compareTo(Node o) {
            return -1 * Integer.compare(this.y, o.y);
        }

        @Override
        public String toString() {
            return "("+id+":"+x+","+y+")";
        }
    }
}
