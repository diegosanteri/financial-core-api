package br.com.financial.common.exception;

import lombok.extern.slf4j.Slf4j;
import me.alidg.errors.HttpError;
import me.alidg.errors.adapter.HttpErrorAttributesAdapter;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static br.com.financial.common.constant.ErrorFields.MESSAGE_ERROR;
import static br.com.financial.common.constant.ErrorFields.TRANSACTION_ID;
import static br.com.financial.common.constant.RequestHeaders.X_TID_HEADER_KEY;

@Component
@Slf4j
public class ErrorHandler implements HttpErrorAttributesAdapter {

    @Override
    public Map<String, Object> adapt(HttpError httpError) {

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();

        MDC.put(X_TID_HEADER_KEY, request.getHeader(X_TID_HEADER_KEY));

        var error = httpError.getErrors().get(0);
        var map = new HashMap<String, Object>();

        map.put(MESSAGE_ERROR, error.getMessage());
        map.put(TRANSACTION_ID, request.getHeader(X_TID_HEADER_KEY));

        var errors = httpError.getErrors().stream().map(e -> e.getMessage()).collect(Collectors.joining());
        log.error("Errors: [" + errors + "]");

        return map;
    }
}
