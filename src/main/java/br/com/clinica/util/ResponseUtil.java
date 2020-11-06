package br.com.clinica.util;

import br.com.clinica.dto.ResponseDTO;

public final class ResponseUtil {

	private ResponseUtil() {

	}

	public static <T> ResponseDTO<T> montarRetorno(String mensagem, T payload) {
		ResponseDTO<T> responseDTO = new ResponseDTO<>();
		responseDTO.setPayload(payload);
		responseDTO.setMessage(mensagem);

		return responseDTO;
	}
}
