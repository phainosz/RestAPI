package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Pessoa;

@Stateless
public class PessoaDAO extends GenericDAO<Pessoa> {

	@PersistenceContext
	private EntityManager em;

	public List<Pessoa> buscarPeloCPF(String cpf) {
		return this.em.createQuery("select p from Pessoa p where p.cpf=:p1", Pessoa.class).setParameter("p1", cpf)
				.getResultList();
	}

	@Override
	public List<Pessoa> listar() {
		return this.em.createQuery("select p from Pessoa p", Pessoa.class).getResultList();
	}

}
