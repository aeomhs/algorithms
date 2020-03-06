package com.aeomhs.codekata.kakaotest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO Not Solved
 *  시간 복잡도 .. 객체 오버헤드
 *  for 문 -> array bit masking 으로 해결해보기
 */
class DroneMinRouteTest {
    /**
     * (0,0) ~ (N-1, N-1) 이동 경로에 대한 최단 이동 횟수를 구한다.
     */
    public int solution(int[][] board) {
        DroneMinRoute droneMinRoute = new DroneMinRoute(board);
        return droneMinRoute.getMinMovementForBfsRoute();
    }

    @Test
    public void solutionTest() {
        DroneMinRouteTest test = new DroneMinRouteTest();
        int[][] board;
        int expected;
        int result;

        board = new int[][] {
                {0, 0, 0, 1, 1},{0, 0, 0, 1, 0},{0, 1, 0, 1, 1},{1, 1, 0, 0, 1},{0, 0, 0, 0, 0}
        };
        expected = 7;
        result = test.solution(board);
        Assertions.assertEquals(expected, result);

        board = new int[][] {
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };
        expected = 7;
        result = test.solution(board);
        Assertions.assertEquals(expected, result);


        board = new int[][] {
                {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0},
                {0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0}
        };
        expected = 9;
        result = test.solution(board);
        Assertions.assertEquals(expected, result);


        board = new int[][] {
                {0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1},
                {0, 0, 0, 0, 0, 0}
        };
        expected = 15;
        result = test.solution(board);
        Assertions.assertEquals(expected, result);


//        board = new int[][] {
//                {0, 0, 0, 0, 0, 0, 0, 0},
//                {1, 0, 0, 0, 0, 0, 0, 0},
//                {1, 0, 1, 1, 0, 0, 1, 1},
//                {1, 1, 1, 0, 0, 0, 1, 0},
//                {0, 0, 0, 0, 0, 1, 1, 0},
//                {1, 0, 0, 1, 1, 1, 0, 0},
//                {1, 0, 0, 1, 0, 0, 0, 0},
//                {1, 0, 0, 0, 0, 0, 0, 0},
//        };
//        expected = 19;
//        result = test.solution(board);
//        Assertions.assertEquals(expected, result);
    }

}
class DroneMinRoute {

    private final int[][] board;

    private final int N;

    private int minTS;

    DroneMinRoute(int [][] board) {
        this.board = board;
        this.N = board.length;
    }

    static class Drone {

        Point p1, p2;

        /**
         * True : 가로
         * False: 세로
         */
        boolean status = true;

        int ts;

        Drone() {
            p1 = new Point(0, 0 );
            p2 = new Point(1, 0 );
            ts = 0;
        }

        static class Point {
            int x, y;
            Point(int x, int y) {
                this.x = x;
                this.y = y;
            }

            Point copy() {
                return new Point(x, y);
            }
        }

        void moveRight() {
            p2.x++;
            p1.x++;
        }

        void moveDown() {
            p2.y++;
            p1.y++;
        }

        void moveLeft() {
            p2.x--;
            p1.x--;
        }

        void moveUp() {
            p2.y--;
            p1.y--;
        }

        /**
         * XX   ->  0X
         * 00   ->  0X
         */
        void downRotateRight() {
            downRotateLeft();
            moveRight();
        }

        /**
         * XX   -> X0
         * 00   -> X0
         */
        void downRotateLeft() {
            status = false;
            p2.x--;
            p2.y++;
        }

        /**
         * 00 -> 0X
         * XX -> 0X
         */
        void upRotateRight() {
            status = false;
            p1.x++;
            p1.y--;
        }

        /**
         * 00 -> X0
         * XX -> X0
         */
        void upRotateLeft() {
            upRotateRight();
            moveLeft();
        }

        /**
         * X0   -> 00
         * X0   -> XX
         */
        void rightRotateDown() {
            rightRotateUp();
            moveDown();
        }

        /**
         * X0   -> XX
         * X0   -> 00
         */
        void rightRotateUp() {
            status = true;
            p2.x++;
            p2.y--;
        }

        /**
         * 0X  -> 00
         * 0X  -> XX
         */
        void leftRotateDown() {
            status = true;
            p1.x--;
            p1.y++;
        }

        /**
         * 0X  -> XX
         * 0X  -> 00
         */
        void leftRotateUp() {
            leftRotateDown();
            moveUp();
        }

        Drone copy() {
            Drone newDrone = new Drone();
            newDrone.status = this.status;
            newDrone.p1 = this.p1.copy();
            newDrone.p2 = this.p2.copy();
            newDrone.ts = this.ts;
            return newDrone;
        }

        public String toString() {
            return "[("+p1.x+","+p1.y+"),("+p2.x+","+p2.y+")]";
        }
    }

    public int getMinMovementForRoute() {
        minTS = Integer.MAX_VALUE;

        Drone drone = new Drone();
        afterMovement(drone);

        route(drone, 0);

        return minTS;
    }

    private void route(Drone drone, int m) {

//        System.out.println(m+"번째 이동");
//        printBoardWithDrone(drone);

        if (isGoalPoint(drone)){
            if (m < minTS)
                minTS = m;
            return;
        }

        if (m == minTS-1)
            return;

        // go to right
        if (goRight(drone)) {
            route(drone, m+1);
            cancelMovedRight(drone);
        }

        // go to down
        if (goDown(drone)) {
            route(drone, m+1);
            cancelMovedDown(drone);
        }

        // 오른쪽, 아래쪽 모두 막힐 경우 우회 시도
        if (goLeft(drone)) {
            route(drone, m+1);
            cancelMovedLeft(drone);
        }

        if (goUp(drone)) {
            route(drone, m+1);
            cancelMovedUp(drone);
        }

        // Rotate
        if (drone.status) {
            // Rotate Left or Right
            if (canDownRotateRightOrLeft(drone)) {
                downRotateRight(drone);
                route(drone, m+1);
                cancelDownRotatedRight(drone);

                downRotateLeft(drone);
                route(drone, m+1);
                cancelDownRotatedLeft(drone);
            }

            if (canUpRotateRightOrLeft(drone)) {
                upRotateRight(drone);
                route(drone, m+1);
                cancelUpRotatedRight(drone);

                upRotateLeft(drone);
                route(drone, m+1);
                cancelUpRotatedLeft(drone);
            }
        }
        else {
            // Rotate Down or Up
            if (canRightRotateDownOrUp(drone)) {
                rightRotateDown(drone);
                route(drone, m+1);
                cancelRightRotatedDown(drone);

                rightRotateUp(drone);
                route(drone, m+1);
                cancelRightRotatedUp(drone);
            }

            if (canLeftRotateDownOrUp(drone)) {
                leftRotateDown(drone);
                route(drone, m+1);
                cancelLeftRotatedDown(drone);

                leftRotateUp(drone);
                route(drone, m+1);
                cancelLeftRotatedUp(drone);
            }
        }
    }

    public int getMinMovementForBfsRoute() {
        return bfsRoute(new LinkedList<>());
    }

    /**
     * @param queue Drone 가능한 위치 정보.
     */
    private int bfsRoute(Deque<Drone> queue) {

        queue.addLast(new Drone());

        while (!queue.isEmpty()) {
            Drone drone = queue.removeFirst();
            drone.ts++;
//            System.out.println(drone.ts + "" + drone);
//            printBoardWithDrone(drone);

            if (canGoRight(drone)) {
                Drone nextPosDrone = drone.copy();
                nextPosDrone.moveRight();
                if (isGoalPoint(nextPosDrone))
                    return drone.ts;
                queue.addLast(nextPosDrone);
            }

            if (canGoDown(drone)) {
                Drone nextPosDrone = drone.copy();
                nextPosDrone.moveDown();
                if (isGoalPoint(nextPosDrone))
                    return drone.ts;
                queue.addLast(nextPosDrone);
            }

            if (canGoLeft(drone)) {
                Drone nextPosDrone = drone.copy();
                nextPosDrone.moveLeft();
                if (isGoalPoint(nextPosDrone))
                    return drone.ts;
                queue.addLast(nextPosDrone);
            }

            if (canGoUp(drone)) {
                Drone nextPosDrone = drone.copy();
                nextPosDrone.moveUp();
                if (isGoalPoint(nextPosDrone))
                    return drone.ts;
                queue.addLast(nextPosDrone);
            }

            // Rotate
            if (drone.status) {
                // Rotate Left or Right
                if (canDownRotateRightOrLeft(drone)) {
                    Drone nextPosDrone = drone.copy();
                    downRotateRight(nextPosDrone);
                    queue.addLast(nextPosDrone);

                    nextPosDrone = drone.copy();
                    downRotateLeft(nextPosDrone);
                    queue.addLast(nextPosDrone);
                }

                if (canUpRotateRightOrLeft(drone)) {
                    Drone nextPosDrone = drone.copy();
                    upRotateRight(nextPosDrone);
                    queue.addLast(nextPosDrone);

                    nextPosDrone = drone.copy();
                    upRotateLeft(nextPosDrone);
                    queue.addLast(nextPosDrone);
                }
            }
            else {
                // Rotate Down or Up
                if (canRightRotateDownOrUp(drone)) {
                    Drone nextPosDrone = drone.copy();
                    rightRotateDown(nextPosDrone);
                    queue.addLast(nextPosDrone);

                    nextPosDrone = drone.copy();
                    rightRotateUp(nextPosDrone);
                    queue.addLast(nextPosDrone);
                }

                if (canLeftRotateDownOrUp(drone)) {
                    Drone nextPosDrone = drone.copy();
                    leftRotateDown(nextPosDrone);
                    queue.addLast(nextPosDrone);

                    nextPosDrone = drone.copy();
                    leftRotateUp(nextPosDrone);
                    queue.addLast(nextPosDrone);
                }
            }
        }

        return -1;
    }

    private boolean isGoalPoint(Drone drone) {
        return (drone.p1.y == N-1 && drone.p1.x == N-1) ||
                (drone.p2.y == N-1 && drone.p2.x == N-1);
    }

    private void afterMovement(Drone drone) {
        board[drone.p1.y][drone.p1.x]--;
        board[drone.p2.y][drone.p2.x]--;
    }

    private void beforeCancelMovement(Drone drone) {
        board[drone.p1.y][drone.p1.x]++;
        board[drone.p2.y][drone.p2.x]++;
    }

    /* MOVEMENT */
    private boolean goDown(Drone drone) {
        if (canGoDown(drone)){
            drone.moveDown();
            afterMovement(drone);
            return true;
        }
        return false;
    }

    private boolean canGoDown(Drone drone) {
        if (drone.status) {
            if (drone.p1.y < N-1 && drone.p2.y < N-1) {
                return (board[drone.p1.y + 1][drone.p1.x] == 0 && board[drone.p2.y + 1][drone.p2.x] == 0);
            }
        }
        else {
            if (drone.p2.y < N-1) {
                return board[drone.p2.y + 1][drone.p2.x] == 0;
            }
        }
        return false;
    }

    private boolean goUp(Drone drone) {
        if (canGoUp(drone)) {
            drone.moveUp();
            afterMovement(drone);
            return true;
        }
        return false;
    }

    private boolean canGoUp(Drone drone) {
        if (drone.status) {
            if (drone.p1.y > 0 && drone.p2.y > 0) {
                return (board[drone.p1.y - 1][drone.p1.x] == 0 && board[drone.p2.y - 1][drone.p2.x] == 0);
            }
        }
        else {
            if (drone.p1.y > 0) {
                return board[drone.p1.y - 1][drone.p1.x] == 0;
            }
        }
        return false;
    }

    private boolean goRight(Drone drone) {
        if (canGoRight(drone)){
            drone.moveRight();
            afterMovement(drone);
            return true;
        }
        return false;
    }

    private boolean canGoRight(Drone drone) {
        if (drone.status) {
            if (drone.p2.x < N-1) {
                return (board[drone.p2.y][drone.p2.x + 1] == 0);
            }
        }
        else {
            if (drone.p1.x < N - 1 && drone.p2.x < N - 1) {
                return (board[drone.p1.y][drone.p1.x + 1] == 0 && board[drone.p2.y][drone.p2.x + 1] == 0);
            }
        }
        return false;
    }

    private boolean goLeft(Drone drone) {
        if (canGoLeft(drone)) {
            drone.moveLeft();
            afterMovement(drone);
            return true;
        }
        return false;
    }

    private boolean canGoLeft(Drone drone) {
        if (drone.status) {
            if (drone.p1.x > 0) {
                return (board[drone.p1.y][drone.p1.x - 1] == 0);
            }
        }
        else {
            if (drone.p1.x > 0 && drone.p2.x > 0) {
                return (board[drone.p1.y][drone.p1.x - 1] == 0 && board[drone.p2.y][drone.p2.x - 1] == 0);
            }
        }
        return false;
    }

    private void cancelMovedRight(Drone drone) {
        beforeCancelMovement(drone);
        drone.moveLeft();
    }

    private void cancelMovedLeft(Drone drone) {
        beforeCancelMovement(drone);
        drone.moveRight();
    }

    private void cancelMovedDown(Drone drone) {
        beforeCancelMovement(drone);
        drone.moveUp();
    }

    private void cancelMovedUp(Drone drone) {
        beforeCancelMovement(drone);
        drone.moveDown();
    }

    /* ROTATION */
    private boolean canDownRotateRightOrLeft(Drone drone) {
        // Drone is Bottom
        if (drone.p1.y > N-2 || drone.p2.y > N-2)
            return false;

        return board[drone.p1.y + 1][drone.p1.x] == 0 && board[drone.p2.y + 1][drone.p2.x] == 0;
    }

    private boolean canUpRotateRightOrLeft(Drone drone) {
        // Drone is Top
        if (drone.p1.y == 0 || drone.p2.y == 0)
            return false;

        return board[drone.p1.y - 1][drone.p1.x] == 0 && board[drone.p2.y - 1][drone.p2.x] == 0;
    }

    private boolean canRightRotateDownOrUp(Drone drone) {
        // Drone is end of Right
        if (drone.p1.x > N-2 || drone.p2.x > N-2)
            return false;

        return board[drone.p1.y][drone.p1.x + 1] == 0 && board[drone.p2.y][drone.p2.x + 1] == 0;
    }

    private boolean canLeftRotateDownOrUp(Drone drone) {
        // Drone is end Of Left
        if (drone.p1.x == 0 || drone.p2.x == 0)
            return false;

        return board[drone.p1.y][drone.p1.x - 1] == 0 && board[drone.p2.y][drone.p2.x - 1] == 0;
    }

    private void downRotateRight(Drone drone) {
        drone.downRotateRight();
        afterMovement(drone);
    }

    private void downRotateLeft(Drone drone) {
        drone.downRotateLeft();
        afterMovement(drone);
    }

    private void upRotateRight(Drone drone) {
        drone.upRotateRight();
        afterMovement(drone);
    }

    private void upRotateLeft(Drone drone) {
        drone.upRotateLeft();
        afterMovement(drone);
    }

    private void rightRotateDown(Drone drone) {
        drone.rightRotateDown();
        afterMovement(drone);
    }

    private void rightRotateUp(Drone drone) {
        drone.rightRotateUp();
        afterMovement(drone);
    }

    private void leftRotateDown(Drone drone) {
        drone.leftRotateDown();
        afterMovement(drone);
    }

    private void leftRotateUp(Drone drone) {
        drone.leftRotateUp();
        afterMovement(drone);
    }

    private void cancelRightRotatedUp(Drone drone) {
        beforeCancelMovement(drone);
        drone.downRotateLeft();
    }

    private void cancelRightRotatedDown(Drone drone) {
        beforeCancelMovement(drone);
        drone.upRotateLeft();
    }

    private void cancelLeftRotatedUp(Drone drone) {
        beforeCancelMovement(drone);
        drone.downRotateRight();
    }

    private void cancelLeftRotatedDown(Drone drone) {
        beforeCancelMovement(drone);
        drone.upRotateRight();
    }

    private void cancelDownRotatedRight(Drone drone) {
        beforeCancelMovement(drone);
        drone.leftRotateUp();
    }

    private void cancelDownRotatedLeft(Drone drone) {
        beforeCancelMovement(drone);
        drone.rightRotateUp();
    }

    private void cancelUpRotatedRight(Drone drone) {
        beforeCancelMovement(drone);
        drone.leftRotateDown();
    }

    private void cancelUpRotatedLeft(Drone drone) {
        beforeCancelMovement(drone);
        drone.rightRotateDown();
    }

    /* TEST CODE */
    private void printBoardWithDrone(Drone drone) {
        int x1, y1;
        int x2, y2;
        x1 = drone.p1.x;
        y1 = drone.p1.y;
        x2 = drone.p2.x;
        y2 = drone.p2.y;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (x1 == j && y1 == i)
                    System.out.print(" X");
                else if (x2 == j && y2 == i)
                    System.out.print(" Y");
                else
                    System.out.print(String.format("%2d", board[i][j]));
            }
            System.out.println();
        }
        System.out.println();
    }
}


