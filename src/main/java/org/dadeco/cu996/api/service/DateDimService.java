package org.dadeco.cu996.api.service;

import java.util.List;

public interface DateDimService {
	public List<String> selectDayInWeekEngSn();

	public List<List<Object>> selectYearWeeks(int startYearMonth, int endYearMonth);

	public List<List<Object>> selectYearMonths(int startYearMonth, int endYearMonth);

	public Object selectWeekStartDate(String yearMonth);

	public Object selectWeekEndDate(String yearMonth);
}
