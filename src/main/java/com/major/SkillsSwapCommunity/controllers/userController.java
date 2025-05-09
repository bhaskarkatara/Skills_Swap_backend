package com.major.SkillsSwapCommunity.controllers;

import com.major.SkillsSwapCommunity.entity.UserDetails;
import com.major.SkillsSwapCommunity.entity.userDetailsUpdate;
import com.major.SkillsSwapCommunity.jwtUtils.jwtUtils;
import com.major.SkillsSwapCommunity.passwordUtils.passwordUtils;
import com.major.SkillsSwapCommunity.service.userService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class userController {

    @Autowired
    private userService UserService;

    @Autowired
    private passwordUtils Passwordutils;

    @Autowired
    private jwtUtils JwtUtils;




    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String tokenHeader) {
        System.out.println("toke is "+ tokenHeader);

        return UserService.check(tokenHeader);
    }

    @PutMapping("/update")
    public ResponseEntity<?> userProfileUpdate(@RequestHeader("Authorization") String tokenHeader, @RequestBody userDetailsUpdate updatedUser) {

        ResponseEntity<?> checkResponse = UserService.check(tokenHeader);
        HttpStatus status = (HttpStatus) checkResponse.getStatusCode();

        if (status == HttpStatus.BAD_REQUEST || status == HttpStatus.UNAUTHORIZED) {
            return checkResponse; // directly return the error response
        }

        @SuppressWarnings("unchecked")
        Optional<User> optionalUser = (Optional<User>) checkResponse.getBody();


        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        UserDetails existingUser = (UserDetails) optionalUser.get();

        existingUser.setName(updatedUser.getName());
        existingUser.setSkills(updatedUser.getSkills());
        existingUser.setContact(updatedUser.getContact());

        UserService.save(existingUser);

        return ResponseEntity.ok(existingUser);
    }
}

