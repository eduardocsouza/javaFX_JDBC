package model.dao;


import java.util.List;

import model.entities.VendasProduto;

public interface VendaDao {
	
	void insert(VendasProduto obj);
	void update(VendasProduto obj);
	void delete(VendasProduto codigo);
	//VendasProduto findById(Integer codigo);
	List<VendasProduto> findAll(String data);
	VendasProduto findById(String data);

}
