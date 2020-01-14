package api.exception;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

/**
 * Classe para tratar excecoes para data em formato invalido
 */
@Provider
public class InvalidFormatExceptionMapper implements ExceptionMapper<InvalidFormatException> {

	@Override
	public Response toResponse(InvalidFormatException e) {
		return buildResponse(buildMessage(e));
	}

	private Mensagem buildMessage(InvalidFormatException e) {
		List<Reference> references = e.getPath();
		String field = "";
		if (CollectionUtils.isNotEmpty(references)) {
			field = "Verifique se o campo '" + references.get(0).getFieldName() + "' está correto.";
		}
		return new Mensagem(field, Status.BAD_REQUEST);
	}

	private static Response buildResponse(Mensagem mensagem) {
		return Response.status(Status.fromStatusCode(mensagem.getStatus())).type(MediaType.APPLICATION_JSON).entity(mensagem).build();
	}

}
