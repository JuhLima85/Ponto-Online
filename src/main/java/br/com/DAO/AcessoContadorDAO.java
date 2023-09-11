package br.com.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.Entity.AcessoContador;

public class AcessoContadorDAO {

	private EntityManagerFactory entityManagerFactory;

	public AcessoContadorDAO() {
		entityManagerFactory = Persistence.createEntityManagerFactory("RegistroPontoUnit");
	}

	public AcessoContador buscarPorId(Long id) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			return entityManager.find(AcessoContador.class, id);
		} finally {
			entityManager.close();
		}
	}

	public void atualizar(AcessoContador acessoContador) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(acessoContador);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
	}
}
