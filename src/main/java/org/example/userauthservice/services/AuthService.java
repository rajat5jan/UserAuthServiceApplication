package org.example.userauthservice.services;

import org.example.userauthservice.exceptions.PasswordMissMatchException;
import org.example.userauthservice.exceptions.UserAlreadyExist;
import org.example.userauthservice.exceptions.UserNotRegisterException;
import org.example.userauthservice.models.Role;
import org.example.userauthservice.models.User;
import org.example.userauthservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private final UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User signup(String email, String password) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if (userOptional.isPresent()) {
            try {
                throw new UserAlreadyExist("Please try logging...");
            } catch (UserAlreadyExist ex) {
                System.err.println(ex.getMessage());
            }
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        Role role = new Role();
        role.setValue("CUSTOMER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        userRepo.save(user);

        return user;
    }

    @Override
    public User login(String email, String password) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if (userOptional.isEmpty()) {
            try {
                throw new UserNotRegisterException("Please signup first");
            } catch (UserNotRegisterException e) {
                throw new RuntimeException(e);
            }
        }

        String storePassword = userOptional.get().getPassword();
        if (!bCryptPasswordEncoder.matches(password, storePassword)) {
            try {
                throw new PasswordMissMatchException("Please try again with the correct password");
            } catch (PasswordMissMatchException e) {
                throw new RuntimeException(e);
            }
        }

        return  userOptional.get();

    }
}
