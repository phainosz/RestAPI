package service;

import java.util.List;

import javax.ws.rs.NotFoundException;

import dao.GenericDAO;
import enums.AcaoDAO;

/**
 * Classe de Servico generico
 * 
 * @author Paulo Hainosz
 *
 * @param <T>
 */
public abstract class GenericService<T> {

	/**
	 * Retornar o EJB com o DAO da entidade
	 * 
	 * @return
	 */
	public abstract GenericDAO<T> getEntidadeDAO();

	public abstract void regrasDeNegocioEntidade(T entity, AcaoDAO acao);

	public T find(Long id) {
		T find = this.getEntidadeDAO().find(id);
		if (find != null) {
			return this.getEntidadeDAO().find(id);
		} else {
			throw new NotFoundException("Não foi escontrado o id " + id);
		}
	}

	public List<T> listar() {
		return this.getEntidadeDAO().listar();
	}

	public T inserir(T entity) {
		this.regrasDeNegocioEntidade(entity, AcaoDAO.INSERT);
		return this.getEntidadeDAO().inserir(entity);
	}

	public T alterar(T entity) {
		this.regrasDeNegocioEntidade(entity, AcaoDAO.UPDATE);
		return this.getEntidadeDAO().alterar(entity);
	}

	public void remover(T entity) {
		this.regrasDeNegocioEntidade(entity, AcaoDAO.DELETE);
		this.getEntidadeDAO().remover(entity);
	}

}
