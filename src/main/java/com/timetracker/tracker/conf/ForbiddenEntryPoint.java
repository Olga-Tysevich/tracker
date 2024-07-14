package com.timetracker.tracker.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timetracker.tracker.dto.resp.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.OutputStream;

import static com.timetracker.tracker.utils.Constants.FORBIDDEN_KEY;
import static com.timetracker.tracker.utils.Constants.FORBIDDEN_MESSAGE;


/**
 * This class represents an implementation of an AuthenticationEntryPoint that handles the commencement
 * of authentication when access to a resource is forbidden.
 */
public class ForbiddenEntryPoint implements AuthenticationEntryPoint {
    /**
     * This method is called when access to a resource is forbidden.It sets the HTTP response status to
     * SC_FORBIDDEN and writes an ErrorResponse object as a JSON value to the response output stream.
     *
     * @param request       the HttpServletRequest object representing the request made to the server.
     * @param response      the HttpServletResponse object representing the response to be sent back to the client.
     * @param authException the AuthenticationException object representing the authentication exception that occurred.
     * @throws IOException if an input or output exception occurs while writing to the response output stream.
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try (OutputStream os = response.getOutputStream()) {
            ErrorResponse resp = new ErrorResponse(FORBIDDEN_KEY, FORBIDDEN_MESSAGE);
            new ObjectMapper().writeValue(os, resp);
            os.flush();
        }
    }
}
