package sliu.unit;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DicCompareUtil {

    /**
     * 匹配单选
     * @param dicJoin 字典表ID拼接
     * @param targetDic 选中值
     * @return
     */
    public static Boolean CompareSignDic(String dicJoin,String targetDic){
        if (targetDic ==null){
            return true;
        }
        List<String> params = Arrays.asList(dicJoin.split(","));
        if(params.contains(targetDic)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 匹配多选
     * @param dicJoin
     * @param targetDic
     * @return
     */
    public static Boolean CompareMuchDic(String dicJoin,String targetDic){
        if (targetDic ==null){
            return true;
        }
        List<String> params = Arrays.asList(dicJoin.split(","));
        List<String> params2 = Arrays.asList(targetDic.split(","));
        if(params.containsAll(params2)){
            return true;
        }else {
            return false;
        }
    }
    /**
     * @return 字符串的长度
     */
    public static int length(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为1 */
                valueLength += 1;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /***
     * 判断 String 是否是 int<br>通过正则表达式判断
     *
     * @param input
     * @return
     */
    public static boolean isInteger(String input){
        String temp = "^[+-]?[0-9]+$";
        Matcher mer = Pattern.compile(temp).matcher(input);
        return mer.find();
    }

    /**
     * 判断 String 是否是 double<br>通过正则表达式判断：带.号
     * @param input
     * @return
     */
    public static boolean isDouble(String input){
        String temp = "^[+-]?[0-9.]+$";
        Matcher mer = Pattern.compile(temp).matcher(input);
        return mer.find();
    }


    /**
     * 检测字符串是否为 number 类型的数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        if(str == null ){
            return false;
        }

        String strF = str.replaceAll("-", "");
        String strFormat = strF.replaceAll("\\.", "");
        if("".equals(strFormat)){
            return false;
        }

        for (int i = strFormat.length();--i>=0;){
            if (!Character.isDigit(strFormat.charAt(i))){
                return false;
            }
        }
        return true;
    }


    /**
     * 验证正则
     * @param target TEST
     * @param regular 正则表达式
     *                ^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\D])|(18[0,5-9]))\d{8}$ 联系方式
     *                (^\d{18}$)|(^\d{15}$) 身份证号
     *                [1-9]\d{5}(?!\d) 邮编
     * @return
     */
    public static Boolean regular(Object target,String regular) {
        String regEx = regular;
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target.toString());
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();
        System.out.println(rs);
        return rs;
    }

    /**
     * 判断数据是否整数部分不超过MAX,小数不超过2位
     * @param target
     * @param Max 整数最大值
     * @return
     */
    public static Boolean doubleFormat(Double target,int Max) {
        try {
            if (target>0 && target<Max){
                String targetStr = String.valueOf(target);
                if(targetStr.contains(".")){
                    int index = targetStr.indexOf(".");
                    int num = targetStr.substring(index+1,targetStr.length()).length();
                    if(num<=2){
                        return true;
                    }else {
                        return false;
                    }

                }
            }else {
                return false;
            }
            return false;
        }catch (Exception e){
            return false;
        }

    }

    public static Boolean intFormat(int target,int Max) {
        try {
            if (target>0 && target<Max){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

}
