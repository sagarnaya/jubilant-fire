package com.bank.backend.service;

import com.bank.backend.configuration.CustomGrantedAuthority;
import com.bank.backend.dto.UserDto;
import com.bank.backend.dto.UserLoginDto;
import com.bank.backend.model.User;
import com.bank.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    public User registerUser(UserDto userDto) {
        try {
            User user = new User();
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setRole(userDto.getRole());
            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            user = userRepository.save(user);
            return user;
        } catch (Exception e) {
            log.error("Exception while registering user, cause of Exception " + e.getCause());
        }
        return null;
    }

    public User loginUser(UserLoginDto userLoginDto) {
        try {
            return userRepository.findByEmailAndPasswordAndRole(userLoginDto.getEmail(), userLoginDto.getPassword(), userLoginDto.getRole());
        } catch (Exception e) {
            log.error("Exception while logging in, cause of Exception " + e.getCause());
        }
        return null;
    }

    public List<User> fetchAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            log.error("Exception while fetching all users, cause of Exception " + e.getCause());
        }
        return null;
    }

    public User findByEmailAndRole(String email, String role) {
        try {
            return userRepository.findByEmailAndRole(email, role);
        } catch (Exception e) {
            log.error("Exception while fetching user by email and role, cause of Exception " + e.getCause());
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByEmailAndRole(s.split(" ")[0],
                    s.split(" ")[1].toLowerCase(Locale.ROOT));

            ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new CustomGrantedAuthority(user.getRole()));

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    grantedAuthorities);
        } catch (Exception e) {
            log.error("Exception while loading user by username, cause of Exception " + e.getCause());
        }
        return null;
    }
}
