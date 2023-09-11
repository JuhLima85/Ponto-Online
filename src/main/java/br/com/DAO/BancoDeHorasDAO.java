package br.com.DAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.Controller.BancoDeHorasServlet;
import br.com.Entity.BancoDeHoras;
import br.com.Entity.MarcacoesFeitas;

public class BancoDeHorasDAO {

	private EntityManagerFactory entityManagerFactory;

	public BancoDeHorasDAO() {
		entityManagerFactory = Persistence.createEntityManagerFactory("RegistroPontoUnit");
	}

	public void salvar(BancoDeHoras bancoDeHoras) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(bancoDeHoras);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
	}

	public void atualizar(BancoDeHoras bancoDeHoras) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			entityManager.merge(bancoDeHoras);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
	}

	public BancoDeHoras buscarPorCpf(String cpf) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			Query query = entityManager.createQuery("SELECT b FROM BancoDeHoras b WHERE b.cpf = :cpf");
			query.setParameter("cpf", cpf);
			BancoDeHoras resultado = (BancoDeHoras) query.getSingleResult();

			if (resultado != null) {
				System.out.println("Resultado encontrado: " + resultado.toString());
			} else {
				System.out.println("Nenhum resultado encontrado para o CPF: " + cpf);
			}

			return resultado;
		} catch (NoResultException e) {
			System.out.println("Nenhum resultado encontrado para o CPF: " + cpf);
			return null;
		} finally {
			entityManager.close();
		}
	}

	public List<BancoDeHoras> buscarPorCpfEPeriodo(String cpf, LocalDate de, LocalDate ate) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			Query query = entityManager
					.createQuery("SELECT b FROM BancoDeHoras b WHERE b.cpf = :cpf AND b.data BETWEEN :de AND :ate");
			query.setParameter("cpf", cpf);
			query.setParameter("de", de);
			query.setParameter("ate", ate);
			return query.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<>();
		} finally {
			entityManager.close();
		}
	}

	public void excluirPorId(Long id) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			BancoDeHoras entity = entityManager.find(BancoDeHoras.class, id);
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

	public void salvarOuAtualizarDiscrepanciaDiaria(MarcacoesFeitas mf) throws Exception {
		BancoDeHorasServlet bhs = new BancoDeHorasServlet();
		BancoDeHoras bh = bhs.calcularDiscrepanciaDiaria(mf);
		BancoDeHoras discrepanciaDiaria = buscarPorCpf(mf.getCpf());
		if (discrepanciaDiaria == null || discrepanciaDiaria.getCpf().isEmpty()) {
			salvar(bh);
		} else {
			discrepanciaDiaria.setDiscrepanciaDiaria(bh.getDiscrepanciaDiaria());
			atualizar(discrepanciaDiaria);
		}

	}
}
