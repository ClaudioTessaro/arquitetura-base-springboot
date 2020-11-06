package br.com.clinica.service;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.clinica.constantes.MensagensUtil;
import br.com.clinica.controller.exceptions.AutheException;
import br.com.clinica.dto.ResponseDTO;
import br.com.clinica.dto.usuario.CadastrarUsuarioDTO;
import br.com.clinica.dto.usuario.UsuarioDTO;
import br.com.clinica.dto.usuario.VisualizarUsuarioDTO;
import br.com.clinica.model.Role;
import br.com.clinica.model.User;
import br.com.clinica.repositories.ProfileRepository;
import br.com.clinica.repositories.UserRepository;
import br.com.clinica.security.JwtTokenProvider;
import br.com.clinica.service.mapper.UsuarioMapper;
import br.com.clinica.util.ResponseUtil;

@Service
public class UsuarioService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	public ResponseDTO<VisualizarUsuarioDTO> cadastrarUsuario(CadastrarUsuarioDTO user) {
		verificarConfirmacaoDeSenha(user.getSenha(), user.getConfirmarSenha());
		verificarUsuarioExistente(user.getUsuario());
		User usuario = UsuarioMapper.mapperCadastro(user, passwordEncoder.encode(user.getSenha()));
		associarRoles(user.getProfile(), usuario);
		userRepository.save(usuario);
		String token = jwtTokenProvider.createToken(usuario.getId(), usuario.getProfile().getRoles());
		VisualizarUsuarioDTO visualizar = UsuarioMapper.mapper(usuario, token);
		return ResponseUtil.montarRetorno(MensagensUtil.USUARIO_CRIADO, visualizar);
	}

	private void associarRoles(String profile, User usuario) {
		usuario.setProfile(profileRepository.findByNome(profile));
		if(profile.equalsIgnoreCase("profissional")){
			usuario.getProfile().setRoles(Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_PACIENTE, Role.ROLE_SECRETARIA));
		}else if(profile.equalsIgnoreCase("secretaria")){
			usuario.getProfile().setRoles(Arrays.asList(Role.ROLE_SECRETARIA));
		}else {
			usuario.getProfile().setRoles(Arrays.asList(Role.ROLE_PACIENTE));
		}
		
	}

	private void verificarUsuarioExistente(String usuario) {
		if (userRepository.existsByUsername(usuario)) {
			throw new AutheException(MensagensUtil.USUARIO_EXISTENTE);
		}

	}

	private void verificarConfirmacaoDeSenha(String senha, String confirmarSenhar) {
		if (!senha.equals(confirmarSenhar)) {
			throw new AutheException(MensagensUtil.ERRO_SENHA);
		}

	}

	public void delete(String username) {
		userRepository.deleteByUsername(username);
	}

	public User search(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new AutheException("The user doesn't exist", HttpStatus.NOT_FOUND);
		}
		return user;
	}

	public User whoami(HttpServletRequest req) {
		return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
	}

	public String refresh(UsuarioDTO user) {
		User usuario = userRepository.findByUsername(user.getUsuario());
		return jwtTokenProvider.createToken(usuario.getId(), usuario.getProfile().getRoles());
	}

}
