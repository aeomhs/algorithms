package com.aeomhs.codekata.kakaotest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.PriorityQueue;

public class ShuttleBusProblem {

    private static DateFormat timeFormat = new SimpleDateFormat("HH:mm");

    private PriorityQueue<Date> waitQueue = new PriorityQueue<>();

    public void initWaitQueue(String[] timetable) throws ParseException {
        for (String time : timetable) {
            waitQueue.add(timeFormat.parse(time));
        }
    }

    static class ShuttleBus {

        private static int busCount = 0; // n번 운행

        private long t;                  // t분마다 운행 (주기)

        private Date atArrivalTime;               // 다음 버스 운행 시간 (역 도착 시간)

        private int person = 0;          // 탑승한 인원

        ShuttleBus(int t) throws ParseException{
            this.t = t;

            atArrivalTime = timeFormat.parse("09:00");
            atArrivalTime.setTime(atArrivalTime.getTime() + ((t*1000*60) * (long)busCount));

            busCount++;
        }
    }

    public String solution(int n, int t, int m, String[] timetable) {
        try {
            initWaitQueue(timetable);
            Date temp = timeFormat.parse("09:00");

            for (int i = 0; i < n; i++) {
                System.out.println(waitQueue);
                ShuttleBus shuttleBus = new ShuttleBus(t);

                // 마지막 배차
                if (ShuttleBus.busCount == n) {
                    System.out.println("막차 시간 : " + timeFormat.format(shuttleBus.atArrivalTime));
                    while (!waitQueue.isEmpty() && shuttleBus.person < m) {
                        if (waitQueue.peek().getTime() <= shuttleBus.atArrivalTime.getTime()) {
                            shuttleBus.person++;
                            temp = waitQueue.poll();
                        }
                        else {
                            break;
                        }
                    }

                    if (shuttleBus.person == m) {
                        System.out.println("막차 만차, 더 빨리 나가기");
                        temp.setTime(temp.getTime()-60000);
                        return timeFormat.format(temp.getTime());
                    } else {
                        System.out.println("막차 맞춰 나가기");
                        return timeFormat.format(shuttleBus.atArrivalTime);
                    }
                }
                else {
                    while (!waitQueue.isEmpty() && shuttleBus.person < m) {
                        if (waitQueue.peek().getTime() <= shuttleBus.atArrivalTime.getTime()) {
                            shuttleBus.person++;
                            waitQueue.poll();
                        }
                        else {
                            break;
                        }
                    }
                }
                System.out.println("temp : " + timeFormat.format(temp));
            }

            return timeFormat.format(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return "";
    }

    public static void main(String[] args) {
        ShuttleBusProblem test = new ShuttleBusProblem();
//        String result = test.solution(1, 1, 5, new String[] { "08:00", "08:01", "08:02", "08:03"});
//        String result = test.solution(2, 10, 2, new String[] { "09:10", "09:09", "08:00"});
//        String result = test.solution(2, 10, 2, new String[] { "09:00", "09:00", "09:00", "09:00"});
        String result = test.solution(1, 1, 5, new String[] { "00:01", "00:01", "00:01", "00:01", "00:01"});
        String expected = "08:59";

        System.out.println("Expected : " + expected);
        System.out.println("Actual : " + result);
    }
}
