package org.gourd.hu.gateway.handler;

import org.gourd.hu.gateway.exception.UnauthorizedException;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gourd.hu
 */
public class JsonErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {
    public JsonErrorWebExceptionHandler(ErrorAttributes errorAttributes,
                                        ResourceProperties resourceProperties,
                                        ErrorProperties errorProperties,
                                        ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        int code = HttpStatus.UNAUTHORIZED.value();
        // 这里其实可以根据异常类型进行定制化逻辑
        Throwable error = super.getError(request);
        String body;
        if (error instanceof NotFoundException) {
            code = HttpStatus.NOT_FOUND.value();
            body = "Service Not Found";
        } else if (error instanceof ResponseStatusException || error instanceof UnauthorizedException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) error;
            code = responseStatusException.getStatus().value();
            body = responseStatusException.getMessage();
        }else {
            code = HttpStatus.INTERNAL_SERVER_ERROR.value();
            body = "Internal Server Error";
        }
        Map<String, Object> errorAttributes = new HashMap<>(8);
        errorAttributes.put("timestamp", System.currentTimeMillis());
        errorAttributes.put("message", body);
        errorAttributes.put("code", code);
        errorAttributes.put("method", request.methodName());
        errorAttributes.put("path", request.path());
        return errorAttributes;
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        // 这里其实可以根据errorAttributes里面的属性定制HTTP响应码
        return HttpStatus.OK.value();
    }
}
