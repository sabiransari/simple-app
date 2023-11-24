package com.simple.app.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.app.model.ApiTrail;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class AuditTrailFilter extends OncePerRequestFilter {
    @Autowired
    private TrailSender trailSender;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(req, res);

        if (RequestMethod.HEAD.toString().equals(request.getMethod()) ||
                RequestMethod.OPTIONS.toString().equals(request.getMethod())) {
            return;
        }

        log.debug("request {}, response {}", request, response);

        ApiTrail trail = new ApiTrail();
        trail.setRequestSource(req.getHeader("x-forwarded-for"));
        trail.setRequestMethod(RequestMethod.valueOf(request.getMethod()));
        trail.setRequestPath(req.getRequestURI());

        byte[] reqBytes = req.getContentAsByteArray();
        byte[] resBytes = res.getContentAsByteArray();
        trail.setRequestBody(new String(reqBytes, StandardCharsets.UTF_8));
        trail.setResponseBody(new String(resBytes, StandardCharsets.UTF_8));

        trail.setSuccessful(res.getStatus() == 200);
        trailSender.send(objectMapper.writeValueAsString(trail));
    }
}
