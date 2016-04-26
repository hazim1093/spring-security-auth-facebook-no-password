package com.example.hazim1093.config;

import com.example.hazim1093.domain.FacebookAccessGrant;
import com.example.hazim1093.domain.User;
import com.example.hazim1093.repository.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.InvalidAuthorizationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hazim on 3/8/16.
 */
@Component
public class FacebookAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserRepositoryService userRepositoryService;
	@Autowired
	private FacebookConnectionFactory facebookConnectionFactory;

		@Override
		protected void doFilterInternal(HttpServletRequest request,
										HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {

			String xAuth = request.getHeader("fbToken");

			try {
				User user = validateToken(xAuth);

				// Create our Authentication and let Spring know about it
				Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, AuthorityUtils.createAuthorityList("ROLE_USER"));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			catch (InvalidAuthorizationException ex){
				logger.error(ex.getMessage());
			}

			filterChain.doFilter(request, response);
		}

	private User validateToken(String fbToken){
		AccessGrant accessGrant = new AccessGrant(fbToken);
		Connection<Facebook> connection = facebookConnectionFactory.createConnection(accessGrant);
		org.springframework.social.facebook.api.User fbUser = connection.getApi().userOperations().getUserProfile();

		String facebookId = fbUser.getId();
		User user = userRepositoryService.findByFacebookId(facebookId);
		if(user == null) {
			String firstName = fbUser.getFirstName();
			String lastName = fbUser.getLastName();
			String email = fbUser.getEmail() == null ? "" : fbUser.getEmail();
			//Save User
			user = new User(facebookId, firstName + " " + lastName, email, new FacebookAccessGrant(accessGrant));
			userRepositoryService.save(user);
		}
		else {
			//update fbToken if not the same
			if(user.getFbAccessGrant() != null
					&& !user.getFbAccessGrant().getAccessToken().equals(accessGrant.getAccessToken())){
				user.getFbAccessGrant().setAccessToken(accessGrant.getAccessToken());
				userRepositoryService.save(user);
			}
		}

		return user;
	}

}
