package org.dadeco.cu996.api.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.dadeco.cu996.api.error.BusinessException;
import org.dadeco.cu996.api.model.ActivityRole;
import org.dadeco.cu996.api.model.RuntimeUserInfo;
import org.dadeco.cu996.api.model.User;
import org.dadeco.cu996.api.service.DateDimService;
import org.dadeco.cu996.api.service.PersonalCapacityService;
import org.dadeco.cu996.api.service.TeamCapacityService;
import org.dadeco.cu996.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller("capacity")
@RequestMapping("/capacity")
@CrossOrigin(origins = { "*" }, allowCredentials = "true")
public class CapacityController extends BaseController {

	@Autowired
	private PersonalCapacityService personalCapacityService = null;

	@Autowired
	private TeamCapacityService teamCapacityService = null;

	@Autowired
	private DateDimService dateDimService = null;

	@RequestMapping(value = "/getPersonalMonthlyCapacity", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public String getPersonalMonthlyCapacity(@SessionAttribute RuntimeUserInfo currentUser,
			@RequestParam(value = "startMonth", required = true) String startMonth,
			@RequestParam(value = "endMonth", required = true) String endMonth)
			throws BusinessException, ParseException, JSONException {

		JSONObject result = new JSONObject();

		if (!CommonUtil.isEmptyString(startMonth) && !CommonUtil.isEmptyString(endMonth)) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat df2 = new SimpleDateFormat("yyyyMM");
			DateFormat df3 = new SimpleDateFormat("yyyy-MM");

			Calendar startMonthC = Calendar.getInstance();
			startMonthC.setTime(df.parse(startMonth));

			Calendar endMonthC = Calendar.getInstance();
			endMonthC.setTime(df.parse(endMonth));

			System.out.println(currentUser.getNtAccount());
			System.out.println(startMonthC.getTime());
			System.out.println(endMonthC.getTime());

			List<List<Object>> activities = personalCapacityService.getPersonalCapacityByMonth(currentUser,
					df.format(new Date(
							((java.sql.Timestamp) dateDimService.selectWeekStartDate(df2.format(startMonthC.getTime())))
									.getTime())),
					df.format(new Date(
							((java.sql.Timestamp) dateDimService.selectWeekEndDate(df2.format(endMonthC.getTime())))
									.getTime())));

			startMonth = df3.format(startMonthC.getTime());
			endMonth = df3.format(endMonthC.getTime());

			if (!CommonUtil.isEmptyList(activities)) {
				JSONObject root = new JSONObject();

				JSONObject chart = new JSONObject();
				chart.put("caption", "My Capactiy Overview");
				chart.put("subcaption", startMonth + "~" + endMonth);
				chart.put("xAxisName", "");
				chart.put("yAxisName", "");
				chart.put("showplotborder", "0");
				chart.put("showValues", "0");
				chart.put("xAxisLabelsOnTop", "0");
				chart.put("baseFontColor", "#333333");
				chart.put("toolTipBorderRadius", "2");
				chart.put("toolTipPadding", "5");
				chart.put("theme", "fusion");

				root.put("chart", chart);

				JSONObject columns = new JSONObject();
				JSONArray columnsArray = new JSONArray();

				List<String> dayInWeekEngSnList = dateDimService.selectDayInWeekEngSn();

				for (int i = 0; i < dayInWeekEngSnList.size(); i++) {
					String str = dayInWeekEngSnList.get(i);

					JSONObject column = new JSONObject();
					column.put("id", str);
					column.put("label", str);

					columnsArray.put(column);
				}

				columns.put("column", columnsArray);

				root.put("columns", columns);

				JSONObject colorrange = new JSONObject();
				colorrange.put("gradient", "0");
				colorrange.put("minvalue", "-1");
				colorrange.put("code", "#000000");

				JSONArray colorArray = new JSONArray();

				JSONObject columnNotWorking = new JSONObject();
				columnNotWorking.put("code", "#aaaaaa");
				columnNotWorking.put("minvalue", "-1");
				columnNotWorking.put("maxvalue", "0");
				columnNotWorking.put("label", "Not Working");

				colorArray.put(columnNotWorking);

				JSONObject columnLower = new JSONObject();
				columnLower.put("code", "#f6bc33");
				columnLower.put("minvalue", "0");
				columnLower.put("maxvalue", "4");
				columnLower.put("label", "Lower");

				colorArray.put(columnLower);

				JSONObject columnGood = new JSONObject();
				columnGood.put("code", "#6da81e");
				columnGood.put("minvalue", "4");
				columnGood.put("maxvalue", "9");
				columnGood.put("label", "Good");

				colorArray.put(columnGood);

				JSONObject columnOverload = new JSONObject();
				columnOverload.put("code", "#e24b1a");
				columnOverload.put("minvalue", "9");
				columnOverload.put("maxvalue", "9999");
				columnOverload.put("label", "Overload");

				colorArray.put(columnOverload);

				colorrange.put("color", colorArray);

				root.put("colorrange", colorrange);

				// Data Area
				JSONArray dataset = new JSONArray();
				JSONObject data = new JSONObject();
				JSONArray dataArray = new JSONArray();

				for (int i = 0; i < activities.size(); i++) {
					List<Object> recordList = activities.get(i);

					if (!CommonUtil.isEmptyList(recordList)) {
						// String name = recordList.get(0) == null ? "" : (String) recordList.get(0);
						String role = recordList.get(1) == null ? "" : (String) recordList.get(1);
						int dailyEffort = recordList.get(2) == null ? 0
								: (int) ((BigDecimal) recordList.get(2)).doubleValue();
						Integer weekInYear = (Integer) recordList.get(3);
						String dayInWeekEngSn = (String) recordList.get(4);
						Date dayId = new Date(((java.sql.Date) recordList.get(5)).getTime());

						JSONObject capacity = new JSONObject();
						capacity.put("rowid", "W" + weekInYear.intValue());
						capacity.put("columnid", dayInWeekEngSn);
						capacity.put("value", dailyEffort);
						capacity.put("tllabel", df.format(dayId));
						capacity.put("trlabel", role);

						dataArray.put(capacity);
					}
				}

				data.put("data", dataArray);

				dataset.put(data);

				root.put("dataset", dataset);

				System.out.println(root);

				result.put("status", "success");
				result.put("data", root);
			} else {
				result.put("status", "fail");
				result.put("data", null);
			}
		} else {
			result.put("status", "fail");
			result.put("data", null);
		}

		return result.toString();
	}

	@RequestMapping(value = "/getTeamMonthlyCapacity", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public String getTeamMonthlyCapacity(@RequestParam(value = "startMonth", required = true) String startMonth,
			@RequestParam(value = "endMonth", required = true) String endMonth,
			@RequestParam(value = "isWeekly", required = true) String isWeekly)
			throws BusinessException, ParseException, JSONException {
		JSONObject result = new JSONObject();

		if (!CommonUtil.isEmptyString(startMonth) && !CommonUtil.isEmptyString(endMonth)) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat df2 = new SimpleDateFormat("yyyyMM");
			DateFormat df3 = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat df4 = new SimpleDateFormat("dd/MM");

			Calendar startMonthC = Calendar.getInstance();
			startMonthC.setTime(df.parse(startMonth));

			Calendar endMonthC = Calendar.getInstance();
			endMonthC.setTime(df.parse(endMonth));
			endMonthC.set(Calendar.DATE, endMonthC.getActualMaximum(Calendar.DAY_OF_MONTH));

			System.out.println(startMonthC.getTime());
			System.out.println(endMonthC.getTime());
			System.out.println(isWeekly);

			List<List<Object>> activities = teamCapacityService.getTeamCapacityByMonth(df.format(startMonthC.getTime()),
					df.format(endMonthC.getTime()));

			if (!CommonUtil.isEmptyList(activities)) {
				JSONObject root = new JSONObject();

				JSONObject chart = new JSONObject();
				chart.put("caption", "Team Capacity Overview");
				chart.put("subcaption", "DAD-AP & PJ-ECO");
				chart.put("dateformat", "dd/mm/yyyy");
				chart.put("plottooltext", "<b>$start - $end</b> as <b>$label</b>");
				chart.put("theme", "fusion");

				root.put("chart", chart);

				JSONArray categorysArray = new JSONArray();

				if (Boolean.parseBoolean(isWeekly)) {
					List<List<Object>> yearWeeks = dateDimService.selectYearWeeks(
							Integer.parseInt(df2.format(startMonthC.getTime())),
							Integer.parseInt(df2.format(endMonthC.getTime())));

					JSONObject categoryWeekFirstLevel = new JSONObject();
					JSONArray categoryArrayWeekFirstLevel = new JSONArray();

					for (int i = 0; i < yearWeeks.size(); i++) {
						List<Object> weekList = yearWeeks.get(i);

						if (!CommonUtil.isEmptyList(weekList)) {
							Date weekStartTime = weekList.get(0) == null ? null
									: new Date(((java.sql.Timestamp) weekList.get(0)).getTime());
							Date weekEndTime = weekList.get(1) == null ? null
									: new Date(((java.sql.Timestamp) weekList.get(1)).getTime());
							Integer weekInYear = (Integer) weekList.get(2);

							JSONObject weekFirstLevel = new JSONObject();
							weekFirstLevel.put("start", df3.format(weekStartTime));
							weekFirstLevel.put("end", df3.format(weekEndTime));
							weekFirstLevel.put("label", "W " + weekInYear.intValue());

							categoryArrayWeekFirstLevel.put(weekFirstLevel);
						}
					}

					categoryWeekFirstLevel.put("category", categoryArrayWeekFirstLevel);
					categorysArray.put(categoryWeekFirstLevel);

					JSONObject categoryWeek = new JSONObject();
					JSONArray categoryArrayWeek = new JSONArray();

					for (int i = 0; i < yearWeeks.size(); i++) {
						List<Object> weekList = yearWeeks.get(i);

						if (!CommonUtil.isEmptyList(weekList)) {
							Date weekStartTime = weekList.get(0) == null ? null
									: new Date(((java.sql.Timestamp) weekList.get(0)).getTime());
							Date weekEndTime = weekList.get(1) == null ? null
									: new Date(((java.sql.Timestamp) weekList.get(1)).getTime());

							JSONObject week = new JSONObject();
							week.put("start", df3.format(weekStartTime));
							week.put("end", df3.format(weekEndTime));
							week.put("label", df4.format(weekStartTime) + "~" + df4.format(weekEndTime));

							categoryArrayWeek.put(week);
						}
					}
					categoryWeek.put("bgalpha", "0");
					categoryWeek.put("category", categoryArrayWeek);
					categorysArray.put(categoryWeek);
				} else {
					List<List<Object>> yearWeeks = dateDimService.selectYearMonths(
							Integer.parseInt(df2.format(startMonthC.getTime())),
							Integer.parseInt(df2.format(endMonthC.getTime())));

					JSONObject categoryMonth = new JSONObject();
					JSONArray categoryArrayMonth = new JSONArray();

					for (int i = 0; i < yearWeeks.size(); i++) {
						List<Object> weekList = yearWeeks.get(i);

						if (!CommonUtil.isEmptyList(weekList)) {
							Date monthStartTime = weekList.get(1) == null ? null
									: new Date(((java.sql.Timestamp) weekList.get(1)).getTime());
							Date monthEndTime = weekList.get(2) == null ? null
									: new Date(((java.sql.Timestamp) weekList.get(2)).getTime());
							String monthEngSn = (String) weekList.get(0);

							JSONObject month = new JSONObject();
							month.put("start", df3.format(monthStartTime));
							month.put("end", df3.format(monthEndTime));
							month.put("label", monthEngSn + ". " + startMonthC.get(Calendar.YEAR));

							categoryArrayMonth.put(month);
						}
					}

					categoryMonth.put("category", categoryArrayMonth);
					categorysArray.put(categoryMonth);
				}

				root.put("categories", categorysArray);

				List<User> userList = teamCapacityService.getAllUserInfo();

				JSONObject processes = new JSONObject();
				processes.put("isbold", "1");
				processes.put("headertext", "Name");

				JSONArray processArray = new JSONArray();

				for (int i = 0; i < userList.size(); i++) {
					User user = userList.get(i);

					JSONObject process = new JSONObject();
					process.put("label", user.getName());
					process.put("id", user.getNtAccount());

					processArray.put(process);
				}

				processes.put("process", processArray);

				root.put("processes", processes);

				List<ActivityRole> activityRoleList = teamCapacityService.getAllActivityRoles();

				JSONObject legend = new JSONObject();

				JSONArray itemArray = new JSONArray();

				for (int i = 0; i < activityRoleList.size(); i++) {
					ActivityRole activityRole = activityRoleList.get(i);

					JSONObject item = new JSONObject();
					item.put("label", activityRole.getName());
					item.put("color", activityRole.getRgb());

					itemArray.put(item);
				}

				legend.put("item", itemArray);

				root.put("legend", legend);

				// Data Area
				JSONObject tasks = new JSONObject();
				JSONArray taskArray = new JSONArray();

				for (int i = 0; i < activities.size(); i++) {
					List<Object> recordList = activities.get(i);

					if (!CommonUtil.isEmptyList(recordList)) {
						// String name = (String) recordList.get(0);
						String role = (String) recordList.get(1);
						// int dailyEffort = recordList.get(2) == null ? 0 : (int) ((BigDecimal)
						// recordList.get(2)).doubleValue();
						Date startDate = new Date(((java.sql.Timestamp) recordList.get(3)).getTime());
						Date endDate = new Date(((java.sql.Timestamp) recordList.get(4)).getTime());
						String userId = (String) recordList.get(5);
						String rgb = (String) recordList.get(6);

						JSONObject task = new JSONObject();
						task.put("label", role);
						task.put("processid", userId);
						task.put("start", df3.format(startDate));
						task.put("end", df3.format(endDate));
						task.put("bordercolor", "#62B58D");
						task.put("color", rgb);
						task.put("id", i + "");

						taskArray.put(task);
					}
				}

				tasks.put("task", taskArray);

				root.put("tasks", tasks);

				System.out.println(root);

				result.put("status", "success");
				result.put("data", root);
			} else {
				result.put("status", "fail");
				result.put("data", null);
			}
		} else {
			result.put("status", "fail");
			result.put("data", null);
		}

		return result.toString();
	}
}
