package com.reesecake.simple_hsk;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                return "error/400";
            }
            else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return "error/401";
            }
            else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error/403";
            }
            else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            }
            else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
                return "error/405";
            }
            else if (statusCode == HttpStatus.NOT_ACCEPTABLE.value()) {
                return "error/406";
            }
            else if (statusCode == HttpStatus.PRECONDITION_FAILED.value()) {
                return "error/412";
            }
            else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/500";
            }
            else if (statusCode == HttpStatus.NOT_IMPLEMENTED.value()) {
                return "error/501";
            }
            else if (statusCode == HttpStatus.BAD_GATEWAY.value()) {
                return "error/502";
            }

        }
        return "error";
    }

}
