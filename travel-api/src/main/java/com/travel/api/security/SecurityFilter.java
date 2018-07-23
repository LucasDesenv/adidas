package com.travel.api.security;

import io.jsonwebtoken.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lusouza on 22/07/18.
 */
public class SecurityFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final Claims claims;

        try {
            claims = TokenUtils.decodeToken(authHeader);
        } catch(IllegalAccessException e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            return;
        } catch (ExpiredJwtException e) {
            response.sendError(HttpStatus.REQUEST_TIMEOUT.value(), "Oops! Seems like you been inactive for long time. For your safety, please login again.");
            return;
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
            return;
        }

        refreshToken(response, claims);
        chain.doFilter(servletRequest, servletResponse);
    }

    private void refreshToken(HttpServletResponse response, Claims claims) {
        String token = TokenUtils.createToken(claims);
        response.setHeader(HttpHeaders.AUTHORIZATION, token);
    }
}
