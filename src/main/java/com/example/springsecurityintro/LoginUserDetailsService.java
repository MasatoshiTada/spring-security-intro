package com.example.springsecurityintro;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class LoginUserDetailsService implements UserDetailsService {

    private final LoginUserRepository loginUserRepository;

    public LoginUserDetailsService(LoginUserRepository loginUserRepository) {
        this.loginUserRepository = loginUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<LoginUser> loginUserOptional = loginUserRepository.findByEmail(email);
        return loginUserOptional.map(loginUser -> new LoginUserDetails(loginUser,toAuthorities(loginUser)))
                .orElseThrow(() -> new UsernameNotFoundException("not found"));
    }

    // LoginUserからロールのリストを取得して、GrantedAuthorityのリストに変換する
    private Collection<? extends GrantedAuthority> toAuthorities(LoginUser loginUser) {
        return loginUser.roleList().stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .toList();
    }
}
