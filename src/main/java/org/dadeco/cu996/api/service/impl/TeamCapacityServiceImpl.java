package org.dadeco.cu996.api.service.impl;

import java.util.List;

import org.dadeco.cu996.api.model.ActivityRole;
import org.dadeco.cu996.api.model.User;
import org.dadeco.cu996.api.repository.ActivityRepository;
import org.dadeco.cu996.api.repository.ActivityRoleRepository;
import org.dadeco.cu996.api.repository.UserRepository;
import org.dadeco.cu996.api.service.TeamCapacityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamCapacityServiceImpl implements TeamCapacityService {
	@Autowired
	private ActivityRepository activityRepository = null;

	@Autowired
	private UserRepository userRepository = null;

	@Autowired
	private ActivityRoleRepository activityRoleRepository = null;

	@Override
	public List<List<Object>> getTeamCapacityByMonth(String startDate, String endDate) {
		return activityRepository.findByStartAndEndForTeam(startDate, endDate);
	}

	@Override
	public List<User> getAllUserInfo() {
		return userRepository.findAll();
	}

	@Override
	public List<ActivityRole> getAllActivityRoles() {
		return activityRoleRepository.findAll();
	}
}
