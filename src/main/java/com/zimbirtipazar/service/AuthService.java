package com.zimbirtipazar.service;

import com.zimbirtipazar.dto.AuthRequest;
import com.zimbirtipazar.dto.AuthResponse;
import com.zimbirtipazar.dto.UserResponse;
import com.zimbirtipazar.entity.Role;
import com.zimbirtipazar.entity.User;
import com.zimbirtipazar.exception.BusinessException;
import com.zimbirtipazar.exception.ErrorCode;
import com.zimbirtipazar.repository.UserRepository;
import com.zimbirtipazar.security.jwt.JwtTokenProvider;
import com.zimbirtipazar.util.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthenticationProvider authenticationProvider;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserResponse register(AuthRequest request) {
        if (!PasswordValidator.isValid(request.getPassword())) {
            throw BusinessException.of(ErrorCode.PASSWORD_NOT_VALID);
        }

        return userService.save(request, Role.USER);
    }

    @Transactional(readOnly = true)
    public AuthResponse authenticate(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException(ErrorCode.BAD_CREDENTIALS.getDescription()));

        authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword())
        );

        if (!user.isApproved()) {
            throw new AccessDeniedException(ErrorCode.USER_NOT_APPROVED.getDescription());
        }


        String accessToken = jwtTokenProvider.generateToken(user);

        return new AuthResponse(accessToken);
    }
}