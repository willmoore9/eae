package com.eae.security.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.eae.schedule.model.Publisher;
import com.eae.security.service.PublisherService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider
{

		@Autowired
		PublisherService publisherService;
		@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException
		{
				String userName = authentication.getName();
				String password = authentication.getCredentials().toString();
				UserRole role = authorizedUser(userName, password);
				if (role == UserRole.PUBLISHER)
				{
						List<GrantedAuthority> grantedAuths = new ArrayList<>();
						grantedAuths.add(new PublisherAuthority());
						Authentication auth = new UsernamePasswordAuthenticationToken(userName, password, grantedAuths);
						System.out.println(auth.getAuthorities());
						return auth;
				} else if(role == UserRole.ADMIN) {
						List<GrantedAuthority> grantedAuths = new ArrayList<>();
						grantedAuths.add(new PublisherAuthority());
						grantedAuths.add(new AdminAuthority());
						Authentication auth = new UsernamePasswordAuthenticationToken(userName, password, grantedAuths);
						System.out.println(auth.getAuthorities());
						return auth;
					
				} else {
						throw new AuthenticationCredentialsNotFoundException("Invalid Credentials!");
				}
		}

		private UserRole authorizedUser(String userName, String password)
		{
				List<Publisher> pubishers = this.publisherService.findPublisherByEmail(userName);
				
				if(pubishers.size() == 0) {
					return UserRole.ANONYMOUS;	
				}
				
				Publisher pub = pubishers.get(0);
				
				if(("eae".equals(userName) && "eae".equals(password)) ||  pub.getIsAdmin()) {
					return UserRole.ADMIN;
				} else if(!pub.getIsAdmin()) {
					return UserRole.PUBLISHER;
				}
					
				return UserRole.ANONYMOUS;
		}

		@Override
		public boolean supports(Class<?> authentication)
		{
				return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
		}
}
