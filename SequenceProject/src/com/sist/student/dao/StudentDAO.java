package com.sist.student.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;
import java.sql.*;
	
public class StudentDAO {
	private Connection conn;
	// 입출력 => InputStream , OutputStream
	private PreparedStatement ps;
	//URL 주소
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	// 드라이버 등록 => 한번 수행
	public StudentDAO()
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
	
	public ArrayList<StudentVO> stdAlldata(int page){
		ArrayList<StudentVO> list = new ArrayList<StudentVO>();
		try {
			getConnection();
			String sql ="SELECT hakbun,name,kor,eng,math "
						+"FROM student "
						+"ORDER BY 1";  //첫번쨰 컬럼으로 정렬
			/*
			 * ORACLE Paging !!
			 * String sql ="SELECT hakbun,name,kor,eng,math,num "
						+"FROM (SELECT hakbun,name,kor,eng,math,rownum as num "
						+"FROM (SELECT hakbun,name,kor,eng,math "
						+"FROM student ORDER by 1)) "
						+"WHERE num BETWEEN 1 AND 10";
			 * TOP N 
			 * INLINEVIEW
			 * SUBQUERY
			 * 스칼라 서브 쿼리
			 * 
			 * =======================
			 * 
			 * 
			 */
			
			
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			int i=0;
			int j=0;
			int pageStart=(page*10)-10;  //0,10,20,30 ....
			
			while(rs.next()){
				StudentVO vo = new StudentVO();
//				vo.setHakbun(rs.getInt(1));
//				vo.setName(rs.getString(2));
//				vo.setKor(rs.getInt(3));
//				vo.setEng(rs.getInt(4));
//				vo.setMath(rs.getInt(5));

				//mybatis 컬럼 이름으로 코딩. 순서
				if(i<10 && j>=pageStart){
					vo.setHakbun(rs.getInt("hakbun"));
					vo.setName(rs.getString("name"));
					vo.setKor(rs.getInt("kor"));
					vo.setEng(rs.getInt("eng"));
					vo.setMath(rs.getInt("math"));
					list.add(vo);
					i++;
				}	
				j++;
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
	
	public void stdInsert(StudentVO vo){
		try {
			getConnection();
			String sql="INSERT INTO student(hakbun,name,kor,eng,math,sex) "
					+"VALUES(std_hakbun_seq.nextval,?,?,?,?,?)";							//primary key 로 생각 NOT NULL, UNIQUE 
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setInt(2, vo.getKor());
			ps.setInt(3, vo.getEng());
			ps.setInt(4, vo.getMath());
			ps.setString(5, vo.getSex());
			ps.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			disConnection();
		}
	}
	
	public int stdRowCount(){
		int count=0;
		try {
			getConnection();
			String sql="SELECT COUNT(*) FROM student";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			count=rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			disConnection();
		}
		return count;
	}
	
	
	public void stdDelete(int hakbun){
		try {
			getConnection();
			String sql= "DELETE FROM student WHERE hakbun=?";					
			ps=conn.prepareStatement(sql);
			ps.setInt(1, hakbun);
			ps.executeQuery();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			disConnection();
		}
	}
	
}

