package com.example.auth_service.service;

import com.example.auth_service.dto.UserDto;
import com.example.auth_service.entity.User;
import com.example.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;

    public User createUser(UserDto userDto) throws Exception {
        if (userRepository.findByUsername(userDto.username()).isPresent()) {
            throw new Exception();
        }
        User user = new User()
                .username(userDto.username())
                .password(passwordEncoder.encode(userDto.password()))
                .roles(Set.of(roleService.findByName("USER")));
        return userRepository.save(user);
    }

    @SuppressWarnings("unused")
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
