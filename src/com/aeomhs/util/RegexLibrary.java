package com.aeomhs.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 코드 테스트를 위한 정규표현식 라이브러리 구현
 */
public class RegexLibrary {

    /**
     * 문자열이 휴대폰 번호 규칙을 만족하는지 검사합니다.
     * 010-XXXX-XXXX
     */
    public static boolean isPhoneNumber(String input) {
        String regex = "^010-[0-9]{4}-[0-9]{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
    /**
     * 문자열이 이메일 주소 규칙을 만족하는지 검사합니다.
     * xxx@yyy.com
     * xxx@yyy.co.kr
     */
    public static boolean isEmailAddress(String input) {
        String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9]+([.][a-zA-Z]+)+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * 문자열이 도메인 주소 규칙을 만족하는지 검사합니다.
     * XXX.com
     * XXX.co.kr
     */
    public static boolean isDomainURI(String input) {
        String regex = "^[a-zA-Z0-9]+([.][a-zA-Z]+)+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * 문자열이 아이피 주소(IPv4) 규칙을 만족하는지 검사합니다.
     * 255.255.255.255
     */
    public static boolean isIPv4Address(String input) {
        String regex = "^([0-2]?[0-9]{1,2}).([0-2]?[0-9]{1,2}).([0-2]?[0-9]{1,2}).([0-2]?[0-9]{1,2})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}

class RegexLibraryTest {

    @Test
    public void phoneNumberRegexTest() {
        Assertions.assertTrue(RegexLibrary.isPhoneNumber("010-6231-4231"));

        Assertions.assertFalse(RegexLibrary.isPhoneNumber("02-533-4402"));
        Assertions.assertFalse(RegexLibrary.isPhoneNumber("533-4402"));
        Assertions.assertFalse(RegexLibrary.isPhoneNumber("010-573-4231"));
    }

    @Test
    public void emailAddressRegexTest() {
        Assertions.assertTrue(RegexLibrary.isEmailAddress("t_aeom@naver.com"));
        Assertions.assertTrue(RegexLibrary.isEmailAddress("aeomhs@naver.com"));
        Assertions.assertTrue(RegexLibrary.isEmailAddress("hong-gil-dong@gmail.com"));
        Assertions.assertTrue(RegexLibrary.isEmailAddress("aeom1234@yahoo.co.kr"));

        Assertions.assertFalse(RegexLibrary.isEmailAddress("@naver.com"));
        Assertions.assertFalse(RegexLibrary.isEmailAddress("aeom@.com"));
        Assertions.assertFalse(RegexLibrary.isEmailAddress("@.com"));
        Assertions.assertFalse(RegexLibrary.isEmailAddress("aeom@naver."));
        Assertions.assertFalse(RegexLibrary.isEmailAddress("aeom@naver"));
        Assertions.assertFalse(RegexLibrary.isEmailAddress("@"));
    }

    @Test
    public void domainURIRegexTest() {
        Assertions.assertTrue(RegexLibrary.isDomainURI("naver.com"));
        Assertions.assertTrue(RegexLibrary.isDomainURI("yahoo.co.kr"));
        Assertions.assertTrue(RegexLibrary.isDomainURI("google.co.kr"));

        Assertions.assertFalse(RegexLibrary.isDomainURI(".c.co"));
    }

    @Test
    public void ipv4AddressRegexTest() {
        Assertions.assertTrue(RegexLibrary.isIPv4Address("192.168.0.1"));
        Assertions.assertTrue(RegexLibrary.isIPv4Address("255.255.255.255"));
        Assertions.assertTrue(RegexLibrary.isIPv4Address("0.255.255.255"));
        Assertions.assertTrue(RegexLibrary.isIPv4Address("127.0.0.0"));
        Assertions.assertTrue(RegexLibrary.isIPv4Address("0.0.0.0"));
        Assertions.assertTrue(RegexLibrary.isIPv4Address("0.0.0.1"));

        Assertions.assertFalse(RegexLibrary.isIPv4Address("255.255.255.255."));
        Assertions.assertFalse(RegexLibrary.isIPv4Address("255.255.255.255.255"));
        Assertions.assertFalse(RegexLibrary.isIPv4Address(".."));
        Assertions.assertFalse(RegexLibrary.isIPv4Address("256.255.255.255"));
        Assertions.assertFalse(RegexLibrary.isIPv4Address("255.255.255"));
    }
}
