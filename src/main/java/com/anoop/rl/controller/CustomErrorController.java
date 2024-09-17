package com.anoop.rl.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public void handleError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        
        int statusCode = status != null ? Integer.parseInt(status.toString()) : HttpStatus.INTERNAL_SERVER_ERROR.value();
        String errorMessage = message != null ? message : "An unexpected error occurred.";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = sdf.format(new Date());

        response.setContentType("text/html");
        response.setStatus(statusCode);

        String errorHtml = "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Error</title>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; padding: 20px; background-color: #f4f4f4; color: #333; }" +
                "h1 { color: #ff4d4d; }" +
                "a { color: #007bff; text-decoration: none; }" +
                "a:hover { text-decoration: underline; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h1>Error</h1>" +
                "<p><strong>Message:</strong> " + errorMessage + "</p>" +
                "<p><strong>Status:</strong> " + statusCode + " (" + HttpStatus.valueOf(statusCode).getReasonPhrase() + ")" + "</p>" +
                "<p><strong>Timestamp:</strong> " + formattedTimestamp + "</p>" +
                "<p><a href=\"/\">Go back to the home page</a></p>" +
                "</body>" +
                "</html>";

        response.getWriter().write(errorHtml);
    }
}
