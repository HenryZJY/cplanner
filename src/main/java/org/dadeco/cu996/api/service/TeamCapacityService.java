package org.dadeco.cu996.api.service;

import java.util.List;

import org.dadeco.cu996.api.model.ActivityRole;
import org.dadeco.cu996.api.model.User;

public interface TeamCapacityService {
	public List<List<Object>> getTeamCapacityByMonth(String startDate, String endDate);

	public List<User> getAllUserInfo();

	public List<ActivityRole> getAllActivityRoles();
}
