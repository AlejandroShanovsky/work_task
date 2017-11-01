package ua.com.vertex.controllers.exceptionHandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.com.vertex.utils.EmailExtractor;

import java.net.SocketTimeoutException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);
    private static final String ERROR = "error";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String CERTIFICATE_DETAILS = "certificateDetails";
    private static final String LOGIN = "logIn";
    private static final String ACCESS_DENIED = "403";

    private final EmailExtractor emailExtractor;

    @Autowired
    public GlobalExceptionHandler(EmailExtractor emailExtractor) {
        this.emailExtractor = emailExtractor;
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        LOGGER.warn(e, e);
        return ERROR;
    }

    @ExceptionHandler(NoCertificateException.class)
    public String handleNoCertificateException(Exception e, Model model) {
        LOGGER.warn(e, e);
        model.addAttribute(ERROR_MESSAGE, "No certificate with this ID");
        return CERTIFICATE_DETAILS;
    }

    @ExceptionHandler({CannotGetJdbcConnectionException.class, SocketTimeoutException.class})
    public String handleCannotGetJdbcConnectionException(Exception e, Model model) {
        LOGGER.warn(e, e);
        model.addAttribute(ERROR_MESSAGE, "Database might temporarily be unavailable");
        return ERROR;
    }

    @ExceptionHandler(UpdatedPasswordNotSaved.class)
    public String handleUpdatedPasswordNotSaved(UpdatedPasswordNotSaved e, Model model) {
        LOGGER.debug(e, e);
        model.addAttribute(ERROR_MESSAGE, "The new password was not saved. Please, try again");
        return ERROR;
    }

    @ExceptionHandler({AccessDeniedException.class, PreAuthenticatedCredentialsNotFoundException.class})
    public String handleAccessOrAuthenticationException(AccessDeniedException e) {
        String view;
        if (emailExtractor.getEmailFromAuthentication() == null) {
            view = LOGIN;
        } else {
            view = ACCESS_DENIED;
            LOGGER.warn(e, e);
        }
        return view;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String view;
        if (emailExtractor.getEmailFromAuthentication() == null) {
            view = LOGIN;
        } else {
            view = ERROR;
            LOGGER.warn(e, e);
        }
        return view;
    }
}