package dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * DAO generico para persistencia de dados
 * 
 * @author Paulo Hainosz
 *
 * @param <T>
 */
public abstract class GenericDAO<T> {

	@PersistenceContext
	private EntityManager em;

	protected Class<T> persistentClass = null;

	protected EntityManager getEntityManager() {
		return em;
	}

	/**
	 * Construtor que obtem classe do parametro estatico que generaliza a classe
	 * 
	 */
	@SuppressWarnings("unchecked")
	protected GenericDAO() {

		if (getClass().getSuperclass().getGenericSuperclass() instanceof ParameterizedType) {
			this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass())
					.getActualTypeArguments()[0];
		} else {
			this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];
		}

	}

	/**
	 * Metodo que busca uma entidade pelo ID
	 * 
	 * @param id
	 * @return T
	 */
	public T find(Long id) {
		return this.em.find(persistentClass, id);
	}

	/**
	 * Metodo que lista uma entidade persistida no banco
	 * 
	 * @return List<T>
	 */
	public abstract List<T> listar();

	/**
	 * Metodo que persiste uma entidade no banco
	 * 
	 * @param entity
	 * @return T
	 */
	public T inserir(T entity) {
		this.em.persist(entity);
		return entity;
	}

	/**
	 * Metodo para deletar um objeto do banco
	 * 
	 * @param entity
	 */
	public void remover(T entity) {
		this.em.remove(this.em.merge(entity));
	}

	/**
	 * Metodo para deletar um objeto pelo ID do banco
	 * 
	 * @param entity
	 */
	public void remover(Long id) {
		T entity = this.find(id);
		this.em.remove(this.em.merge(entity));
	}

	/**
	 * Metodo que atualiza um objeto do banco
	 * 
	 * @param entity
	 * @return
	 */
	public T alterar(T entity) {
		return this.em.merge(entity);
	}

	/**
	 * Metodo que atualiza um objeto do banco pelo ID
	 * 
	 * @param entity
	 * @return
	 */
	public T alterar(Long id) {
		T entity = this.find(id);
		return this.em.merge(entity);
	}
}
