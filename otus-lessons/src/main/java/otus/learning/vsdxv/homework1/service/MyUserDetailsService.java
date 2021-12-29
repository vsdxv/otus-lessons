package otus.learning.vsdxv.homework1.service;

import lombok.SneakyThrows;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.learning.vsdxv.homework1.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUserName(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        return buildUserForAuthentication(user, authorities);
    }
    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true, true, authorities);
    }
}
