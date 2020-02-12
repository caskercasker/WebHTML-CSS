package com.sist.board;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.dao.*;
/**
 * Servlet implementation class BoardUpdate
 */
@WebServlet("/BoardUpdate")
public class BoardUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		
		//사용자 요청한 번호를 받는다.

		String no=request.getParameter("no");
		BoardDAO dao = new BoardDAO();
		BoardVO vo=dao.boardUpdateData(Integer.parseInt(no));
		
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>"); //html 5.0 이면 구분 짓기 위해 사용
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=\"css/table.css\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>수정하기</h1>");
		
		out.println("<form method=post action=BoardUpdate>"); //form tag 안에 있는 방식을 한번에 전송
		
		// 필수 입력에 대한 예외처리  ex>DB 설계에 NOT NULL 이면 NVL으로 0값을 넣어주던가/배포가 가능하지 않도록 한다. 
		
		
		
		//BoardInsert 에 메소드 post 를 하라  ==> dopost  실행, 데이터를 받아서 처리
		//get 받은 데이터를 화면에 출력 get
		
		//! JSP에는 dopost,doget 을 처리하기 위해 파일을 2개 써야 한다. 
		
		out.println("<table id=\"table_content\"width=500>");
		out.println("<tr>");

		
		out.println("<th width=15% align=right>이름</th>");
		out.println("<td width=85%>");
		out.println("<input type=text name=name size=15 required value=\""+vo.getName()+"\">");

		out.println("<input type=hidden name=no value="+vo.getNo()+">");
		//HTML 코드가 form 으로 보낼때 저장되어 있지 않게되면 값을 활용하지 못하기 때문에 따로 저장해 두어야 한다.
		//따라서 HTML에 
		
		out.println("</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<th width=15% align=right>제목</th>");
		out.println("<td width=85%>");
		out.println("<input type=text name=subject size=50 required value=\""+vo.getSubject()+"\">"); // 공백 처리하기 위해 ""로 value 값을 문자로 묶음
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<th width=15% align=right valign=top>내용</th>");   //vr Vertical Align top/middel/center
		out.println("<td width=85%>");
		out.println("<textarea rows=8 cols=55 name=content required>"+vo.getContent()+"</textarea>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<th width=15% align=right>비밀번호</th>");
		out.println("<td width=85%>");
		out.println("<input type=password name=pwd size=10 required ");
		out.println("</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td colspan=2 align=center>");
		out.println("<input type=submit value=수정하기>");
		out.println("<input type=button value=취소 onclick=\"javascript:history.back()\">");   //on 이벤트 메소드 호출 onclick
		out.println("</td>");
		out.println("</tr>");
		
		out.println("</table>");
		
		out.println("</form>");					//				<===== form으로 감싼다. 
		
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		//사용자가 보내준 값을 저장
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
		
		String no = request.getParameter("no");
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String pwd = request.getParameter("pwd");
		
		BoardVO vo = new BoardVO();
		vo.setNo(Integer.parseInt(no));
		vo.setName(name);
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setPwd(pwd);
		
		BoardDAO dao = new BoardDAO();
		boolean bCheck=dao.boardUpdate(vo);
		//오라클 연동 -> DAO의 메소드를 호출
		
		if(bCheck==true) {
			response.sendRedirect("BoardDetailServlet?no="+no);
			//이동 ==> sendRedirect 보드 리스트 서블릿으로 이동 .
		}else {
			out.println("<html>");
			out.println("<head>");
			out.println("<script type=\"text/javascript\">");
			out.println("alert(\"비밀번호가 틀립니다!!\");");
			out.println("history.back()");			
			out.println("</script>");
			out.println("</head>");
			out.println("</html>");
			
		}
		//저장된 값 => DAO 
		//처리

		
		
		
		//이동 ==> 상세보기 이동
		
	}

}
