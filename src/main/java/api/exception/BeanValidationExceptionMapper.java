package api.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Classe que trata excecoes do tipo bean validation
 * 
 * @author Paulo Hainosz
 *
 */
@Provider
public class BeanValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException e) {
		Status status = Status.NOT_ACCEPTABLE;
		Mensagem msg = new Mensagem(getValidacoes(e), status);
		return Response.status(status).entity(msg).type(MediaType.APPLICATION_JSON).build();
	}

	private List<Validacao> getValidacoes(ConstraintViolationException constraintViolation) {
		List<Validacao> validacoes = new ArrayList<>();
		String atributo = "";
		String mensagem = "";
		for (ConstraintViolation<?> cv : constraintViolation.getConstraintViolations()) {
			for (Node node : cv.getPropertyPath()) {
				atributo = node.getName();
				mensagem = cv.getMessage();
			}
			validacoes.add(new Validacao(atributo, mensagem));
		}
		return validacoes;
	}

}
