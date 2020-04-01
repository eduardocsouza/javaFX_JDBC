package model.entities;

import java.io.Serializable;

public class Produto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	private String nome;
	private Integer qtd;
	private Double preco;
	
	public Produto() {
	}
	
	public Produto(Integer codigo, String nome, Double preco, Integer qtd) {
		this.codigo = codigo;
		this.nome = nome;
		this.preco = preco;
		this.qtd = qtd;
	}
	
	

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQtd() {
		return qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Produto other = (Produto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto [codigo=" + codigo + ", nome=" + nome + ", qtd=" + qtd + ", preco=" + preco + "]";
	}

	
	

}
