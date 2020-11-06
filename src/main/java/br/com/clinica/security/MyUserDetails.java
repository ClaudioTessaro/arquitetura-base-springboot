package br.com.clinica.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.clinica.model.User;
import br.com.clinica.repositories.UserRepository;

@Service
public class MyUserDetails implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = new User();
		if (Character.isDigit(username.charAt(0))) {
			user = userRepository.findById(Long.valueOf(username)).get();
		}else {
			user = userRepository.findByUsername(username);
		}

		if (user == null) {
			throw new UsernameNotFoundException("Usuario '" + user.getUsername() + "' não encontrado!");
		}

		return org.springframework.security.core.userdetails.User//
				.withUsername(user.getUsername())//
				.password(user.getPassword())//
				.authorities(user.getProfile().getRoles())//
				.accountExpired(false)//
				.accountLocked(false)//
				.credentialsExpired(false)//
				
				.disabled(false)//
				.build();
	}

}
