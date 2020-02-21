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
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
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
//			//Context c=(Context)init.lookup("java://comp//env"); 
//			DataSource dsp=(DataSource)c.lookup("java://comp//env//jdbc/oracle");   //경로 설정 가능 / 
//			
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
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
	
public void FoodHouseInsert(FoodHouseVO vo){
		
		
		try {
			getConnection();
			String sql="INSERT INTO FoodHouse VALUES("
					+"foodhouse_no_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,'none')";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo.getCno());
			ps.setString(2, vo.getTitle());
			ps.setDouble(3, vo.getScore());
			ps.setString(4, vo.getAddress());
			ps.setString(5, vo.getTel());
			ps.setString(6, vo.getType());
			ps.setString(7, vo.getPrice());
			ps.setString(8, vo.getImage());
			
			ps.setInt(9, vo.getGood());
			ps.setInt(10, vo.getSoso());
			ps.setInt(11, vo.getBad());
			
			ps.executeUpdate();
			
					
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			disConnection();
		}
	}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public ArrayList<CategoryVO> categoryAllData(){								//메인화면 카테고리들 데이터 	1층
		ArrayList<CategoryVO> list = new ArrayList<CategoryVO>();
		try {
			getConnection();
			String sql = "SELECT cateno,title,subject,poster "
						+"FROM category "
						+"ORDER BY cateno ASC";
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
	

	
	public ArrayList<FoodHouseVO> foodHouseListData(int cno){								 //카테고리에 해당하는 데이터 뿌리기 2층 상세 데이터
		//2층에 해당하는 DB 생성. 
		ArrayList<FoodHouseVO> list = new ArrayList<FoodHouseVO>();
		try {
			getConnection();
			//SQL 문장 전송
			/* 
			 * JDBC/DBCP
			 * 1) 연결
			 * 2) SQL 전송
			 * 3) 결과값 받기
			 * 4) 닫기
			 */
			String sql="SELECT image,title,score,address,no " //클릭 시 no로 이동.
					+"FROM foodhouse "
					+"WHERE cno=?";
			
			ps=conn.prepareStatement(sql);
			ps.setInt(1, cno);
			//결과값 받기 
			ResultSet rs=ps.executeQuery();
			while(rs.next()){	
				FoodHouseVO vo = new FoodHouseVO();
				String img=rs.getString(1);
				vo.setImage(img.substring(0,img.indexOf("^")));  //이미지 구분점 ^
				vo.setTitle(rs.getString(2));
				vo.setScore(rs.getDouble(3));
				vo.setAddress(rs.getString(4));
				vo.setNo(rs.getInt(5));
				
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
	
	// category 데이터 
	public CategoryVO categoryInfoData(int cno){			
		CategoryVO vo = new CategoryVO();
		try {
			getConnection();
			String sql="SELECT title,subject "
						+"FROM category "
						+"WHERE cateno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, cno);
			
			ResultSet rs= ps.executeQuery();
			rs.next();
			vo.setTitle(rs.getString(1));
			vo.setSubject(rs.getString(2));
			
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			disConnection();
		}
		return vo;
	}
	
	// 상세보기
	public FoodHouseVO foodDetailData(int no){
		FoodHouseVO vo = new FoodHouseVO();
		try {
			getConnection();
			String sql="SELECT image,title,score,address,tel,type,price,good,soso,bad "
						+"FROM foodhouse "
						+"WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
				vo.setImage(rs.getString(1));
				vo.setTitle(rs.getString(2));
				vo.setScore(rs.getDouble(3));
				vo.setAddress(rs.getString(4));
				vo.setTel(rs.getString(5));
				vo.setType(rs.getString(6));
				vo.setPrice(rs.getString(7));
				vo.setGood(rs.getInt(8));
				vo.setSoso(rs.getInt(9));
				vo.setBad(rs.getInt(10));
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			disConnection();
		}
		
		return vo;		
	}
	public ArrayList<FoodHouseVO> foodlocationData(String loc){
		//FoodHouseVO vo = new FoodHouseVO();
		ArrayList<FoodHouseVO> list = new ArrayList<FoodHouseVO>();
		try {
			getConnection();
			String sql="SELECT image,title,score,address,tel,type,price,rownum "
						+"FROM (SELECT image,title,score,address,tel,type,price "
						+"FROM foodhouse "
						+"WHERE address LIKE '%'||?||'%' "
						+"ORDER BY score DESC) "
						+"WHERE rownum<=5";    //인라인 뷰에서 평점으로 정렬된 데이터를 가지고 와서 테이블을 생성한다 (같은 동네)=> 정렬된 데이터에 rownum을 주고 5보다 작은 순서들을 출력한다.   INLINE VIEW
			
			//특정 컬럼을 지칭하기 위해서 가상 컬럼을 생성하고 정렬된 데이터를 만든뒤, BETWEEN AND 로 처리......
			ps = conn.prepareStatement(sql);
			ps.setString(1, loc);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				FoodHouseVO vo = new FoodHouseVO();
				vo.setImage(rs.getString(1));
				vo.setTitle(rs.getString(2));
				vo.setScore(rs.getDouble(3));
				vo.setAddress(rs.getString(4));
				vo.setTel(rs.getString(5));
				vo.setType(rs.getString(6));
				vo.setPrice(rs.getString(7));
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
}

