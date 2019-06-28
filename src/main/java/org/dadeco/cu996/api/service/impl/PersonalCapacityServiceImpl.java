package org.dadeco.cu996.api.service.impl;

import java.util.List;

import org.dadeco.cu996.api.model.RuntimeUserInfo;
import org.dadeco.cu996.api.repository.ActivityRepository;
import org.dadeco.cu996.api.service.PersonalCapacityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalCapacityServiceImpl implements PersonalCapacityService {
	@Autowired
	private ActivityRepository activityRepository = null;

	@Override
	public List<List<Object>> getPersonalCapacityByMonth(RuntimeUserInfo user, String startDate, String endDate) {
		List<List<Object>> activities = null;

		if (user != null) {
			activities = activityRepository.findByUserIdAndStartAndEnd(user.getNtAccount(), startDate, endDate);
		}

		return activities;
	}

}
