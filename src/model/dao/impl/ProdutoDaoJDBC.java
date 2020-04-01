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
import model.dao.ProdutoDao;
import model.entities.Produto;

public class ProdutoDaoJDBC implements ProdutoDao {
	
	//nesse bloco estamos criando uma injeção de dependência.
	private Connection conn;
	
	public ProdutoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	//fim do bloco de injeção. 

	@Override
	public void insert(Produto obj) {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("insert into produto "
					+ "(nome, preco, quantidade) "
					+ "values "
					+ "(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
						
			st.setString(1, obj.getNome());
			st.setDouble(2, obj.getPreco());
			st.setInt(3, obj.getQtd());
						
			int row = st.executeUpdate();
			
			if(row > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setCodigo(id);
					
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
	public void update(Produto obj) {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("update produto set preco = ?, quantidade = ? where nome = ?;");			
			st.setDouble(1, obj.getPreco());
			st.setInt(2, obj.getQtd());
			st.setString(3, obj.getNome());
			
			st.executeUpdate();
			
		}catch(SQLException e) {
			
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}		
	}
	
	

	@Override
	public void delete(Produto codigo) {
		PreparedStatement st = null;
		try {
			
			st = conn.prepareStatement("delete from produto where codigo = ?");
			
			st.setInt(1, codigo.getCodigo());
			st.executeUpdate();
			
		}catch( SQLException e) {
			throw new DbException(e.getMessage());
		}
		
	}

	@Override
	public Produto findById(Integer codigo) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select * from produto "
					+ "where "
					+ "produto.codigo "
					+ "= ?");
			
			st.setInt(1, codigo);
			rs = st.executeQuery();
			if(rs.next()) {
				Produto prod = produtoRest(rs);				
				return prod;
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
	
	@Override
	public List<Produto> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("select * from produto");
			rs = st.executeQuery();
			
			List<Produto> prod = new ArrayList<>();
			while(rs.next()) {				
				Produto p = new Produto();
				p.setCodigo(rs.getInt("codigo"));
				p.setNome(rs.getString("nome"));
				p.setPreco(rs.getDouble("preco"));
				p.setQtd(rs.getInt("quantidade"));
				
				prod.add(p);
				
			}			
			return prod;			
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}
	
	
	private Produto produtoRest(ResultSet rs) throws SQLException {
		Produto prod = new Produto();
		prod.setCodigo(rs.getInt("codigo"));
		prod.setNome(rs.getString("nome"));
		prod.setPreco(rs.getDouble("preco"));
		prod.setQtd(rs.getInt("quantidade"));
		return prod;
	}

}
