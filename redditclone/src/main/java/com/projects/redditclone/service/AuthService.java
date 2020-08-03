package com.projects.redditclone.service;

import com.projects.redditclone.dto.RegisterRequest;
import com.projects.redditclone.exception.SpringRedditException;
import com.projects.redditclone.model.NotificationEmail;
import com.projects.redditclone.model.User;
import com.projects.redditclone.model.VerificationToken;
import com.projects.redditclone.repository.UserRepository;
import com.projects.redditclone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
@Slf4j
public class AuthService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    @Transactional
    public void signup(RegisterRequest registerRequest){
        User user=new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token=generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please activate your Acoount",user.getEmail(),"Thank you for signing up to our Reddit Clone,"+" please click on the below url to activate your account: "+"" +
                "http://localhost:8080/api/auth/accountVerification/"+ token));
    }

    private String generateVerificationToken(User user) {
        String token= UUID.randomUUID().toString();

        VerificationToken verificationToken=new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(token);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token){
        Optional<VerificationToken>  verificationToken=verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(()->new SpringRedditException("Invalid token"));
        fetchUserAndEnable(verificationToken.get());

    }
@Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username=verificationToken.getUser().getUsername();
        User user=userRepository.findByUsername(username).orElseThrow(()->new SpringRedditException("User not found with name: "+ username));
        user.setEnabled(true);
        userRepository.save(user);

    }


}
