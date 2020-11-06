package br.com.clinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.clinica.dto.usuario.UsuarioDTO;
import br.com.clinica.dto.usuario.VisualizarUsuarioDTO;
import br.com.clinica.service.SessionService;

@RestController
@RequestMapping("/session")
public class SessionController {
	
	@Autowired
	private SessionService service;
	
	
	@PostMapping("/signin")
	public ResponseEntity<VisualizarUsuarioDTO> login(@RequestBody UsuarioDTO usuario){
		return new ResponseEntity<VisualizarUsuarioDTO>(service.signin(usuario),HttpStatus.ACCEPTED);
	}

}
