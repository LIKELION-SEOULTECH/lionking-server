package com.example.lionking.domain.user.service;

import com.example.lionking.domain.user.entity.User;
import com.example.lionking.domain.user.repository.UserRepository;
import com.example.lionking.domain.user.dto.UserRequest;
import com.example.lionking.domain.user.dto.UserResponse;
import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.repository.MemberRepository;
import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse signUp(UserRequest request) {
        validateDuplicate(request.loginId(), request.email());

        User user = request.toUser(passwordEncoder.encode(request.password()));
        userRepository.save(user);

        Member member = request.toMember(user);
        memberRepository.save(member);

        return UserResponse.from(member);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> UserResponse.from(user.getMember()))
                .toList();
    }

    private void validateDuplicate(String loginId, String email) {
        if (userRepository.existsByLoginId(loginId)) {
            throw new CustomException(GlobalErrorCode.BAD_REQUEST, "중복된 로그인 아이디입니다.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new CustomException(GlobalErrorCode.BAD_REQUEST,"중복된 이메일 주소입니다.");
        }
    }

}