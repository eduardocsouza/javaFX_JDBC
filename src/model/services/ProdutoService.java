package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.ProdutoDao;
import model.entities.Produto;

public class ProdutoService {
	
	private ProdutoDao dao = DaoFactory.createProdutoDao();
	
	
	public List<Produto> findAll(){
		return dao.findAll();
	}
	
	
	
	public void insertProd(Produto prod) {
		
		if(prod.getCodigo() == null){
			
			dao.insert(prod);
			
		}else {
			
			dao.update(prod);
		}
		
		
	}
	
	public void deleteProd(Produto prod) {
		dao.delete(prod);
	}
	
	
	public Produto findById(Integer id) {
		return dao.findById(id);
	}
	
}
