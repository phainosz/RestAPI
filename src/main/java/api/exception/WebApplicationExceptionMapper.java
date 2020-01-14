package api.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

	@Override
	public Response toResponse(WebApplicationException e) {
		Mensagem msg = new Mensagem(e.getMessage(), Status.fromStatusCode(e.getResponse().getStatus()));
		return Response.status(Status.fromStatusCode(e.getResponse().getStatus())).entity(msg)
				.type(MediaType.APPLICATION_JSON).build();
	}

}
