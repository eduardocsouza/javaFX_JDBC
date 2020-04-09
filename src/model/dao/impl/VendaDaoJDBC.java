package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.VendaDao;
import model.entities.Produto;
import model.entities.VendasProduto;

public class VendaDaoJDBC implements VendaDao {
	
	//nesse bloco estamos criando uma injeção de dependência.
	private Connection conn;
	
	public VendaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	//fim do bloco de injeção. 

	@Override
	public void insert(VendasProduto obj) {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("insert into venda "
					+ "(dataVenda, codigoVenda, quantidade) "
					+ "values "
					+ "( ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);			
						
			st.setDate(1, new java.sql.Date((obj.getDataVenda().getTime())));
			st.setInt(2, obj.getProd().getCodigo());
			st.setInt(3, obj.getQtd());
			
				
			int row = st.executeUpdate();
			
			if(row > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
					
					DB.closeResultSet(rs);
					
				}else {
					throw new DbException("Erro ao realizar a inserção!");
				}
			}
		
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
		
	}

	@Override
	public void update(VendasProduto obj) {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("update venda set quantidade = quantidade + ? where id = ?");			
			st.setInt(1, obj.getQtd());
			st.setInt(2, obj.getId());
			
			st.executeUpdate();
			
		}catch(SQLException e) {
			
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}		
	}
	
	

	@Override
	public void delete(VendasProduto id) {
		PreparedStatement st = null;
		try {
			
			st = conn.prepareStatement("delete from venda where id = ?");
			
			st.setInt(1, id.getId()); 
			st.executeUpdate();
			
		}catch( SQLException e) {
			throw new DbException(e.getMessage());
		}
		
	}

	@Override
	public VendasProduto findById(String data) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT venda.*,produto.nome as VendaNome, produto.preco as VendaPreco "
					+ "FROM venda INNER JOIN produto "
					+ "ON venda.codigoVenda = produto.codigo "
					+ "WHERE dataVenda = ?");
			
			st.setString(1, data);
			rs = st.executeQuery();
			if(rs.next()) {
				Produto prod = produtoRest(rs);
				VendasProduto venda = vendaRest(rs, prod);				
				return venda;
			}			
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		
		return null;
	}
	
//	@Override
//	public List<VendasProduto> findAll() {
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		
//		try {
//			
//			st = conn.prepareStatement("select * from venda");
//			rs = st.executeQuery();
//			
//			List<VendasProduto> listVenda = new ArrayList<>();
//			while(rs.next()) {				
//				VendasProduto venda = new VendasProduto();
//				Produto prod = new Produto();
//				
//				venda.setId(rs.getInt("id"));
//				venda.setDataVenda(new java.util.Date(rs.getTimestamp("dataVenda").getTime()));
//				venda.setQtd(rs.getInt("quantidade"));
//				venda.setProd(prod);			
//				
//				
//				listVenda.add(venda);
//				
//			}			
//			return listVenda;			
//			
//		}catch(SQLException e) {
//			throw new DbException(e.getMessage());
//		}
//		finally {
//			DB.closeStatement(st);
//			DB.closeResultSet(rs);
//		}
//		
//	}
//	
	
	@Override
	public List<VendasProduto> findAll(String data) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT venda.*,produto.nome as VendaNome, produto.preco as VendaPreco "
					+ "FROM venda INNER JOIN produto "
					+ "ON venda.codigoVenda = produto.codigo "
					+ "WHERE dataVenda = ?");
			
			st.setString(1, data);
			rs = st.executeQuery();
			List<VendasProduto> listVenda = new ArrayList<>();
			while(rs.next()) {				
				VendasProduto venda = new VendasProduto();
				Produto prod = new Produto();
				
				venda.setId(rs.getInt("id"));
				venda.setDataVenda(new java.util.Date(rs.getTimestamp("dataVenda").getTime()));
				venda.setQtd(rs.getInt("quantidade"));
				venda.setNome(rs.getString("VendaNome"));
				venda.setCodigo(rs.getInt("codigoVenda"));
				venda.setProd(prod);			
				
				
				listVenda.add(venda);
				
			}			
			return listVenda;			
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}
	
	private VendasProduto vendaRest(ResultSet rs, Produto prod) throws SQLException {
		VendasProduto venda = new VendasProduto();
		
		venda.setId(rs.getInt("id"));
		venda.setDataVenda(new java.util.Date(rs.getTimestamp("dataVenda").getTime()));
		venda.setQtd(rs.getInt("quantidade"));
		venda.setProd(prod);		
		return venda;
		
	}
	
	private Produto produtoRest(ResultSet rs) throws SQLException {
		Produto prod = new Produto();		
		
		prod.setNome(rs.getString("VendaNome"));
		prod.setPreco(rs.getDouble("VendaPreco"));
		
		return prod;
	}

}
