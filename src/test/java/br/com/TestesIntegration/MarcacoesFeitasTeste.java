package br.com.TestesIntegration;

import java.util.List;

import br.com.DAO.MarcacoesFeitasDAO;
import br.com.Entity.MarcacoesFeitas;

public class MarcacoesFeitasTeste {

	public static void main(String[] args) {
		MarcacoesFeitas mf = new MarcacoesFeitas();
		MarcacoesFeitasDAO mfdao = new MarcacoesFeitasDAO();
		try {
			mf.setCpf("6977984000");
			mf.setEntrada("08:00");
			mf.setIntervaloInicio("12:15");
			mf.setIntervaloFim("13:20");
			mf.setSaida("16:00");
			
			//mfdao.salvar(mf);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		//---------------------------------------Metodo para Listar todos os registros-----------------------------------------------------
	//	
//		try {
//			List<MarcacoesFeitas> horarios = mfdao.listarTodos();
//
//			for (MarcacoesFeitas mfs : horarios) {
//				System.out.println("CPF: " + mf.getCpf() + "Entrada: " + mf.getEntrada() + ", Saï¿½da: " + mfs.getSaida() + 
//						", IntervaloInicio: " + mfs.getIntervaloInicio() + ", IntervaloFim: " + mfs.getIntervaloFim());
//			}
//
//		} catch (Exception e) {
//			System.out.println(e);
//		}

		mf.setCpf("6977984000");
		mfdao.buscarPorCpf(mf.getCpf() + "Entrada" + mf.getEntrada());
		
	}

}
