package br.com.Entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bancodehoras")
public class BancoDeHoras implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate data = LocalDate.now();
	private String cpf;
	private String discrepanciaDiaria;
	private String discrepanciaMensal;
	private String totalHorasTrabalhadas;
	
	@ManyToOne	
	@JoinColumn(name = "cpf", referencedColumnName = "cpf", insertable = false, updatable = false)
	private HorarioDeTrabalho horarioTrabalho;

	public BancoDeHoras() {		
		// TODO Auto-generated constructor stub
	}

	public BancoDeHoras(Long id, LocalDate data, String cpf, String discrepanciaDiaria, String discrepanciaMensal,
			String totalHorasTrabalhadas, HorarioDeTrabalho horarioTrabalho) {
		super();
		this.id = id;
		this.data = data;
		this.cpf = cpf;
		this.discrepanciaDiaria = discrepanciaDiaria;
		this.discrepanciaMensal = discrepanciaMensal;
		this.totalHorasTrabalhadas = totalHorasTrabalhadas;
		this.horarioTrabalho = horarioTrabalho;
	}

	public BancoDeHoras(String cpf, String discrepanciaDiaria) {
		super();
		this.cpf = cpf;
		this.discrepanciaDiaria = discrepanciaDiaria;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getDiscrepanciaDiaria() {
		return discrepanciaDiaria;
	}

	public void setDiscrepanciaDiaria(String discrepanciaDiaria) {
		this.discrepanciaDiaria = discrepanciaDiaria;
	}

	public String getDiscrepanciaMensal() {
		return discrepanciaMensal;
	}

	public void setDiscrepanciaMensal(String discrepanciaMensal) {
		this.discrepanciaMensal = discrepanciaMensal;
	}

	public String getTotalHorasTrabalhadas() {
		return totalHorasTrabalhadas;
	}

	public void setTotalHorasTrabalhadas(String totalHorasTrabalhadas) {
		this.totalHorasTrabalhadas = totalHorasTrabalhadas;
	}

	public HorarioDeTrabalho getHorarioTrabalho() {
		return horarioTrabalho;
	}

	public void setHorarioTrabalho(HorarioDeTrabalho horarioTrabalho) {
		this.horarioTrabalho = horarioTrabalho;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BancoDeHoras other = (BancoDeHoras) obj;
		return Objects.equals(cpf, other.cpf);
	}

	@Override
	public String toString() {
		return "BancoDeHoras [id=" + id + ", data=" + data + ", cpf=" + cpf + ", discrepanciaDiaria=" + discrepanciaDiaria
				+ ", discrepanciaMensal=" + discrepanciaMensal + ", totalHorasTrabalhadas=" + totalHorasTrabalhadas
				+ ", horarioTrabalho=" + horarioTrabalho + "]";
	}	
}
