package com.aeomhs.codekata.kakaotest;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindMusic {

    public static void main(String[] args) {
        FindMusic test = new FindMusic();
        String m = "ABCDEFG";
        String[] musicinfos = new String[] {
                "12:00,12:14,HELLO,CDEFGAB",
                "13:00,13:05,WORLD,ABCDEF"
        };
        String expected = "HELLO";
        String result = test.solution(m, musicinfos);
        System.out.println(expected + ", " + result);

        m = "CC#BCC#BCC#BCC#B";
        musicinfos = new String[] {
                "03:00,03:30,FOO,CC#B",
                "04:00,04:08,BAR,CC#BCC#BCC#B"
        };
        expected = "FOO";
        result = test.solution(m, musicinfos);
        System.out.println(expected + ", " + result);

        m = "ABC";
        musicinfos = new String[] {
                "12:00,12:14,HELLO,C#DEFGAB",
                "13:00,13:05,WORLD,ABCDEF"
        };
        expected = "WORLD";
        result = test.solution(m, musicinfos);
        System.out.println(expected + ", " + result);


        m = "ABCDAGBC#C#";
        musicinfos = new String[] {
                "00:00,00:10,TEN_MIN,BC#C#ABCDAG",
                "01:00,02:10,HOUR,BC#C#ABCDAG",
                "03:00,05:10,TWO_HOUR,BC#C#ABCDAG",
                "01:00,09:10,FOUR_HOUR,BC#C#ABCDAGABCDAG",
                "10:00,15:10,FIVE_HOUR,BC#C#ABCDAG",
                "16:00,23:10,LAST_HOUR,BC#C#ABCDAG",
        };
        expected = "LAST_HOUR";
        result = test.solution(m, musicinfos);
        System.out.println(expected + ", " + result);
    }

    static Pattern pattern = Pattern.compile("[CDEFGAB]#?");

    public String solution(String m, String[] musicinfos) {

        Matcher matcher = pattern.matcher(m);
        List<Pitch> melody = new LinkedList<>();
        while (matcher.find()) {
            melody.add(Pitch.getPitch(m.substring(matcher.start(), matcher.end())));
        }
        Record[] records = new Record[musicinfos.length];

        int matchRecordIdx = -1;
        for (int i = 0; i < records.length; i++) {
            records[i] = new Record(musicinfos[i]);
            if (isMatchMelody(melody, records[i].getMelodyOnTime()) &&
                    (matchRecordIdx == -1 ||
                            records[matchRecordIdx].playTime < records[i].playTime)) {
                matchRecordIdx = i;
            }
        }

        if (matchRecordIdx == -1)
            return "`(None)`";
        return records[matchRecordIdx].musicName;
    }

    private boolean isMatchMelody(List<Pitch> melody, List<Pitch> recordMelody) {

        if (melody.size() > recordMelody.size())
            return false;

        for (int i = 0; i <= recordMelody.size() - melody.size(); i++) {
            int idx = i;
            boolean matched = true;
            for (int j = 0; j < melody.size() && idx < recordMelody.size(); j++, idx++) {
                if (!melody.get(j).equals(recordMelody.get(idx))) {
                    matched = false;
                    break;
                }
            }
            if (matched)
                return true;
        }
        return false;
    }

    static class Record {

        String musicName;

        LocalTime startAt;

        LocalTime endAt;

        List<Pitch> melody;

        int playTime;

        Record(String record) {
            String[] infos = record.split(",");
            this.startAt = LocalTime.parse(infos[0], DateTimeFormatter.ofPattern("HH:mm"));
            this.endAt = LocalTime.parse(infos[1], DateTimeFormatter.ofPattern("HH:mm"));
            this.musicName = infos[2];
            Matcher matcher = pattern.matcher(infos[3]);

            melody = new LinkedList<>();
            while (matcher.find()) {
                melody.add(Pitch.getPitch(infos[3].substring(matcher.start(), matcher.end())));
            }

            playTime = (int) ChronoUnit.MINUTES.between(startAt, endAt);
        }

        List<Pitch> getMelodyOnTime() {
            List<Pitch> fullMelody = new LinkedList<>();
            for (int i = 0; i < playTime; i++) {
                fullMelody.add(melody.get(i%melody.size()));
            }
            return fullMelody;
        }

        @Override
        public String toString() {
            return "Record{" +
                    "musicName='" + musicName + '\'' +
                    ", startAt=" + startAt +
                    ", endAt=" + endAt +
                    ", melody=" + melody +
                    '}';
        }
    }
}


enum Pitch {
    C("C"), Cs("C#"), D("D"), Ds("D#"),
    E("E"), F("F"), Fs("F#"), G("G"),
    Gs("G#"), A("A"), As("A#"), B("B");

    private String value;

    private static Map<String, Pitch> hashMap = new HashMap<>();

    static {
        for (Pitch pitch : Pitch.values()) {
            hashMap.put(pitch.value, pitch);
        }
    }

    Pitch(String value) {
        this.value = value;
    }

    public static Pitch getPitch(String value) {
        return hashMap.get(value);
    }

}