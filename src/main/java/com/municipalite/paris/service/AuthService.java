package com.municipalite.paris.service;

import com.municipalite.paris.dto.auth.AuthResponse;
import com.municipalite.paris.dto.auth.LoginRequest;
import com.municipalite.paris.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);
}


