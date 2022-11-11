package com.example.springsecurityintro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;

public class LoggingFilter extends OncePerRequestFilter {

    private static Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        // このメソッドを呼ぶとリクエストボディがキャッシュされる
        requestWrapper.getParameterNames();
        String requestBody = new String(requestWrapper.getContentAsByteArray(), requestWrapper.getCharacterEncoding());
        logger.info("リクエストボディ = {}", requestBody);
        filterChain.doFilter(requestWrapper, response);
        dumpRequest(request);
        dumpSession(request);
    }

    private void dumpRequest(HttpServletRequest request) {
        for (Iterator<String> iterator = request.getAttributeNames().asIterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            Object value = request.getAttribute(key);
            logger.info("Request key = {}, value = {}", key, value);
        }
    }

    private void dumpSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            logger.info("Session is null");
            return;
        }
        for (Iterator<String> iterator = session.getAttributeNames().asIterator(); iterator.hasNext();) {
            String key = iterator.next();
            Object value = session.getAttribute(key);
            logger.info("Session key = {}, value = {}", key, value);
        }
    }
}
