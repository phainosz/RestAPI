package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotAcceptableException;

import dao.GenericDAO;
import dao.PessoaDAO;
import entities.Pessoa;
import enums.AcaoDAO;

@Stateless
public class PessoaService extends GenericService<Pessoa> {

	@Inject	
	private PessoaDAO pessoaDAO;

	@Override
	public GenericDAO<Pessoa> getEntidadeDAO() {
		return this.pessoaDAO;
	}

	@Override
	public void regrasDeNegocioEntidade(Pessoa entity, AcaoDAO acao) {
		if (AcaoDAO.INSERT.equals(acao)) {
			verificarCPF(entity);
		}
	}

	private void verificarCPF(Pessoa entity) {
		List<Pessoa> buscarPeloCPF = pessoaDAO.buscarPeloCPF(entity.getCpf());
		if (buscarPeloCPF.size() > 0) {
			throw new NotAcceptableException("CPF já cadastrado no banco");
		}
	}

}
