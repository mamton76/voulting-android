package com.example.mamton.testapp;

import android.support.annotation.Nullable;

import com.example.mamton.testapp.test.ArrayUtils;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest1 {


    @Test
    public void testStrings() {
        assertTrue(findSubstr("ac", "ac"));
        assertTrue(findSubstr("abcd", "ac"));
        assertTrue(findSubstr("abcd", null));
        assertTrue(findSubstr("abcd", ""));
        assertTrue(findSubstr("abcd", "cd"));
        assertTrue(findSubstr("abcd", "c"));
        assertFalse(findSubstr("abcd", "cc"));
        assertFalse(findSubstr(null, "cc"));
    }

    public boolean findSubstr(@Nullable String s1, @Nullable String s2) {
        if (s2 == null || s2.length() == 0) {
            return true;
        }

        if (s1 == null || s1.length() < s2.length()) {
            return false;
        }

        byte[] s1Bytes = s1.getBytes();
        byte[] s2Bytes = s2.getBytes();

        boolean lastFound = true;
        int lastS = -1;

        for (int i = 0; lastFound && (i < s2Bytes.length); i++) {
            lastFound = false;
            for (int j = lastS + 1; !lastFound && j < s1Bytes.length; j++) {
                if (s1Bytes[j] == s2Bytes[i]) {
                    lastFound = true;
                    lastS = j;
                }
            }
        }

        return lastFound;
    }


    @Test
    public void testStrings2() {
        assertTrue(testSubstr2("ac", "ac"));
        assertTrue(testSubstr2("abcd", "ba"));
        assertTrue(testSubstr2("abcd", null));
        assertTrue(testSubstr2("abcd", ""));
        assertTrue(testSubstr2("abcd", "bc"));
        assertTrue(testSubstr2("abcd", "ab"));
        assertTrue(testSubstr2("abcd", "c"));
        assertFalse(testSubstr2("abcd", "cc"));
        assertFalse(testSubstr2("abcd", "ac"));
        assertFalse(testSubstr2(null, "cc"));
    }


    boolean testSubstr2(@Nullable String s1, @Nullable String s2) {
        if (s2 == null || s2.length() == 0) {
            return true;
        }

        if (s1 == null || s1.length() < s2.length()) {
            return false;
        }

        if (s2.length() > s1.length()) {
            return false;
        }

        byte[] s2b = s2.getBytes();
        Arrays.sort(s2b);

        boolean found = false;

        int lastDiffSize = 0;


        byte[] s1b = s1.getBytes();
        for (int i = 0; !found && i <= s1b.length - s2b.length; i = i + lastDiffSize) {
            List<Byte> lbytes = ArrayUtils.toList(s2b);

            for (int j = 0; j < s2b.length; j++) {
                lbytes.remove((Byte) s1b[i + j]);
            }
            lastDiffSize = lbytes.size();

            if (lastDiffSize == 0) {
                found = true;
            }
        }

        return found;
    }

    @Test
    public void testSym() throws Exception {
        assertTrue(hasLine(Arrays.asList(new Point(0, 1), new Point(1, 1), new Point(2, 1))));
        assertTrue(hasLine(Arrays.asList(new Point(1, 1), new Point(2, 1),
                                         new Point(1, 1), new Point(2, 1))));
        assertTrue(hasLine(Arrays.asList(new Point(0, 0), new Point(-1, 1), new Point(1, 1))));
        assertTrue(hasLine(Arrays.asList(new Point(-3, 0), new Point(-1, 0),
                                         new Point(0, 1), new Point(-4, 1))));
        assertFalse(hasLine(Arrays.asList(new Point(0, 0), new Point(1, 1), new Point(2, 1))));
        assertFalse(hasLine(Arrays.asList(new Point(1, 1), new Point(2, 1), new Point(4, 1))));

    }

    private boolean hasLine(final List<Point> src) {
        Float minX = Float.MAX_VALUE;
        Float maxX = Float.MIN_VALUE;

        for (Point point : src) {
            if (point.x < minX) {
                minX = (float) point.x;
            } else if (point.x > maxX) {
                maxX = (float) point.x;
            }
        }
        float avgX = ((float) (maxX + minX)) / 2;

        Map<Float, Map<Float, Float>> map = new HashMap<>();
        for (Point point : src) {
            float dist = point.x - avgX;
            float sum = dist;

            Map<Float, Float> ySums = map.get((float) point.y);
            if (ySums == null) {
                ySums = new HashMap<>();
                map.put((float) point.y, ySums);
            }

            float distKey = Math.abs(dist);

            if (ySums.containsKey(distKey)) {
                sum += ySums.get(distKey);
            }
            ySums.put(distKey, sum);
        }

        boolean hasResult = true;

        Iterator<Map<Float, Float>> yIterator = map.values().iterator();
        while (yIterator.hasNext() && hasResult) {
            Map<Float, Float> nextY = yIterator.next();
            Iterator<Float> distIterator = nextY.values().iterator();
            while (distIterator.hasNext() && hasResult) {
                Float aFloat = distIterator.next();
                if (aFloat != 0f) {
                    hasResult = false;
                }
            }

        }

        System.out.println(String.format("avgX %s", avgX));
        System.out.println(String.format("map %s", map));
        System.out.println(hasResult);
        return hasResult;
    }

    @Test
    public void tesInc() throws Exception {
        char[] str = "aa".toCharArray();
        increment("ss999aa".toCharArray());
        increment("999aa".toCharArray());
        increment("a10a".toCharArray());
        increment("aa39".toCharArray());
        increment("aa32".toCharArray());

    }

    private void increment(final char[] str) {
        System.out.print(new String(str) + ' ');
        boolean start = false;
        for (int i = str.length - 1; i >= 0; i--) {
            if (Character.isDigit(str[i])) {
                if (str[i] == '9') {
                    start = true;
                    str[i] = '0';
                } else {
                    str[i] += 1;
                    break;
                }
            } else if (start) {
                break;
            }
        }
        System.out.println(new String(str));
    }

    class Point {
        int x, y;
        Point(final int x, final int y) {
            this.x = x;
            this.y = y;
        }
    }

}