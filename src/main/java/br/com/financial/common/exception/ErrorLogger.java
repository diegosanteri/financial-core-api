package br.com.financial.common.exception;

import lombok.extern.slf4j.Slf4j;
import me.alidg.errors.ExceptionLogger;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static br.com.financial.common.constant.RequestHeaders.X_TID_HEADER_KEY;

@Component
@Slf4j
public class ErrorLogger implements ExceptionLogger {

    private static final String STACK_ERROR_MESSAGE = "Stack error";

    @Override
    public void log(Throwable exception) {
        if (exception != null) {
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                            .getRequest();
            MDC.put(X_TID_HEADER_KEY, request.getHeader(X_TID_HEADER_KEY));
            log.error(STACK_ERROR_MESSAGE, exception);
        }
    }
}