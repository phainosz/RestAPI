package api.exception;

public class Validacao {

	private String atributo;
	private String mensagem;

	public Validacao() {
	}

	public Validacao(String atributo, String mensagem) {
		this.atributo = atributo;
		this.mensagem = mensagem;
		
	}

	public String getAtributo() {
		return atributo;
	}

	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
