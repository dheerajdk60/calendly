package com.mountblue.app.service;

import com.mountblue.app.model.MyUserDetails;
import com.mountblue.app.model.User;
import com.mountblue.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user=userRepository.findByName(userName);
        user.orElseThrow(()->new UsernameNotFoundException("Not found "+userName));
        return new MyUserDetails(user.get());
    }
}
