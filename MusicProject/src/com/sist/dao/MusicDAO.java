package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;
import java.sql.*;
public class MusicDAO {
	private Connection conn;
	// 입출력 => InputStream , OutputStream
	private PreparedStatement ps;
	//URL 주소
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	// 드라이버 등록 => 한번 수행
	public MusicDAO()
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
	//기능
	//순위, state, idincrement, poster, 타이틀, 가수명,album
	public ArrayList<MusicVO> musicListData(int page){
		ArrayList<MusicVO> list = new ArrayList<MusicVO>();
//		private int mno;
//		private int rank;
//		private String title;
//		private String Singer;
//		private String album;
//		private String poster;
//		private int idcrement;
//		private String state;
//		private String key;
		
		try {
			getConnection();
			String sql = "SELECT rank,state,idcrement,poster,title,singer,album "
						+ "FROM music_genie "
						+ "ORDER BY rank ASC";
			ps = conn.prepareStatement(sql);	
			ResultSet rs = ps.executeQuery();
			
			int rowSize = 50;
			int pageStart = (page*rowSize)-rowSize;
			
			int i=0;   //50개씩 나누기
			int j=0;	//처음부터 출력 
			
			while(rs.next()){
				if(i<rowSize && j>=pageStart){
					MusicVO vo = new MusicVO();
					vo.setRank(rs.getInt(1));
					vo.setState(rs.getString(2));
					vo.setIdcrement(rs.getInt(3));
					vo.setPoster(rs.getString(4));
					vo.setTitle(rs.getString(5));
					vo.setSinger(rs.getString(6));
					vo.setAlbum(rs.getString(7));
					list.add(vo);
					i++;
				}
				j++;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		return list;
	}
	
	public int musicTotalPage(){
		int total=0;
		try {
			getConnection();
			String sql="SELECT CEIL(COUNT(*)/50.0) " // 전체 양에서 페이지마다 숫자를 나누고, 그 값을 올림값으로 가져온다. 
						+"FROM music_genie";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			total = rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}finally{
			disConnection();
		}
		return total;
	}
	// rank,state,idcrement,title,singer,poster,key
	
	public MusicVO musicDetailData(int no){
		MusicVO vo = new MusicVO();
		
		try {
			getConnection();
			String sql = "SELECT rank,state,idcrement,title,singer,poster,key "
						+"FROM music_genie "
						+"WHERE rank=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			vo.setRank(rs.getInt(1));
			vo.setState(rs.getString(2));
			vo.setIdcrement(rs.getInt(3));
			vo.setTitle(rs.getString(4));
			vo.setSinger(rs.getString(5));
			vo.setPoster(rs.getString(6));
			vo.setKey(rs.getString(7));
			rs.close();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}finally{
			disConnection();
		}
		return vo;
	}
	
	
}
