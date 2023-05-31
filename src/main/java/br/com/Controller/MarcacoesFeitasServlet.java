package br.com.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.DAO.CalculoAtrasoDAO;
import br.com.DAO.MarcacoesFeitasDAO;
import br.com.Entity.HorarioDeTrabalho;
import br.com.Entity.MarcacoesFeitas;

@WebServlet("/MarcacoesFeitasServlet")
public class MarcacoesFeitasServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private MarcacoesFeitasDAO marcacoesFeitasDAO;   

    public void init() {
        marcacoesFeitasDAO = new MarcacoesFeitasDAO();
               
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {      	
		String action = request.getParameter("action");		
    	try {
			switch (action) {
			case "add":
				adicionarMarcacao(request, response);				
				break;		
			default:
				listarMarcacoes(request, response);
				break;
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Ocorreu um erro ao processar a solicitação: " + e.getMessage());
		}    	
       
    }
    
    private void adicionarMarcacao(HttpServletRequest request, HttpServletResponse response) throws Exception {    	
    	String cpf = (String) request.getSession().getAttribute("cpf");//recebo cpf por sessão para saber em qual cpf marcar as horas
    	request.getSession().setAttribute("cpf", cpf);// insiro o cpf no request para ser usado no método listar     	
    	String entrada = request.getParameter("entrada");
        String intervaloInicio = request.getParameter("intervaloInicio");
        String intervaloFim = request.getParameter("intervaloFim");
        String saida = request.getParameter("saida");     
        
        MarcacoesFeitas horario = new MarcacoesFeitas();
        horario.setCpf(cpf);
        horario.setEntrada(entrada);
        horario.setIntervaloInicio(intervaloInicio);
        horario.setIntervaloFim(intervaloFim);
        horario.setSaida(saida);
        
     // Formatar a data no formato desejado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dataFormatada = horario.getData().format(formatter);

        // Setar a data formatada no objeto MarcacoesFeitas
        horario.setData(LocalDate.parse(dataFormatada, formatter));
        
        	if(entrada == null || entrada.isEmpty()) {
        		//Resgata as informações e salva
        		MarcacoesFeitas resgate = new MarcacoesFeitas();        		
            	resgate = marcacoesFeitasDAO.buscarUltimaEntrada();
            	if(!horario.getIntervaloInicio().isEmpty()) {            		
            		horario.setId(resgate.getId());
            		horario.setEntrada(resgate.getEntrada());                	
                	//Update no id da entrada
               	 CalculoAtrasoDAO atrasoDAO = new CalculoAtrasoDAO();	        
        	        atrasoDAO.atualizar(horario);     	 
            	}else if(!horario.getIntervaloFim().isEmpty()) {
            		horario.setId(resgate.getId());
            		horario.setEntrada(resgate.getEntrada());
                	horario.setIntervaloInicio(resgate.getIntervaloInicio());
                	//Update no id da entrada
                  	 CalculoAtrasoDAO atrasoDAO = new CalculoAtrasoDAO();	        
           	        atrasoDAO.atualizar(horario);     	 
            	}else if(!horario.getSaida().isEmpty()) {
            		horario.setId(resgate.getId());
            		horario.setEntrada(resgate.getEntrada());
                	horario.setIntervaloInicio(resgate.getIntervaloInicio());
                	horario.setIntervaloFim(resgate.getIntervaloFim());
                	// Aplicamos o calculo 
                	CalculoAtrasoServlet calculoAtrasoServlet = new CalculoAtrasoServlet();       
                   	calculoAtrasoServlet.adicionarAtraso(horario); 
            	}          	
            	       
        	}else {        		
     	    // se entrada estiver presente ele apenas salva a entrada
             	 marcacoesFeitasDAO.salvar(horario); 
        	}                
        listarMarcacoes(request, response);        
}
    
    private void removerMarcacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {       
    	String cpf = request.getParameter("cpf");
        marcacoesFeitasDAO.excluirPorCPF(cpf);
        listarMarcacoes(request, response);
    }

    private void listarMarcacoes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String cpf = (String) request.getSession().getAttribute("cpf");// recebo cpf por sessão para saber de qual cpf listar as marcaçoes    	 
        List<MarcacoesFeitas> marcacoes = marcacoesFeitasDAO.listarTodosPorCpf(cpf);
        request.setAttribute("marcacoes", marcacoes);
        request.getRequestDispatcher("controleDeHora.jsp").forward(request, response);
    }
    
    private void listMarcaEmHorarioDeTrab(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    String cpf = request.getParameter("cpf");
	    List<MarcacoesFeitas> marcacoes = marcacoesFeitasDAO.listarTodosPorCpf(cpf);	    
	    HttpSession session = request.getSession();
	    session.setAttribute("cpf", cpf); // Armazena o CPF na sessão em vez de atribuí-la ao objeto request, para que possamos usar em outro servlet e conseguir usa-lo ao adicionar Marcações feitas
	 // Armazena a lista de horários na sessão em vez de atribuí-la ao objeto request, para que os horários permaneçam listado ao navegar na tela
	    session.setAttribute("marcacoes", marcacoes); 

	    request.getRequestDispatcher("horarioTrabalho.jsp").forward(request, response);
	}
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
    		String action = request.getParameter("action");
    		if (action != null) {
    		switch (action) {
    		case "delete":
    		removerMarcacao(request, response);
    		break;
    		case "list": 
    			listMarcaEmHorarioDeTrab(request, response);
    		break;
    		default:
    		listarMarcacoes(request, response);
    		break;
    		}
    		} 
    		}

}
