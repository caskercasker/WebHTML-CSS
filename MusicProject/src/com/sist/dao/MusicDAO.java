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
			String sql="SELECT CEIL(COUNT(*)/50.0) "
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
	
	// 로그인
	public String isLogin(String id, String pwd){
		String result="";
		
		try {
			getConnection();
			String sql="SELECT COUNT(*) FROM music_member "
						+"WHERE id=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int count=rs.getInt(1);
			rs.close();
			
			if(count==0){
				result="NOID";					
			}else{
				sql="SELECT pwd,name FROM music_member "
						+"WHERE id=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, id);
				rs = ps.executeQuery();
				rs.next();
				String db_pwd=rs.getString(1);
				String name=rs.getString(2);
				rs.close();
				
				if(db_pwd.equals(pwd)){
					result=name;
				}else{
					result="NOPWD";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			disConnection();
		}
		
		return result;
	}
	// 댓글쓰기		INSERT
	// 댓글 수정 		UPDATE
	// 댓글 삭제		DELETE
	// 댓글 보기		SELECT => WHERE 
}
