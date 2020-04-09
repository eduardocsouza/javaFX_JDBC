package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.ProdutoDao;
import model.dao.VendaDao;
import model.entities.Produto;
import model.entities.VendasProduto;

public class ProdutoService {

	private ProdutoDao dao = DaoFactory.createProdutoDao();
	private VendaDao daoVenda = DaoFactory.createVendaDao();

	public List<Produto> findAll() {
		return dao.findAll();
	}

	public void insertProd(Produto prod) {

		if (prod.getCodigo() == null) {

			dao.insert(prod);

		} else {

			dao.update(prod);
		}

	}

	public void deleteProd(Produto prod) {
		dao.delete(prod);
	}

	public Produto findById(Integer id) {
		return dao.findById(id);
	}

	// -----------------Dao De VENDAS-----------------------------------

	public List<VendasProduto> findAllVenda(String data) {
		return daoVenda.findAll(data);
	}

	public void insertVenda(VendasProduto venda) {	
		
			daoVenda.insert(venda);			
	
	}

	public void deleteProd(VendasProduto prod) {
		daoVenda.delete(prod);
	}

	public VendasProduto findByIdVenda(String date) {
		return daoVenda.findById(date);
	}

}
