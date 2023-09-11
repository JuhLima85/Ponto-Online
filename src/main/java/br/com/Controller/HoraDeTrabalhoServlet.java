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
import br.com.Entity.HorarioDeTrabalho;
import br.com.Utils.CpfValidador;

@WebServlet("/HoraDeTrabalhoServlet")
public class HoraDeTrabalhoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HoraDeTrabalhoDAO horaDeTrabalhoDAO;
	private String acaoAnterior = "";

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

		try {
			switch (action) {
			case "add":
				adicionarHorario(request, response);
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
		List<HorarioDeTrabalho> horarios = null;

		if (CpfValidador.isValid(cpf)) {
			String senha = request.getParameter("senha");
			String entrada = request.getParameter("entrada");
			String intervaloInicio = request.getParameter("intervaloInicio");
			String intervaloFim = request.getParameter("intervaloFim");
			String saida = request.getParameter("saida");

			HorarioDeTrabalho ht = horaDeTrabalhoDAO.buscarPorCpf(cpf);

			if (ht == null || ht.getCpf() == null || ht.getCpf().isEmpty()) {

				if (cpf == null || cpf.isEmpty() || senha == null || senha.isEmpty() || entrada == null
						|| entrada.isEmpty() || intervaloInicio == null || intervaloInicio.isEmpty()) {
					request.setAttribute("mensagem", "Preencha pelo menos o primeiro período por completo.");
					request.getRequestDispatcher("horarioTrabalho.jsp").forward(request, response);

				} else {
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
				horarios = horaDeTrabalhoDAO.listarTodosPorCpf(cpf);
				request.setAttribute("horarios", horarios);
				request.setAttribute("mensagem",
						"Este CPF já está cadastrado. Caso necessário, utilize as opções Editar ou Excluir.");
				request.getRequestDispatcher("horarioTrabalho.jsp").forward(request, response);
			}
		} else {
			request.setAttribute("horarios", horarios);
			request.setAttribute("mensagem", "CPF Inválido.");
			request.getRequestDispatcher("horarioTrabalho.jsp").forward(request, response);
		}
	}

	protected void removerHorarioPorId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));
		horaDeTrabalhoDAO.excluirPorId(id);
		if (acaoAnterior.equals("listCPF")) {
			listarHorarios(request, response);
		} else if (acaoAnterior.equals("listAll")) {
			listarTodosHorarios(request, response);
		}
	}

	private void listarHorarios(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cpf = request.getParameter("cpf");
		List<HorarioDeTrabalho> horarios = horaDeTrabalhoDAO.listarTodosPorCpf(cpf);

		if (horarios == null || horarios.isEmpty()) {
			String mensagem = "Não há registros cadastrados para o CPF informado.";
			request.setAttribute("mensagem", mensagem);
		} else {
			request.setAttribute("horarios", horarios);
		}

		request.getRequestDispatcher("horarioTrabalho.jsp").forward(request, response);
	}

	private void listarHorariosMF(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cpf = request.getParameter("cpf");
		List<HorarioDeTrabalho> horarios = horaDeTrabalhoDAO.listarTodosPorCpf(cpf);
		HttpSession session = request.getSession();
		session.setAttribute("cpf", cpf);
		session.setAttribute("horarios", horarios);
		request.getRequestDispatcher("controleDeHora.jsp").forward(request, response);
	}

	private void listarTodosHorarios(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<HorarioDeTrabalho> horarios = horaDeTrabalhoDAO.listarTodos();
		if (horarios == null || horarios.isEmpty()) {
			String mensagem = "Não há registros cadastrados.";
			request.setAttribute("mensagem", mensagem);
		} else {
			request.setAttribute("horarios", horarios);
		}
		request.getRequestDispatcher("horarioTrabalho.jsp").forward(request, response);
	}

	private void exibirFormularioEdicao(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long idConverter = Long.parseLong(request.getParameter("id"));
		HorarioDeTrabalho ht = horaDeTrabalhoDAO.selecionarHorario(idConverter);
		request.setAttribute("horario", ht);
		RequestDispatcher rd = request.getRequestDispatcher("/editarHorario.jsp");
		rd.forward(request, response);
	}

	protected void atualizarHorario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idConverter = request.getParameter("id");

		HorarioDeTrabalho horario = new HorarioDeTrabalho();
		horario.setId(Long.parseLong(idConverter));
		horario.setCpf(request.getParameter("cpf"));
		horario.setEntrada(request.getParameter("entrada"));
		horario.setIntervaloInicio(request.getParameter("intervaloInicio"));
		horario.setIntervaloFim(request.getParameter("intervaloFim"));
		horario.setSaida(request.getParameter("saida"));
		horario.setSenha(request.getParameter("senha"));

		if (CpfValidador.isValid(horario.getCpf())) {
			horaDeTrabalhoDAO.atualizar(horario);
			request.setAttribute("mensagem", "Atualizado com sucesso !");
			listarHorarios(request, response);
		} else {
			request.setAttribute("horario", horario);
			request.setAttribute("mensagem", "CPF Inválido.");
			request.getRequestDispatcher("/editarHorario.jsp").forward(request, response);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action != null) {
			acaoAnterior = action;
			switch (action) {
			case "listCPF":
				listarHorarios(request, response);
				break;
			case "listAll":
				listarTodosHorarios(request, response);
				break;
			case "edit":
				exibirFormularioEdicao(request, response);
				break;
			}
		}

	}
}
