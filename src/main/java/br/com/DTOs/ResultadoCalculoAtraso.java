package br.com.DTOs;

import br.com.Utils.CodigoErro;

public class ResultadoCalculoAtraso {
    private String diferenca;
    private String periodoAtraso;
    private CodigoErro codigoErro;
    private String mensagem;

    private String cpf;
    private String entrada;
    private String saida;   

	public ResultadoCalculoAtraso(String cpf, String entrada, String saida) {
		super();
		this.cpf = cpf;
		this.entrada = entrada;
		this.saida = saida;
	}

	public ResultadoCalculoAtraso() {
		super();
		// TODO Auto-generated constructor stub
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
