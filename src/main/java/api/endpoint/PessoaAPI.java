package api.endpoint;

import java.net.URI;
import java.util.List;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import api.exception.Mensagem;
import entities.Pessoa;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import service.PessoaService;

/**
 * API rest para classe {@link Pessoa}
 * 
 * @author E804684
 *
 */
@Path("pessoas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(tags = "Pessoas")
public class PessoaAPI {

	@Inject
	private PessoaService pessoaService;

	@Context
	private UriInfo uriInfo;

	@Inject
	private Event<String> evt;

	@Inject
	private Event<Pessoa> evtP;

	@POST
	@ApiOperation("Insere uma pessoa na base de dados")
	public Response inserir(Pessoa p) {
		Pessoa inseririda = pessoaService.inserir(p);
		URI uri = criarUri(inseririda);

		evt.fire("Uma pessoa foi inserida");
		evtP.fire(p);
		// 200
		return Response.created(uri).entity(inseririda).build();
	}

	@GET
	@Consumes(MediaType.WILDCARD)
	@ApiOperation("Lista todas as Pessoas gravadas no banco")
	public Response listar(@QueryParam("id") Long id, @QueryParam("cpf") String cpf) {
		if (id != null) {
			Pessoa pessoa = pessoaService.find(id);
			return Response.ok(pessoa).build();
		} else {
			List<Pessoa> pessoas = pessoaService.listar();

			evt.fire("Feito uma requisição GET para listar as pessoas");
			// 201
			return Response.ok(pessoas).build();
		}
	}

	@DELETE
	@Path("{id}")
	@ApiOperation("Remove uma pessoa do banco")
	public Response remover(@PathParam("id") Long id) {
		Pessoa pessoa = pessoaService.find(id);
		if (pessoa != null) {
			pessoaService.remover(pessoa);
			evt.fire("Uma pessoa foi removida");
			// 202
			return Response.accepted(new Mensagem("Pessoa removida do banco", Status.ACCEPTED)).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@PUT
	@Path("{id}")
	@ApiOperation("Edita uma pessoa")
	public Response editar(@PathParam(value = "id") Long id, Pessoa p) {
		Pessoa pessoaBuscada = pessoaService.find(id);
		if (pessoaBuscada != null) {
			editarPessoa(p, pessoaBuscada);
			Pessoa editada = pessoaService.alterar(pessoaBuscada);
			URI uri = criarUri(editada);

			evt.fire("Uma pessoa foi editada por seu ID");
			return Response.accepted(uri).entity(editada).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	/**
	 * @param p
	 * @param pessoaBuscada
	 */
	private void editarPessoa(Pessoa p, Pessoa pessoaBuscada) {
		pessoaBuscada.setCpf(p.getCpf());
		pessoaBuscada.setNome(p.getNome());
		pessoaBuscada.setIdade(p.getIdade());
	}

	@GET
	@Path(value = "{id}")
	@ApiOperation("Busca uma pessoa pelo ID")
	public Response buscar(@PathParam("id") Long id) {
		Pessoa buscada = pessoaService.find(id);
		if (buscada != null) {
			URI uri = criarUri(buscada);
			evt.fire("Uma pessoa foi buscada por ID");
			return Response.ok(uri).entity(buscada).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@POST
	@Path("form")
	@ApiOperation("Insere uma pessoa usando formulário")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response formulario(@FormParam("nome") String nome, @FormParam("cpf") String cpf, @FormParam("idade") Integer idade) {
		Pessoa pessoa = new Pessoa();
		pessoa.setCpf(cpf);
		pessoa.setNome(nome);
		pessoa.setIdade(idade);

		pessoaService.inserir(pessoa);

		URI uri = criarUri(pessoa);
		return Response.ok(uri).build();
	}

	/**
	 * Utilitario para criar a a URI
	 * 
	 * @param inseririda
	 * @return
	 */
	private URI criarUri(Pessoa p) {
		// getBaseUriBuilder retorna a base da url ate a /api
		UriBuilder baseUri = uriInfo.getBaseUriBuilder();
		String absoluta = uriInfo.getAbsolutePath().toString();
		System.out.println(absoluta);
		// aqui e adicionado o path da classe PessoasAPI e o id que esta sendo inserido
		URI uri = baseUri.path(PessoaAPI.class).path(p.toPath()).build();
		return uri;
	}

	public void observer(@Observes String arg) {
		System.out.println(arg);
	}

	public void observer(@Observes Pessoa arg) {
		System.out.println(arg);
	}
}
