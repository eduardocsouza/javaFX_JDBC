package model.entities;

import java.io.Serializable;
import java.util.Date;


public class VendasProduto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date dataVenda;
	private Integer qtd;
	private String nome;
	private Integer codigo;
	
	private Produto prod;
	
	
	public VendasProduto() {
	}
	
	public VendasProduto(Integer id, Date dataVenda, Integer qtd, Produto prod) {
		this.id = id;
		this.dataVenda = dataVenda;
		this.qtd = qtd;
		this.prod = prod;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Integer getQtd() {
		return qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}

	public Produto getProd() {
		return prod;
	}

	public void setProd(Produto prod) {
		this.prod = prod;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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
		VendasProduto other = (VendasProduto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ID: " + getId()
					  + ", "
					  + "quantidade: "
					  + getQtd()
					  + ", "
					  + "Data: "
					  + getDataVenda()
					  + " Nome: "
					  + getNome()
					  + " Codigo do Produto "
					  + getCodigo();
	}

}
