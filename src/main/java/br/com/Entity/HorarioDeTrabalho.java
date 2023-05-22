package br.com.Entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "horariodetrabalho")
public class HorarioDeTrabalho implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cpf;
	private String entrada;
	private String intervaloInicio;
    private String intervaloFim;
    private String saida;

    public HorarioDeTrabalho() {
		// TODO Auto-generated constructor stub
	}   
   
	public HorarioDeTrabalho(Long id, String cpf, String entrada, String intervaloInicio, String intervaloFim,
			String saida) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.entrada = entrada;
		this.intervaloInicio = intervaloInicio;
		this.intervaloFim = intervaloFim;
		this.saida = saida;
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
		HorarioDeTrabalho other = (HorarioDeTrabalho) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HorarioDeTrabalho [id=" + id + ", cpf=" + cpf + ", entrada=" + entrada + ", intervaloInicio="
				+ intervaloInicio + ", intervaloFim=" + intervaloFim + ", saida=" + saida + "]";
	}
	
	
}