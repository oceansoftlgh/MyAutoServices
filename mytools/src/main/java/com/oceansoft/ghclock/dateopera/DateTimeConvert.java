package com.oceansoft.ghclock.dateopera;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 将字符串转换成固定格式
 * 
 * @author Administrator
 * 
 */
@SuppressLint("SimpleDateFormat")
public class DateTimeConvert {

	public static int dayDiff = 0;
	public static int secondsDiff = 0;
	/*
	 * 获取当前系统时间（与服务器一致）
	 */
	public Date getNowDate() {
		Date date;
		try {
			date = getAddDayTime(new Date(), -dayDiff);
			date = getAddSecondTime(date, -secondsDiff);
			return date;
		} catch (ParseException e) {
			return new Date();
		}
	}

	/*
	 * 获取当前系统时间与服务器时间相差的天数及转换成同一天后相差的毫秒数
	 */
	public void getDiffSecondsAddDay(String serviceTimeStr)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date serviceTime = format.parse(serviceTimeStr.replace("/", "-")
				.toString());
		int dayDiff = getTwoDateDayDifference(format.format(new Date()),
				format.format(serviceTime));
		dayDiff = dayDiff;
		Date date = getAddDayTime(new Date(), -dayDiff);
		long secondsDiff = getTwoTimeDifference(format.format(date),
				format.format(serviceTime));
		secondsDiff = secondsDiff;
	}

	/*
	 * 获得两个日期相差天数
	 */
	public static int getTwoDateDayDifference(String firstDate,
			String secondDate) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(format.parse(firstDate));
		int d1 = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.setTime(format.parse(secondDate));
		int d2 = calendar.get(Calendar.DAY_OF_YEAR);

		return d1 - d2;
	}

	/*
	 * 获得date添加day之后的日期
	 */
	public static Date getAddDayTime(Date date, int day) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, day);
		return format.parse(format.format(calendar.getTime()));
	}

	/*
	 * 获取当前日期添加固定毫秒值之后的日期 参数：当前日期，毫秒值
	 */
	private static Date getAddSecondTime(Date date, long diff)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MILLISECOND, (int) diff);
		return format.parse(format.format(calendar.getTime()));
	}

	/*
	 * 返回第一个日期比第二个日期大的毫秒值 返回值大于0，则一个日期较大，否则，第二个日期较大
	 */
	public static long getTwoTimeDifference(String firstDate, String secondDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date d1 = format.parse(firstDate);
			Date d2 = format.parse(secondDate);

			long diff = d1.getTime() - d2.getTime();
			return diff;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/*
	 * 将字符串转化成yyyy-MM-dd格式
	 */
	public String dateConvert(String dateStr) {
		dateStr = dateStr.replace("/", "-").toString();
		if (dateStr.indexOf("-") > 0) {
			String[] dateArray = dateStr.split("-");
			if (dateArray.length > 2) {
				// 年
				if (dateArray[0].length() == 2) {
					dateStr = "20" + dateArray[0] + "-";
				} else {
					dateStr = dateArray[0] + "-";
				}

				// 月
				if (dateArray[1].length() == 1) {
					dateStr += "0" + dateArray[1] + "-";
				} else {
					dateStr += dateArray[1] + "-";
				}

				// 日
				if (dateArray[2].length() == 1) {
					dateStr += "0" + dateArray[2];
				} else {
					dateStr += dateArray[2];
				}
			} else if (dateArray.length == 2) {
				// 年
				SimpleDateFormat formate = new SimpleDateFormat("yyyy");
				dateStr = formate.format(new Date()) + "-";

				// 月
				if (dateArray[0].length() == 1) {
					dateStr += "0" + dateArray[1] + "-";
				} else {
					dateStr += dateArray[1] + "-";
				}

				// 日
				if (dateArray[1].length() == 1) {
					dateStr += "0" + dateArray[2];
				} else {
					dateStr += dateArray[2];
				}
			}
		}

		return dateStr;
	}

	/*
	 * 将字符串转化成HH:mm格式
	 */
	public String timeConvert(String timeStr) {
		if (timeStr.indexOf(":") > 0) {
			String[] timeArray = timeStr.split(":");
			if (timeArray[0].length() == 1) {
				timeStr = "0" + timeArray[0] + ":";
			} else {
				timeStr = timeArray[0] + ":";
			}

			if (timeArray[1].length() == 1) {
				timeStr += "0" + timeArray[1];
			} else {
				timeStr += timeArray[1];
			}
		} else {
			if (timeStr.length() == 1) {
				timeStr = "0" + timeStr + ":00";
			} else if (timeStr.length() == 2) {
				timeStr = timeStr + ":00";
			}
		}

		return timeStr;
	}


}
