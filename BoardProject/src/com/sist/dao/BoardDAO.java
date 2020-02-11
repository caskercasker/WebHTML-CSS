package com.sist.dao;
import java.util.*; 	//ArrayList
import java.sql.*;		//Connection,PreparedStatement,ResultSet

/*
 * 	Connection => 오라클 연결
 * 	PreparedStatement => SQL 문장 전송
 *  ResultSet => 결과값 저장  =================> SELECT 데이터 분석호 파일이 필요할떄 
 *  	= ResultSet executeQuery("SQL") ==> SELECT
 *  	= executeUpdate("SQL") => INSERT,UPDATE,DELETE
 *  			=>  COMMIT 첨부
 *  	JDBC (java-oracle) 	=> DBCP  =>  ORM   => JPA
 *  	====				   ====		 ===	  ===
 *  									 MyBatis,Hibernate
 *  
 *  	==================================
 *  	1. 데이터 저장소		==> Oracle
 *  	2. 데이터 수집
 *  	==================================
 *  	3. 자바로 연동			==> Java==================> 관리하는 프로그램(Spring)
 *  	==================================
		4. HTML 로 화면 제작
		5. HTML 데이터 출력		==> HTML
		6. CSS				==> CSS
		7. JavaScript		==> 이벤트 헨들러
 */
public class BoardDAO {
	
	private Connection conn;
	private PreparedStatement ps;
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	//드라이버 등록
	public BoardDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
	//연결
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL,"hr","happy");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	//연결 해제 
	public void disConnection() {	
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	//게시판 목록 읽기 		====> SELECT ~ ORDER BY
	
	public ArrayList<BoardVO> boardListData(int page){
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		try {
			getConnection();
			String sql ="SELECT no,subject,name,regdate,hit "
					+"FROM board "
					+"ORDER BY no";
			int rowSize=10;
			/*
			 * 	1page : 0~9
			 *  2page : 10~19
			 *   
			 */
			 int i=0;	//10개씩 나눠주는 변수
			 int j=0;	//while 돌아가는 횟수
			 int pageStart = (page*rowSize)-rowSize; //row 시작 위치 값 을 가져오기 위해서 출력위치를 잡음 
			
			 ps = conn.prepareStatement(sql);	
			 ResultSet rs = ps.executeQuery();
			 
			 while(rs.next()) {
				 if(i<10 && j>=pageStart) {
					 BoardVO vo = new BoardVO();
					 vo.setNo(rs.getInt(1));
					 vo.setSubject(rs.getString(2));
					 vo.setName(rs.getString(3));
					 vo.setRegedate(rs.getDate(4));
					 vo.setHit(rs.getInt(5));
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
	
	public int boardTotalPage() {
		int total = 0;
		try {
			getConnection();
			String sql="SELECT CEIL(COUNT(*)/10.0) FROM board";  // 한페이지에 출력할 숫자로 나눠서 숫자를 가져옴 CEIL 올림
			ps=conn.prepareStatement(sql); //오라클 전송
			//실행명령
			ResultSet rs = ps.executeQuery();
			rs.next();
			total=rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		return total;
	}
	//게시판 => 내용보기		====> SELECT ~ WHERE
	
	public BoardVO boardDetailData(int no) {
		BoardVO vo = new BoardVO();
		
		try {
			getConnection();
			String sql="UPDATE board SET "
						+"hit=hit+1 "
						+"WHERE no=?";
			ps=conn.prepareStatement(sql);
			//?에 값을 채운다.
			
			ps.setInt(1, no);
			//실행요청
			ps.executeUpdate(); //COMMIT    AUTO COMMIT 자동처리를 막아야 트랜잭션에서 오류가 발생했을 때를 처리할 수 있다. 
			//상세보기
			sql="SELECT no,name,subject,content,regdate,hit "
					+"FROM board "
					+"WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1,no);
			
			ResultSet rs=ps.executeQuery();
			rs.next();
			
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setRegedate(rs.getDate(5));
			vo.setHit(rs.getInt(6));
			
			rs.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		return vo;
	}
	//추가하기				====> INSERT
	//수정하기				====> UPDATE
	//삭제하기				====> DELETE
	//찾기				====> SELECT  ~ LIKE


}
