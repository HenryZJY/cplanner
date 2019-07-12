package org.dadeco.cu996.api.service;

import org.dadeco.cu996.api.model.Activity;

import java.util.List;

public interface ActivityService {
    Activity save(Activity activity);
    List<Object[]> findEffortByPjoname();
}
