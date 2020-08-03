package com.projects.redditclone.controller;

import com.projects.redditclone.dto.RegisterRequest;
import com.projects.redditclone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest){
    authService.signup(registerRequest);
    return new ResponseEntity<>("user registration susccesfull",
            HttpStatus.OK);
    }
    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return  new ResponseEntity<>("Account activated succesfully",
                HttpStatus.OK);
    }

}
