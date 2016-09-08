package com.chyld.controllers;

import com.chyld.entities.Profile;
import com.chyld.entities.User;
import com.chyld.security.JwtToken;
import com.chyld.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createProfile(@RequestBody Profile profile, Principal user) {
        int uid = ((JwtToken)user).getUserId();
        User u = userService.findUserById(uid);

        if(u.getProfile() == null){
            u.setProfile(profile);
            profile.setUser(u);
        } else {
            u.getProfile().setAge(profile.getAge());
            u.getProfile().setGender(profile.getGender());
            u.getProfile().setPhoto(profile.getPhoto());
            u.getProfile().setHeight(profile.getHeight());
            u.getProfile().setWeight(profile.getWeight());
        }

        userService.saveUser(u);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Profile getProfile(Principal user) {
        int uid = ((JwtToken)user).getUserId();
        User u = userService.findUserById(uid);
        return u.getProfile();
    }
}
