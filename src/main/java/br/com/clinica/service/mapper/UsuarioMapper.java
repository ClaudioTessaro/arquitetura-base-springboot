package br.com.clinica.service.mapper;

import br.com.clinica.dto.usuario.CadastrarUsuarioDTO;
import br.com.clinica.dto.usuario.VisualizarUsuarioDTO;
import br.com.clinica.model.User;

public class UsuarioMapper {

	public static VisualizarUsuarioDTO mapper(User usuario, String token) {
		VisualizarUsuarioDTO visualizar = new VisualizarUsuarioDTO();
		visualizar.setEmail(usuario.getEmail());
		visualizar.setUsuario(usuario.getUsername());
		visualizar.setToken(token);
		return visualizar;
	}

	public static User mapperCadastro(CadastrarUsuarioDTO user, String encode) {
		User usuario = new User();
		usuario.setEmail(user.getEmail());
		usuario.setPassword(encode);
		usuario.setUsername(user.getUsuario());
		return usuario;
	}


}
