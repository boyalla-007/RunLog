package com.rc.runlog.Service;

import com.rc.runlog.Dto.UserRequest;
import com.rc.runlog.Repository.UserRepository;
import com.rc.runlog.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password((user.getPassword()))
                .roles("USER")
                .build();
    }
}
