package sol_5pecia1.expense_manager.util

import java.util.*

/**
 * Created by sol on 2016-12-22.
 */
//TODO Be optimize!!
/**
 * End day is not counted
 */
fun getSelectedRangeWeekendCount(start: Calendar, end: Calendar): Int {
    val compareResult = start.compareTo(end) < 0
    val startCalendar
            = (if (compareResult) start.clone() else end.clone())
            as Calendar
    val endCalendar
            = (if (compareResult) end.clone() else start.clone())
            as Calendar

    val startWeekDay = startCalendar.get(Calendar.DAY_OF_WEEK)

    val startDayCount = Calendar.SATURDAY - startWeekDay + 1
    val endDayCount = endCalendar.get(Calendar.DAY_OF_WEEK) - 1

    val startWeekSaturday = startCalendar.clone() as Calendar
    val endWeekSunday = endCalendar.clone() as Calendar

    startWeekSaturday.add(Calendar.DAY_OF_MONTH, startDayCount - 1)
    endWeekSunday.add(Calendar.DAY_OF_MONTH, -endDayCount)

    var weekendCount = 0

    if (startWeekSaturday.compareTo(endWeekSunday) < 0) {
        weekendCount += if (startCalendar.get(Calendar.DAY_OF_WEEK)
                == Calendar.SUNDAY) 2 else 1
        weekendCount += if (endCalendar.get(Calendar.DAY_OF_WEEK)
                == Calendar.SUNDAY) 0 else 1
        startWeekSaturday.add(Calendar.DAY_OF_MONTH, 1)
        val notCountedDay
                = getSelectedRangeDayCount(startWeekSaturday, endWeekSunday)

        weekendCount += notCountedDay / 7 * 2

    } else { //same week
        weekendCount += if (startCalendar.get(Calendar.DAY_OF_WEEK)
                == Calendar.SUNDAY
                &&
                endCalendar.get(Calendar.DAY_OF_WEEK)
                != Calendar.SUNDAY) 1 else 0
    }

    return weekendCount
}

fun getSelectedRangeWeekdayCount(start: Calendar, end: Calendar): Int {
    return (getSelectedRangeDayCount(start, end)
            - getSelectedRangeWeekendCount(start, end)
            )
}

/**
 * End day is not counted
 */
fun getSelectedRangeDayCount(start: Calendar, end: Calendar): Int {
    val compareResult  = start.compareTo(end) < 0
    val startCalendar
            = (if (compareResult) start.clone() else end.clone())
            as Calendar
    val endCalendar
            = (if (compareResult) end.clone() else start.clone())
            as Calendar

    if (startCalendar.get(Calendar.MONTH)
            == endCalendar.get(Calendar.MONTH)) {

        return (endCalendar.get(Calendar.DAY_OF_MONTH)
        - startCalendar.get(Calendar.DAY_OF_MONTH))
    } else {
        var dayCount
                = (startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                - startCalendar.get(Calendar.DAY_OF_MONTH)
                + 1)

        startCalendar.add(Calendar.MONTH, 1)

        dayCount += endCalendar.get(Calendar.DAY_OF_MONTH) - 1

        while((startCalendar.get(Calendar.YEAR)
                != endCalendar.get(Calendar.YEAR)
                ) || (
                startCalendar.get(Calendar.MONTH)
                != endCalendar.get(Calendar.MONTH))) {
            dayCount += startCalendar
                    .getActualMaximum(Calendar.DAY_OF_MONTH)
            startCalendar.add(Calendar.MONTH, 1)
        }

        return dayCount
    }
}
