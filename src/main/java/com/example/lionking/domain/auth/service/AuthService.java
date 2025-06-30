package com.example.lionking.domain.auth.service;

import com.example.lionking.domain.auth.dto.LoginUserResponse;
import com.example.lionking.domain.auth.redis.RefreshTokenRedisService;
import com.example.lionking.domain.auth.security.JwtProvider;
import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.auth.dto.LoginRequest;
import com.example.lionking.domain.auth.dto.LoginResponse;
import com.example.lionking.domain.member.repository.MemberRepository;
import com.example.lionking.domain.user.entity.User;
import com.example.lionking.domain.user.repository.UserRepository;
import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRedisService refreshTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByLoginId(request.loginId())
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 로그인 ID입니다."));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new CustomException(GlobalErrorCode.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        Member member = user.getMember();
        Long userId = user.getId();

        // JWT 토큰 발급
        String accessToken = jwtProvider.createAccessToken(user.getId(), member.getId(), member.getRole().name());
        String refreshToken = jwtProvider.createRefreshToken(userId);

        // RefreshToken Redis 저장 or 갱신
        refreshTokenService.save(userId, refreshToken, jwtProvider.getRefreshTokenTtlSeconds());

        return new LoginResponse(accessToken, refreshToken, user.getId(), member.getId(), member.getUsername());
    }

    @Transactional
    public void logout(String accessToken) {
        if (accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }

        Long userId = jwtProvider.getUserIdFromAccessToken(accessToken);
        refreshTokenService.delete(userId);
    }

    public Map<String, String> reissueTokens(String oldRefreshToken) {
        Long userId = jwtProvider.getUserIdFromRefreshToken(oldRefreshToken);
        String savedToken = refreshTokenService.getToken(userId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.INVALID_TOKEN, "Refresh Token 없음"));

        if (!savedToken.equals(oldRefreshToken)) {
            throw new CustomException(GlobalErrorCode.UNAUTHORIZED, "Refresh Token이 일치하지 않습니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "사용자 없음"));

        Member member = user.getMember();

        String newAccessToken = jwtProvider.createAccessToken(user.getId(), member.getId(), member.getRole().name());
        String newRefreshToken = jwtProvider.createRefreshToken(userId);

        refreshTokenService.save(userId, newRefreshToken, jwtProvider.getRefreshTokenTtlSeconds());

        return Map.of(
                "accessToken", newAccessToken,
                "refreshToken", newRefreshToken
        );
    }

    public LoginUserResponse checkUser(String accessToken) {
        if (accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }

        Long userId = jwtProvider.getUserIdFromAccessToken(accessToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));
        Member member = memberRepository.findById(user.getMember().getId())
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));
        return LoginUserResponse.from(member);
    }
}