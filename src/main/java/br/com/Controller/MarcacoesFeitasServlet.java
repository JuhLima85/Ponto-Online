package br.com.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.DAO.BancoDeHorasDAO;
import br.com.DAO.HoraDeTrabalhoDAO;
import br.com.DAO.MarcacoesFeitasDAO;
import br.com.Entity.BancoDeHoras;
import br.com.Entity.HorarioDeTrabalho;
import br.com.Entity.MarcacoesFeitas;

@WebServlet("/MarcacoesFeitasServlet")
public class MarcacoesFeitasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MarcacoesFeitasDAO marcacoesFeitasDAO;
	private HoraDeTrabalhoDAO horaDeTrabalhoDAO;
	private BancoDeHorasDAO bancoDeHorasDAO;

	public void init() {
		marcacoesFeitasDAO = new MarcacoesFeitasDAO();
		horaDeTrabalhoDAO = new HoraDeTrabalhoDAO();
		bancoDeHorasDAO = new BancoDeHorasDAO();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		try {
			switch (action) {
			case "add":
				registrarPonto(request, response);
				break;
			case "edit":
				atualizarMarcacao(request, response);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Ocorreu um erro ao processar a solicitação: " + e.getMessage());
		}
	}

	private void registrarPonto(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String cpf = request.getParameter("cpf");
		String senha = request.getParameter("senha");

		if (cpf == null || senha == null) {
			request.setAttribute("mensagem", "CPF e senha são obrigatórios para realizar o registro de ponto.");
		}

		HorarioDeTrabalho ht = horaDeTrabalhoDAO.buscarPorCpf(cpf);

		if (ht == null || ht.getCpf() == null || ht.getCpf().isEmpty()) {
			request.setAttribute("mensagem",
					"Este CPF não está cadastrado na base de dados. Entre em contato com o RH.");

		} else if (ht.getSenha().equals(senha)) {
			processarMarcacao(request, response, cpf);
		} else {
			request.setAttribute("mensagem", "Senha incorreta.");
		}

		BancoDeHoras bancoDeHoras = bancoDeHorasDAO.buscarPorCpf(cpf);
		if (bancoDeHoras != null) {

			request.setAttribute("bhs", bancoDeHoras);
		}

		listarMarcacoesDoDia(request, response);
	}

	private void processarMarcacao(HttpServletRequest request, HttpServletResponse response, String cpf)
			throws Exception {

		LocalDate dataAtual = LocalDate.now();
		LocalTime horaAtual = LocalTime.now();
		DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
		String horaFormatada = horaAtual.format(formatoHora);

		MarcacoesFeitas ultimaMarcacao = marcacoesFeitasDAO.buscarUltimaEntradaPorCpf(cpf);

		if (ultimaMarcacao == null || !ultimaMarcacao.getData().equals(dataAtual)) {
			criarNovaMarcacao(horaFormatada, cpf);

		} else {
			atualizarMarcacaoExistente(request, response, ultimaMarcacao, horaFormatada);
		}

		request.getSession().setAttribute("cpf", cpf);
	}

	private void criarNovaMarcacao(String horaFormatada, String cpf) {
		MarcacoesFeitas novaMarcacao = new MarcacoesFeitas();
		novaMarcacao.setCpf(cpf);
		novaMarcacao.setEntrada(horaFormatada);
		marcacoesFeitasDAO.salvar(novaMarcacao);
	}

	private void atualizarMarcacaoExistente(HttpServletRequest request, HttpServletResponse response,
			MarcacoesFeitas ultimaMarcacao, String horaFormatada) throws Exception {

		if ((ultimaMarcacao.getIntervaloInicio() == null || ultimaMarcacao.getIntervaloInicio().isEmpty())
				&& !horaFormatada.equals(ultimaMarcacao.getEntrada())) {

			ultimaMarcacao.setIntervaloInicio(horaFormatada);
			marcacoesFeitasDAO.atualizar(ultimaMarcacao);
			calculo(ultimaMarcacao);

		} else if ((ultimaMarcacao.getIntervaloFim() == null || ultimaMarcacao.getIntervaloFim().isEmpty())
				&& (!horaFormatada.equals(ultimaMarcacao.getEntrada())
						&& !horaFormatada.equals(ultimaMarcacao.getIntervaloInicio()))) {
			ultimaMarcacao.setIntervaloFim(horaFormatada);
			marcacoesFeitasDAO.atualizar(ultimaMarcacao);

		} else if ((ultimaMarcacao.getSaida() == null || ultimaMarcacao.getSaida().isEmpty())
				&& (!horaFormatada.equals(ultimaMarcacao.getEntrada())
						&& !horaFormatada.equals(ultimaMarcacao.getIntervaloInicio())
						&& !horaFormatada.equals(ultimaMarcacao.getIntervaloFim()))) {

			ultimaMarcacao.setSaida(horaFormatada);
			marcacoesFeitasDAO.atualizar(ultimaMarcacao);
			calculo(ultimaMarcacao);

		} else if (horaFormatada.equals(ultimaMarcacao.getEntrada())
				|| horaFormatada.equals(ultimaMarcacao.getIntervaloInicio())
				|| horaFormatada.equals(ultimaMarcacao.getIntervaloFim())
				|| horaFormatada.equals(ultimaMarcacao.getSaida())) {

			String mensagem = "Já existe uma marcação registrada nesta data e horário, aguarde um instante para realizar uma nova marcação.";
			processarMensagem(request, response, ultimaMarcacao.getCpf(), mensagem);

		} else {

			String mensagem = "Todas as marcações desta data já foram realizadas.";
			processarMensagem(request, response, ultimaMarcacao.getCpf(), mensagem);
		}
	}

	private void calculo(MarcacoesFeitas ultimaMarcacao) throws Exception {
		bancoDeHorasDAO.salvarOuAtualizarDiscrepanciaDiaria(ultimaMarcacao);
	}

	private void processarMensagem(HttpServletRequest request, HttpServletResponse response, String cpf,
			String mensagem) throws ServletException, IOException {
		request.setAttribute("mensagem", mensagem);
		LocalDate dataAtual = LocalDate.now();
		List<MarcacoesFeitas> marcacoes = marcacoesFeitasDAO.listarPorCpfDiaAtual(cpf, dataAtual);
		request.setAttribute("mf", marcacoes);
		request.getRequestDispatcher("registrarPonto.jsp").forward(request, response);
	}

	private void removerMarcacaoPorId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String cpf = (String) session.getAttribute("cpf");
		LocalDate localDate1 = (LocalDate) session.getAttribute("localDate1");
		LocalDate localDate2 = (LocalDate) session.getAttribute("localDate2");
		
		//recebo os dois IDs da view
		Long idBancoDeHoras  = Long.parseLong(request.getParameter("idBancoDeHoras"));		
		Long idMarcacao = Long.parseLong(request.getParameter("idMarcacao"));
		marcacoesFeitasDAO.excluirPorId(idMarcacao);
		bancoDeHorasDAO.excluirPorId(idBancoDeHoras);
		session.setAttribute("chamador", "excluir");
		
		listMarcaEmHorarioDeTrab(request, response);
	}

	protected void atualizarMarcacao(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String cpf = (String) session.getAttribute("cpf");
		LocalDate localDate1 = (LocalDate) session.getAttribute("localDate1");
		LocalDate localDate2 = (LocalDate) session.getAttribute("localDate2");
		String idConverter = request.getParameter("id");

		MarcacoesFeitas marcacao = new MarcacoesFeitas();
		marcacao.setId(Long.parseLong(idConverter));
		marcacao.setCpf(cpf);
		marcacao.setEntrada(request.getParameter("entrada"));
		marcacao.setIntervaloInicio(request.getParameter("intervaloInicio"));
		marcacao.setIntervaloFim(request.getParameter("intervaloFim"));
		marcacao.setSaida(request.getParameter("saida"));
		marcacao.setPeriodoAtraso(request.getParameter("periodoAtraso"));
		marcacao.setQtdHorasNegativa(request.getParameter("qtdHorasNegativa"));

		marcacoesFeitasDAO.atualizar(marcacao);
		request.setAttribute("mensagem1", "Atualizado com sucesso !");

		session.setAttribute("chamador", "atualizar");
		listMarcaEmHorarioDeTrab(request, response);
	}

	private void exibirFormularioEdicao(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long idConverter = Long.parseLong(request.getParameter("id"));
		MarcacoesFeitas marcacao = marcacoesFeitasDAO.selecionarHorario(idConverter);
		request.setAttribute("marcacao", marcacao);
		RequestDispatcher rd = request.getRequestDispatcher("/editarMarcacao.jsp");
		rd.forward(request, response);
	}

	private void listarMarcacoesDoDia(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cpf = (String) request.getSession().getAttribute("cpf");
		LocalDate dataAtual = LocalDate.now();
		List<MarcacoesFeitas> marcacoes = marcacoesFeitasDAO.listarPorCpfDiaAtual(cpf, dataAtual);
		request.setAttribute("mf", marcacoes);
		request.getRequestDispatcher("registrarPonto.jsp").forward(request, response);
	}

	private void listMarcaEmHorarioDeTrab(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cpf = null;
		LocalDate localDate1 = null;
		LocalDate localDate2 = null;

		HttpSession session = request.getSession();

		String chamador = (String) session.getAttribute("chamador");

		if ("excluir".equals(chamador) || "atualizar".equals(chamador)) {

			cpf = (String) session.getAttribute("cpf");
			localDate1 = (LocalDate) session.getAttribute("localDate1");
			localDate2 = (LocalDate) session.getAttribute("localDate2");

		} else {
			cpf = request.getParameter("cpf");
			String data1 = request.getParameter("dataInicio");
			String data2 = request.getParameter("dataFim");

			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			localDate1 = LocalDate.parse(data1, dateFormatter);
			localDate2 = LocalDate.parse(data2, dateFormatter);

			session.setAttribute("cpf", cpf);
			session.setAttribute("localDate1", localDate1);
			session.setAttribute("localDate2", localDate2);
		}
		List<BancoDeHoras> bancoDeHoras = bancoDeHorasDAO.buscarPorCpfEPeriodo(cpf, localDate1, localDate2);				
		 if (bancoDeHoras != null && !bancoDeHoras.isEmpty()) {			    	
		    	request.setAttribute("bhs", bancoDeHoras);
		    }
	
		List<MarcacoesFeitas> marcacoes = marcacoesFeitasDAO.listarPorCpfEPeriodo(cpf, localDate1, localDate2);		
		if (marcacoes == null || marcacoes.isEmpty()) {
			String mensagem = "Não há registros cadastrados para o CPF ou período informado.";
			request.setAttribute("mensagem1", mensagem);
		} else {

			request.setAttribute("marcacoes", marcacoes);
		}

		request.getRequestDispatcher("horarioTrabalho.jsp").forward(request, response);

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action != null) {
			switch (action) {
			case "delete":
				removerMarcacaoPorId(request, response);
				break;
			case "edit":
				exibirFormularioEdicao(request, response);
				break;
			case "list":
				listMarcaEmHorarioDeTrab(request, response);
				break;
			default:
				break;
			}
		}
	}
}
