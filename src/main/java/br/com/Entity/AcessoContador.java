package br.com.Entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "acesso_contador")
public class AcessoContador {
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantidadeAcessos() {
		return quantidadeAcessos;
	}

	public void setQuantidadeAcessos(int quantidadeAcessos) {
		this.quantidadeAcessos = quantidadeAcessos;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantidade_acessos")
    private int quantidadeAcessos;

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AcessoContador other = (AcessoContador) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "AcessoContador [id=" + id + ", quantidadeAcessos=" + quantidadeAcessos + "]";
	}

    
}



