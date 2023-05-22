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
@Table(name = "calculohoraextra")
public class CalculoHoraExtra  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cpf;
    private String data;
    private String entrada;
    private String saida;
    private int horaExtraEntrada;
    private int horaExtraSaida;
    
    @ManyToOne
    @JoinColumn(name = "cpf", referencedColumnName = "cpf", insertable = false, updatable = false)
    private HorarioDeTrabalho horarioTrabalho;
   
	public CalculoHoraExtra() {
		
	}

	public CalculoHoraExtra(Long id, String cpf, String data, String entrada, String saida, int horaExtraEntrada,
			int horaExtraSaida, HorarioDeTrabalho horarioTrabalho) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.data = data;
		this.entrada = entrada;
		this.saida = saida;
		this.horaExtraEntrada = horaExtraEntrada;
		this.horaExtraSaida = horaExtraSaida;
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getEntrada() {
		return entrada;
	}

	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}

	public String getSaida() {
		return saida;
	}

	public void setSaida(String saida) {
		this.saida = saida;
	}

	public int getHoraExtraEntrada() {
		return horaExtraEntrada;
	}

	public void setHoraExtraEntrada(int horaExtraEntrada) {
		this.horaExtraEntrada = horaExtraEntrada;
	}

	public int getHoraExtraSaida() {
		return horaExtraSaida;
	}

	public void setHoraExtraSaida(int horaExtraSaida) {
		this.horaExtraSaida = horaExtraSaida;
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
		CalculoHoraExtra other = (CalculoHoraExtra) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CalculoHoraExtra [id=" + id + ", cpf=" + cpf + ", data=" + data + ", entrada=" + entrada + ", saida="
				+ saida + ", horaExtraEntrada=" + horaExtraEntrada + ", horaExtraSaida=" + horaExtraSaida
				+ ", horarioTrabalho=" + horarioTrabalho + "]";
	}
   
	
}