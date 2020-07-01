package com.project.jetpack.DrugReminder.utils;

import android.content.Context;

import com.project.jetpack.DrugReminder.R;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PersianCalander {
    private class SolarCalendar {

        public String strWeekDay = "";
        public String strMonth = "";

        int date;
        int month;
        int year;

        public SolarCalendar()
        {
        }

        public SolarCalendar(Calendar calendar)
        {
            calcSolarCalendar(calendar);
        }

        private void calcSolarCalendar(Calendar calendar) {

            int ld;

            int miladiYear = calendar.get(Calendar.YEAR);
            int miladiMonth = calendar.get(Calendar.MONTH) + 1;
            int miladiDate = calendar.get(Calendar.DATE);
            int WeekDay = calendar.get(Calendar.DAY_OF_WEEK);

            int[] buf1 = new int[12];
            int[] buf2 = new int[12];

            buf1[0] = 0;
            buf1[1] = 31;
            buf1[2] = 59;
            buf1[3] = 90;
            buf1[4] = 120;
            buf1[5] = 151;
            buf1[6] = 181;
            buf1[7] = 212;
            buf1[8] = 243;
            buf1[9] = 273;
            buf1[10] = 304;
            buf1[11] = 334;

            buf2[0] = 0;
            buf2[1] = 31;
            buf2[2] = 60;
            buf2[3] = 91;
            buf2[4] = 121;
            buf2[5] = 152;
            buf2[6] = 182;
            buf2[7] = 213;
            buf2[8] = 244;
            buf2[9] = 274;
            buf2[10] = 305;
            buf2[11] = 335;

            if ((miladiYear % 4) != 0) {
                date = buf1[miladiMonth - 1] + miladiDate;

                if (date > 79) {
                    date = date - 79;
                    if (date <= 186) {
                        switch (date % 31) {
                            case 0:
                                month = date / 31;
                                date = 31;
                                break;
                            default:
                                month = (date / 31) + 1;
                                date = (date % 31);
                                break;
                        }
                        year = miladiYear - 621;
                    } else {
                        date = date - 186;

                        switch (date % 30) {
                            case 0:
                                month = (date / 30) + 6;
                                date = 30;
                                break;
                            default:
                                month = (date / 30) + 7;
                                date = (date % 30);
                                break;
                        }
                        year = miladiYear - 621;
                    }
                } else {
                    if ((miladiYear > 1996) && (miladiYear % 4) == 1) {
                        ld = 11;
                    } else {
                        ld = 10;
                    }
                    date = date + ld;

                    switch (date % 30) {
                        case 0:
                            month = (date / 30) + 9;
                            date = 30;
                            break;
                        default:
                            month = (date / 30) + 10;
                            date = (date % 30);
                            break;
                    }
                    year = miladiYear - 622;
                }
            } else {
                date = buf2[miladiMonth - 1] + miladiDate;

                if (miladiYear >= 1996) {
                    ld = 79;
                } else {
                    ld = 80;
                }
                if (date > ld) {
                    date = date - ld;

                    if (date <= 186) {
                        switch (date % 31) {
                            case 0:
                                month = (date / 31);
                                date = 31;
                                break;
                            default:
                                month = (date / 31) + 1;
                                date = (date % 31);
                                break;
                        }
                        year = miladiYear - 621;
                    } else {
                        date = date - 186;

                        switch (date % 30) {
                            case 0:
                                month = (date / 30) + 6;
                                date = 30;
                                break;
                            default:
                                month = (date / 30) + 7;
                                date = (date % 30);
                                break;
                        }
                        year = miladiYear - 621;
                    }
                }

                else {
                    date = date + 10;

                    switch (date % 30) {
                        case 0:
                            month = (date / 30) + 9;
                            date = 30;
                            break;
                        default:
                            month = (date / 30) + 10;
                            date = (date % 30);
                            break;
                    }
                    year = miladiYear - 622;
                }

            }

            switch (month) {
                case 1:
                    strMonth = "فروردين";
                    break;
                case 2:
                    strMonth = "ارديبهشت";
                    break;
                case 3:
                    strMonth = "خرداد";
                    break;
                case 4:
                    strMonth = "تير";
                    break;
                case 5:
                    strMonth = "مرداد";
                    break;
                case 6:
                    strMonth = "شهريور";
                    break;
                case 7:
                    strMonth = "مهر";
                    break;
                case 8:
                    strMonth = "آبان";
                    break;
                case 9:
                    strMonth = "آذر";
                    break;
                case 10:
                    strMonth = "دي";
                    break;
                case 11:
                    strMonth = "بهمن";
                    break;
                case 12:
                    strMonth = "اسفند";
                    break;
            }

            switch (WeekDay) {

                case 0:
                    strWeekDay = "يکشنبه";
                    break;
                case 1:
                    strWeekDay = "دوشنبه";
                    break;
                case 2:
                    strWeekDay = "سه شنبه";
                    break;
                case 3:
                    strWeekDay = "چهارشنبه";
                    break;
                case 4:
                    strWeekDay = "پنج شنبه";
                    break;
                case 5:
                    strWeekDay = "جمعه";
                    break;
                case 6:
                    strWeekDay = "شنبه";
                    break;
            }

        }

    }

    /**
     *
     * index 0: year
     * index 1: month
     * index 2: day
     * @param calendar
     * @return
     */
    public static List<Integer> getCurrentShamsidate(Calendar calendar) {
        Locale loc = new Locale("en_US");
        PersianCalander util = new PersianCalander();
        SolarCalendar sc = util.new SolarCalendar(calendar);

        return Arrays.asList(
                sc.year,
                sc.month,
                sc.date
        );
    }

    public static String getMonth(Calendar calendar, Context context, boolean withYear){
        List<Integer> date = getCurrentShamsidate(calendar);
        String month = "";

        switch (date.get(1)){
            case 1:
                month = context.getResources().getString(R.string.farvardin);
                break;
            case 2:
                month = context.getResources().getString(R.string.ordibehesht);
                break;
            case 3:
                month = context.getResources().getString(R.string.khordad);
                break;
            case 4:
                month = context.getResources().getString(R.string.tir);
                break;
            case 5:
                month = context.getResources().getString(R.string.mordad);
                break;
            case 6:
                month = context.getResources().getString(R.string.shahrivar);
                break;
            case 7:
                month = context.getResources().getString(R.string.mehr);
                break;
            case 8:
                month = context.getResources().getString(R.string.aban);
                break;
            case 9:
                month = context.getResources().getString(R.string.azar);
                break;
            case 10:
                month = context.getResources().getString(R.string.dey);
                break;
            case 11:
                month = context.getResources().getString(R.string.bahman);
                break;
            case 12:
                month = context.getResources().getString(R.string.esfand);
                break;
        }
        if (withYear)
            return String.format("%s %d", month, date.get(0));
        else return String.format("%s", month);
    }

    public static String getWeekName(int dayOfWeek, Context context){
        String day = "";

        switch (dayOfWeek) {
            case 7:
                day = context.getResources().getString(R.string.saturday);
                break;
            case 1:
                day = context.getResources().getString(R.string.sunday);
                break;
            case 2:
                day = context.getResources().getString(R.string.monday);
                break;
            case 3:
                day = context.getResources().getString(R.string.tuesday);
                break;
            case 4:
                day = context.getResources().getString(R.string.wednesday);
                break;
            case 5:
                day = context.getResources().getString(R.string.thursday);
                break;
            case 6:
                day = context.getResources().getString(R.string.friday);
                break;
        }
        return day;
    }

    public static long atEndOfDay(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    public static long atStartOfDay(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getWeekStartDate() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        return calendar.getTimeInMillis();
    }

    public static long getWeekEndDate() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        calendar.add(Calendar.DATE, 1);
        return calendar.getTimeInMillis();
    }

    public static long startMonth(Calendar calendar){
        List<Integer> date = PersianCalander.getCurrentShamsidate(calendar);
        date.set(2, 1);
        Calendar calendar1 = Calendar.getInstance();


        calendar1.set(Calendar.YEAR, JalaliCalendar.jalaliToGregorian(new JalaliCalendar.YearMonthDate(date.get(0), date.get(1) - 1, date.get(2))).getYear());
        calendar1.set(Calendar.MONTH, JalaliCalendar.jalaliToGregorian(new JalaliCalendar.YearMonthDate(date.get(0), date.get(1) - 1, date.get(2))).getMonth());
        calendar1.set(Calendar.DATE, JalaliCalendar.jalaliToGregorian(new JalaliCalendar.YearMonthDate(date.get(0), date.get(1) - 1, date.get(2))).getDate());

        return calendar1.getTimeInMillis();
    }

    public static long endMonth(Calendar calendar){
        List<Integer> date = PersianCalander.getCurrentShamsidate(calendar);
        if (date.get(1) >= 1 && date.get(1) <= 6) {
            date.set(2, 31);
        }else if (date.get(1) >= 7 && date.get(1) <= 11){
            date.set(2, 30);
        }else if(date.get(0) % 4 == 3){
            date.set(2, 30);
        }else date.set(2, 29);
        Calendar calendar1 = Calendar.getInstance();


        calendar1.set(Calendar.YEAR, JalaliCalendar.jalaliToGregorian(new JalaliCalendar.YearMonthDate(date.get(0), date.get(1) - 1, date.get(2))).getYear());
        calendar1.set(Calendar.MONTH, JalaliCalendar.jalaliToGregorian(new JalaliCalendar.YearMonthDate(date.get(0), date.get(1) - 1, date.get(2))).getMonth());
        calendar1.set(Calendar.DATE, JalaliCalendar.jalaliToGregorian(new JalaliCalendar.YearMonthDate(date.get(0), date.get(1) - 1, date.get(2))).getDate());

        return calendar1.getTimeInMillis();
    }

    public static long endYear(Calendar calendar){
        List<Integer> date = PersianCalander.getCurrentShamsidate(calendar);
        date.set(1, 12);
        if (date.get(1) >= 1 && date.get(1) <= 6) {
            date.set(2, 31);
        }else if (date.get(1) >= 7 && date.get(1) <= 11){
            date.set(2, 30);
        }else if(date.get(0) % 4 == 3){
            date.set(2, 30);
        }else date.set(2, 29);
        Calendar calendar1 = Calendar.getInstance();


        calendar1.set(Calendar.YEAR, JalaliCalendar.jalaliToGregorian(new JalaliCalendar.YearMonthDate(date.get(0), date.get(1) - 1, date.get(2))).getYear());
        calendar1.set(Calendar.MONTH, JalaliCalendar.jalaliToGregorian(new JalaliCalendar.YearMonthDate(date.get(0), date.get(1) - 1, date.get(2))).getMonth());
        calendar1.set(Calendar.DATE, JalaliCalendar.jalaliToGregorian(new JalaliCalendar.YearMonthDate(date.get(0), date.get(1) - 1, date.get(2))).getDate());

        return calendar1.getTimeInMillis();
    }
    public static long startYear(Calendar calendar){
        List<Integer> date = PersianCalander.getCurrentShamsidate(calendar);
        date.set(1, 1);
        date.set(2, 1);
        Calendar calendar1 = Calendar.getInstance();


        calendar1.set(Calendar.YEAR, JalaliCalendar.jalaliToGregorian(new JalaliCalendar.YearMonthDate(date.get(0), date.get(1) - 1, date.get(2))).getYear());
        calendar1.set(Calendar.MONTH, JalaliCalendar.jalaliToGregorian(new JalaliCalendar.YearMonthDate(date.get(0), date.get(1) - 1, date.get(2))).getMonth());
        calendar1.set(Calendar.DATE, JalaliCalendar.jalaliToGregorian(new JalaliCalendar.YearMonthDate(date.get(0), date.get(1) - 1, date.get(2))).getDate());

        return calendar1.getTimeInMillis();
    }

}
