package com.project.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.blog.controllers.AuthController;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	Logger logger =  LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		logger.info("request :- {}",request);
		// 1. get token
		String requestToken = request.getHeader("Authorization");
		
		// Bearer 23525223hghjk
		
		logger.info("requestToken :- {}",requestToken);
				
		String username = null;
		String token = null;
		
		if(requestToken != null && requestToken.startsWith("Bearer")) {
			
			token = requestToken.substring(7);
			try {
				username = this.jwtTokenHelper.getUsernameFromToken(token);
		
			}
			catch (IllegalArgumentException e) {
				System.out.println("Unable to get Jwt token : "+e.toString());
			}
			catch (ExpiredJwtException e) {
				System.out.println("Jwt token has been WExpired");
			}
			catch (MalformedJwtException e) {
				System.out.println("Invalid Jwt : "+e.toString());
			}
		}
		else {
			System.out.println("jwt does not beging with Bearer");
		}
		
		// once we get token , noe validate
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if(this.jwtTokenHelper.validateToken(token, userDetails)) {
				// sahi chal raha h
				// authentication karna h
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else {
				System.out.println("Invalid token");
			}
		}
		else {
			System.out.println("UserName is null or Context is not null");
		}
		
		
		filterChain.doFilter(request, response);
		
		
	}

	
	
	
}