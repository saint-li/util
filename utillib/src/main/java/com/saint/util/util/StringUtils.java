package com.saint.util.util;

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
}
