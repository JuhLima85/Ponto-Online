package br.com.DAO;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.Entity.MarcacoesFeitas;

public class MarcacoesFeitasDAO {

	private EntityManagerFactory entityManagerFactory;

	public MarcacoesFeitasDAO() {
		entityManagerFactory = Persistence.createEntityManagerFactory("RegistroPontoUnit");
	}

	public void salvar(MarcacoesFeitas registroPonto) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(registroPonto);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
	}

	public MarcacoesFeitas buscarUltimaEntradaPorCpf(String cpf) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			TypedQuery<MarcacoesFeitas> query = entityManager.createQuery(
					"SELECT m FROM MarcacoesFeitas m WHERE m.cpf = :cpf ORDER BY m.data DESC", MarcacoesFeitas.class);
			query.setParameter("cpf", cpf);
			query.setMaxResults(1);
			List<MarcacoesFeitas> resultados = query.getResultList();
			if (resultados.isEmpty()) {
				return null;
			}
			return resultados.get(0);
		} finally {
			entityManager.close();
		}
	}

	public List<MarcacoesFeitas> listarPorCpfEPeriodo(String cpf, LocalDate de, LocalDate ate) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			String jpql = "SELECT m FROM MarcacoesFeitas m WHERE m.cpf = :cpf AND m.data BETWEEN :de AND :ate";
			Query query = entityManager.createQuery(jpql);
			query.setParameter("cpf", cpf);
			query.setParameter("de", de);
			query.setParameter("ate", ate);
			return query.getResultList();
		} finally {
			entityManager.close();
		}
	}

	public List<MarcacoesFeitas> listarPorCpfDiaAtual(String cpf, LocalDate data) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			String jpql = "SELECT m FROM MarcacoesFeitas m WHERE m.cpf = :cpf AND m.data = :data";
			Query query = entityManager.createQuery(jpql);
			query.setParameter("cpf", cpf);
			query.setParameter("data", data);
			return query.getResultList();
		} finally {
			entityManager.close();
		}
	}

	public void excluirPorId(Long id) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			MarcacoesFeitas entity = entityManager.find(MarcacoesFeitas.class, id);
			if (entity != null) {
				entityManager.remove(entity);
			}
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
	}

	public void atualizar(MarcacoesFeitas registroPonto) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(registroPonto); 
												
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
	}

	public MarcacoesFeitas selecionarHorario(Long id) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			return entityManager.find(MarcacoesFeitas.class, id);
		} finally {
			entityManager.close();
		}
	}
}
