package org.dadeco.cu996.api.controller;

import org.dadeco.cu996.api.error.BusinessException;
import org.dadeco.cu996.api.model.Activity;
import org.dadeco.cu996.api.model.RuntimeUserInfo;
import org.dadeco.cu996.api.response.CommonReturnType;
import org.dadeco.cu996.api.service.impl.ActivityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller("activity")
@RequestMapping("/activity")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class ActivityController extends BaseController {

    @Autowired
    private ActivityServiceImpl activityService;

    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    @ResponseBody
    public CommonReturnType createItem(@SessionAttribute RuntimeUserInfo currentUser,
    								   @RequestParam(name = "name") String name,
                                       @RequestParam(name = "start") String start,
                                       @RequestParam(name = "end") String end,
                                       @RequestParam(name = "role") String role,
                                       @RequestParam(name = "is_planned") String is_planned,
                                       @RequestParam(name = "chargeable") String chargeable,
                                       @RequestParam(name = "daily_effort") Integer effort) throws BusinessException {
        Activity activity = new Activity();
        activity.setName(name);
        activity.setStart(start);
        activity.setEnd(end);
        activity.setChargeable(chargeable);
        activity.setRole(role);
        activity.setDailyEffort(effort);
        activity.setIsPlanned(is_planned);
        activity.setUserId(currentUser.getNtAccount());

        activityService.save(activity);

        return CommonReturnType.create(null);
    }

}
