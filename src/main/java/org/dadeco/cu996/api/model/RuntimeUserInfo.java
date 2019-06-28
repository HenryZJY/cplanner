package org.dadeco.cu996.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Setter
@Getter
public class RuntimeUserInfo extends User {
    private String name;
    private String ntAccount;
    private String email;

    public RuntimeUserInfo(String username, String password, String name, String email, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, true, true, true, true, authorities);
        this.name = name;
        this.ntAccount = username;
        this.email = email;
    }
}
