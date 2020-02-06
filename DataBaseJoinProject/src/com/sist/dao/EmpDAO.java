package com.sist.dao;
//오라클 연결 => 데이터베이스 프로그램 ==> 단위 RECORD (RECORD => Column 여러개를 묶어준것)
// => bit => byte => word => field => record => file => database
// => bit => byte => word => record(VO,DTO,Bean) => table
// 					Column값
/*					일반데이터형 클래스 => ArrayList
 * 	class A{
 * 		int aa;
 * }
 * 
 * for(int i=0;i<10;i++{
 * 	A a = new A();
 * 	a.aa = i;
 * } //메모리에 여러개가 올라감
 * 
 * for(int i=0;i<10;i++{
 * 	int a=i; 	
 * } //메모리에 한개만 올라감.
 */
import java.util.*;
import java.sql.*;
public class EmpDAO {
	// 연결도구 Connection (Socket)
		private Connection conn;
		// 입출력 => InputStream , OutputStream
		private PreparedStatement ps;
		//URL 주소
		private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
		
		// 드라이버 등록 => 한번 수행
		public EmpDAO()
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

		//기능 -> join
		public ArrayList<EmpVO> empJoinData(){
			ArrayList<EmpVO> list = new ArrayList<EmpVO>();
			
			try {
				getConnection();
				//sql 문장
				String sql="SELECT empno,ename,job,hiredate,sal,dname,loc,grade " // String 에서 oracle 처럼 KeyWOrd를 구분하지 못한다. 공백을 주의해서 주자
							+"FROM emp,dept,salgrade "
							+"WHERE emp.deptno=dept.deptno "
							+"AND sal BETWEEN losal AND hisal";
				//전송
				ps=conn.prepareStatement(sql);
				//실행요청
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					EmpVO vo = new EmpVO(); //14개 생성
					vo.setEmpno(rs.getInt(1));
					vo.setEname(rs.getString(2));
					vo.setJob(rs.getString(3));
					vo.setHiredate(rs.getDate(4));
					vo.setSal(rs.getInt(5));
					vo.getDvo().setDname(rs.getNString(6));
					vo.getDvo().setLoc(rs.getString(7));
					vo.getSvo().setGrade(rs.getInt(8));
					list.add(vo);
								
				}
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			finally {
				disConnection();
			}
			return list;
		}

}
