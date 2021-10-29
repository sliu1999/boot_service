package sliu.unit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DateUtil {


     //获取当前时间yyyy-MM-dd HH:mm:ss
    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

    //获取当前月份
    public static int getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        return currentMonth;
    }
    //获取当前时间yyyy-MM-dd HH
    public static String getCurrentHour() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        return dateFormat.format(new Date());
    }
    //获取当前时间yyyy-MM
    public static String getCurrentYearMonth() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return dateFormat.format(new Date());
    }
    //获取当前时间yyyy-MM
    public static String getLastYearMonth() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return simpleDateFormat.format(c.getTime());
    }
    //获取当前时间yyyy年MM月
    public static String getLastYearMonth2() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return simpleDateFormat.format(c.getTime());
    }
    //获取当前时间MM-dd
    public static String getMonthDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        return simpleDateFormat.format(new Date());
    }

    //获取当前时间yyyy-MM-dd
    public static String getYearMonthDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    //判断当前时间是否在某一时间范围内
    public static int compareDate(String startTime, String endTime) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = simpleDateFormat.parse(startTime);
            Date now = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Date endDate = simpleDateFormat.parse(endTime);
            if (now.compareTo(startDate) < 0) {
                //当前时间小于时间范围
                return -1;
            } else if (now.compareTo(startDate) >= 0 && now.compareTo(endDate) <= 0) {
                //当前时间在时间范围内
                return 0;
            } else if (now.compareTo(endDate) > 0) {
                //当前时间超过时间范围
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    //获取某一时间加天后的时间
    public static String getDateAdd(String date, int addDay) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int time1 = Integer.parseInt(date.substring(0, 4));
        int time2 = Integer.parseInt(date.substring(5, 7)) - 1;
        int time3 = Integer.parseInt(date.substring(8, 10));
        GregorianCalendar calendar = new GregorianCalendar(time1, time2, time3);
        calendar.add(Calendar.DATE, addDay);
        return format.format(calendar.getTime());
    }

    public static String getDateTimeAdd(String date, int addDay) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int time1 = Integer.parseInt(date.substring(0, 4));
        int time2 = Integer.parseInt(date.substring(5, 7)) - 1;
        int time3 = Integer.parseInt(date.substring(8, 10));
        int time4 = Integer.parseInt(date.substring(11, 13));
        int time5 = Integer.parseInt(date.substring(14, 16));
        int time6 = Integer.parseInt(date.substring(17, 19));
        GregorianCalendar calendar = new GregorianCalendar(time1, time2, time3,time4, time5, time6);
        calendar.add(Calendar.DATE, addDay);
        return format.format(calendar.getTime());
    }

    public static String getCurrentYearMonthDayAdd(int addDay) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        c.add(Calendar.DATE, addDay);
        return simpleDateFormat.format(c.getTime());
    }

    public static String getCurrentMonthDayAdd(int addDay) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        c.add(Calendar.DAY_OF_MONTH, addDay);
        return simpleDateFormat.format(c.getTime());
    }

    public static String getCurrentDayAdd(int addDay) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        c.add(Calendar.DAY_OF_MONTH, addDay);
        return simpleDateFormat.format(c.getTime());
    }

    public static String getCurrentDateAddMonth(int addMonth) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        c.add(Calendar.MONTH, addMonth);
        return simpleDateFormat.format(c.getTime());
    }

    public static String getCurrentDateAddYear(int addYear) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        c.add(Calendar.YEAR, addYear);
        return simpleDateFormat.format(c.getTime());
    }

    /**
     * 时间与当前时间比较
     *
     * @param str
     * @return
     */
    public static boolean isValidDateYM(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        String s = str.replaceAll("[/\\ ]", "");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String currentTime = format.format(new Date());
        try {
            Date date = format.parse(s);
            //判断是否大于当前时间
            if (format.parse(s).compareTo(format.parse(currentTime)) > 0) {
                return false;
            }
            if (!format.format(date).equals(s)) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 时间与当前时间比较
     *
     * @param str
     * @return
     */
    public static boolean isValidDateYMNoNow(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        String s = str.replaceAll("[/\\ ]", "");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(s);
            if (!format.format(date).equals(s)) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 时间与当前时间比较
     *
     * @param str
     * @return
     */
    public static boolean isValidDateYMD(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        String s = str.replaceAll("[/\\ ]", "");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = format.format(new Date());
        try {
            Date date = format.parse(s);
            //判断是否大于当前时间
            if (format.parse(s).compareTo(format.parse(currentTime)) > 0) {
                return false;
            }
            if (!format.format(date).equals(s)) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    /**
     * 时间与当前时间比较
     *
     * @param str
     * @return
     */
    public static boolean isValidDateYMDhms(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = format.format(new Date());
        try {
            Date date = format.parse(str);

            if (!format.format(date).equals(str)) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 时间与当前时间比较
     *
     * @param str
     * @return
     */
    public static boolean isValidDateHm(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        String s = str.replaceAll("[/\\ ]", "");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            Date date = format.parse(s);
            if (!format.format(date).equals(s)) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean isValidDateMD(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        String s = str.replaceAll("[/\\ ]", "");
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        try {
            Date date = format.parse(s);
            if (!format.format(date).equals(s)) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        return true;
    }


    /**
     * 判断年月 比较两个日期大小
     *
     * @return
     */
    public static boolean isValidDateYMCompare(String start, String end) {
        String date1 = start;
        String date2 = end;
        int compareTo = date1.compareTo(date2);
        if (compareTo > 0) {
            return false;
        } else if (compareTo == 0) {
            return true;
        } else {
            return true;
        }

    }
    /**
     * 判断年月 比较两个日期大小
     *
     * @return
     */
    public static boolean isValidDateYMComparess(String start, String end) {
        String date1 = start;
        String date2 = end;
        int compareTo = date1.compareTo(date2);
        if (compareTo > 0) {
//            System.out.println("date1 大于 date2");
            return false;
        } else if (compareTo == 0) {
//            System.out.println("date1 等于 date2");
            return false;
        } else {
//            System.out.println("date1 小于 date2");
            return true;
        }

    }
    /**
     * 获取当前时间 5前数据
     */
    public static String  getHalfNow() {
        long curren = System.currentTimeMillis();
        curren -= 30 * 60 * 1000;
        Date da = new Date(curren);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateFormat.format(da));
        return dateFormat.format(da);
    }

    /**
     * 根据开始时间和结束时间返回时间段内的时间集合
     * @param beginDate
     * @param endDate
     * @return List<Date>
     * @throws ParseException
     */
    public static List<String> getDatesBetweenTwoDate(String beginDate, String endDate){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> lDate = new ArrayList<String>();
        //把开始时间加入集合
        lDate.add(beginDate);
        Calendar cal = Calendar.getInstance();
        //使用给定的 Date 设置此 Calendar 的时间
        try {
            cal.setTime(sdf.parse(beginDate));
            boolean bContinue = true;
            while (bContinue) {
                //根据日历的规则，为给定的日历字段添加或减去指定的时间量
                cal.add(Calendar.DAY_OF_MONTH, 1);
                // 测试此日期是否在指定日期之后
                if (sdf.parse(endDate).after(cal.getTime())) {
                    lDate.add(sdf.format(cal.getTime()));
                } else {
                    break;
                }
            }
            if(!endDate.equals(beginDate)){
                //把结束时间加入集合
                lDate.add(endDate);
            }
        }catch (Exception e){
            System.out.println("时间计算异常");
            e.getStackTrace();
        }

        return lDate;
    }

    /**
     * 根据开始时间前七天和结束时间返回时间段内的时间集合
     * @param beginDate
     * @param endDate
     * @return List<Date>
     * @throws ParseException
     */
    public static List<String> getDatesBetweenTwoDateQianQ(String beginDate, String endDate) throws ParseException {
        beginDate =beginDate.substring(0, 10);
        endDate =endDate.substring(0, 10);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> lDate = new ArrayList<String>();
        lDate.add(beginDate);//把开始时间加入集合
        Calendar cal = Calendar.getInstance();
        //使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(sdf.parse(beginDate));
        cal.add(Calendar.DAY_OF_MONTH,-7);
        boolean bContinue = true;
        while (bContinue) {
            //根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (sdf.parse(endDate).after(cal.getTime())) {
                lDate.add(sdf.format(cal.getTime()));
            } else {
                break;
            }
        }
        if(!endDate.equals(beginDate)){
            lDate.add(endDate);//把结束时间加入集合
        }
        return lDate;
    }


    /**
     * 根据时间字符串 取前一天的日期
     * @param beginDate
     * @return List<Date>
     * @throws ParseException
     */
    public static String getDateNoYIByString(String beginDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        //使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(sdf.parse(beginDate));
        cal.add(Calendar.DAY_OF_MONTH,-1);
        return  sdf.format(cal.getTime());
    }

    public static List<String> getMonthList(String beginDate,String endDate) throws ParseException {
        List<String> dateList = new ArrayList<>(10);
        if(beginDate.equals(endDate)){
            dateList.add(beginDate);
            return dateList;
        }else {
            dateList.add(beginDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Calendar cal = Calendar.getInstance();
            //使用给定的 Date 设置此 Calendar 的时间
            cal.setTime(sdf.parse(beginDate));
            for (int i = 1;i<20;i++){
                cal.add(Calendar.MONTH,1);
                String tempDate = sdf.format(cal.getTime());
                if(!tempDate.equals(endDate)){
                    dateList.add(tempDate);
                }else {
                    dateList.add(endDate);
                    return dateList;
                }
            }
            return null;
        }
    }

    public static List<String> getLastSevenDayList() throws ParseException {
        List<String> dateList = new ArrayList<>(10);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDate = sdf.format(new Date());
        dateList.add(firstDate);
        Calendar cal = Calendar.getInstance();
        //使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(new Date());
        for (int i = 1; i < 7; i++) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
            String tempDate = sdf.format(cal.getTime());
            dateList.add(tempDate);

        }
        return dateList;
    }

    public static String nowMonthLastDay() throws ParseException {
        Calendar  calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-01").format(new Date())));
        calendar.add(Calendar.MONTH,1);
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        String r = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())+" 23:00:00";
        System.out.print(r);
        return r;
    }


}
