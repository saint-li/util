package com.saint.util.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static String getHTMLStr(String htmlStr) {

        //先将换行符保留，然后过滤标签
        Pattern p_enter = Pattern.compile("<br/>", Pattern.CASE_INSENSITIVE);
        Matcher m_enter = p_enter.matcher(htmlStr);
        htmlStr = m_enter.replaceAll("\n");

        //过滤html标签
        Pattern p_html = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        return m_html.replaceAll("");
    }

    /**
     * 返回中文拼音及英文大写，
     *
     * @return
     */
    public static String getPinYin(String input) {
        ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance().get(input);
        StringBuilder sb = new StringBuilder();
        if (tokens != null && tokens.size() > 0) {
            for (HanziToPinyin.Token token : tokens) {
                if (HanziToPinyin.Token.PINYIN == token.type) {
                    sb.append(token.target);
                } else {
                    sb.append(token.source);
                }
            }
        } else {
            //如果获取不到实例，则返回一个特殊字符
            sb.append("#");
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 获取名字对应的首字母字符串
     * 规则：
     * 当名称为中文开头时，返回名称第一个汉字的拼音的首字母的大写
     * 当名称为英文开头时，返回第一次英文字母的大写
     * 当名称为数字开头或者是其他特殊符号时，返回字符串“其他”
     */
    public static String getFirstChar(String name) {
        if (name != null && name.length() > 0) {
            String firstChar = String.valueOf(getPinYin(name).charAt(0));
            if (Pattern.compile("[a-zA-Z]").matcher(firstChar).matches()) {//是字符
                return firstChar + "";
            } else {
                return "其他";
            }
        } else {
            return " ";
        }
    }
}
