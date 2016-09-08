package com.chyld.controllers;

import com.chyld.dtos.AuthDto;
import com.chyld.entities.Exercise;
import com.chyld.entities.Profile;
import com.chyld.entities.Role;
import com.chyld.entities.User;
import com.chyld.enums.RoleEnum;
import com.chyld.security.JwtToken;
import com.chyld.utilities.JwtUtil;
import com.chyld.services.RoleService;
import com.chyld.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Exercise> getExercises(Principal user) {
        int uid = ((JwtToken)user).getUserId();
        User u = userService.findUserById(uid);
        return u.getExercises();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Exercise createExercise(Principal user, @RequestBody Exercise exercise) {
        int uid = ((JwtToken)user).getUserId();
        User u = userService.findUserById(uid);
        exercise.setUser(u);
        u.getExercises().add(exercise);
        userService.saveUser(u);
        return exercise;
    }
}
