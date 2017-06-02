package org.learning.abshar.ws.security;

import org.learning.abshar.ws.model.Account;
import org.learning.abshar.ws.model.Role;
import org.learning.abshar.ws.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AccountUserDetailsService implements UserDetailsService{
    @Autowired
    AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = accountService.findByUsername(s);
        if(account == null){
            // Not found
            return null;
        }
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for(Role role:account.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getCode()));
        }
        User userDetails = new User(account.getUsername(),account.getPassword(),
                account.isEnabled(),!account.isExpired(),!account.isCredentialsExpired(),
                !account.isLocked() ,grantedAuthorities);
        return userDetails;
    }
}
