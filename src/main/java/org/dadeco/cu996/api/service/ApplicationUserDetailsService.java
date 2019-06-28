package org.dadeco.cu996.api.service;

import org.dadeco.cu996.api.model.Role;
import org.dadeco.cu996.api.model.RuntimeUserInfo;
import org.dadeco.cu996.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.dadeco.cu996.api.repository.*;

import javax.transaction.Transactional;
import java.util.*;

@Service("userDetailsService")
@Transactional
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /*@Autowired
    private RoleRepository repository;

    @Autowired
    private PrivilegeRepository privilegeRepository;*/

    @Override
    public UserDetails loadUserByUsername(String ntAccount) throws UsernameNotFoundException {
        User user = userRepository.findByNtAccount(ntAccount);

        if(user != null) {
            RuntimeUserInfo userInfo = new RuntimeUserInfo(user.getNtAccount(), user.getPassword(), user.getName(), user.getEmail(), getAuthorities(user.getRole()));
            return userInfo;
        }
        throw new UsernameNotFoundException("User doesn't exist!");
    }

    private Collection<GrantedAuthority> getAuthorities(
            Role role) {

        return getGrantedAuthorities(role);
    }

    /*private List<String> getPrivileges(Set<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }*/

    /*private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }*/
    private List<GrantedAuthority> getGrantedAuthorities(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        return authorities;
    }

}
