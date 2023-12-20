package com.boilerplate.userservice.user.application;

import com.boilerplate.userservice.user.dto.request.UserJoinRequest;
import com.boilerplate.userservice.user.exception.DuplicateEmailException;
import com.boilerplate.userservice.user.persistence.UserRepository;
import com.boilerplate.userservice.user.persistence.domain.Gender;
import com.boilerplate.userservice.user.persistence.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(UserJoinRequest userJoinRequest) {
        User user = convertToUser(userJoinRequest);

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateEmailException("이메일 중복");
        }

        userRepository.save(user);
    }

    private User convertToUser(UserJoinRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Gender gender = Gender.valueOf(request.getGender());
        return new User(request.getEmail(), encodedPassword, request.getNickname(),
                gender, request.getAge());
    }

}
