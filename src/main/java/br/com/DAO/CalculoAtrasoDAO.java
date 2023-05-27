package br.com.DAO;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.DTOs.ResultadoCalculoAtraso;
import br.com.Entity.CalculoAtraso;
import br.com.Entity.HorarioDeTrabalho;
import br.com.Entity.MarcacoesFeitas;

public class CalculoAtrasoDAO {
	
	 private EntityManagerFactory entityManagerFactory;

   public CalculoAtrasoDAO() {
   	entityManagerFactory = Persistence.createEntityManagerFactory("RegistroPontoUnit"); 
   }
	
	public static ResultadoCalculoAtraso calculoDeHorasExtras(MarcacoesFeitas mf) {	   
	    HoraDeTrabalhoDAO hdtdao = new HoraDeTrabalhoDAO();
	    HorarioDeTrabalho hdt = hdtdao.buscarPorCpf(mf.getCpf());
	  
	    String discrepancia = null;
	    
	    if (mf != null && hdt != null) {
	        LocalTime entrMf = LocalTime.parse(mf.getEntrada());
	        LocalTime inicioIntMf = LocalTime.parse(mf.getIntervaloInicio());
	        LocalTime fimIntMf = LocalTime.parse(mf.getIntervaloFim());
	        LocalTime saidMf = LocalTime.parse(mf.getSaida());
	        
	        LocalTime entrHt = LocalTime.parse(hdt.getEntrada());
	        LocalTime inicioIntHt = LocalTime.parse(hdt.getIntervaloInicio());
	        LocalTime fimIntHt = LocalTime.parse(hdt.getIntervaloFim());
	        LocalTime saidHt = LocalTime.parse(hdt.getSaida());
	        
	        // usar o método abaixo igual a formula e dpois somar e ....
	        long diferencaMf1 = ChronoUnit.MINUTES.between(entrMf, inicioIntMf);
	        long diferencaMf2 = ChronoUnit.MINUTES.between(fimIntMf, saidMf);
	        
	        long totalMf = diferencaMf1 + diferencaMf2;	        
	        
	        long diferencaHt1 = ChronoUnit.MINUTES.between(entrHt, inicioIntHt);
	        long diferencaHt2 = ChronoUnit.MINUTES.between(fimIntHt, saidHt);
	       
	        long totalHt = diferencaHt1 + diferencaHt2;	        
	        
	        long diferenca = totalMf - totalHt;
	        
	        //calcular a diferença em horas e minutos entre dois valores inteiros que representam um período de tempo em minutos.
	        long diferencaEmHoras = diferenca / 60; // get the number of hours
	        long diferencaEmMinutos = diferenca % 60; // get the remaining minutes	         
	        
	        String horas = Long.toString(diferencaEmHoras);
	        String minutos = Long.toString(diferencaEmMinutos).replace("-", "");
	        
	        if ("0".equals(horas) && "0".equals(minutos)) {
				discrepancia = "-";
			}else {
				discrepancia = horas + ":" + minutos;	
			}	        
	    }
	       //String saida = mf.getSaida();	    
	    return new ResultadoCalculoAtraso(discrepancia);	   
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
	
	//Salvar marcações feitas na mesma linha
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
	    
	  public static void calcularEInserirAtraso(MarcacoesFeitas mf) {
	        ResultadoCalculoAtraso resultado = calculoDeHorasExtras(mf);  
	        
	        // Obtem o período de atraso, entrada e saída do objeto ResultadoCalculoAtraso
	        String discrepancia = resultado.getDiferenca();
	        
	        // Agora podemos criar um novo objeto CalculoAtraso e inseri-lo no banco de dados
	       mf.setQtdHorasNegativa(discrepancia);
	       System.out.println("discrepancia: " + mf.getQtdHorasNegativa());
	        // Atualizar a entrada, saida, o periodo de atraso e o horas negativas no banco de dados
	        CalculoAtrasoDAO atrasoDAO = new CalculoAtrasoDAO();	        
	        atrasoDAO.atualizar(mf);     
	    }	   
	    
	    public List<CalculoAtraso> listarTodos() {
	        EntityManager entityManager = entityManagerFactory.createEntityManager();
	        try {
	            String jpql = "SELECT m FROM CalculoAtraso m";
	            TypedQuery<CalculoAtraso> query = entityManager.createQuery(jpql, CalculoAtraso.class);
	            return query.getResultList();
	        } finally {
	            entityManager.close();
	        }
	    }

	    public HorarioDeTrabalho buscarPorCpf(String cpf) {
	        EntityManager entityManager = entityManagerFactory.createEntityManager();
	        try {
	            Query query = entityManager.createQuery("SELECT h FROM atraso h WHERE h.cpf = :cpf");
	            query.setParameter("cpf", cpf);
	            return (HorarioDeTrabalho) query.getSingleResult();
	        } finally {
	            entityManager.close();
	        }
	    }

	    public String buscarUltimoRegistro() {
			EntityManager entityManager = entityManagerFactory.createEntityManager();
		    try {
		        String jpql = "SELECT hdt FROM HorarioDeTrabalho hdt ORDER BY hdt.cpf DESC";
		        TypedQuery<HorarioDeTrabalho> query = entityManager.createQuery(jpql, HorarioDeTrabalho.class);
		        query.setMaxResults(1);

		        List<HorarioDeTrabalho> resultados = query.getResultList();

		        if (!resultados.isEmpty()) {
		            HorarioDeTrabalho ultimoRegistro = resultados.get(0);
		            // Construa a representação em formato de String do último registro
		            String representacao = ultimoRegistro.getCpf();
		            // Substitua os getters e formate a representação conforme sua necessidade

		            return representacao;
		        }

		        return null; // Retorna null caso não seja encontrado nenhum registro
		    } finally {
		        entityManager.close();
		    }
		}
}
