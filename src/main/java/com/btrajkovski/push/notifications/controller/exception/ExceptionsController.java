package com.btrajkovski.push.notifications.controller.exception;

import com.btrajkovski.push.notifications.model.exception.UserNotAuthenticated;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by bojan on 28.3.16.
 */
@ControllerAdvice
public class ExceptionsController {
    @ExceptionHandler(value = UserNotAuthenticated.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "User Not Authenticated")
    public void userNotAuthenticated() {
        // Do nothing
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Access Denied")
    public void accessDenied() {
        // Do nothing
    }
}
