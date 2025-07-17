package com.zimbirtipazar.service;

import com.zimbirtipazar.dto.AuthRequest;
import com.zimbirtipazar.dto.UserResponse;
import com.zimbirtipazar.entity.Role;
import com.zimbirtipazar.entity.User;
import com.zimbirtipazar.exception.BusinessException;
import com.zimbirtipazar.exception.ErrorCode;
import com.zimbirtipazar.repository.UserRepository;
import com.zimbirtipazar.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private UserResponse toDto(User user) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);

        return userResponse;
    }

    @Transactional
    public UserResponse save(AuthRequest request, Role role) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw BusinessException.of(ErrorCode.USERNAME_ALREADY_TAKEN);
        }

        User savedUser = userRepository.save(createUser(request, role));

        UserResponse createdUserResponse = new UserResponse();
        BeanUtils.copyProperties(savedUser, createdUserResponse);

        return createdUserResponse;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long userId) {
        User sentRequestUser = userRepository.findByUsername(SecurityUtil.getCurrentUsername())
                .orElseThrow(() -> BusinessException.of(ErrorCode.USER_NOT_FOUND));

        if (SecurityUtil.isUser() && !userId.equals(sentRequestUser.getId())) {
            throw new AccessDeniedException(ErrorCode.FORBIDDEN.getDescription());
        }

        return toDto(userRepository.findById(userId)
                .orElseThrow(() -> BusinessException.of(ErrorCode.USER_NOT_FOUND)));
    }

    @Transactional
    public void approveUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> BusinessException.of(ErrorCode.USER_NOT_FOUND));

        if (user.isApproved()) {
            throw BusinessException.of(ErrorCode.USER_ALREADY_APPROVED);
        }

        user.setApproved(true);
        userRepository.save(user);
    }

    private UserResponse updateRole(User user, Role role) {
        user.setRole(role);
        User updatedUser = userRepository.save(user);

        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(updatedUser, response);
        return response;
    }

    @Transactional
    public UserResponse makeUserAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> BusinessException.of(ErrorCode.USER_NOT_FOUND));

        if (user.getRole().equals(Role.ADMIN)) {
            throw BusinessException.of(ErrorCode.USER_ALREADY_ADMIN);
        }

        return updateRole(user, Role.ADMIN);
    }

    @Transactional
    public UserResponse removeAdminRole(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> BusinessException.of(ErrorCode.USER_NOT_FOUND));

        if (!user.getRole().equals(Role.ADMIN)) {
            throw BusinessException.of(ErrorCode.USER_NOT_ADMIN);
        }

        return updateRole(user, Role.USER);
    }

    private User createUser(AuthRequest request, Role role) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setApproved(false);

        return user;
    }

}
