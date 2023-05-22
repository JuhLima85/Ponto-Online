package br.com.TestesIntegration;

import java.util.List;

import br.com.DAO.CalculoAtrasoDAO;
import br.com.Entity.CalculoAtraso;
import br.com.Entity.MarcacoesFeitas;


public class CalculoAtrasoTeste {

	public static void main(String[] args) {
		CalculoAtraso cal = new CalculoAtraso();
		CalculoAtrasoDAO cdao = new CalculoAtrasoDAO();
		MarcacoesFeitas mf = new MarcacoesFeitas();
		mf.setCpf("01439869103");
		mf.setEntrada("09:00");
		mf.setSaida("12:00");
		
//		cal.setCpf("6977984000");
//		cal.setEntrada("08:00");
//		cal.setIntervaloInicio("12:15");
//		cal.setIntervaloFim("13:20");
//		cal.setSaida("16:00");
		
		//cdao.salvar(cal);
		
		/*Testando calcularEInserirAtraso*/
		String cpf = "01439869103";
		cdao.calcularEInserirAtraso(mf);
		
		
		//---------------------------------------Metodo para Listar todos os registros-----------------------------------------------------
	//	
		try {
			List<CalculoAtraso> horarios = cdao.listarTodos();

			for (CalculoAtraso cals : horarios) {
				System.out.println("CPF: " + cal.getCpf() + "Entrada: " + cal.getEntrada() + ", Saï¿½da: " + cals.getSaida() + 
						", IntervaloInicio: " + cals.getIntervaloInicio() + ", IntervaloFim: " + cals.getIntervaloFim()+ ", Período de Atraso: " + cals.getPeriodoAtraso());
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
