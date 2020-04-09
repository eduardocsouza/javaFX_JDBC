package model.dao;

import db.DB;
import model.dao.impl.ProdutoDaoJDBC;
import model.dao.impl.VendaDaoJDBC;

public class DaoFactory {

	public static ProdutoDao createProdutoDao() {
		return new ProdutoDaoJDBC(DB.getConnection());
	}
	
	public static VendaDao createVendaDao() {
		return new VendaDaoJDBC(DB.getConnection());
	}
}
