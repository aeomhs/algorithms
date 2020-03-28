package com.aeomhs.codekata.kakaotest;

import java.util.*;

public class HotelRoom {
    public static void main(String[] args) {
        HotelRoom test = new HotelRoom();
        int k = 10;
        long[] room_number = new long[] {
                1,3,4,1,3,1
        };
        long[] expected = new long[] {
                1,3,4,2,5,6
        };
        long[] result = test.solution(k, room_number);
        System.out.println(Arrays.toString(expected) + " , " + Arrays.toString(result));
        assert Arrays.equals(expected, result);


         k = 100;
        room_number = new long[] {
                50, 1, 100, 25, 75, 1, 1, 1, 1, 5
        };
        expected = new long[] {
                50, 1, 100, 25, 75, 2, 3, 4, 5, 6
        };
        result = test.solution(k, room_number);
        System.out.println(Arrays.toString(expected) + " , " + Arrays.toString(result));
        assert Arrays.equals(expected, result);
    }

    public long[] solution(long k, long[] room_number) {
        long[] answer = new long[room_number.length];
        System.out.println(Arrays.toString(room_number));
        FreeList freeList = new FreeList(k);
        System.out.println(freeList);
        for (int i = 0; i < room_number.length; i++) {

            FreeSpace includeFreeList = freeList.find(room_number[i]);
            if (includeFreeList != null) {
                freeList.add(includeFreeList.allocated(room_number[i]));
                answer[i] = room_number[i];
            } else {
                answer[i] = freeList.allocateMinAfter(room_number[i]);
            }
            System.out.println(freeList);
        }

        return answer;
    }

    static class FreeList {
        List<FreeSpace> freeList;
        FreeList(long k) {
            freeList = new ArrayList<>();
            freeList.add(new FreeSpace(k));
        }

        long allocateMinAfter(long id) {
            int i = 0;
            for (; i < freeList.size(); i++) {
                FreeSpace freeSpace = freeList.get(i);
                if (freeSpace.after(id)) {
                    long ret = freeSpace.start;
                    this.add(freeSpace.allocated(ret));
                    freeList.remove(i);
                    return ret;
                }
            }
            return -1;
        }

        FreeSpace find(long id) {
            boolean find = false;
            int i = 0;
            for (; i < freeList.size(); i++){
                if (freeList.get(i).include(id)){
                    find = true;
                    break;
                }
            }
            if (find)
                return freeList.remove(i);
            else
                return null;
        }

        void add(FreeSpace[] freeSpaces) {
            Collections.addAll(freeList, freeSpaces);
            Collections.sort(freeList);
        }

        @Override
        public String toString() {
            return this.freeList.toString();
        }
    }

    static class FreeSpace implements Comparable<FreeSpace> {
        long start;
        long end;
        FreeSpace(long size) {
            this(1, size);
        }
        FreeSpace(long start, long end) {
            this.start = start;
            this.end = end;
        }

        FreeSpace[] allocated(long i) {
            if (start == end)
                return new FreeSpace[]{};
            if (i == start)
                return new FreeSpace[] { new FreeSpace(this.start+1, this.end)};
            if (i == end)
                return new FreeSpace[] { new FreeSpace(this.start, this.end-1)};

            return new FreeSpace[] { new FreeSpace(this.start, i-1), new FreeSpace(i+1, this.end) };
        }

        boolean include(long i) {
            return start <= i && i <= end;
        }

        boolean after(long i) {
            return i < start;
        }

        @Override
        public String toString() {
            return "["+start+"-"+end+"]";
        }

        @Override
        public int compareTo(FreeSpace o) {
            return Long.compare(this.start, o.start);
        }
    }
}
