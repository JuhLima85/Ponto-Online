package br.com.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.Entity.CalculoHoraExtra;

public class CalculoHoraExtraDAO {
	
	private EntityManagerFactory entityManagerFactory;
  
    public CalculoHoraExtraDAO() {
    	entityManagerFactory = Persistence.createEntityManagerFactory("RegistroPontoUnit");
         }

    public void salvar(CalculoHoraExtra registroPonto) {
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

    public void atualizar(CalculoHoraExtra registroPonto) {
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

    public void excluir(CalculoHoraExtra registroPonto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            CalculoHoraExtra entity = entityManager.find(CalculoHoraExtra.class, registroPonto.getId());
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

    public CalculoHoraExtra buscarPorCpf(String cpf) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT h FROM CalculoHoraExtra h WHERE h.cpf = :cpf");
            query.setParameter("cpf", cpf);
            return (CalculoHoraExtra) query.getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    public List<CalculoHoraExtra> listarTodos() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String jpql = "SELECT r FROM CalculoHoraExtra r";
            Query query = entityManager.createQuery(jpql);
            return query.getResultList();
        } finally {
            entityManager.close();
        }        

    }
    
    public void excluirPorCPF(String cpf) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            
            // Consulta para encontrar os registros de ponto pelo CPF
            Query query = entityManager.createQuery("SELECT h FROM HorarioDeTrabalho h WHERE h.cpf = :cpf");
            query.setParameter("cpf", cpf);
            List<CalculoHoraExtra> registros = query.getResultList();
            
            // Remover os registros encontrados
            for (CalculoHoraExtra registro : registros) {
                entityManager.remove(registro);
            }
            
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }
}