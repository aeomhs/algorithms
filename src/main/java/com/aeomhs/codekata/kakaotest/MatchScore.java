package com.aeomhs.codekata.kakaotest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * Test Case 9 : <meta ... > 형태
 * Test Case 10 : <a href="" > 의 형태
 * 삽질 개꿀잼
 */
public class MatchScore {

    public int solution(String word, String[] _pages) {
        HashMap<Page, Double> basicScore = new HashMap<>();
        HashMap<String, Double> linkScore = new HashMap<>();

        for (String html : _pages) {
            Page page = new Page(html);

            double wordCount = 0;
            for (String w : page.getWords()) {
                if (word.equalsIgnoreCase(w))
                    wordCount++;
            }
            basicScore.put(page, wordCount);
            System.out.println("["+wordCount+"]"+Arrays.toString(page.getWords()));

            double linkNum = page.getLinks().size();
            double basicPerLinks = linkNum == 0 ? wordCount : wordCount/linkNum;
            for (Page.Link link : page.getLinks()) {
                linkScore.put(link.toString(),
                        linkScore.getOrDefault(link.toString(), 0.0)
                                +basicPerLinks);
            }
        }

        ArrayList<Page> pages = new ArrayList<>();
        for (Page page : basicScore.keySet()) {
            double bs = basicScore.get(page);
            double linkFrom = linkScore.getOrDefault(page.getUrl().toString(), 0.0);
            page.setMatchScore(bs + linkFrom);
            pages.add(page);
        }

        System.out.println(pages);

        pages.stream().forEach(page -> System.out.println(page+" "+page.getMatchScore()));

        return Collections.max(pages).getId();
    }

    @Test
    public void solutionTest1() {
        String word;
        String[] pages;
        int expected, result;
        MatchScore test = new MatchScore();

        word = "blind";
        pages = new String[] {
                "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "  <meta property=\"og:url\" content=\"https://a.com\"/>\n" +
                        "</head>  \n" +
                        "<body>\n" +
                        "Blind Lorem Blind ipsum dolor Blind test sit amet, consectetur adipiscing elit. \n" +
                        "<a href=\"https://b.com\"> Link to b </a>\n" +
                        "</body>\n" +
                        "</html>",
                "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "  <meta property=\"og:url\" content=\"https://b.com\"/>\n" +
                        "</head>  \n" +
                        "<body>\n" +
                        "Suspendisse potenti. Vivamus venenatis tellus non turpis bibendum, \n" +
                        "<a href=\"https://a.com\"> Link to a </a>\n" +
                        "blind sed congue urna varius. Suspendisse feugiat nisl ligula, quis malesuada felis hendrerit ut.\n" +
                        "<a href=\"https://c.com\"> Link to c </a>\n" +
                        "</body>\n" +
                        "</html>",
                "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "  <meta property=\"og:url\" content=\"https://c.com\"/>\n" +
                        "</head>  \n" +
                        "<body>\n" +
                        "Ut condimentum urna at felis sodales rutrum. Sed dapibus cursus diam, non interdum nulla tempor nec. Phasellus rutrum enim at orci consectetu blind\n" +
                        "<a href=\"https://a.com\"> Link to a </a>\n" +
                        "</body>\n" +
                        "</html>"
        };
        expected = 0;
        result = test.solution(word, pages);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void solutionTest2() {
        String word;
        String[] pages;
        int expected, result;
        MatchScore test = new MatchScore();

        word = "Muzi";
        pages = new String[] {
                "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "  <meta property=\"og:url\" content=\"https://careers.kakao.com/interview/list\"/>\n" +
                        "</head>  \n" +
                        "<body>\n" +
                        "<a href=\"https://programmers.co.kr/learn/courses/4673\"></a>#!MuziMuzi!)jayg07con&&\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>",
                "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "  <meta property=\"og:url\" content=\"https://www.kakaocorp.com\"/>\n" +
                        "</head>  \n" +
                        "<body>\n" +
                        "con%    muzI92apeach&2<a href=\"https://hashcode.co.kr/tos\"></a>\n" +
                        "\n" +
                        "    ^\n" +
                        "</body>\n" +
                        "</html>"
        };
        expected = 1;
        result = test.solution(word, pages);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void solutionTest3() {
        String word;
        String[] pages;
        int expected, result;
        MatchScore test = new MatchScore();

        word = "blinD";
        pages = new String[] {
                "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "  <meta property=\"og:url\" content=\"https://a.com\"/>\n" +
                        "</head>  \n" +
                        "<body>\n" +
                        "Blind Lorem Blind ipsum dolor Blind test sit amet, consectetur adipiscing elit. \n" +
                        "<a href=\"https://b.com\"> Link to b </a>\n" +
                        "<a href=\"https://c.com\"> Link to c </a>\n" +
                        "<a href=\"https://d.com\"> Link to d </a>\n" +
                        "<a href=\"https://e.com\"> Link to e </a>\n" +
                        "<a href=\"https://e.com\"> Link to e </a>\n" +
                        "<a href=\"https://f.com\"> Link to f </a>\n" + /*0.5*/
                        "</body>\n" +
                        "</html>", /* 3 + 1.5 */
                "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "  <meta property=\"og:url\" content=\"https://b.com\"/>\n" +
                        "</head>  \n" +
                        "<body>\n" +
                        "Suspendisse potenti. Vivamus venenatis tellus non turpis bibendum, \n" +
                        "<a href=\"https://a.com\"> Link to a </a>\n" +
                        "blind sed congue urna varius. Suspendisse feugiat nisl ligula, quis malesuada felis hendrerit ut.\n" +
                        "<a href=\"https://c.com\"> Link to c </a>\n" + /* 0.5 */
                        "</body>\n" +
                        "</html>", /* 1 + 1.5 */
                "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "  <meta property=\"og:url\" content=\"https://c.com\"/>\n" +
                        "</head>  \n" +
                        "<body>\n" +
                        "Ut condimentum urna at felis sodales rutrum. Sed dapibus cursus diam, non interdum nulla tempor nec. Phasellus rutrum enim at orci consectetu blind\n" +
                        "<a href=\"https://a.com\"> Link to a </a>\n" +
                        "<a href=\"https://b.com\"> Link to b </a>\n" + /* 0.5 */
                        "</body>\n" +
                        "</html>", /* 1 + 1.0 */
                "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "  <meta property=\"og:url\" content=\"https://d.com\"/>\n" +
                        "</head>  \n" +
                        "<body>\n" +
                        "Ut condimentum urna at felis sodales rutrum. Sed dapibus cursus diam, non interdum nulla tempor nec. Phasellus rutrum enim at orci consectetu blind\n" +
                        "<a href=\"https://a.com\"> Link to a </a>\n" +
                        "<a href=\"https://b.com\"> Link to b </a>\n" + /* 0.5 */
                        "</body>\n" +
                        "</html>" /* 1 + 0 */
        };
        expected = 0;
        result = test.solution(word, pages);
        Assertions.assertEquals(expected, result);
    }
}

class Page implements Comparable<Page> {

    private static int globalId = 0;

    private final int id;

    private double matchScore;

    private String html;

    private Link url;

    private List<Link> links;

    private String[] words;

    @Override
    public int compareTo(Page o) {
        if (this.matchScore < o.matchScore) {
            return -1;
        } else if (this.matchScore > o.matchScore) {
            return 1;
        } else {
            if (this.id > o.id) {
                return -1;
            } else if (this.id < o.id) {
                return 1;
            }
            return 0;
        }
    }

    static class Link {

        String url;

        Link(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return url;
        }
    }

    public Page(String html) {
        this.id = globalId++;
        this.html = html;
        this.url = findUrlByMetaTag();
        this.links = findLinks();
        this.words = html.split("[^a-zA-Z]");
    }

    private Link findUrlByMetaTag() {
        String contentAttr = "content=\"";
        String url = null;
        List<String> metaTags = findAllTag("<meta", ">");

        for (String metaTag : metaTags) {
            int start = metaTag.indexOf(contentAttr);
            int end = metaTag.indexOf("\"", start+contentAttr.length());

            if (start > 0 && end > 0) {
                url = metaTag.substring(start + contentAttr.length(), end);
                break;
            }
        }

        if (isValidUrl(url)) {
            Link myUrl = new Link(url);
            return myUrl;
        }
        else {
            throw new IllegalArgumentException("Cannot find URL in Meta Tags");
        }
    }

    private boolean isValidUrl(String url) {
        return url.indexOf("https://") == 0;
    }

    private List<Link> findLinks() {
        String attr = "href=\"";
        List<String> aTags = findAllTag("<a", ">");
        List<Link> urls = new LinkedList<>();

        for (String aTag : aTags) {
            int start = aTag.indexOf(attr);
            int end = aTag.indexOf("\"", start+attr.length());
            if (start < 0 || end < 0) {
                break;
            }

            String url = aTag.substring(start + attr.length(), end);
            if (isValidUrl(url))
                urls.add(new Link(url));
        }

        return urls;
    }

    private List<String> findAllTag(String tagHead, String tagTail) {
        LinkedList<String> metaTags = new LinkedList<>();

        int start = -1;
        int end = -1;

        while (true) {
            start = html.indexOf(tagHead, start + 1);
            end = html.indexOf(tagTail, start + 1);

            if (start < 0 || end < 0)
                break;
            metaTags.add(html.substring(start, end + tagTail.length()));
        }

        return metaTags;
    }

    @Override
    public String toString() {
        return url.toString();
    }

    public Link getUrl() {
        return url;
    }

    public List<Link> getLinks() {
        return links;
    }

    public String[] getWords() {
        return words;
    }

    public int getId() {
        return id;
    }

    public double getMatchScore() {
        return this.matchScore;
    }

    public void setMatchScore(double matchScore) {
        this.matchScore = matchScore;
    }

    public static void main(String[] args) {
        String[] pages = new String[] {
                "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "  <meta property=\"og:url\" content=\"https://a.com\"/>\n" +
                        "</head>  \n" +
                        "<body>\n" +
                        "Blind Lorem Blind ipsum dolor Blind test sit amet, consectetur adipiscing elit. \n" +
                        "<a href=\"https://b.com\"> Link to b </a>\n" +
                        "</body>\n" +
                        "</html>",
                "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "  <meta property=\"og:url\" content=\"https://b.com\"/>\n" +
                        "</head>  \n" +
                        "<body>\n" +
                        "Suspendisse potenti. Vivamus venenatis tellus non turpis bibendum, \n" +
                        "<a href=\"https://a.com\"> Link to a </a>\n" +
                        "blind sed congue urna varius. Suspendisse feugiat nisl ligula, quis malesuada felis hendrerit ut.\n" +
                        "<a href=\"https://c.com\"> Link to c </a>\n" +
                        "</body>\n" +
                        "</html>",
                "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "  <meta property=\"og:url\" content=\"https://c.com\"/>\n" +
                        "</head>  \n" +
                        "<body>\n" +
                        "Ut condimentum urna at felis sodales rutrum. Sed dapibus cursus diam, non interdum nulla tempor nec. Phasellus rutrum enim at orci consectetu blind\n" +
                        "<a href=\"https://a.com\"> Link to a </a>\n" +
                        "</body>\n" +
                        "</html>"
        };
        for (String html : pages) {
            Page page = new Page(html);
            System.out.println(page);
            System.out.println(page.links);
            System.out.println(Arrays.toString(page.words));
        }
    }
}
