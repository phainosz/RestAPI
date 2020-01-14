package api.exception;

import java.util.List;

import javax.ws.rs.core.Response.Status;;

/**
 * Classe que entrega a mensagem de erro como json. Quando um excecao for
 * lancada, a msg sera enviada a partir desta classe.
 * 
 * @author Paulo Henrique
 *
 */
public class Mensagem {

	private int status;
	private String descricao;
	private String mensagem;
	private List<Validacao> validacoes;

	public Mensagem() {
	}
	
	public Mensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Mensagem(String mensagem, Status status) {
		this.status = status.getStatusCode();
		this.descricao = status.getReasonPhrase();
		this.mensagem = mensagem;
	}

	public Mensagem(List<Validacao> validacoes, Status status) {
		this.validacoes = validacoes;
		this.status = status.getStatusCode();
		this.descricao = status.getReasonPhrase();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Validacao> getValidacoes() {
		return validacoes;
	}

	public void setValidacoes(List<Validacao> validacoes) {
		this.validacoes = validacoes;
	}

}
