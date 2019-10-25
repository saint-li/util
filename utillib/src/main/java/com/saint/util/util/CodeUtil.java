package com.saint.util.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qby on 2017/11/30 0030.
 * 编码格式转换
 */

public class CodeUtil {

    /**
     * 表情过滤器
     */
    private static InputFilter emojiFilter = new InputFilter() {


        Pattern emoji = Pattern.compile(


//                "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]",


                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);


        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                   int dstart,


                                   int dend) {


            Matcher emojiMatcher = emoji.matcher(source);


            if (emojiMatcher.find()) {


                return "";


            }
            return null;


        }
    };

    //表情过滤器
    public static InputFilter[] emojiFilters = {emojiFilter};

    /** * 检测是否有emoji表情 * @param source * @return */
    public static boolean containsEmoji(String source) {                          //两种方法限制emoji
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }


    /** * 判断是否是Emoji * @param codePoint 比较的单个字符 * @return */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 避免logcat打印中文为Unicode编码格式
     *
     * @param src
     * @return
     */
    public static String unicodeToUTF_8(String src) {
        if (null == src) {
            return null;
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < src.length(); ) {
            char c = src.charAt(i);
            if (i + 6 < src.length() && c == '\\' && src.charAt(i + 1) == 'u') {
                String hex = src.substring(i + 2, i + 6);
                try {
                    out.append((char) Integer.parseInt(hex, 16));
                } catch (NumberFormatException nfe) {
                    nfe.fillInStackTrace();
                }
                i = i + 6;
            } else {
                out.append(src.charAt(i));
                ++i;
            }
        }
        return out.toString();

    }

    //中文转Unicode
    public static String gbToUnicode(final String gbString) {   //gbString = "测试"
        if (TextUtils.isEmpty(gbString)) {
            return "";
        }
        char[] utfBytes = gbString.toCharArray();   //utfBytes = [测, 试]
        String unicodeBytes = "";
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
            String hexB = Integer.toHexString(utfBytes[byteIndex]);   //转换为16进制整型字符串
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }


    /**
     * unicode转中文
     *
     * @param str
     * @return
     * @author yutao
     * @date 2017年1月24日上午10:33:25
     */
    public static String unicodeToString(String str) {
        try {
            Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

            Matcher matcher = pattern.matcher(str);

            char ch;

            while (matcher.find()) {

                ch = (char) Integer.parseInt(matcher.group(2), 16);

                str = str.replace(matcher.group(1), ch + "");

            }

            return str;
        } catch (Exception e) {
            return "";
        }

    }
}
