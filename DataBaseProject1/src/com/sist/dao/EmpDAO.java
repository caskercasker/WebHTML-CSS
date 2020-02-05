package com.sist.dao;
// 오라클 연동/==> SQL(오라클 실행) ==> java 
// 연결과정 library 화 해서 사용 jar 파일 연결을 통해서 활용 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/*
 * 기능수행1
 * SELECT 전체데이터읽기
 * 컬럼
 * where
 * 연산자 사용
 * 함수 이용
 * GROUP BY 
 * Having (행을 모은 값들을 함수로 처리한 집합함수를 사용한 결과값을 사용 한다. )
 * 
 * 
 * 메모
 * ResultSet rs (empVO가 모인 Arraylist 가 query 를 통해 만들어진 형태 ) 
 * 		rs = ps.executequery();
 * 		Cursor의 위치를 기준으로 next를 통해 값을 접근한다?
 * SET ( 값이 동일하지 않은 형태만으로 접근 가능한 index가 없기 떄문에 query 를 기준으로 위치값을 활용한다?????
 * 
 * RS역시 collection 타입으로 append 와 next로 값 접근을 한다. 
 * while(boolean 값을 활용)
 * 테이블 구조에서 int형을 값을 넘길떄 0으로 들어와서 11g에서는 NVL을 사용할 필요가 없다. 
 * 
 */


import java.util.*;
public class EmpDAO {
	private Connection conn ;
	private PreparedStatement ps;	
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	//생성자 연결 
	public EmpDAO(){
		//연결 구문은 해제와 실행을 반복하여야 하기 때문에 
		//try catch실행 
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.getStackTrace();
			// TODO: handle exception
		}
	}
	
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}
	}
	//해제
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	
	
	public ArrayList<EmpVO> empSet()
	{
		
		ArrayList<EmpVO> list=new ArrayList<EmpVO>();
		try
		{
			getConnection();
			//sql 문 제작
			String sql="SELECT * "
					+ "FROM emp"; //전송
			ps=conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				EmpVO empValue = new EmpVO();
				empValue.setEmpno(rs.getInt(1));
				empValue.setEname(rs.getString(2));
				empValue.setJob(rs.getString(3));
				empValue.setMgr(rs.getInt(4));
				empValue.setHiredate(rs.getDate(5));
				empValue.setSal(rs.getInt(6));
				empValue.setComm(rs.getInt(7));
				empValue.setDeptno(rs.getInt(8));
			}
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return list;
	}
	
	//해볼일 입력값을 통해 검색결과를 출력하는 방식 코딩
	
//	Scanner scan = new Scanner(System.in);
//	int empno = scan.nextInt();
	//vo에서 empno와 같은 값을 화화여 출력한다. 
}
