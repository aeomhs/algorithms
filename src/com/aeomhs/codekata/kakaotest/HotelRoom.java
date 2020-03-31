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

    HashMap<Long, Long> hashReq;

    public long[] solution(long k, long[] room_number) {
        long[] answer = new long[room_number.length];
        hashReq = new HashMap<>();

        for (int i = 0 ; i < answer.length; i++) {
            long reserved = findEmptyRoom(room_number[i]);
            reservedRoom(reserved, reserved+1);
            answer[i] = reserved;
        }

        return answer;
    }

    private void reservedRoom(long req, long roomId) {
        hashReq.put(req, roomId);
    }

    private long findEmptyRoom(long req) {
        if (!hashReq.containsKey(req))
            return req;
        long nextRoom = findEmptyRoom(hashReq.get(req));
        hashReq.put(req, nextRoom);
        return nextRoom;
    }

}
