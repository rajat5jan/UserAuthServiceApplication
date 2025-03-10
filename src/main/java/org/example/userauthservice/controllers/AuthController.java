package org.example.userauthservice.controllers;

import org.example.userauthservice.dtos.LoginRequest;
import org.example.userauthservice.dtos.SignupRequest;
import org.example.userauthservice.dtos.UserDto;
import org.example.userauthservice.models.User;
import org.example.userauthservice.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private IAuthService iAuthService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequest signupRequest){

        User user = new User();
        try {
            user = iAuthService.signup(signupRequest.getEmail(),signupRequest.getPassword());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(from(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequest loginRequest){
        User user = new User();
        try {
            user = iAuthService.login(loginRequest.getEmail(),loginRequest.getPassword());
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

       return new ResponseEntity<>(from(user), HttpStatus.OK);
    }

    public UserDto from(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
