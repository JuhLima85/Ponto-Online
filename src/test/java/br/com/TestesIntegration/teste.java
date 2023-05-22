package br.com.TestesIntegration;

import java.util.List;

import br.com.DAO.CalculoAtrasoDAO;
import br.com.DAO.CalculoHoraExtraDAO;
import br.com.Entity.CalculoHoraExtra;

public class teste {
	    
	public static void main(String[] args) {
		CalculoHoraExtra chx = new CalculoHoraExtra();
		CalculoHoraExtraDAO chdao = new CalculoHoraExtraDAO();
		CalculoAtrasoDAO cdao = new CalculoAtrasoDAO();
		chx.setCpf("6977984000");
		chx.setEntrada("08:00");		
		chx.setSaida("16:00");
		
		//chdao.salvar(chx);

		String h = "78945612321";
		//System.out.println(cdao.buscarUltimoRegistro());
		//cdao.calcularEInserirAtraso(h);
		
	
	//---------------------------------------Metodo para Listar todos os registros-----------------------------------------------------
		//	
			try {
				List<CalculoHoraExtra> horarios = chdao.listarTodos();

				for (CalculoHoraExtra cals : horarios) {
					System.out.println("CPF: " + cals.getCpf() + "Entrada: "
				+ cals.getEntrada() + ", Saï¿½da: " + cals.getSaida());
				}

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	


	

	
	


}
