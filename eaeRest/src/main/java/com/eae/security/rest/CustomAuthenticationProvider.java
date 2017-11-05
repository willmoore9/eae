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
				Publisher role = authorizedUser(userName, password);
				if (role != null && !role.getIsAdmin())
				{
						List<GrantedAuthority> grantedAuths = new ArrayList<>();
						grantedAuths.add(new PublisherAuthority());
						Authentication auth = new UsernamePasswordAuthenticationToken(role, userName + ":" + password, grantedAuths);
						return auth;
				} else if(role != null && role.getIsAdmin()) {
						List<GrantedAuthority> grantedAuths = new ArrayList<>();
						grantedAuths.add(new PublisherAuthority());
						grantedAuths.add(new AdminAuthority());
						Authentication auth = new UsernamePasswordAuthenticationToken(role, userName + ":" + password, grantedAuths);
						System.out.println(auth.getAuthorities());
						return auth;
					
				} else {
						throw new AuthenticationCredentialsNotFoundException("Invalid Credentials!");
				}
		}

		private Publisher authorizedUser(String userName, String password)
		{
			Publisher pub = null;
			if("eae".equals(userName) && "eae".equals(password)) {
				pub = new Publisher();
				pub.setIsAdmin(true);
			}
			
			List<Publisher> pubishers = this.publisherService.findPublisherByEmail(userName);
			if(pubishers.size() > 0) {
				pub = pubishers.get(0);
				
			}
			
			return pub;
		}

		@Override
		public boolean supports(Class<?> authentication)
		{
				return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
		}
}
