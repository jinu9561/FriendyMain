package web.mvc.config.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import web.mvc.entity.user.Users;
import web.mvc.repository.user.UserRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Transactional
public class CustomLogoutHandler implements LogoutHandler {

    private final UserRepository userRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        // 뷰에서 localStorage에 있는 걸 꺼내서 form형식으로 보내줘야 된다.

        String userId = request.getParameter("userId");
        Users user = userRepository.findUserByUserId(userId);
//
//        if (!user.getRole().equals("ROLE_ADMIN")) {
//            user.getUserDetail().setLastLoginDate(LocalDateTime.now());
//            userRepository.save(user);
//        }

        user.getUserDetail().setLastLoginDate(LocalDateTime.now());
        userRepository.save(user);

        SecurityContextHolder.clearContext();

    }
}
