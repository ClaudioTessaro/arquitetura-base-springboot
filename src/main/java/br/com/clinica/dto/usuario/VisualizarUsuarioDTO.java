package br.com.clinica.dto.usuario;

import java.io.Serializable;

public class VisualizarUsuarioDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String usuario;
	
	private String email;
	
	private String token;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	

}
