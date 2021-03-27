package com.sparta.eng80.pressplay.services;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class SecurityService {

    private final AuthenticationManager authenticationManager;

    private final LoginCredentialService loginCredentialService;

    public SecurityService(AuthenticationManager authenticationManager, LoginCredentialService loginCredentialService) {
        this.authenticationManager = authenticationManager;
        this.loginCredentialService = loginCredentialService;
    }

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null ||
                AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    public void autoLogin(String email, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = authToken(email, password);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

    public UsernamePasswordAuthenticationToken authToken(String email, String password) {
        UserDetails userDetails = loginCredentialService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return usernamePasswordAuthenticationToken;
    }
}
