
package com.todoapp.demo.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * A filter that logs request and response details including method, URI, query parameters,
 * request body, response status, response body, and user agent.
 */

@Component
public class RequestLoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

        long start = System.currentTimeMillis();
        chain.doFilter(wrappedRequest, wrappedResponse);
        long duration = System.currentTimeMillis() - start;

        String method = wrappedRequest.getMethod();
        String uri = wrappedRequest.getRequestURI();
        String queryString = wrappedRequest.getQueryString();
        String userAgent = wrappedRequest.getHeader("User-Agent");
        int status = wrappedResponse.getStatus();

        String requestBody = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
        String responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);

        System.out.printf(
            "Request: %s %s%s \n| Query: %s \n| Body: %s \n| Status: %d \n| Response: %s \n| Time: %dms \n| UA: %s%n \n",
            method,
            uri,
            (queryString != null ? "?" + queryString : ""),
            queryString,
            requestBody,
            status,
            responseBody,
            duration,
            userAgent
        );

        wrappedResponse.copyBodyToResponse();
    }
}