package br.com.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.DAO.CalculoAtrasoDAO;
import br.com.Entity.CalculoAtraso;
import br.com.Entity.MarcacoesFeitas;


@WebServlet("/CalculoAtrasoServlet")
public class CalculoAtrasoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private CalculoAtrasoDAO calculoAtrasoDAO;
   
    public void init() {
        calculoAtrasoDAO = new CalculoAtrasoDAO();        
          listaratrasos();
}
    
    private void listaratrasos() {
      	 List<CalculoAtraso> at = calculoAtrasoDAO.listarTodos();
      	 getServletContext().setAttribute("at", at);
      }
    
    public void adicionarAtraso(MarcacoesFeitas horario) throws Exception {
    	CalculoAtrasoDAO.calcularEInserirAtraso(horario);
            
    }
    
    public void listarAtrasos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {    	 
    	List<CalculoAtraso> atraso = calculoAtrasoDAO.listarTodos();
        request.setAttribute("atraso", atraso);
        request.getRequestDispatcher("controleDeHora.jsp").forward(request, response);
    }    
   
    @Override    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        	//http://localhost:8080/RegisterPoint/HoraDeTrabalhoServlet?action=list
        if (action != null ) {
            switch (action) {
            case "list":
				   	listarAtrasos(request, response);
                    break;                
                default:
                	listarAtrasos(request, response);
                    break;
            }
        } else {
        	listarAtrasos(request, response);
        }
    }
}
