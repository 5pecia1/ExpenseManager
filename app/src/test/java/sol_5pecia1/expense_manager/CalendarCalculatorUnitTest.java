package sol_5pecia1.expense_manager;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import sol_5pecia1.expense_manager.util.CalendarCalculatorKt;
import sol_5pecia1.expense_manager.util.Custom8;

import static org.junit.Assert.assertEquals;

/**
 * Created by sol on 2016-12-22.
 */

public class CalendarCalculatorUnitTest {
    private final static SimpleDateFormat dataForamt
            = new SimpleDateFormat("yyyy-MM-dd");

    private static final List<Integer> addWeekDays
            = Arrays.asList(7, 14, 301, 1001);
    private static final List<Integer> addSameDays
            = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 3, 31, 370);//weekday, month, year
    private static final List<Integer> addWeekendDays
            = Arrays.asList(1, 2, 3);


    private static Calendar startWeekDayCalendar;
    private static Calendar fridayCalendar;
    private static Calendar saturdayCalendar;

    @BeforeClass
    public static void calenderCalculatorKt_Main() {
        startWeekDayCalendar = GregorianCalendar.getInstance();

        int dayOfWeek = startWeekDayCalendar.get(Calendar.DAY_OF_WEEK);

        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            startWeekDayCalendar.add(Calendar.DAY_OF_MONTH, -2);
        }

        fridayCalendar = new GregorianCalendar(2016, 11, 23);
        saturdayCalendar = new GregorianCalendar(2016, 11, 24); //X-mas Eve ㅜ.ㅜ


        System.out.println("== start ==");
        System.out.println("startWeekDayCalendar : "
                + dataForamt.format(startWeekDayCalendar.getTime())
        );
        System.out.println("== end ==");
        System.out.println();
    }

    @Before
    @After
    public void addBlankLine() {
        System.out.println();
    }


    @Test
    public void getSelectedRangeWeekendCount_Weekday() {
        assertEqualsSupport(
                CalendarCalculatorKt::getSelectedRangeWeekendCount
                , addWeekDays
                , startWeekDayCalendar
                , day -> day / 7 * 2
        );
    }

    @Test
    public void getSelectedRangeWeekendCount_SameDay() {
        for (Integer day : addSameDays) {
            Calendar start = (Calendar)startWeekDayCalendar.clone();
            start.add(Calendar.DAY_OF_MONTH, day);

            System.out.println("Start Calendar : "
                    + dataForamt.format(start.getTime())
            );

            assertEqualsSupport(
                    CalendarCalculatorKt::getSelectedRangeWeekendCount
                    , start
                    , d -> 0
            );
        }
    }

    @Test
    public void getSelectedRangeWeekendCount_Weekend() {
        assertEqualsSupport(
                CalendarCalculatorKt::getSelectedRangeWeekendCount
                , addWeekendDays
                , fridayCalendar
                , new Custom8.Function<Integer, Integer>() {
                    int result = 0;

                    @Override
                    public Integer apply(Integer day) {
                        return result++;
                    }
                }
        );
    }

    @Test
    public void getSelectedRangeWeekdayCount_Weekend() {
        assertEqualsSupport(
                CalendarCalculatorKt::getSelectedRangeWeekdayCount
                , Arrays.asList(0, 1, 2, 3)
                , fridayCalendar
                , new Custom8.Function<Integer, Integer>() {
                    int i = -1;

                    @Override
                    public Integer apply(Integer day) {
                        if (i  != 1){
                            i ++;
                        }
                        return i;
                    }
                }
        );
    }

    @Test
    public void getSelectedRangeWeekdayCount_Weekday() {
        assertEqualsSupport(
                CalendarCalculatorKt::getSelectedRangeWeekdayCount
                , Arrays.asList(0, 1, 2, 3, 4, 5)
                , saturdayCalendar
                , new Custom8.Function<Integer, Integer>() {
                    int i = 1;

                    @Override
                    public Integer apply(Integer day) {
                        return (day < 3)? 0 : i++;
                    }
                }
        );
    }

    @Test
    public void getSelectedRangeDayCount_Weekday() {
        assertEqualsSupport(
                CalendarCalculatorKt::getSelectedRangeDayCount
                , addWeekDays
                , startWeekDayCalendar
                , new Custom8.Function<Integer, Integer>(){
                    int i = 0;

                    @Override
                    public Integer apply(Integer day) {
                        return addWeekDays.get(i++);
                    }
                }
        );
    }

    @Test
    public void getSelectedRangeDayCount_SameDay() {
        for (Integer day : addSameDays) {
            Calendar start = (Calendar)startWeekDayCalendar.clone();
            start.add(Calendar.DAY_OF_MONTH, day);

            System.out.println("Start Calendar : "
                    + dataForamt.format(start.getTime())
            );

            assertEqualsSupport(
                    CalendarCalculatorKt::getSelectedRangeDayCount
                    , start
                    , d -> 0
            );
        }
    }

    @Test
    public void getSelectedRangeDayCount_Weekend() {
        assertEqualsSupport(
                CalendarCalculatorKt::getSelectedRangeDayCount
                , addWeekendDays
                , fridayCalendar
                , new Custom8.Function<Integer, Integer>() {
                    int result = 1;

                    @Override
                    public Integer apply(Integer day) {
                        return result++;
                    }
                }
        );
    }

    private static void assertEqualsSupport(
            Custom8.BiFunction<Calendar, Calendar, Integer> calculator
            , Calendar start
            , Custom8.Function<Integer, Integer> dayCheck) {
        assertEqualsSupport(
                calculator
                , Collections.singletonList(0)
                , start
                , dayCheck
        );
    }

    private static void assertEqualsSupport(
            Custom8.BiFunction<Calendar, Calendar, Integer> calculator
            , List<Integer> list
            , Calendar start
            , Custom8.Function<Integer, Integer> dayCheck) {
        StackTraceElement[] stackTraceElements
                = Thread.currentThread().getStackTrace();

        System.out.println("start == " + stackTraceElements[2].getMethodName() + " ==");

        for (Integer day : list) {
            Calendar end = (Calendar) start.clone();
            end.add(Calendar.DAY_OF_MONTH, day);

            System.out.println("Add day : " + day
                    + "\nEnd Calendar : " + dataForamt.format(end.getTime())

            );

            Integer result  = calculator.apply(start, end);

            assertEquals(dayCheck.apply(day), result);
        }
        System.out.println("end == " + stackTraceElements[2].getMethodName() + " ==");
    }
}
