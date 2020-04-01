package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	private static Connection conn = null;
	
	// 1- carregar configurações do banco de dados.
		private static Properties loadPropiedade() {
			try(FileInputStream fs = new FileInputStream("db.propiedade")){
				Properties props = new Properties();
				props.load(fs);
				return props;
			}catch(IOException e) {
				throw new DbException(e.getMessage());
			}
		}	
	
	//2 - metodo para criar uma conecxão com o banco de dados
	public static Connection getConnection() {
		if(conn == null) {
			try {
				Properties props = loadPropiedade();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		
		return conn;
	}	
	
	//realiza o fechamento do statement
	public static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	
	//realiza o fechamento do ResultSet.
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	//Fechar conecxão com o banco. 
			public static void closeConnection() {
				if(conn != null) {
					try {
						conn.close();
					}catch(SQLException e) {
						throw new DbException(e.getMessage());
					}
				}	
				
			}
	
}
