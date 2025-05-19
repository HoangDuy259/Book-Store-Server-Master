package com.example.assignment1_api.service.user;

import com.example.assignment1_api.dto.identity.ClientTokenExchangeParam;
import com.example.assignment1_api.dto.identity.KeycloakProvider;
import com.example.assignment1_api.dto.identity.TokenExchangeResponse;
import com.example.assignment1_api.dto.user.UserDto;
import com.example.assignment1_api.entity.user.User;
import com.example.assignment1_api.repository.UserRepository;
import com.example.assignment1_api.request.RegisterUserModel;
import com.example.assignment1_api.request.user.LoginRequest;
import com.example.assignment1_api.response.keycloak.IdentityClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final KeycloakProvider keycloakProvider;
    private final IdentityClient identityClient;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public TokenExchangeResponse login(LoginRequest loginRequest) throws AuthenticationException {
        User user = userRepository.findUserByUsername(loginRequest.getUsername());

        // Kiểm tra nếu user không tồn tại
        if (user == null) {
            throw new AuthenticationException("Sai tên đăng nhập hoặc mật khẩu.");
        }

        // Kiểm tra nếu tài khoản bị vô hiệu hóa
        if (!user.getEnabled()) {
            throw new DisabledException("Tài khoản chưa được kích hoạt.");
        }

        // Gửi request đến Keycloak để lấy token
        return identityClient.exchangeTokenClient(ClientTokenExchangeParam.builder()
                .grant_type("password")
                .client_id(keycloakProvider.getClientID())
                .client_secret(keycloakProvider.getClientSecret())
                .username(loginRequest.getUsername())
                .password(loginRequest.getPassword())
                .scope("openid")
                .build());
    }

    @Override
    public User register(RegisterUserModel registerUserModel) {
        // Kiểm tra username đã tồn tại chưa
        if (userRepository.existsByUsername(registerUserModel.getUserName())) {
            throw new IllegalArgumentException("Username đã tồn tại");
        }

        // Kiểm tra email đã tồn tại chưa
        if (userRepository.existsByEmail(registerUserModel.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        // Nếu không bị trùng, tiếp tục tạo user
        User user = modelMapper.map(registerUserModel, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        return userRepository.save(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return List.of();
    }

    @Override
    public UserDto getUserById() {
        return null;
    }

    @Override
    public Boolean deleteUser(Long userId) {
        return null;
    }

    @Override
    public boolean isUserEnabled(String username) {
        return false;
    }
}
