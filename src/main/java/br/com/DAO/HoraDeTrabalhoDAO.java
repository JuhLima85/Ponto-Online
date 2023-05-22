package br.com.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.Entity.HorarioDeTrabalho;

public class HoraDeTrabalhoDAO {

    private EntityManagerFactory entityManagerFactory;

    public HoraDeTrabalhoDAO() {
        entityManagerFactory = Persistence.createEntityManagerFactory("RegistroPontoUnit");
    }

    public void salvar(HorarioDeTrabalho registroPonto) {
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

    public void atualizar(HorarioDeTrabalho registroPonto) {
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

    public void excluir(HorarioDeTrabalho registroPonto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            HorarioDeTrabalho entity = entityManager.find(HorarioDeTrabalho.class, registroPonto.getId());
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

    public HorarioDeTrabalho buscarPorCpf(String cpf) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT h FROM HorarioDeTrabalho h WHERE h.cpf = :cpf");
            query.setParameter("cpf", cpf);
            return (HorarioDeTrabalho) query.getSingleResult();
        } finally {
            entityManager.close();
        }
    }
    
    public HorarioDeTrabalho selecionarHorario(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(HorarioDeTrabalho.class, id);
        } finally {
            entityManager.close();
        }
    }
    
    public List<HorarioDeTrabalho> listarTodos() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String jpql = "SELECT r FROM HorarioDeTrabalho r";
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
            List<HorarioDeTrabalho> registros = query.getResultList();
            
            // Remover os registros encontrados
            for (HorarioDeTrabalho registro : registros) {
                entityManager.remove(registro);
            }
            
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }
    
    public void excluirPorId(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            HorarioDeTrabalho entity = entityManager.find(HorarioDeTrabalho.class, id);
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
    
    public String buscarUltimoRegistro() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String jpql = "SELECT hdt FROM HorarioDeTrabalho hdt ORDER BY hdt.data DESC";
            TypedQuery<HorarioDeTrabalho> query = entityManager.createQuery(jpql, HorarioDeTrabalho.class);
            query.setMaxResults(1);

            List<HorarioDeTrabalho> resultados = query.getResultList();

            if (!resultados.isEmpty()) {
                HorarioDeTrabalho ultimoRegistro = resultados.get(0);
                // Construa a representação em formato de String do último registro
                String representacao = "ID: " + ultimoRegistro.getId() +  ", CPF: " + ultimoRegistro.getCpf();
                // Substitua os getters e formate a representação conforme sua necessidade

                return representacao;
            }

            return null; // Retorna null caso não seja encontrado nenhum registro
        } finally {
            entityManager.close();
        }
    }




}
