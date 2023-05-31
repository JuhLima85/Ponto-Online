package br.com.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.DAO.HoraDeTrabalhoDAO;
import br.com.DAO.MarcacoesFeitasDAO;
import br.com.Entity.HorarioDeTrabalho;
import br.com.Entity.MarcacoesFeitas;

@WebServlet("/HoraDeTrabalhoServlet")
public class HoraDeTrabalhoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HoraDeTrabalhoDAO horaDeTrabalhoDAO;
	String cpf;

	public void init() throws ServletException {
		super.init();
		horaDeTrabalhoDAO = new HoraDeTrabalhoDAO();
		try {			       
		} catch (Exception e) {
			throw new ServletException("Erro ao listar horários", e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		String action = request.getParameter("action");
		cpf = request.getParameter("cpf");				  
		
		try {
			switch (action) {
			case "add":
				adicionarHorario(request, response);
				break;
			case "delete_all":
				removerHorario(request, response);
				break;
			case "delete":
				removerHorarioPorId(request, response);
				break;
			 case "edit":
	                atualizarHorario(request, response);
	                break;
			 case "list":
				 listarHorariosMF(request, response);
	                break;
			}
		} catch (Exception e) {			
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Ocorreu um erro ao processar a solicitação: " + e.getMessage());
		}
	}
	
	private void adicionarHorario(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    String cpf = request.getParameter("cpf");
	    String senha = request.getParameter("senha");
	    String entrada = request.getParameter("entrada");
	    String intervaloInicio = request.getParameter("intervaloInicio");
	    String intervaloFim = request.getParameter("intervaloFim");
	    String saida = request.getParameter("saida");

	    HorarioDeTrabalho ht = horaDeTrabalhoDAO.buscarPorCpf(cpf);

	    if (ht == null || ht.getCpf() == null || ht.getCpf().isEmpty()) {
	        if (cpf == null || cpf.isEmpty() || senha == null || senha.isEmpty() ||
	                entrada == null || entrada.isEmpty() || intervaloInicio == null || intervaloInicio.isEmpty()) {
	        	 request.setAttribute("mensagem", "Preencha pelo menos um período por completo.");
	     	   request.getRequestDispatcher("horarioTrabalho.jsp").forward(request, response);
	        }else {

	        HorarioDeTrabalho horario = new HorarioDeTrabalho();
	        horario.setCpf(cpf);
	        horario.setSenha(senha);
	        horario.setEntrada(entrada);
	        horario.setIntervaloInicio(intervaloInicio);
	        horario.setIntervaloFim(intervaloFim);
	        horario.setSaida(saida);

	        horaDeTrabalhoDAO.salvar(horario);

	        listarHorarios(request, response);
	        }
	    } else {

	    List<HorarioDeTrabalho> horarios = horaDeTrabalhoDAO.listarTodosPorCpf(cpf);
	    request.setAttribute("horarios", horarios);
	    request.setAttribute("mensagem", "Este CPF já está cadastrado. Caso necessário, utilize as opções Editar ou Excluir.");
	    request.getRequestDispatcher("horarioTrabalho.jsp").forward(request, response);
	    }
	}

	private void removerHorario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cpf = request.getParameter("cpf");
		horaDeTrabalhoDAO.excluirPorCPF(cpf);
		System.out.println("Removido com sucesso.");
		listarHorarios(request, response);
	}

	protected void removerHorarioPorId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));
		horaDeTrabalhoDAO.excluirPorId(id);
		listarHorarios(request, response);

	}

	private void listarHorarios(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cpf = request.getParameter("cpf");
		List<HorarioDeTrabalho> horarios = horaDeTrabalhoDAO.listarTodosPorCpf(cpf);
		request.setAttribute("horarios", horarios);
		request.getRequestDispatcher("horarioTrabalho.jsp").forward(request, response);
	}	
	
	private void listarHorariosMF(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    String cpf = request.getParameter("cpf");
	    List<HorarioDeTrabalho> horarios = horaDeTrabalhoDAO.listarTodosPorCpf(cpf);	    
	    HttpSession session = request.getSession();
	    session.setAttribute("cpf", cpf); // Armazena o CPF na sessão em vez de atribuí-la ao objeto request, para que possamos usar em outro servlet e conseguir usa-lo ao adicionar Marcações feitas
	 // Armazena a lista de horários na sessão em vez de atribuí-la ao objeto request, para que os horários permaneçam listado ao navegar na tela
	    session.setAttribute("horarios", horarios); 

	    request.getRequestDispatcher("controleDeHora.jsp").forward(request, response);
	}	
		
	private void listarHorariosPorCPF(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			String cpf = request.getParameter("cpf");
			List<HorarioDeTrabalho> lista = horaDeTrabalhoDAO.listarTodosPorCpf(cpf);
			HttpSession session = request.getSession();
			session.setAttribute("cpf", cpf); // Armazena o CPF na sessão em vez de atribuí-la ao objeto request, para que possamos usar em outro servlet e conseguir usa-lo ao adicionar Marcações feitas
			// Armazena a lista de horários na sessão em vez de atribuí-la ao objeto request, para que os horários permaneçam listado ao navegar na tela
			session.setAttribute("lista", lista);
			request.getRequestDispatcher("horarioTrabalho.jsp").forward(request, response);
			}
	
	private void exibirFormularioEdicao(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		Long horario = Long.parseLong(request.getParameter("id"));		
		horaDeTrabalhoDAO.selecionarHorario(horario);
		request.setAttribute("horario", horario);
		RequestDispatcher rd = request.getRequestDispatcher("/editar.jsp");
		rd.forward(request, response);   
	}
	
	protected void atualizarHorario(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    String idConverter = request.getParameter("id");
	    HorarioDeTrabalho horario = new HorarioDeTrabalho();
	    horario.setId(Long.parseLong(idConverter));
	    horario.setEntrada(request.getParameter("entrada"));
	    horario.setSaida(request.getParameter("saida"));	    
	    horaDeTrabalhoDAO.atualizar(horario);
	    response.sendRedirect("controleDeHora.jsp");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");			
		if (action != null) {
			switch (action) {			
			case "list":
				  listarHorariosPorCPF(request, response);
	                break;
			 case "edit":
	                exibirFormularioEdicao(request, response);
	                break;

			}
	} 

	}

}
