package com.dxj.common.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 输入一个字符串s，我们可以删除字符串s中的任意字符，让剩下的字符串形成一个对称字符串，且该字符串为最长对称字符串。
 * 如：输入google，则找到最长对称字符串为goog；如输入abcda则能找到最长对称字符串为aba。
 * 若最长对称字符串存在多个，则输出多个相同长度的最长对称字符串。
 *
 * @Author: dxj
 * @Date: 2019-07-11 16:08
 */
public class Test1 {

    private static String[] stringFilter(String str) {
        String regEx = "[^a-zA-Z0-9]";
        Pattern p = Pattern.compile(regEx);
        return p.split(str);
    }

    private static void getAllLcs(String a, String b, int[][] mux, int i, int j, String path, Set<String> paths) {

        StringBuilder pathBuilder = new StringBuilder(path);
        while (i > 0 && j > 0) {
            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                pathBuilder.append(a.charAt(i - 1));
                --i;
                --j;
            } else {
                if (mux[i - 1][j] > mux[i][j - 1]) {
                    --i;
                } else if (mux[i - 1][j] < mux[i][j - 1]) {
                    --j;
                } else {
                    getAllLcs(a, b, mux, i - 1, j, pathBuilder.toString(), paths);
                    getAllLcs(a, b, mux, i, j - 1, pathBuilder.toString(), paths);
                    return;
                }
            }
        }
        paths.add(pathBuilder.toString());
    }

    private static String findLCS(String input) {
        if (input == null || input.length() == 0) {
            return "";
        }

        // 将字符串反转
        String reverse = new StringBuilder(input).reverse().toString();

        // 字符串长度
        int len = input.length();

        // 矩阵 -> 二维数组
        int[][] tmp = new int[len + 1][len + 1];

        for (int i = 1; i < len + 1; i++) {
            for (int j = 1; j < len + 1; j++) {
                if (input.charAt(i - 1) == reverse.charAt(j - 1)) {
                    tmp[i][j] = tmp[i - 1][j - 1] + 1;
                } else {
                    tmp[i][j] = Math.max(tmp[i - 1][j], tmp[i][j - 1]);
                }
            }
        }

        Set<String> paths = new HashSet<String>() {
        };
        Test1.getAllLcs(input, reverse, tmp, input.length(), reverse.length(), "", paths);

        return String.join("/", paths);
    }

    private static String max(String input) {
        String[] prepare = stringFilter(input);
        StringBuilder sb = new StringBuilder();
        for (String str : prepare) {
            String result = findLCS(str);
            sb.append(result);
            sb.append("/");
        }
        return sb.substring(0, sb.length() - 1);
    }

    public static void main(String[] args) {

        // TODO 输出最长对称字符串：goog
        String input1 = "google";
        System.out.println(max(input1));
        // TODO 输出3个最长对称字符串：aba/aca/ada
        String input2 = "abcda";
        System.out.println(max(input2));
        // TODO 输出2个最长对称字符串：pop/upu，不会输出特殊字符的对称字符串p-p
        String input3 = "pop-upu";
        System.out.println(max(input3));
    }
}
