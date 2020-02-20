package com.sist.dao;
import java.util.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;
import com.sist.manager.*;
import com.sist.dao.*;

public class FoodDAO {
	private Connection conn;
	private PreparedStatement ps;
	private Statement stat;
	private static FoodDAO dao;
//	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
//	public FoodDAO(){
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println(e.getMessage());
//		}
//	}
	// <dataSource type="POOLED"?  ==> Jsoup, JAXP, JAXB, Simple-JSON,CSV	
	public void getConnection(){
		try {
			//conn=DriverManager.getConnection(URL,"hr","happy");
			//받아올 값
			//DataSource
			//conn 에 값을 넣어야함. 

			Context init=new InitialContext(); //JNDI(Java Naming Directory Interface)  인터페이스 가져옴(아무것도 없음)
			Context c=(Context)init.lookup("java://comp//env"); 
			DataSource ds=(DataSource)c.lookup("jdbc/oracle");
			conn=ds.getConnection();
			//Context c=(Context)init.lookup("java://comp//env"); 
			DataSource dsp=(DataSource)c.lookup("java://comp//env//jdbc/oracle");   //경로 설정 가능 / 
			
			} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void disConnection(){
		try {
			if(ps!=null)ps.close();
			if(conn!=null)conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// DAO를 각 사용자당 한개만 사용이 가능하게 만든다. 
	public static FoodDAO newInstance(){
		if(dao==null)
			dao=new FoodDAO();
		return dao;
	}
	
	public void CategoryInsert(CategoryVO vo){
		
		
		try {
			getConnection();
			String sql="INSERT INTO category VALUES( "
					+"?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo.getCateno());
			ps.setString(2, vo.getTitle());
			ps.setString(3, vo.getSubject());
			ps.setString(4,	vo.getPoster());
			ps.setString(5, vo.getLink());
			ps.executeUpdate();
			
					
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			disConnection();
		}
	}
	
	public ArrayList<CategoryVO> categoryAllData(){
		ArrayList<CategoryVO> list = new ArrayList<CategoryVO>();
		try {
			getConnection();
			String sql = "SELECT cateno,title,subject,poster "
						+"FROM category";
			ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				CategoryVO vo = new CategoryVO();
				vo.setCateno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setSubject(rs.getString(3));
				vo.setPoster(rs.getString(4));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			disConnection();
		}
				
		return list;
	}
	
}

