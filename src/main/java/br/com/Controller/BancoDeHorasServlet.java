package br.com.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.DAO.BancoDeHorasDAO;
import br.com.DAO.HoraDeTrabalhoDAO;
import br.com.Entity.BancoDeHoras;
import br.com.Entity.HorarioDeTrabalho;
import br.com.Entity.MarcacoesFeitas;

@WebServlet("/BancoDeHorasServlet")
public class BancoDeHorasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BancoDeHorasDAO bancoDeHorasDAO;

	public void init() {
		bancoDeHorasDAO = new BancoDeHorasDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		try {
			switch (action) {

			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Ocorreu um erro ao processar a solicitação: " + e.getMessage());
		}
	}

	public BancoDeHoras calcularDiscrepanciaDiaria(MarcacoesFeitas mf) {
		HoraDeTrabalhoDAO hdtdao = new HoraDeTrabalhoDAO();
		HorarioDeTrabalho hdt = hdtdao.buscarPorCpf(mf.getCpf());

		String discrepanciaDiaria = null;

		if (mf != null && hdt != null) {

			LocalTime entrMf = LocalTime.parse(mf.getEntrada());
			LocalTime inicioIntMf = LocalTime.parse(mf.getIntervaloInicio());
			LocalTime fimIntMf = (mf.getIntervaloFim() != null && !mf.getIntervaloFim().isEmpty())
					? LocalTime.parse(mf.getIntervaloFim())
					: LocalTime.MIN;
			LocalTime saidMf = (mf.getSaida() != null && !mf.getSaida().isEmpty()) ? LocalTime.parse(mf.getSaida())
					: LocalTime.MIN;

			LocalTime entrHt = LocalTime.parse(hdt.getEntrada());
			LocalTime inicioIntHt = LocalTime.parse(hdt.getIntervaloInicio());
			LocalTime fimIntHt = (hdt.getIntervaloFim() != null && !hdt.getIntervaloFim().isEmpty())
					? LocalTime.parse(hdt.getIntervaloFim())
					: LocalTime.MIN;
			LocalTime saidHt = (hdt.getSaida() != null && !hdt.getSaida().isEmpty()) ? LocalTime.parse(hdt.getSaida())
					: LocalTime.MIN;

			long diferencaMf1 = ChronoUnit.MINUTES.between(entrMf, inicioIntMf);
			long diferencaMf2 = (fimIntMf != null && saidMf != null) ? ChronoUnit.MINUTES.between(fimIntMf, saidMf) : 0;
			long totalMf = diferencaMf1 + diferencaMf2;

			long diferencaHt1 = ChronoUnit.MINUTES.between(entrHt, inicioIntHt);
			long diferencaHt2 = (fimIntHt != null && saidHt != null) ? ChronoUnit.MINUTES.between(fimIntHt, saidHt) : 0;
			long totalHt = diferencaHt1 + diferencaHt2;

			long diferenca = totalMf - totalHt;

			long diferencaEmHoras = diferenca / 60;

			long diferencaEmMinutos = diferenca % 60;

			String horas = Long.toString(diferencaEmHoras);
			String minutos = Long.toString(diferencaEmMinutos).replace("-", "");

			if ("0".equals(horas) && "0".equals(minutos)) {

				discrepanciaDiaria = "-";
			} else {

				discrepanciaDiaria = horas + ":" + minutos;
			}
		}

		BancoDeHoras bhs = new BancoDeHoras();
		bhs.setCpf(mf.getCpf());
		bhs.setDiscrepanciaDiaria(discrepanciaDiaria);

		return new BancoDeHoras(mf.getCpf(), discrepanciaDiaria);

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action != null) {

			switch (action) {

			}
		}

	}
}
