package com.btrajkovski.push.notifications.controller.filter;

import com.btrajkovski.push.notifications.controller.auth.utils.SecurityUtil;
import com.btrajkovski.push.notifications.model.auth.User;
import com.btrajkovski.push.notifications.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by bojan on 2.6.16.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class APILoginFilter extends OncePerRequestFilter {
    private UserRepository userRepository;

    @Autowired
    public APILoginFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {


        if (httpServletRequest.getHeader("API-Authorization") != null) {
            Long id = Long.parseLong(httpServletRequest.getHeader("API-Authorization"));
            User user = userRepository.findOne(id);

            if (user != null) {
                SecurityUtil.logInUser(user);

                // Create a new session and add the security context.
                HttpSession session = httpServletRequest.getSession(true);
                session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
