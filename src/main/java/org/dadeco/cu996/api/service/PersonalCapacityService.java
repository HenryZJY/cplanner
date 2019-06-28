package org.dadeco.cu996.api.service;

import java.util.List;

import org.dadeco.cu996.api.model.RuntimeUserInfo;

public interface PersonalCapacityService {
	public List<List<Object>> getPersonalCapacityByMonth(RuntimeUserInfo user, String startDate, String endDate);
}
