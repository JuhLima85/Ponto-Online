package br.com.DTOs;

import br.com.Utils.CodigoErro;

public class ResultadoCalculoAtraso {
    private String diferenca;
    private String periodoAtraso;
    private CodigoErro codigoErro;
    private String mensagem;

    private String cpf;
    private String entrada;
    private String intervaloInicio;
    private String intervaloFim;
    private String saida;


	public ResultadoCalculoAtraso() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	public ResultadoCalculoAtraso(String diferenca) {
		super();
		this.diferenca = diferenca;
		
	}



	public ResultadoCalculoAtraso(String cpf, String entrada, String saida) {
		super();
		this.cpf = cpf;
		this.entrada = entrada;	
		this.saida = saida;		
	}

	public ResultadoCalculoAtraso(String diferenca, String periodoAtraso, String entrada, String saida) {

        this.diferenca = diferenca;
        this.periodoAtraso = periodoAtraso;
        this.entrada = entrada;
		this.saida = saida;
    }
    
    public ResultadoCalculoAtraso(CodigoErro codigoErro, String mensagem) {
        this.codigoErro = codigoErro;
        this.mensagem = mensagem;
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

	public String getDiferenca() {
        return diferenca;
    }
    
    public String getPeriodoAtraso() {
        return periodoAtraso;
    }
    public CodigoErro getCodigoErro() {
        return codigoErro;
    }

    public String getMensagem() {
        return mensagem;
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

	public String getSaida() {
		return saida;
	}

	public void setSaida(String saida) {
		this.saida = saida;
	}

}
