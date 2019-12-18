package com.xinyu.simple.common.comparator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 *@Author xinyu
 *@Date 17:00 2019/12/18
 *@Description  名字比较器，按照windows排序规则进行比较
 **/
public class FileNameComparator implements Comparator<String> {

    /**
     * 比较规则：
     * @1、单个非数字字符算作一个比较单元，比较时忽略大小写
     * @2、出现连续数字，将连续数字看成一个整数，算作一个比较单元，数字 小于 非数字字符
     * @3、如果相等，则单独比较其中的数字单元，规则：数字位数较多的小于位数较少的
     *
     */
    @Override
    public int compare(String s1, String s2) {
        if (s1 == null)
            return 1;
        if (s2 == null)
            return -1;

        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        if (s1.equals(s2))
            return 0;

        List<String> ss1 = split(s1);
        List<String> ss2 = split(s2);

        // 取两个比较单元的最小长度
        int len = ss1.size() < ss2.size() ? ss1.size() : ss2.size();

        // 比较结果
        int r = 0;

        String t1 = null;
        String t2 = null;
        boolean b1 = false;
        boolean b2 = false;

        for (int i = 0; i < len; i++) {
            t1 = ss1.get(i);
            t2 = ss2.get(i);

            b1 = Character.isDigit(t1.charAt(0));
            b2 = Character.isDigit(t2.charAt(0));

            // t1是数字 t2非数字
            if (b1 && !b2)
                return -1;

            // t2是数字 t1非数字
            if (!b1 && b2)
                return 1;

            // t1、t2 非数字
            if (!b1 && !b2) {
                r = t1.compareTo(t2);
                if (r != 0)
                    return r;
            }

            // t1 t2都是数字
            if (b1 && b2) {
                r = compareNumber(t1, t2);
                if (r != 0)
                    return r;
            }
        }
        // 如果两个集合的 0-(len-1)部分相等
        if (r == 0) {
            if (ss1.size() > ss2.size()) {
                r = 1;
            } else if (ss1.size() < ss2.size()) {
                r = -1;
            } else {
                r = compareNumberPart(s1, s2);
            }
        }
        return r;
    }

    /**
     * 将传入的字符串拆分为 由比较单元组成的数组
     * @param s
     * @return
     */
    protected List<String> split(String s) {

        List<String> list = new ArrayList<String>();

        char[] cs = s.toCharArray();
        int tmp = -1;

        for (int i = 0; i < cs.length; i++) {
            char c = cs[i];
            if (Character.isDigit(c)) {
                if (tmp < 0) {
                    tmp = i;
                }
            } else {
                if (tmp >= 0) {
                    list.add(s.substring(tmp, i));
                    tmp = -1;
                }
                list.add(String.valueOf(c));
            }
        }

        // 如果最后一个是数字,将最后的数字加入list中
        if (cs.length>0 && Character.isDigit(cs[cs.length - 1])) {
            tmp = tmp < 0 ? cs.length - 1 : tmp;
            list.add(s.substring(tmp, cs.length));
            tmp = -1;
        }

        return list;

    }

    /**
     * 比较各数字单元的长度
     * @param s1
     * @param s2
     * @return
     */
    private int compareNumberPart(String s1, String s2) {
        int r = 0;
        String[] ss1 = s1.split("\\D+");
        String[] ss2 = s2.split("\\D+");

        for (int i = 0; i < ss1.length; i++) {
            r = compareValueEqualNumber(ss1[i], ss2[i]);
            if (r != 0)
                return r;
        }
        return r;
    }

    /**
     * 比较两个数字字符串表示的数字值的大小
     * @param s1
     * @param s2
     * @return
     */
    private int compareNumber(String s1, String s2) {
        int max = String.valueOf((Integer.MAX_VALUE)).length() - 1;
        int r = 0;
        if (s1.length() > max || s2.length() > max) {
            r = new BigInteger(s1).compareTo(new BigInteger(s2));
        } else {
            r = Integer.valueOf(s1).compareTo(Integer.valueOf(s2));
        }
        return r;
    }

    /**
     * 比较数字相等的数字文本的大小，规则：位数较多者较小
     * @param s1
     * @param s2
     * @return
     */
    private int compareValueEqualNumber(String s1, String s2) {
        int r = 0;
        if (s1.length() > s2.length()) {
            r = -1;
        } else if (s1.length() < s2.length()) {
            r = 1;
        } else {
            r = 0;
        }
        return r;
    }


    public static void main(String[] args) {
        String[] names = new String[] { "13a", "000013c", "013cd", "1ab30cd",
                "a0010b010c020", "3123", "ad10c", "014", "ab010a", "0289",
                "bfhd", "cds", "a10b10c10", "bf", "cgf", "ak", "ak8",
                "a010b10c30", "a00100", "a0020", "ac0010b", "ab3", "a001ac",
                "a0001a", "a001a", "a00001a", "a001aa", "a001", "a002",
                "a0001", "a01", "1", "0001", "01", "001", "010", "011",
                "a2918238791982739729b", "a0002918238791982739729b",
                "a0002918238791982739729848b", "a02918238791982739729b",
                "a0002918238791982739729848484b", "a02918238791982739719b",
                "020", "010", "010a", "0010a", "010a20", "000", "0", "00000",
                "000", "00", "0", "0000a01", "000a001", "00a00000000001",
                "a001", "a01", "a", "a0" };
        long t1 = System.nanoTime();
        long t2 = System.nanoTime();
        System.out.println(t2 - t1);

        Comparator<String> comparator = new FileNameComparator();
        Arrays.sort(names, comparator);

        System.out.println(Arrays.toString(names));
        for (String s : names) {
            System.out.println(s);
        }
    }
}

