package org.dadeco.cu996.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Test {
	private static final int[][] MONTH_ARRAY = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };

	private static Calendar getDayStartTime(Calendar cal) {
		Calendar c = Calendar.getInstance();
		c.setTime(cal.getTime());

		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		return c;
	}

	private static Calendar getDayEndTime(Calendar cal) {
		Calendar c = Calendar.getInstance();
		c.setTime(cal.getTime());

		c.set(Calendar.HOUR, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);

		return c;
	}

	private static Calendar getWeekStartTime(Calendar cal) {
		Calendar c = Calendar.getInstance();
		c.setTime(cal.getTime());

		int dayInWeekId = c.get(Calendar.DAY_OF_WEEK);

		c.add(Calendar.DATE, -(dayInWeekId - 1));

		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		return c;
	}

	private static Calendar getWeekEndTime(Calendar cal) {
		Calendar c = Calendar.getInstance();
		c.setTime(cal.getTime());

		int dayInWeekId = c.get(Calendar.DAY_OF_WEEK);

		c.add(Calendar.DATE, (7 - dayInWeekId));

		c.set(Calendar.HOUR, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);

		return c;
	}

	private static Calendar getMonthStartTime(Calendar cal) {
		Calendar c = Calendar.getInstance();
		c.setTime(cal.getTime());

		c.set(Calendar.DATE, 1);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		return c;
	}

	private static Calendar getMonthEndTime(Calendar cal) {
		Calendar c = Calendar.getInstance();
		c.setTime(cal.getTime());

		int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);

		c.set(Calendar.DATE, lastDay);
		c.set(Calendar.HOUR, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);

		return c;
	}

	private static int getQuarterDayNum(Calendar cal) {
		Calendar c = Calendar.getInstance();
		c.setTime(cal.getTime());

		int monthId = c.get(Calendar.MONTH) + 1;
		int quarterId = monthId >= 1 && monthId <= 3 ? 1
				: monthId >= 4 && monthId <= 6 ? 2
						: monthId >= 7 && monthId <= 9 ? 3 : monthId >= 10 && monthId <= 12 ? 4 : 0; // 季度
		int[] quarterMonthArray = MONTH_ARRAY[quarterId - 1];

		int quarterDayNum = 0;

		for (int i = 0; i < quarterMonthArray.length; i++) {
			c.set(Calendar.MONTH, quarterMonthArray[i] - 1);
			quarterDayNum = quarterDayNum + c.getActualMaximum(Calendar.DAY_OF_MONTH);
		}

		return quarterDayNum;
	}

	private static Calendar getQuarterStartTime(Calendar cal) {
		Calendar c = Calendar.getInstance();
		c.setTime(cal.getTime());

		int monthId = c.get(Calendar.MONTH) + 1;
		int quarterId = monthId >= 1 && monthId <= 3 ? 1
				: monthId >= 4 && monthId <= 6 ? 2
						: monthId >= 7 && monthId <= 9 ? 3 : monthId >= 10 && monthId <= 12 ? 4 : 0; // 季度
		int[] quarterMonthArray = MONTH_ARRAY[quarterId - 1];

		int index = 0;
		for (int i = 0; i < quarterMonthArray.length; i++) {
			if (quarterMonthArray[i] == monthId) {
				index = i;
			}
		}

		c.add(Calendar.MONTH, -index);

		c.set(Calendar.DATE, 1);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		return c;
	}

	private static Calendar getQuarterEndTime(Calendar cal) {
		Calendar c = Calendar.getInstance();
		c.setTime(cal.getTime());

		int monthId = c.get(Calendar.MONTH) + 1;
		int quarterId = monthId >= 1 && monthId <= 3 ? 1
				: monthId >= 4 && monthId <= 6 ? 2
						: monthId >= 7 && monthId <= 9 ? 3 : monthId >= 10 && monthId <= 12 ? 4 : 0; // 季度
		int[] quarterMonthArray = MONTH_ARRAY[quarterId - 1];

		int index = 0;
		for (int i = 0; i < quarterMonthArray.length; i++) {
			if (quarterMonthArray[i] == monthId) {
				index = i;
			}
		}

		c.add(Calendar.MONTH, 3 - (index + 1));

		int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);

		c.set(Calendar.DATE, lastDay);
		c.set(Calendar.HOUR, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);

		return c;
	}

	private static Calendar getYearStartTime(Calendar cal) {
		Calendar c = Calendar.getInstance();
		c.setTime(cal.getTime());

		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DATE, 1);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		return c;
	}

	private static Calendar getYearEndTime(Calendar cal) {
		Calendar c = Calendar.getInstance();
		c.setTime(cal.getTime());

		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DATE, 31);
		c.set(Calendar.HOUR, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);

		return c;
	}

	public static void main(String[] args) {
		Calendar calStart = Calendar.getInstance();
		calStart.set(Calendar.YEAR, 2019);
		calStart.set(Calendar.MONTH, 0);
		calStart.set(Calendar.DATE, 1);

		Calendar calEnd = Calendar.getInstance();
		calEnd.set(Calendar.YEAR, 2030);
		calEnd.set(Calendar.MONTH, 11);
		calEnd.set(Calendar.DATE, 31);

		int gap = (int) ((calEnd.getTime().getTime() - calStart.getTime().getTime()) / (1000 * 3600 * 24)) + 1;

		System.out.println("gap : " + gap);

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement st = null;

		try {
			String url = "jdbc:mysql://localhost:3306/cplanner?user=root&password=root";
			conn = DriverManager.getConnection(url);

			conn.setAutoCommit(false);

			st = conn.createStatement();
			st.execute("truncate table cplanner.date_dim");

			// Id Columns
			String sql = "insert into date_dim (id, ";
			// Date columns
			sql = sql
					+ "day_id, day_start_time, day_end_time, day_in_week_id, day_in_week_eng, day_in_week_eng_sn, day_in_week_chs_name, day_in_year, is_working_day, ";
			// Week columns
			sql = sql + "week_in_year, week_in_month, week_day_num, week_start_time, week_end_time, ";
			// Month columns
			sql = sql
					+ "month_id, month_day_num, month_start_time, month_end_time, month_eng_name, month_eng_sn, last_day_of_month, ";
			// Quarter columns
			sql = sql
					+ "quarter_id, quarter_chs_name, quarter_day_num, quarter_start_time, quarter_end_time, last_month_of_quarter, last_day_of_quarter, ";
			// Year columns
			sql = sql
					+ "year_id, yearmonth, year_start_time, year_end_time, last_quarter_of_year, last_month_of_year, last_day_of_year, year_day_num, ";
			// Info columns
			sql = sql + "last_update_user, last_update_time) values ";
			sql = sql
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			ps = conn.prepareStatement(sql);

			for (int i = 0; i < gap; i++) {
				Calendar c = Calendar.getInstance();
				c.setTime(calStart.getTime());
				c.add(Calendar.DATE, i);
				c.set(Calendar.HOUR, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);

				// Date
				Date dayId = c.getTime();
				Date dayStartTime = getDayStartTime(c).getTime();
				Date dayEndTime = getDayEndTime(c).getTime();
				int dayInWeekId = c.get(Calendar.DAY_OF_WEEK);
				String dayInWeekEng = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
				String dayInWeekEngSn = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH);
				String dayInWeekChs = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.CHINESE);
				int dayInYear = c.get(Calendar.DAY_OF_YEAR);
				int isWorkingDay = dayInWeekId != 1 && dayInWeekId != 7 ? 1 : 0;
				// Week
				int weekInYear = c.get(Calendar.WEEK_OF_YEAR);
				int weekInMonth = c.get(Calendar.WEEK_OF_MONTH);
				int weekDayNum = 7;
				Date weekStartTime = getWeekStartTime(c).getTime();
				Date weekEndTime = getWeekEndTime(c).getTime();
				// Month
				int monthId = c.get(Calendar.MONTH) + 1;
				int monthDayNum = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				Date monthStartTime = getMonthStartTime(c).getTime();
				Date monthEndTime = getMonthEndTime(c).getTime();
				String monthEngName = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
				String monthEngSn = c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
				int lastDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				// Quarter
				int quarteId = monthId >= 1 && monthId <= 3 ? 1
						: monthId >= 4 && monthId <= 6 ? 2
								: monthId >= 7 && monthId <= 9 ? 3 : monthId >= 10 && monthId <= 12 ? 4 : 0;
				String quarterChsName = monthId >= 1 && monthId <= 3 ? "一"
						: monthId >= 4 && monthId <= 6 ? "二"
								: monthId >= 7 && monthId <= 9 ? "三" : monthId >= 10 && monthId <= 12 ? "四" : "";
				int quarterDayNum = getQuarterDayNum(c);
				Date quarterStartTime = getQuarterStartTime(c).getTime();
				Date quarterEndTime = getQuarterEndTime(c).getTime();
				int lastMonthOfQuarter = getQuarterEndTime(c).get(Calendar.MONTH) + 1;
				int lastDayOfQuarter = getQuarterEndTime(c).getActualMaximum(Calendar.DAY_OF_MONTH);
				// Year
				int yearId = c.get(Calendar.YEAR);
				int yearmonth = Integer.parseInt(c.get(Calendar.YEAR) + ""
						+ ("00" + (c.get(Calendar.MONTH) + 1)).substring(
								("00" + (c.get(Calendar.MONTH) + 1)).length() - 2,
								("00" + (c.get(Calendar.MONTH) + 1)).length()));
				Date yearStartTime = getYearStartTime(c).getTime();
				Date yearEndTime = getYearEndTime(c).getTime();
				int lastQuarterOfYear = 4;
				int lastMonthOfYear = 12;
				int lastDayOfYear = 31;
				int yearDayNum = c.getActualMaximum(Calendar.DAY_OF_YEAR);

				// Id Columns
				ps.setInt(1, (i + 1));
				// Date Columns
				ps.setDate(2, new java.sql.Date(dayId.getTime()));
				ps.setDate(3, new java.sql.Date(dayStartTime.getTime()));
				ps.setDate(4, new java.sql.Date(dayEndTime.getTime()));
				ps.setInt(5, dayInWeekId);
				ps.setString(6, dayInWeekEng);
				ps.setString(7, dayInWeekEngSn);
				ps.setString(8, dayInWeekChs);
				ps.setInt(9, dayInYear);
				ps.setInt(10, isWorkingDay);
				// Week columns
				ps.setInt(11, weekInYear);
				ps.setInt(12, weekInMonth);
				ps.setInt(13, weekDayNum);
				ps.setDate(14, new java.sql.Date(weekStartTime.getTime()));
				ps.setDate(15, new java.sql.Date(weekEndTime.getTime()));
				// Month columns
				ps.setInt(16, monthId);
				ps.setInt(17, monthDayNum);
				ps.setDate(18, new java.sql.Date(monthStartTime.getTime()));
				ps.setDate(19, new java.sql.Date(monthEndTime.getTime()));
				ps.setString(20, monthEngName);
				ps.setString(21, monthEngSn);
				ps.setInt(22, lastDayOfMonth);
				// Quarter columns
				ps.setInt(23, quarteId);
				ps.setString(24, quarterChsName);
				ps.setInt(25, quarterDayNum);
				ps.setDate(26, new java.sql.Date(quarterStartTime.getTime()));
				ps.setDate(27, new java.sql.Date(quarterEndTime.getTime()));
				ps.setInt(28, lastMonthOfQuarter);
				ps.setInt(29, lastDayOfQuarter);
				// Year columns
				ps.setInt(30, yearId);
				ps.setInt(31, yearmonth);
				ps.setDate(32, new java.sql.Date(yearStartTime.getTime()));
				ps.setDate(33, new java.sql.Date(yearEndTime.getTime()));
				ps.setInt(34, lastQuarterOfYear);
				ps.setInt(35, lastMonthOfYear);
				ps.setInt(36, lastDayOfYear);
				ps.setInt(37, yearDayNum);
				// Info columns
				ps.setString(38, "System");
				ps.setDate(39, new java.sql.Date(new Date().getTime()));

				ps.addBatch();

//				// id int(11) AI PK
//				System.out.println("id : " + (i + 1));
				//
//				// day_id date
//				System.out.println("day_id : " + dayId);
//				// day_start_time datetime
//				System.out.println("day_start_time : " + dayStartTime);
//				// day_end_time datetime
//				System.out.println("day_end_time : " + dayEndTime);
//				// day_in_week_id int(11)
//				System.out.println("day_in_week_id : " + dayInWeekId);
//				// day_in_week_eng_sn varchar(255)
//				System.out.println("day_in_week_eng_sn : " + dayInWeekEngSn);
//				// day_in_week_eng_name varchar(255)
//				System.out.println("day_in_week_eng_name : " + dayInWeekEng);
//				// day_in_week_chs_name varchar(255)
//				System.out.println("day_in_week_chs_name : " + dayInWeekChs);
//				// day_in_year int(11)
//				System.out.println("day_in_year : " + dayInYear);
//				// is_working_day int(11)
//				System.out.println("is_working_day : " + isWorkingDay);
				//
//				// week_in_year int(11)
//				System.out.println("week_in_year : " + weekInYear);
//				// week_in_month int(11)
//				System.out.println("week_in_month : " + weekInMonth);
//				// week_day_num int(11)
//				System.out.println("week_day_num : " + weekDayNum);
//				// week_start_time datetime
//				System.out.println("week_start_time : " + weekStartTime);
//				// week_end_time datetime
//				System.out.println("week_end_time : " + weekEndTime);
				//
//				// month_id int(11)
//				System.out.println("month_id : " + monthId);
//				// month_day_num int(11)
//				System.out.println("month_day_num : " + monthDayNum);
//				// month_start_time datetime
//				System.out.println("month_start_time : " + monthStartTime);
//				// month_end_time datetime
//				System.out.println("month_end_time : " + monthEndTime);
//				// month_eng_name varchar(255)
//				System.out.println("month_eng_name : " + monthEngName);
//				// month_eng_sn varchar(255)
//				System.out.println("month_eng_sn : " + monthEngSn);
//				// last_day_of_month int(11)
//				System.out.println("last_day_of_month : " + lastDayOfMonth);
				//
//				// quarter_id int(11)
//				System.out.println("quarter_id : " + quarteId);
//				// quarter_chs_name varchar(255)
//				System.out.println("quarter_chs_name : " + quarterChsName);
//				// quarter_day_num int(11)
//				System.out.println("quarter_day_num : " + quarterDayNum);
//				// quarter_start_time datetime
//				System.out.println("quarter_start_time : " + quarterStartTime);
//				// quarter_end_time datetime
//				System.out.println("quarter_end_time : " + quarterEndTime);
//				// last_month_of_quarter int(11)
//				System.out.println("last_month_of_quarter : " + lastMonthOfQuarter);
//				// last_day_of_quarter int(11)
//				System.out.println("last_day_of_quarter : " + lastDayOfQuarter);
				//
//				// year_id int(11)
//				System.out.println("year_id : " + yearId);
//				// yearmonth int(11)
//				System.out.println("yearmonth : " + yearmonth);
//				// year_start_time datetime
//				System.out.println("year_start_time : " + yearStartTime);
//				// year_end_time datetime
//				System.out.println("year_end_time : " + yearEndTime);
//				// last_quarter_of_year int(11)
//				System.out.println("last_quarter_of_year : " + lastQuarterOfYear);
//				// last_month_of_year int(11)
//				System.out.println("last_month_of_year : " + lastMonthOfYear);
//				// last_day_of_year int(11)
//				System.out.println("last_day_of_year : " + lastDayOfYear);
//				// year_day_num int(11)
//				System.out.println("year_day_num : " + yearDayNum);
			}

			int[] rows = ps.executeBatch();

			System.out.println("rows : " + rows.length);

			conn.commit();

			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
