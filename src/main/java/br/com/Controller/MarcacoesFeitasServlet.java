package br.com.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.DAO.CalculoAtrasoDAO;
import br.com.DAO.MarcacoesFeitasDAO;
import br.com.Entity.MarcacoesFeitas;

@WebServlet("/MarcacoesFeitasServlet")
public class MarcacoesFeitasServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private MarcacoesFeitasDAO marcacoesFeitasDAO; 
    private CalculoAtrasoDAO trabalho;
   

    public void init() {
        marcacoesFeitasDAO = new MarcacoesFeitasDAO();
        trabalho = new CalculoAtrasoDAO();      
        
      //Para que os horários permaneçam listado ao navegar na tela
        listarMarcacoes();
    }
    
    //Para que os horários permaneçam listado ao navegar na tela
    private void listarMarcacoes() {
    	 List<MarcacoesFeitas> marcacoes = marcacoesFeitasDAO.listarTodos();
    	 getServletContext().setAttribute("marcacoes", marcacoes);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {   
    	//Teste
   	 System.out.println("Iniciando doPost() em MarcacoesFeitasDAO");
		String action = request.getParameter("action");
		
    	try {
			switch (action) {
			case "add":
				adicionarMarcacao(request, response);				
				break;
			case "delete_all":
				//removerHorario(request, response);
				break;
			case "delete":
				//removerHorarioPorId(request, response);
				break;
			 case "edit":
	            //    atualizarHorario(request, response);
	                break;
			default:
				listarMarcacoes(request, response);
				break;
			}
		} catch (Exception e) {
			// trata a exceção
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Ocorreu um erro ao processar a solicitação: " + e.getMessage());
		}    	
       
    }
    
    private void adicionarMarcacao(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	String cpf = trabalho.buscarUltimoRegistro();
    	request.getParameter("cpf");
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
        
        if(entrada == null || entrada.isEmpty()) {         	   
        	   
        	// Buscar entrada pelo último id
           	horario.setEntrada(marcacoesFeitasDAO.buscarUltimaEntrada());           	
           	
           	// Chama o método adicionarAtraso 
           	CalculoAtrasoServlet calculoAtrasoServlet = new CalculoAtrasoServlet();       
           	calculoAtrasoServlet.adicionarAtraso(horario);      
        }else { 
        	// se entrada estiver presente ele apenas salva a entrada
        	 marcacoesFeitasDAO.salvar(horario); 
       
        }
        
        listarMarcacoes(request, response);
        
}

//    private void adicionarMarcacao(HttpServletRequest request, HttpServletResponse response)
//            throws Exception {
//    	String cpf = trabalho.buscarUltimoRegistro();
//    	request.getParameter("cpf");
//    	String entrada = request.getParameter("entrada");
//        String intervaloInicio = request.getParameter("intervaloInicio");
//        String intervaloFim = request.getParameter("intervaloFim");
//        String saida = request.getParameter("saida");
//
//        if (        	
//           entrada == null || entrada.isEmpty() ||           
//            saida == null || saida.isEmpty()) {
//        	 throw new Exception("Todos os campos devem ser preenchidos");
//        }
//
//        MarcacoesFeitas horario = new MarcacoesFeitas();
//        horario.setCpf(cpf);
//        horario.setEntrada(entrada);
//        horario.setIntervaloInicio(intervaloInicio);
//        horario.setIntervaloFim(intervaloFim);
//        horario.setSaida(saida);
//        //marcacoesFeitasDAO.salvar(horario);   agora vai salvar quando salvar os calculos
//        
//        // Chama o método adicionarAtraso 
//        CalculoAtrasoServlet calculoAtrasoServlet = new CalculoAtrasoServlet();       
//        calculoAtrasoServlet.adicionarAtraso(cpf);      
//        
//        listarMarcacoes(request, response);
//        
//}

    private void removerMarcacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {       
    	String cpf = request.getParameter("cpf");
        marcacoesFeitasDAO.excluirPorCPF(cpf);
        listarMarcacoes(request, response);
    }

    private void listarMarcacoes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<MarcacoesFeitas> marcacoes = marcacoesFeitasDAO.listarTodos();
        request.setAttribute("marcacoes", marcacoes);
        request.getRequestDispatcher("controleDeHora.jsp").forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        	//http://localhost:8080/RegisterPoint/HoraDeTrabalhoServlet?action=list
        if (action != null) {
            switch (action) {
                case "delete":
                	removerMarcacao(request, response);
                    break;
                case "list":
                	//listarMarcacoes(request, response);
                    break;
                
                default:
                	listarMarcacoes(request, response);
                    break;
            }
        } else {
        	listarMarcacoes(request, response);
        }
    }
}
