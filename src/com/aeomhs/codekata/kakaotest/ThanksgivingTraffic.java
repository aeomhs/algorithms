package com.aeomhs.codekata.kakaotest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 이전 추석 당일날 발생한 트래픽을 분석하여, 이번 추석에 서버를 증설해야할지 결정해야한다.
 * 이전 추석 당일날 발생한 초당 최대 처리량을 구해서 판단해야한다.
 * 초당 최대 처리량이란 1초 안에 발생할 수 있는 가장 많은 요청 개수이다.
 *
 * # 1
 * 비트맵 방식으로 count 하기엔, 초당 1000개의 배열이 필요하고, 분, 시까지 나타내기 위해서는 2억개 이상의 배열 크기가 필요하다.
 * 아무리 분할하여 문제를 해결한다고 하더라도, 좋지 않은 방법이라 생각했다.
 *
 * # 2
 * 그림을 그리고 답을 찾아가는 과정에서 풀이 방법을 도출했다.
 * 응답 완료 시간에 대해서 정렬되어 있기 때문에, i < j 라고 할때
 * 항상 Si < Sj 성립한다.
 * 이를 활용해서 1초 안에 같이 요청이 처리되는지 확인하기 위해서는
 *  Si + 1 > (S - Tj)
 * 임을 확인하면 된다.
 * 이 공식의 의미는
 *  i번째 요청의 응답시간(요청 완료 시간) + 1초 안에
 *  j번째 요청이 시작되면 같은 1초라는 범위 안에 존재할 수 있다.
 * 라는 의미이다.
 *
 * 이제는 i번째 요청에 대해서 j의 범위를 한정지을 필요가 있다.
 * j는 i보단 크다는 사실은 이미 가정했다.
 * 0.001 <= T <= 3.000
 * 이라는 사실에 주목한다.
 * T는 3초를 넘어갈 수 없기 때문에,
 * Si + 4 초 이후에 끝나는 j번째 요청부터는 Si + 1초보다 작은 시간 안에서 시작될 수 없다.
 * 즉, Si + 4 < Sj 이면, 루프를 탈출한다.
 *
 */
public class ThanksgivingTraffic {

    // Date[][0] = S - T
    // Date[][1] = S
    private Date[][] requestTimeSet;

    public int solution(String[] lines) {

        try {
            generateRequestTimeSet(lines);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int maxCount = 0;
        for (int i = 0; i < requestTimeSet.length; i++) {
            int count = 1;

            for (int j = i + 1; j < requestTimeSet.length; j++) {
                if (requestTimeSet[i][1].getTime() + 4000 < requestTimeSet[j][1].getTime())
                    break;

                if (requestTimeSet[i][1].getTime() + 1000 >= requestTimeSet[j][0].getTime())
                    count++;
            }

            if (maxCount < count)
                maxCount = count;
        }


        return maxCount;
    }

    private void generateRequestTimeSet(String[] lines) throws ParseException {

        requestTimeSet = new Date[lines.length][2];

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        for (int i = 0; i < lines.length; i++) {
            String[] splitST = lines[i].split(" ");

            Date S = dateFormat.parse(splitST[0] + " " + splitST[1]);
            long T = milliSecondToLong(splitST[2]);

            requestTimeSet[i][1] = S;
            requestTimeSet[i][0] = new Date(S.getTime() - T + 1);

            System.out.println("S : " + dateFormat.format(requestTimeSet[i][1]));
            System.out.println("T : " + dateFormat.format(requestTimeSet[i][0]));
        }

    }

    /**
     * @param T XX.YYYs
     * @return XXYYY
     */
    private long milliSecondToLong(String T) {
        String [] splitSecond = T.split("\\.");

        if (splitSecond.length == 1)
            return Long.parseLong(splitSecond[0].replace("s", "")) * 1000;

        long milliSecond = Long.parseLong(splitSecond[0]) * 1000;

        int i = 100;
        for (char c : splitSecond[1].toCharArray()) {
             if (c == 's')
                 break;
             milliSecond += (c - '0') * i;
             i /= 10;
        }

        return milliSecond;
    }


    public static void main(String[] args) {
        ThanksgivingTraffic test = new ThanksgivingTraffic();
        int result = -1;

        result = test.solution(new String[]{
                "2016-09-15 20:59:57.421 0.351s",
                "2016-09-15 20:59:58.233 1.181s",
                "2016-09-15 20:59:58.299 0.8s",
                "2016-09-15 20:59:58.688 1.041s",
                "2016-09-15 20:59:59.591 1.412s",
                "2016-09-15 21:00:00.464 1.466s",
                "2016-09-15 21:00:00.741 1.581s",
                "2016-09-15 21:00:00.748 2.31s",
                "2016-09-15 21:00:00.966 0.381s",
                "2016-09-15 21:00:02.066 2.62s"
        });
//        result = test.solution(new String[]{
//                "2016-09-15 01:00:04.002 2.0s",
//                "2016-09-15 01:00:07.000 2s"
//        });

//        if (result != 2) throw new AssertionError("Expected : 2, actual : " + result);
        if (result != 7) throw new AssertionError("Expected : 7, actual : " + result);

    }
}
