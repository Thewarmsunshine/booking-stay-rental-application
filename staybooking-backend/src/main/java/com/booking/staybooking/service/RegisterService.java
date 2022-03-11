package com.booking.staybooking.service;

import com.booking.staybooking.entity.UserRole;
import com.booking.staybooking.exception.UserAlreadyExistException;
import com.booking.staybooking.repository.AuthorityRepository;
import com.booking.staybooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.booking.staybooking.entity.Authority;
import com.booking.staybooking.entity.User;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RegisterService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(User user, UserRole role) throws UserAlreadyExistException {
        if (userRepository.existsById(user.getUsername())) {
            throw new UserAlreadyExistException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        authorityRepository.save(new Authority(user.getUsername(), role.name()));
    }
}
