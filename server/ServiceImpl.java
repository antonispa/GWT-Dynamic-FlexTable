package com.fishes.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fishes.client.DeleteFish;
import com.fishes.client.FishTable;
import com.fishes.client.InsertFish;
import com.fishes.client.Login;
import com.fishes.client.Service;
import com.fishes.client.UpdateFish;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ServiceImpl extends RemoteServiceServlet implements Service {
	
	private String user = "root";
	private String pass = "root";
	private String host = "localhost";
	private String port = "3306";
	private String database = "Fishes";
	private String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=utf8";
	private String query;

	private PreparedStatement ps = null;
	private Connection conn = null;
	private ResultSet rs;
	
	private int i = 0;
	
	private String[] idArray;
	private String[] nameArray;
	private String[] priceArray;
	
	
	public Login checkLoginData(String username, String password) {
		
		Login login = new Login();
		
		query = "select * from Users where username=? and password=?";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url , user , pass);
			ps = conn.prepareStatement(query);
			
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			
			if(rs.next()){
				login.setLoginCount(1);
			}
			
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException se){
			se.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e) {

				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {

				}
			}
		}
		
		return login;	
	}

	public FishTable getFishes() {
		
		FishTable fishTable = new FishTable();

		query = "select * from Fishes";

		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url , user , pass);
			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			
			int rowCount = 0;
			
			while(rs.next()){
				rowCount++;
			}
			
			idArray = new String[rowCount];
			nameArray = new String[rowCount];
			priceArray = new String[rowCount];
			
			try {
				rs.beforeFirst();
			} catch(SQLException e){
				e.printStackTrace();
			} 
			
			
			while(rs.next()){
				
				idArray[i] = rs.getString(1);
				nameArray[i] = rs.getString(2);
				priceArray[i] = rs.getString(3);
				
				i++;
			}
			
			fishTable.setIdArray(idArray);
			fishTable.setNameArray(nameArray);
			fishTable.setPriceArray(priceArray);
			
			i = 0;
			
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException se){
			se.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e) {

				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {

				}
			}
		}
		
		return fishTable;
	}

	public InsertFish insertFish(String fishId , String fishName , String fishPrice) {
		
		InsertFish insertFish = new InsertFish();
		
		query = "insert into Fishes values (?,?,?)";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url , user , pass);
			ps = conn.prepareStatement(query);
			
			ps.setString(1, fishId);
			ps.setString(2, fishName);
			ps.setString(3, fishPrice);
			ps.executeUpdate();	
			
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException se){
			se.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e) {

				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {

				}
			}
		}

		return insertFish;
	}

	public DeleteFish deleteFish (String fishId) {
		
		DeleteFish deleteFish = new DeleteFish();
		
		query = "delete from Fishes where id=?";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url , user , pass);
			ps = conn.prepareStatement(query);
			
			ps.setString(1, fishId);
			ps.executeUpdate();	
			
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException se){
			se.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e) {

				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {

				}
			}
		}
		
		return deleteFish;
	}

	public UpdateFish updateFish(String fishId , String fishName , String fishPrice) {
		
		UpdateFish updateFish = new UpdateFish();
		
		query = "update Fishes set name=?,price=? where id=?";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url , user , pass);
			ps = conn.prepareStatement(query);
			
			ps.setString(1, fishName);
			ps.setString(2, fishPrice);
			ps.setString(3, fishId);
			ps.executeUpdate();	
			
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException se){
			se.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e) {

				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {

				}
			}
		}
		
		return updateFish;
	}
	
}
