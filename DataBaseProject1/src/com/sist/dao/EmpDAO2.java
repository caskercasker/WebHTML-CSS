package com.sist.dao;
// 오라클 연동 ==> SQL (오라클실행) ==> Java 
/*
 * 	1.드라이버 등록
 * 	2.연결
 * 	3.해제
 * 	4.기능 설정 ==> SQL전송 , 결과값 받기
 * 				 ======
 * 	  DML
 * 		= SELECT : 데이터 검색
 * 			1) 형식
 * 				SELECT * | column1,column2...
 * 				FROM table명(SELECT,View)
 * 				[
 * 					WHERE 조건절 (컬럼명 연산자 값)
 * 						empno = 7788
 * 						ename LIKE '%A%'
 * 					GROUP BY 컬럼명(함수)
 * 					HAVING 그룹 조건 ==> 집합함수
 * 					ORDER BY (컬럼명,함수) ASC|DESC
 * 				]
 * 
 * 				= 연산자
 * 
 * 				1. 산술연산자 => 각 개인의 통계 (+,-,*,/(실수))
 * 				2. 비교연산자 => =,!=(<>,^=), <, >, <=, >=
 * 				3. 논리연산자 => AND, OR
 * 				4. 대입 연산자 => = (UPDATE)
 * 				5. BETWEEN ~AND => 페이지 나누기, 예약기간
 * 				6. IN => 포함된 데이터를 추출 ( 다중 조건) => checkbox
 * 				7. NuLL ==> 연산 처리중에 Null이 있는 경우에는 연산처리 불가능 
 * 							IS NULL, IS NOT NULL
 * 				8. NOT => 부정
 * 							NOT LIKE
 * 				= 내장 함수
 * 		= INSERT : 데이터 추가
 * 		= UPDATE : 데이터 수정
 * 		= DELETE : 데이터 삭제
 * 		
 * 		숫자(ROUND,TRUNC,CEIl 날짜 SYSDATE,MONTHS_BETWEEN, 일반함수 NVL 집함함수, COUNT, MAX, SUM,AVG, RANK,DENSE_RANK,CUBE,ROLLUP
 */
import java.util.*;
import java.sql.*;
public class EmpDAO2 {
	// 연결도구 Connection (Socket)
	private Connection conn;
	// 입출력 => InputStream , OutputStream
	private PreparedStatement ps;
	//URL 주소
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	// 드라이버 등록 => 한번 수행
	public EmpDAO2()
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
	
	// 기능 수행 1
	// SELECT => 전체 데이터 읽기
	public ArrayList<EmpVO> empAllData()
	{
		ArrayList<EmpVO> list=new ArrayList<EmpVO>();
		try
		{
			// 연결
			getConnection();
			// SQL문장 제장
			String sql="SELECT * "
					  +"FROM emp "
					 +"ORDER BY empno"; // 나중에 INDEX로 작성예정
			// 오라클 전송
			ps=conn.prepareStatement(sql);
			
			// 결과값 받기
			ResultSet rs=ps.executeQuery();  // ResultSet에 다 저장됨
				//오라클 테이블에서 커서위치를 어덯게 놓느냐에따라서 방향이 달라짐 
				// next로 첫번째->마지막 , previous로 마지막->첫번쨰  
			while(rs.next())
			{
				EmpVO vo=new EmpVO();
				vo.setEmpno(rs.getInt(1));
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setMgr(rs.getInt(4));
				vo.setHiredate(rs.getDate(5));
				vo.setSal(rs.getInt(6));
				vo.setComm(rs.getInt(7));
				vo.setDeptno(rs.getInt(8));
				// 출력받은 순서대로 작성해야함
				list.add(vo); // 14개 모아서 전송
			}
			rs.close();
			// list에 첨부
		}catch(Exception ex)
		{
			// 에러 처리
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
			// 연결 해제
		}
		return list;
	}
	// SELECT => 원하는 컬럼만 읽기
	// SELECT => WHERE 문장 이용
	public EmpVO empDetailData(int empno)
	{
		EmpVO vo=new EmpVO();
		try
		{
			// 연결
			getConnection();
			// SQL문장 제장
			String sql="SELECT * "
					  +"FROM emp "
					 // +"ORDER BY empno"; // 나중에 INDEX로 작성예정
					  +"WHERE empno=?";
			// 오라클 전송
			ps=conn.prepareStatement(sql);
			ps.setInt(1, empno);
			// 결과값 받기
			ResultSet rs=ps.executeQuery();  // ResultSet에 다 저장됨
				//오라클 테이블에서 커서위치를 어덯게 놓느냐에따라서 방향이 달라짐 
				// next로 첫번째->마지막 , previous로 마지막->첫번쨰  
			while(rs.next()) {
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setMgr(rs.getInt(4));
				vo.setHiredate(rs.getDate(5));
				vo.setSal(rs.getInt(6));
				vo.setComm(rs.getInt(7));
				vo.setDeptno(rs.getInt(8));
				// 출력받은 순서대로 작성해야함
			}
			
			rs.close();
			// list에 첨부
		}catch(Exception ex)
		{
			// 에러 처리
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
			// 연결 해제
		}
		return vo;
	}
	// SELECT => 연산자 사용 방법
	// SELECT => 함수 이용
	// SELECT => GROUP BY
	public ArrayList<EmpVO> empFindDate(String ename)
	{
		ArrayList<EmpVO> list=new ArrayList<EmpVO>();
		try
		{
			// 연결
			getConnection();
			// SQL문장 제장
			String sql="SELECT * "
					  +"FROM emp "
					 +"WHERE ename LIKE '%'||?||'%'"; // 나중에 INDEX로 작성예정
					// +"WHERE ename LIKE '%"+ename+"%'"; // 이것도 맞음
			// 오라클 전송
			ps=conn.prepareStatement(sql);
			ps.setString(1, ename);
			
			// 결과값 받기
			ResultSet rs=ps.executeQuery();  // ResultSet에 다 저장됨
				//오라클 테이블에서 커서위치를 어덯게 놓느냐에따라서 방향이 달라짐 
				// next로 첫번째->마지막 , previous로 마지막->첫번쨰  
			while(rs.next())
			{
				EmpVO vo=new EmpVO();
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setMgr(rs.getInt(4));
				vo.setHiredate(rs.getDate(5));
				vo.setSal(rs.getInt(6));
				vo.setComm(rs.getInt(7));
				vo.setDeptno(rs.getInt(8));
				list.add(vo);
			}
			rs.close();
			// list에 첨부
		}catch(Exception ex)
		{
			// 에러 처리
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
			// 연결 해제
		}
		return list;
	}
	public ArrayList<EmpVO> empRangeData(int year)
	{
		ArrayList<EmpVO> list=new ArrayList<EmpVO>();
		try
		{
			// 연결
			getConnection();
			// SQL문장 제장
			String sql="SELECT * "
					  +"FROM emp "
					 +"WHERE hiredate BETWEEN ? AND ?"; // 나중에 INDEX로 작성예정
			// 오라클 전송
			ps=conn.prepareStatement(sql);
			ps.setString(1, year+"/01/01");
			ps.setString(2, year+"/12/31");			
			// 결과값 받기
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				EmpVO vo=new EmpVO();
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setMgr(rs.getInt(4));
				vo.setHiredate(rs.getDate(5));
				vo.setSal(rs.getInt(6));
				vo.setComm(rs.getInt(7));
				vo.setDeptno(rs.getInt(8));
				// 출력받은 순서대로 작성해야함
				list.add(vo); // 14개 모아서 전송
			}
			rs.close();
			// list에 첨부
		}catch(Exception ex)
		{
			// 에러 처리
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
			// 연결 해제
		}
		return list;
	}
}
