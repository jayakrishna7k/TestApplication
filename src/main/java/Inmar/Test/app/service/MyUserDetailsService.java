package Inmar.Test.app.service;

import Inmar.Test.app.dto.request.UserRequest;
import Inmar.Test.app.dto.response.UserResponse;
import Inmar.Test.app.jpa.model.AppUser;
import Inmar.Test.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUserName(userName);
        if (appUser == null) {
            throw new UsernameNotFoundException("User Name Not found" + userName);
        }
        return new User(appUser.getUsername(), appUser.getPassword(), new ArrayList<>());
    }

    public ResponseEntity signup(UserRequest request) {
        if (userRepository.findByUserName(request.getUserName()) != null) {
            return new ResponseEntity(new UserResponse("User already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        AppUser appUser = new AppUser();
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
        appUser.setUsername(request.getUserName());
        userRepository.save(appUser);
        return ResponseEntity.ok(new UserResponse("User registered successfully"));
    }
}