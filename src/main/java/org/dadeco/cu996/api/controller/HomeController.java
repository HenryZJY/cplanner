package org.dadeco.cu996.api.controller;


import org.dadeco.cu996.api.model.ActivityRole;
import org.dadeco.cu996.api.model.RuntimeUserInfo;
import org.dadeco.cu996.api.repository.ActivityRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class HomeController {
    @Autowired
    ActivityRoleRepository activityRoleRepository;

    @GetMapping("/")
    public String home(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RuntimeUserInfo currentUser = (RuntimeUserInfo) auth.getPrincipal();

        session.setAttribute("currentUser", currentUser);
        return "overall_capacity_overview";
    }

    @GetMapping("/plan")
    public String showPlan(Model model) {
        List<ActivityRole> roles = activityRoleRepository.findAll();
        model.addAttribute("activityRoles",roles);

        return "plan";
    }

}

