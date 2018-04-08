package com.hishixi;



import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class StringUtils {
    /**
     * 现有电话号码的正则表达式
     */
    private static String tel_reg = "^(1[34578])[0-9]{9}$";
    /**
     * 邮箱的正则表达式$reg = '/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/';
     */
    private static String email_reg = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    // private static String email_reg =
    // "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * 身份证号的正则表达式 [1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}(\d{1}|X{1})
     */
//    private static Pattern identity_card = Pattern
//            .compile("^([1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}" +
//                    "(\\d|X|x))$");
    /**
     * 0-8位的汉字
     */
    private static String hanzi_reg = "^[\u4E00-\u9FA5]$";

    private static String password_reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";

    /**
     * 判断是否是汉字
     *
     * @param s str
     * @return boolean
     */
    public static boolean isHanzi(String s) {
        Pattern pattern = Pattern.compile(hanzi_reg);
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }

    /***
     * 判断是否是正确的电话号码
     *
     * @param str phone
     * @return boolean
     */
    public static boolean isPhoneNum(String str) {
        Pattern pattern = Pattern.compile(tel_reg);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /***
     * 判断是否是正确的密码
     *
     * @param str pwd
     * @return boolean
     */
    public static boolean isPassword(String str) {
        Pattern pattern = Pattern.compile(password_reg);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isEmail(String str) {
        Pattern pattern = Pattern.compile(email_reg);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 判断字符串是否为null或""
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (null == str) || (str.trim().length() == 0)
                || str.equals("null");
    }

    /**
     * 是否不为空
     *
     * @param paramString
     * @return
     */
    public static boolean isNotEmpty(String paramString) {
        return !isEmpty(paramString) && !paramString.equals("null");
    }

    /**
     * 得到年月日的数组
     *
     * @return Time
     */
    @SuppressWarnings("null")
    public static int[] getTime(String time, String mSplitChar) {
        int[] strArray = null;
        if (StringUtils.isNotEmpty(time) && !time.equals("0000.00.00")
                && !time.equals("0000-00-00")) {
            String[] startArray = time.split("\\" + mSplitChar);
            if (null != startArray || startArray.length >= 3) {
                strArray = new int[3];
                strArray[0] = Integer.parseInt(startArray[0]);
                strArray[1] = Integer.parseInt(startArray[1]) - 1;
                strArray[2] = Integer.parseInt(startArray[2]);
                return strArray;
            }
        }
        return getTime(mSplitChar);
    }

    private static int[] getTime(String mSplitChar) {
        int[] strArray = null;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        strArray = new int[3];
        strArray[0] = year;
        strArray[1] = month;
        strArray[2] = day;
        return strArray;
    }

    /**
     * 半角转全角
     *
     * @param input 半角
     * @return 全角
     */
    public static String ToSBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /**
     * 全角转换为半角
     *
     * @param input 全角
     * @return 半角
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * * 去除特殊字符或将所有中文标号替换为英文标号
     *
     * @param str str
     * @return str
     */
    public static String stringFilter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]")
                .replaceAll("！", "!").replaceAll("：", ":").replaceAll("，", ",")
                .replaceAll("？", "?");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String digit2Number(String d) {
        //        String[] str = { "零", "壹", "贰", "叁", "肆", "伍",
        // "陆", "柒", "捌", "玖" };
        String[] str = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        //        String ss[] = new String[] { "元", "拾", "佰", "仟", "万",
        // "拾", "佰", "仟", "亿" };
        String ss[] = new String[]{"", "十", "百", "千", "万", "十", "百", "千", "亿"};
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < d.length(); i++) {
            String index = String.valueOf(d.charAt(i));
            sb = sb.append(str[Integer.parseInt(index)]);
        }
        String sss = String.valueOf(sb);
        int i = 0;
        for (int j = sss.length(); j > 0; j--) {
            sb = sb.insert(j, ss[i++]);
        }
        return  sb.toString();
    }

}
