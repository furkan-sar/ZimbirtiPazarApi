package com.zimbirtipazar.controller;

import com.zimbirtipazar.dto.ApiResponse;
import com.zimbirtipazar.dto.UserResponse;
import com.zimbirtipazar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/make-admin/{userId}")
    public ResponseEntity<ApiResponse<?>> makeUserAdmin(@PathVariable Long userId) {
        userService.makeUserAdmin(userId);
        return ResponseEntity.ok(ApiResponse.success("Kullanıcı admin yapıldı."));
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/remove-admin/{userId}")
    public ResponseEntity<ApiResponse<?>> removeAdminRole(@PathVariable Long userId) {
        userService.removeAdminRole(userId);
        return ResponseEntity.ok(ApiResponse.success("Admin yetkisi kaldırıldı."));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> findAll() {
        List<UserResponse> users = userService.findAll();
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN') or #id == principal.id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> findById(@PathVariable Long id) {
        UserResponse response = userService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PutMapping("/approve-user/{id}")
    public ResponseEntity<ApiResponse<String>> approveUser(@PathVariable Long id) {
        userService.approveUser(id);
        return ResponseEntity.ok(ApiResponse.success("Kullanıcı onaylandı."));
    }


}
