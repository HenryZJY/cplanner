package org.dadeco.cu996.api.service.impl;

import org.dadeco.cu996.api.model.Activity;
import org.dadeco.cu996.api.repository.ActivityRepository;
import org.dadeco.cu996.api.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public List<Object[]> findEffortByPjoname() {
        List<Object[]> list = new ArrayList<Object[]>();

        list = activityRepository.findEffortByPjoname();

        int total = 0;
        return null;
    }
}
