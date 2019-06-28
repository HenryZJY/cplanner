package org.dadeco.cu996.api.service.impl;

import java.util.List;

import org.dadeco.cu996.api.repository.DateDimRepository;
import org.dadeco.cu996.api.service.DateDimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DateDimServiceImpl implements DateDimService {
	@Autowired
	private DateDimRepository dateDimRepository = null;

	@Override
	public List<String> selectDayInWeekEngSn() {
		return dateDimRepository.selectDayInWeekEngSn();
	}

	@Override
	public List<List<Object>> selectYearWeeks(int startYearMonth, int endYearMonth) {
		return dateDimRepository.selectYearWeeks(startYearMonth, endYearMonth);
	}

	@Override
	public List<List<Object>> selectYearMonths(int startYearMonth, int endYearMonth) {
		return dateDimRepository.selectYearMonths(startYearMonth, endYearMonth);
	}

	@Override
	public Object selectWeekStartDate(String yearMonth) {
		return dateDimRepository.selectWeekStartDate(yearMonth);
	}

	@Override
	public Object selectWeekEndDate(String yearMonth) {
		return dateDimRepository.selectWeekEndDate(yearMonth);
	}
}
