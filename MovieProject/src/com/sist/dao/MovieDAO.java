package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;

import com.sist.vo.MovieVo;
import com.sist.vo.NewsVO;

import java.sql.*;

public class MovieDAO{
	private Connection conn;
	// 입출력 => InputStream , OutputStream
	private PreparedStatement ps;
	//URL 주소
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private static MovieDAO dao;
	
	// 드라이버 등록 => 한번 수행
	public MovieDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	// 연결
	public void getConnection()
	{
		try 
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
			// conn hr/happy
		}catch (Exception ex) {}
	}
	//해제
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch (Exception ex) {}
	}

	//싱글턴 객체 하나만 사용 static으로 사용 
	// = 접속자마다 한개의 DAO만 사용할 수 있다. 
	/*
	 *  디자인 패턴 
	 *  ========
	 *    싱글턴 =====> Spring
	 *    팩토리 (여러개의 클래스 사용) 
	 *    MV (java,html)
	 *    MVC (java,jsp,html)
	 *    옵저버 (윈도우, 시스템이 감지 및 처리, 이동 
	 *    어뎁터 (어뎁터 class, 자동 형변환... --- generics 기능) POJO
	 * 	   프로토타입 (객체 복제, Class Clone, 메모리가 다른 복제 클래스 사용)
	 * 	  GOF 패턴
	 * 
	 * 
	 * 	 최적화의 일종 (메모리 누수를 줄임)
	 *   라이브러리에 맞춘 패턴들이 있다. 
	 *   
	 *   React
	 *   	React-Hooks
	 *   Redux
	 */
	public static MovieDAO newInstance(){
		if(dao==null)
			dao=new MovieDAO();
		return dao;
	}
	//저장
	public void movieInsert(MovieVo vo){
		
		
		try {
			getConnection();
			String sql="INSERT INTO movie VALUES( "
					+"(SELECT NVL(MAX(mno)+1,1) FROM movie), "
					+"?,?,?,?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getTitle());
			ps.setString(2, vo.getPoster());
			ps.setDouble(3, vo.getScore());
			ps.setString(4, vo.getGenre());
			ps.setString(5, vo.getRegdate());
			ps.setString(6, vo.getTime());
			ps.setString(7, vo.getGrade());
			ps.setString(8, vo.getDirector());
			ps.setString(9, vo.getActor());
			ps.setString(10, vo.getStory());
			ps.setInt(11, vo.getType());
			ps.executeUpdate();
			
					
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			disConnection();
		}
	}
	public ArrayList<MovieVo> test(int c){
		ArrayList<MovieVo>	list=new ArrayList<MovieVo>();
		try {
			getConnection();
			String sql="SELECT title,score "
						+"FROM movie "
						+"WHERE mno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, c);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				MovieVo vo = new MovieVo();
				vo.setTitle(rs.getString(1));
				vo.setScore(rs.getDouble(2));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			disConnection();
		}
		return list;
	}
	
	
	public ArrayList<MovieVo> movieListData(int page, int type){
		ArrayList<MovieVo>	list=new ArrayList<MovieVo>();
		try {
			getConnection();
			int rowSize = 12;
			int start=(page)*rowSize-(rowSize-1);	//rownum => 1
			//	 12-11 => 1 ~12
			//	 24-11 => 13~24
			// 	 sequence 생성시 오라클은 1부터 생성하기 때문에 1을 고려해야 한다. 
			int end=page*rowSize;
			
			String sql = "SELECT mno,title,poster,score,regdate,num "
						+"FROM (SELECT mno,title,poster,score,regdate,rownum as num "
						+"FROM (SELECT mno,title,poster,score,regdate "
						+"FROM movie WHERE type=? ORDER BY mno ASC)) "
						+"WHERE num BETWEEN ? AND ?";
				
			ps=conn.prepareStatement(sql);
			ps.setInt(1, type);
			ps.setInt(2, start);
			ps.setInt(3, end);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				MovieVo vo=new MovieVo();
				vo.setMno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setPoster(rs.getString(3));
				vo.setScore(rs.getDouble(4));
				vo.setRegdate(rs.getString(5));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			disConnection();
		}
		return list;
	}
	
	public void NewsInsert(NewsVO vo){
		
		
		try {
			getConnection();
			String sql="INSERT INTO news VALUES("
						+"?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getTitle());
			ps.setString(2, vo.getPoster());
			ps.setString(3, vo.getLink());
			ps.setString(4, vo.getAuthor());
			ps.setString(5, vo.getRegdate());
			ps.setString(6, vo.getContent());
			
			ps.executeUpdate();
			
					
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			disConnection();
		}
	}
}
