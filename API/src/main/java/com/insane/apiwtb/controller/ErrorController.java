package com.insane.apiwtb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping("error")
    private String handleError(HttpServletRequest request, HttpServletResponse response, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int statusCode = Integer.parseInt(status.toString());

        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            model.addAttribute("msg", "Page not found! Status code: " + statusCode);
        } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
            model.addAttribute("msg", "Permission denied!");
        } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            model.addAttribute("msg", "Internal server error! Status code: "+statusCode);
        } else {
            model.addAttribute("msg", "Oops Something went wrong! Status code: "+statusCode);
        }

        response.setStatus(statusCode);
        return "index";
    }

}
