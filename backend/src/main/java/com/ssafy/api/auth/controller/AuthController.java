package com.ssafy.api.auth.controller;

import com.ssafy.api.auth.dto.request.AuthRequestDto;
import com.ssafy.api.auth.dto.response.OAuthDto;
import com.ssafy.api.auth.service.AuthService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@RestController
public class AuthController {
    private final AuthService authService;


    @PostMapping("/kakao")
    public ResponseEntity<? extends BaseResponseBody> join(@RequestBody AuthRequestDto requestDto, HttpServletResponse response){
        OAuthDto oAuthDto = authService.signUp(requestDto);
        JwtTokenProvider.setTokenInHeader(response, "Bearer " + oAuthDto.getToken());
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

}
