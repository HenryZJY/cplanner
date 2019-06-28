package org.dadeco.cu996.api.component;

import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.dadeco.cu996.api.model.ActivityRole;
import org.dadeco.cu996.api.model.Role;
import org.dadeco.cu996.api.model.User;
import org.dadeco.cu996.api.repository.*;
import org.dadeco.cu996.utils.DateDimUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

@Component
@DependsOn("securityConfig")
public class DataInitializer implements
        ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private ActivityRoleRepository activityRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        /*Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");*/

        /*Set<Privilege> adminPrivileges = new HashSet<Privilege>(Arrays.asList(
                readPrivilege, writePrivilege));*/
        /*createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", new HashSet<>(Arrays.asList(readPrivilege)));*/

        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");

        String adminEmail = "test@dadeco.com";
        String adminNtAccount = "test7sgh";

        String userEmail = "user@dadeco.com";
        String userAccount = "user7sgh";

        String userEmail2 = "user2@dadeco.com";
        String userAccount2 = "user8sgh";

        User admin = userRepository.findByEmail(adminEmail);
        User nonAdmin = userRepository.findByEmail(userEmail);
        User nonAdmin2 = userRepository.findByNtAccount(userAccount2);
        if(admin == null) {

            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            User user = new User();
            user.setName("Test");
            user.setPassword(passwordEncoder.encode("test"));
            user.setEmail(adminEmail);
            /*Set<Role> roles = new HashSet<>();
            roles.add(adminRole);*/
            user.setRole(adminRole);
            user.setNtAccount(adminNtAccount);
            userRepository.save(user);
        }

        if(nonAdmin == null) {

            Role userRole = roleRepository.findByName("ROLE_USER");
            User user = new User();
            user.setName("User");
            user.setPassword(passwordEncoder.encode("test"));
            user.setEmail(userEmail);
            /*Set<Role> roles = new HashSet<>();
            roles.add(userRole);*/
            user.setRole(userRole);
            user.setNtAccount(userAccount);
            userRepository.save(user);
        }

        if(nonAdmin2 == null) {

            Role userRole = roleRepository.findByName("ROLE_USER");
            User user = new User();
            user.setName("User2");
            user.setPassword(passwordEncoder.encode("test"));
            user.setEmail(userEmail2);
           /* Set<Role> roles = new HashSet<>();
            roles.add(userRole);*/
            user.setRole(userRole);
            user.setNtAccount(userAccount2);
            userRepository.save(user);
        }

        ActivityRole pm = new ActivityRole();
        pm.setName("PM");
        pm.setDescription("Project Manager");
        pm.setRgb("#4287f5");

        ActivityRole ba = new ActivityRole();
        ba.setName("BA");
        ba.setDescription("Business Analyst");
        ba.setRgb("#42f55d");

        ActivityRole arch = new ActivityRole();
        arch.setName("Arch");
        arch.setDescription("Architect");
        arch.setRgb("#f5c242");

        ActivityRole dev = new ActivityRole();
        dev.setName("Dev");
        dev.setDescription("Developer");
        dev.setRgb("#f56c42");

        if(activityRoleRepository.findByName(pm.getName()) == null)
            activityRoleRepository.save(pm);
        if(activityRoleRepository.findByName(ba.getName()) == null)
            activityRoleRepository.save(ba);
        if(activityRoleRepository.findByName(arch.getName()) == null)
        activityRoleRepository.save(arch);
        if(activityRoleRepository.findByName(dev.getName()) == null)
            activityRoleRepository.save(dev);

        try {
            DateDimUtil.initDateDim();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        alreadySetup = true;
    }

    /*@Transactional
    protected Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }*/

    /*@Transactional
    protected Role createRoleIfNotFound(
            String name, Set<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }*/

    @Transactional
    protected Role createRoleIfNotFound(
            String name) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }
}
