package api.exception;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

/**
 * Classe que trata excecoes de atributos de entidades nao existentes
 *
 */
@Provider
public class UnrecognizedPropertyExceptionMapper implements ExceptionMapper<UnrecognizedPropertyException> {

	@Override
	public Response toResponse(UnrecognizedPropertyException e) {
		// alterar e usar esse aqui apenas e.getPropertyName();
		Status status = Status.BAD_REQUEST;
		Mensagem mensagem = new Mensagem(getValidacoes(e), status);
		return Response.status(Status.fromStatusCode(mensagem.getStatus())).type(MediaType.APPLICATION_JSON).entity(mensagem)
				.build();
	}

	private List<Validacao> getValidacoes(UnrecognizedPropertyException e) {
		List<Validacao> validacoes = new ArrayList<>();
		validacoes.add(new Validacao(e.getPropertyName(), "Campo inválido. Opções válidas -" + e.getKnownPropertyIds()));
		return validacoes;
	}

}
