package br.com.Entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "calculoatraso")
public class CalculoAtraso implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cpf;
	private String entrada;
	private String intervaloInicio;
	private String intervaloFim;
	private String saida;
	private String periodoAtraso;

	@ManyToOne
	@JoinColumn(name = "cpf", referencedColumnName = "cpf", insertable = false, updatable = false)
	private HorarioDeTrabalho horarioTrabalho;

	public CalculoAtraso() {
		// TODO Auto-generated constructor stub
	}

	public CalculoAtraso(Long id, String cpf, String entrada, String intervaloInicio, String intervaloFim, String saida,
			String periodoAtraso, HorarioDeTrabalho horarioTrabalho) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.entrada = entrada;
		this.intervaloInicio = intervaloInicio;
		this.intervaloFim = intervaloFim;
		this.saida = saida;
		this.periodoAtraso = periodoAtraso;
		this.horarioTrabalho = horarioTrabalho;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEntrada() {
		return entrada;
	}

	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}

	public String getIntervaloInicio() {
		return intervaloInicio;
	}

	public void setIntervaloInicio(String intervaloInicio) {
		this.intervaloInicio = intervaloInicio;
	}

	public String getIntervaloFim() {
		return intervaloFim;
	}

	public void setIntervaloFim(String intervaloFim) {
		this.intervaloFim = intervaloFim;
	}

	public String getSaida() {
		return saida;
	}

	public void setSaida(String saida) {
		this.saida = saida;
	}

	public String getPeriodoAtraso() {
		return periodoAtraso;
	}

	public void setPeriodoAtraso(String periodoAtraso) {
		this.periodoAtraso = periodoAtraso;
	}

	public HorarioDeTrabalho getHorarioTrabalho() {
		return horarioTrabalho;
	}

	public void setHorarioTrabalho(HorarioDeTrabalho horarioTrabalho) {
		this.horarioTrabalho = horarioTrabalho;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CalculoAtraso other = (CalculoAtraso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CalculoAtraso [id=" + id + ", cpf=" + cpf + ", entrada=" + entrada + ", intervaloInicio="
				+ intervaloInicio + ", intervaloFim=" + intervaloFim + ", saida=" + saida + ", periodoAtraso="
				+ periodoAtraso + ", horarioTrabalho=" + horarioTrabalho + "]";
	}

}
