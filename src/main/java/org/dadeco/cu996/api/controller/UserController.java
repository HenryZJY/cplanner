package org.dadeco.cu996.api.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.dadeco.cu996.api.model.Role;
import org.dadeco.cu996.api.model.User;
import org.dadeco.cu996.api.repository.RoleRepository;
import org.dadeco.cu996.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @GetMapping
    public ModelAndView getUserList(HttpServletRequest request) {
        List<User> userList = userRepository.findAll();
        List<Role> roleList = roleRepository.findAll();
        //Map<Integer, List<String>> roleMap = new HashMap<>();
        ModelAndView mv = new ModelAndView();
        /*if(userList != null) {
            userList.stream().forEach( u -> {
                Set<Role> roles = u.getRoles();
                List<String> roleNames = roles.stream().map(r -> StringUtils.replaceIgnoreCase(r.getName(), "ROLE_", "")).collect(Collectors.toList());
                roleMap.put(u.getId(),roleNames);
            });
            mv.addObject("roleMap", roleMap);
        }*/
        mv.setViewName("user");
        mv.addObject("userList", userList);
        mv.addObject("roleList", roleList);
        mv.addObject("user", new User());
        mv.addObject("newRole", new Role());

        return mv;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User getUserById(@PathVariable int id) {
        return userRepository.findById(id).get();
    }

    @PostMapping

    public ModelAndView addUser(@ModelAttribute @NotNull  User user, HttpServletRequest request)  {
        boolean userCreated = false;
        boolean userExists = false;

        if (userRepository.findUsersByEmailOrNtAccount(user.getEmail(), user.getNtAccount()).isEmpty()) {
            userRepository.save(user);
            userCreated = true;
        } else {
            userExists = true;
        }
        ModelAndView mv = getUserList(request);
        mv.addObject("userCreated", userCreated);
        mv.addObject("userExists", userExists);
        return mv;
    }

    /*@DeleteMapping("/{id}")
    public ModelAndView deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user");
        mv.addObject("userList", userRepository.findAll());
        return mv;
    }*/

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponse deleteUser(@RequestParam String[] ntAccounts){
        String currentNtAccount = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        String currentNtAccountToDelete = Stream.of(ntAccounts).filter(nt -> nt.equalsIgnoreCase(currentNtAccount)).findAny().orElse(null);
        if(currentNtAccountToDelete != null) {
            APIResponse response = new APIResponse();
            response.success = false;
            response.message = "Cannot delete current user!";
            return response;
        }
        Stream.of(ntAccounts).filter( nt -> !nt.equalsIgnoreCase(currentNtAccount)).collect(Collectors.toList()).forEach( nt -> userRepository.deleteByNtAccount(nt));
        APIResponse response = new APIResponse();
        response.success = true;
        response.message = "User(s) deleted successfully!";
        return response;
       /* ModelAndView mv = new ModelAndView();
        mv.setViewName("user");
        mv.addObject("userList", userRepository.findAll());
        mv.addObject("userDeleted", true);
        return mv;*/
    }


    @NoArgsConstructor
    @AllArgsConstructor
    class APIResponse {
        @JsonProperty
        public boolean success;

        @JsonProperty
        public String message;


    }

}
