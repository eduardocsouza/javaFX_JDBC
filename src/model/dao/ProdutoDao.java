package model.dao;

import java.util.List;

import model.entities.Produto;

public interface ProdutoDao {
	
	void insert(Produto obj);
	void update(Produto obj);
	void delete(Produto codigo);
	Produto findById(Integer codigo);
	List<Produto> findAll();

}
