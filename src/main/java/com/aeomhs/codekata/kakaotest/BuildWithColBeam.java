package com.aeomhs.codekata.kakaotest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BuildWithColBeam {

    public int[][] solution(int n, int[][] build_frame) {

        int[][] virtualWall = new int[n + 1][n + 1];
        ArrayList<Structure> structureList = new ArrayList<>();

        for (int i = 0; i < build_frame.length; i++) {
            int x = build_frame[i][0];
            int y = build_frame[i][1];
            int a = build_frame[i][2];
            int b = build_frame[i][3];

            // 기둥
            if (a == 0){
                Column column = (Column) Column.columnBuilder.build(x, y);
                System.out.println(column);
                // 설치
                if (b == 1) {
                    if (Column.columnBuilder.buildRuleCheck(column, virtualWall)) {
                        virtualWall[column.getP1().getY()][column.getP1().getX()] |= Point.COLUMN_P1;
                        virtualWall[column.getP2().getY()][column.getP2().getX()] |= Point.COLUMN_P2;
                        structureList.add(column);
                    }
                }
                // 제거
                else if (b == 0) {
                    // 제거됨
                    if (structureList.removeIf(structure -> structure instanceof Column && structure.equals(column))) {
                        virtualWall[column.getP1().getY()][column.getP1().getX()] ^= Point.COLUMN_P1;
                        virtualWall[column.getP2().getY()][column.getP2().getX()] ^= Point.COLUMN_P2;

                        // 유지 가능한 구조인지 확인
                        if (!Structure.StructureBuilder.buildRuleChecks(structureList, virtualWall)) {
                            // 제거 취소
                            virtualWall[column.getP1().getY()][column.getP1().getX()] |= Point.COLUMN_P1;
                            virtualWall[column.getP2().getY()][column.getP2().getX()] |= Point.COLUMN_P2;
                            structureList.add(column);
                        }
                    }
                }
            }
            // 보
            else if (a == 1){
                Beam beam = (Beam) Beam.beamBuilder.build(x, y);
                System.out.println(beam);
                // 설치
                if (b == 1) {
                    if (Beam.beamBuilder.buildRuleCheck(beam, virtualWall)) {
                        virtualWall[beam.getP1().getY()][beam.getP1().getX()] |= Point.BEAM_P1;
                        virtualWall[beam.getP2().getY()][beam.getP2().getX()] |= Point.BEAM_P2;
                        structureList.add(beam);
                    }
                }
                // 제거
                else if (b == 0) {
                    // 제거됨
                    if (structureList.removeIf(structure -> structure instanceof Beam && structure.equals(beam))) {
                        virtualWall[beam.getP1().getY()][beam.getP1().getX()] ^= Point.BEAM_P1;
                        virtualWall[beam.getP2().getY()][beam.getP2().getX()] ^= Point.BEAM_P2;

                        // 유지 가능한 구조인지 확인
                        if (!Structure.StructureBuilder.buildRuleChecks(structureList, virtualWall)) {
                            // 제거 취소
                            virtualWall[beam.getP1().getY()][beam.getP1().getX()] |= Point.BEAM_P1;
                            virtualWall[beam.getP2().getY()][beam.getP2().getX()] |= Point.BEAM_P2;
                            structureList.add(beam);
                        }
                    }
                }
            }

            System.out.println(structureList);
        }

        Collections.sort(structureList);
        System.out.println(structureList);

        int [][] answer = new int[structureList.size()][3];

        int index = 0;
        for (Structure s : structureList) {
            answer[index][0] = s.getP1().getX();
            answer[index][1] = s.getP1().getY();
            answer[index][2] = s instanceof Column ? 0 : 1;
            index++;
        }

        return answer;
    }

    public static void main(String[] args) {
        BuildWithColBeam test = new BuildWithColBeam();
//        int[][] result = test.solution(
//                5, new int[][] {
//                        {1,0,0,1},
//                        {1,1,1,1},
//                        {2,1,0,1},
//                        {2,2,1,1},
//                        {5,0,0,1},
//                        {5,1,0,1},
//                        {4,2,1,1},
//                        {3,2,1,1}
//                }
//        );
//        int[][] result = test.solution(
//                5, new int[][] {
//                        {0,0,0,1},
//                        {2,0,0,1},
//                        {4,0,0,1},
//                        {0,1,1,1},
//                        {1,1,1,1},
//                        {2,1,1,1},
//                        {3,1,1,1},
//                        {2,0,0,0},
//                        {1,1,1,0},
//                        {2,2,0,1}
//                }
//        );
        int[][] result = test.solution(
                5, new int[][] {
                        {0,0,0,1},
                        {0,1,0,1},
                        {0,1,1,1},
                        {0,0,0,0},
                }
        );
//        int[][] expected = new int[][] {
//                {1,0,0},
//                {1,1,1},
//                {2,1,0},
//                {2,2,1},
//                {3,2,1},
//                {4,2,1},
//                {5,0,0},
//                {5,1,0}
//        };
//        int[][] expected = new int[][] {
//                {0,0,0},
//                {0,1,1},
//                {1,1,1},
//                {2,1,1},
//                {3,1,1},
//                {4,0,0}
//        };
        int[][] expected = new int[][] {
                {0,0,0},
                {0,1,0},
                {0,1,1},
        };


        for (int[] line : expected)
            System.out.println("Expected : "+Arrays.toString(line));

        for (int[] line : result)
            System.out.println("Actual : "+Arrays.toString(line));
    }
}

class Column extends Structure {

    public static final ColumnBuilder columnBuilder = new ColumnBuilder();

    private Column(Point p1, Point p2) {
        super(p1, p2);
    }

    static class ColumnBuilder extends StructureBuilder {

        @Override
        public boolean buildRuleCheck(Structure structure, final int[][] virtualWall) {
            // 기둥이 바닥 위에 있다.
            if (structure.getP1().getY() == 0)
                return true;

            // 기둥은 다른 기둥 위에 있어야 한다.
            if ((virtualWall[structure.getP1().getY()][structure.getP1().getX()] & Point.COLUMN_P2) == Point.COLUMN_P2)
                return true;

            // 기둥은 보의 한쪽 끝 부분 위에 있어야 한다.
            if ((virtualWall[structure.getP1().getY()][structure.getP1().getX()] & Point.BEAM_P1) == Point.BEAM_P1)
                return true;

            if ((virtualWall[structure.getP1().getY()][structure.getP1().getX()] & Point.BEAM_P2) == Point.BEAM_P2)
                return true;

            return false;
        }

        @Override
        public Structure build(int x, int y) {
            Point p1 = new Point(x, y);
            Point p2 = new Point(x, y + 1);

            return new Column(p1, p2);
        }
    }

    @Override
    public String toString() {
        return super.toString() + ":Column";
    }
}

class Beam extends Structure {

    public static final BeamBuilder beamBuilder = new BeamBuilder();

    private Beam(Point p1, Point p2) {
        super(p1, p2);
    }

    static class BeamBuilder extends StructureBuilder {

        @Override
        public boolean buildRuleCheck(Structure structure, final int[][] virtualWall) {
            // 보 한쪽 끝 부분이 기둥 위에 있다.
            if ((virtualWall[structure.getP1().getY()][structure.getP1().getX()] & Point.COLUMN_P2)
                    == Point.COLUMN_P2)
                return true;
            if ((virtualWall[structure.getP2().getY()][structure.getP2().getX()] & Point.COLUMN_P2)
                    == Point.COLUMN_P2)
                return true;

            // 양쪽 끝 부분이 다른 보와 동시에 연결되어 있다.
            if (((virtualWall[structure.getP1().getY()][structure.getP1().getX()] & Point.BEAM_P2)
                    == Point.BEAM_P2) &&
                ((virtualWall[structure.getP2().getY()][structure.getP2().getX()] & Point.BEAM_P1)
                    == Point.BEAM_P1))
                return true;

            return false;
        }

        @Override
        public Structure build(int x, int y) {
            Point p1 = new Point(x, y);
            Point p2 = new Point(x + 1, y);

            return new Beam(p1, p2);
        }
    }

    @Override
    public String toString() {
        return super.toString() + ":Beam";
    }
}



/**
 * 건축 구조물 클래스
 */
class Structure implements Comparable<Structure> {

    private Point p1;

    private Point p2;

    Structure(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * 설치하는 지점에 대해서 시작 좌표와 끝 좌표를 계산한 뒤, 구조물 인스턴스를 반환한다.
     */
    abstract static class StructureBuilder {

        static boolean buildRuleChecks(List<Structure> structureList, final int[][] virtualWall) {
            // 유지 가능한 구조인지 확인
            for (Structure s : structureList) {
                if (s instanceof Column) {
                    if (!Column.columnBuilder.buildRuleCheck(s, virtualWall)) {
                        return false;
                    }
                }
                if (s instanceof Beam) {
                    if (!Beam.beamBuilder.buildRuleCheck(s, virtualWall)) {
                        return false;
                    }
                }
            }

            return true;
        }

        abstract boolean buildRuleCheck(Structure structure, final int [][] virtualWall);

        abstract Structure build(int x, int y);
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public boolean equals(Structure other) {
        return this.p1.equals(other.p1) && this.p2.equals(other.p2);
    }

    @Override
    public int compareTo(Structure o) {
        // x
        if (this.p1.getX() < o.p1.getX())
            return -1;
        if (this.p1.getX() > o.p1.getX())
            return 1;

        // y
        if (this.p1.getY() < o.p1.getY())
            return -1;
        if (this.p1.getY() > o.p1.getY())
            return 1;

        // structure
        if (o instanceof Beam)
            return -1;
        else
            return 1;
    }

    @Override
    public String toString() {
        return "[" + this.p1 + " to " + this.p2 + "]";
    }
}

/**
 * 2차원 평면 좌표 클래스
 * X - 가로 좌표
 * Y - 세로 좌표
 */
class Point {

    public static final byte COLUMN_P1 = 0b0001;
    public static final byte COLUMN_P2 = 0b0010;
    public static final byte BEAM_P1   = 0b0100;
    public static final byte BEAM_P2   = 0b1000;

    private int x;

    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Point other) {
        return (this.x == other.x) && (this.y == other.y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}