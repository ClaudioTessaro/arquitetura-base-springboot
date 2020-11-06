package br.com.clinica.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.clinica.dto.ResponseDTO;
import br.com.clinica.dto.usuario.CadastrarUsuarioDTO;
import br.com.clinica.dto.usuario.VisualizarUsuarioDTO;
import br.com.clinica.service.UsuarioService;

@RestController
@RequestMapping("/users")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/signup")
	public ResponseEntity<ResponseDTO<VisualizarUsuarioDTO>> cadastrarUsuario(@RequestBody CadastrarUsuarioDTO usuario){
		return new ResponseEntity<ResponseDTO<VisualizarUsuarioDTO>>(usuarioService.cadastrarUsuario(usuario), HttpStatus.CREATED);
	}
	
	@GetMapping("/teste")
	@RolesAllowed("ROLE_ADMIN")
	public String tetes() {
		return "teste";
	}
	
}
