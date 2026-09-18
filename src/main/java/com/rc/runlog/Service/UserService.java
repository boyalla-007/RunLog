package com.rc.runlog.Service;

import com.rc.runlog.Dto.UserRequest;
import com.rc.runlog.Dto.UserRespone;
import com.rc.runlog.Repository.UserRepository;
import com.rc.runlog.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public UserRespone createUser(UserRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        return new UserRespone(
                savedUser.getId().toString(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );
    }
}

