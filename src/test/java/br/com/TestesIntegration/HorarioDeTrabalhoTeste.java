package br.com.TestesIntegration;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import br.com.DAO.CalculoAtrasoDAO;
import br.com.DAO.HoraDeTrabalhoDAO;
import br.com.DAO.MarcacoesFeitasDAO;
import br.com.DTOs.ResultadoCalculoAtraso;
import br.com.Entity.CalculoAtraso;
import br.com.Entity.HorarioDeTrabalho;
import br.com.Entity.MarcacoesFeitas;
import br.com.Utils.CodigoErro;

public class HorarioDeTrabalhoTeste {

	public static void main(String[] args) {

		HorarioDeTrabalho ht = new HorarioDeTrabalho();
		HoraDeTrabalhoDAO cdao = new HoraDeTrabalhoDAO();
		MarcacoesFeitas mf = new MarcacoesFeitas();
		MarcacoesFeitasDAO mfdao = new MarcacoesFeitasDAO();
		
		ht.setCpf("13087097845");
		ht.setEntrada("13:00");
		ht.setSaida("17:00");
		
//		mf.setCpf("01439869103");
//		ht.setEntrada("08:00");
//		mf.setEntrada("08:00");
//		mf.setSaida("16:00");		
//		ht.setSaida("16:00");
//		
		//mfdao.salvar(mf);
		
		//cdao.salvar(ht);
		
	//calcularEInserirAtraso("69779848134");

	
	
	//---------------------------------------Metodo para Listar todos os registros-----------------------------------------------------
	
	try {
		List<HorarioDeTrabalho> horarios = cdao.listarTodos();

		for (HorarioDeTrabalho hts : horarios) {
			System.out.println("CPF: " + ht.getCpf() + "Entrada: " + ht.getEntrada() + ", Saï¿½da: " + hts.getSaida() + 
					", IntervaloInicio: " + hts.getIntervaloInicio() + ", IntervaloFim: " + hts.getIntervaloFim());
		}

	} catch (Exception e) {
		System.out.println(e);
	}
		
	}
	
	/*
	public static ResultadoCalculoAtraso calculoDeHorasAtraso(String cpf) {
	    MarcacoesFeitasDAO mfdao = new MarcacoesFeitasDAO();
	    MarcacoesFeitas mf = mfdao.buscarPorCpf(cpf);
	    HoraDeTrabalhoDAO hdtdao = new HoraDeTrabalhoDAO();
	    HorarioDeTrabalho hdt = hdtdao.buscarPorCpf(cpf);

	    if (mf != null && hdt != null) {
	        LocalTime timeMf = LocalTime.parse(mf.getEntrada());
	        LocalTime timeHdt = LocalTime.parse(hdt.getEntrada());

	        long minutesBetween = ChronoUnit.MINUTES.between(timeMf, timeHdt);
	        long hours = minutesBetween / 60; // get the number of hours
	        long minutes = minutesBetween % 60; // get the remaining minutes

	        if (minutesBetween == 0) {
	            return new ResultadoCalculoAtraso("O horário está igual", null);
	        } else {
	            LocalTime periodoInicio = timeHdt.minusMinutes(minutesBetween);
	            String periodoAtraso = periodoInicio.toString() + " às " + timeHdt.toString();
	            return new ResultadoCalculoAtraso(hours + " horas e " + minutes + " minutos", periodoAtraso);
	        }
	    }

	    return new ResultadoCalculoAtraso(CodigoErro.INFORMACAO_INCOMPLETA, "Informação de horário incompleta");
	}
	
	 public static void calcularEInserirAtraso(String cpf) {
	        ResultadoCalculoAtraso resultado = calculoDeHorasAtraso(cpf);
	        
	        String atraso = resultado.getDiferenca();
	        // Remova a parte inicial da string para ficar apenas com "X horas e Y minutos"
	        atraso = atraso.replace("A diferenca e: ", "");
	        
	        // Agora podemos dividir a string em horas e minutos
	        String[] partes = atraso.split(" e ");
	        String horas = partes[0].replace(" horas", "");
	        String minutos = partes[1].replace(" minutos", "");
	        
	        // Obtenha o período de atraso do objeto ResultadoCalculoAtraso
	        String periodoAtraso = resultado.getPeriodoAtraso();
	        
	        // Agora temos as horas e os minutos como strings. Podemos criar uma string no formato TIME do SQL
	        String timeSql = horas + ":" + minutos + ":00";
	        
	        // Agora podemos criar um novo objeto CalculoAtraso e inseri-lo no banco de dados
	        CalculoAtraso calculoAtraso = new CalculoAtraso();
	        calculoAtraso.setCpf(cpf);
	        calculoAtraso.setEntrada(timeSql);
	        calculoAtraso.setSaida(timeSql);
	        
	        // Defina o campo 'periodoAtraso' do objeto CalculoAtraso com o valor adequado
	        calculoAtraso.setPeriodoAtraso(periodoAtraso);
	        
	        // Inserimos o atraso no banco de dados
	        CalculoAtrasoDAO atrasoDAO = new CalculoAtrasoDAO();
	        atrasoDAO.salvar(calculoAtraso);
	    }	   
*/
		
}
