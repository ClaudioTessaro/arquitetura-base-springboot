package br.com.clinica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.clinica.constantes.MensagensUtil;
import br.com.clinica.controller.exceptions.AutheException;
import br.com.clinica.dto.usuario.UsuarioDTO;
import br.com.clinica.dto.usuario.VisualizarUsuarioDTO;
import br.com.clinica.model.User;
import br.com.clinica.repositories.UserRepository;
import br.com.clinica.security.JwtTokenProvider;
import br.com.clinica.service.mapper.UsuarioMapper;

@Service
public class SessionService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	public VisualizarUsuarioDTO signin(UsuarioDTO user) {
		try {
			User usuario = userRepository.findByUsername(user.getUsuario());
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsuario(), user.getSenha()));
			String token = jwtTokenProvider.createToken(usuario.getId(), usuario.getProfile().getRoles());
			VisualizarUsuarioDTO usuarioDTO = UsuarioMapper.mapper(usuario, token);
			return usuarioDTO;
		} catch (AuthenticationException e) {
			throw new AutheException(MensagensUtil.CREDENCIAIS_INVALIDAS, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
