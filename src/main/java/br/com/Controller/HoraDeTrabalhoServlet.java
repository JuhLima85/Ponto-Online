package br.com.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.DAO.HoraDeTrabalhoDAO;
import br.com.DAO.MarcacoesFeitasDAO;
import br.com.Entity.HorarioDeTrabalho;
import br.com.Entity.MarcacoesFeitas;

@WebServlet("/HoraDeTrabalhoServlet")
public class HoraDeTrabalhoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HoraDeTrabalhoDAO horaDeTrabalhoDAO;
	

	public void init() throws ServletException {
		super.init();
		horaDeTrabalhoDAO = new HoraDeTrabalhoDAO();
		try {
			listarHorarios();
		} catch (Exception e) {
			throw new ServletException("Erro ao listar horários", e);
		}
	}

	// Para que os horários permaneçam listado ao navegar na tela
	private void listarHorarios() {
		List<HorarioDeTrabalho> horarios = horaDeTrabalhoDAO.listarTodos();
		getServletContext().setAttribute("horarios", horarios);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Teste
		 System.out.println("Iniciando doPost() em HoraDeTrabalhoServlet");
		String action = request.getParameter("action");

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
			default:
				listarHorarios(request, response);
				break;
			}
		} catch (Exception e) {
			// trata a exceção
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Ocorreu um erro ao processar a solicitação: " + e.getMessage());
		}
	}

	private void adicionarHorario(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String cpf = request.getParameter("cpf");
		String entrada = request.getParameter("entrada");
		String intervaloInicio = request.getParameter("intervaloInicio");
		String intervaloFim = request.getParameter("intervaloFim");
		String saida = request.getParameter("saida");

		if (cpf == null || cpf.isEmpty() || entrada == null || entrada.isEmpty() || saida == null || saida.isEmpty()) {
			throw new Exception("Todos os campos devem ser preenchidos");
		}

		HorarioDeTrabalho horario = new HorarioDeTrabalho();

		horario.setCpf(cpf);
		horario.setEntrada(entrada);
		horario.setIntervaloInicio(intervaloInicio);
		horario.setIntervaloFim(intervaloFim);
		horario.setSaida(saida);		

		horaDeTrabalhoDAO.salvar(horario);	

		listarHorarios(request, response);
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
		List<HorarioDeTrabalho> horarios = horaDeTrabalhoDAO.listarTodos();
		request.setAttribute("horarios", horarios);
		request.getRequestDispatcher("controleDeHora.jsp").forward(request, response);
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
				listarHorarios(request, response);
				break;
			 case "edit":
	                exibirFormularioEdicao(request, response);
	                break;
			default:
				listarHorarios(request, response);
				break;
			}
		} else {
			listarHorarios(request, response);
		}
	}

}
