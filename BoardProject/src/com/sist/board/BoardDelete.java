package com.sist.board;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.BoardDAO;
import com.sist.dao.BoardVO;


@WebServlet("/BoardDelete")
public class BoardDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String no =request.getParameter("no");
				
		out.println("<!DOCTYPE html>"); //html 5.0 이면 구분 짓기 위해 사용
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=\"css/table.css\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>삭제하기</h1>");
		
		out.println("<form method=post action=BoardDelete>"); //form tag 안에 있는 방식을 한번에 전송
		
		// 필수 입력에 대한 예외처리  ex>DB 설계에 NOT NULL 이면 NVL으로 0값을 넣어주던가/배포가 가능하지 않도록 한다. 
		
		
		
		//BoardInsert 에 메소드 post 를 하라  ==> dopost  실행, 데이터를 받아서 처리
		//get 받은 데이터를 화면에 출력 get
		
		//! JSP에는 dopost,doget 을 처리하기 위해 파일을 2개 써야 한다. 
		
		out.println("<table id=\"table_content\"width=300>");
		out.println("<tr>");
		out.println("<th width=35% align=right>비밀번호</th>");
		out.println("<td width=65%>");
		out.println("<input type=password name=pwd size=15 required>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td colspan=2 align=center>");
		out.println("<input type=submit value=삭제하기>");
		
		// submit => Input, select, textarea 입력한 값만 넘어간다. 
		out.println("<input type=hidden name=no value="+no+">");
		
		out.println("<input type=button value=취소 onclick=\"javascript:history.back()\">");   //on 이벤트 메소드 호출 onclick
		out.println("</td>");
		out.println("</tr>");
		
		
		out.println("</table>");
		out.println("</form>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");

		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String no = request.getParameter("no");
		String pwd = request.getParameter("pwd");
		
		BoardDAO dao = new BoardDAO();
		boolean bCheck=dao.boardDelete(Integer.parseInt(no), pwd);
		//오라클 연동 -> DAO의 메소드를 호출
		
		if(bCheck==true) {
			response.sendRedirect("BoardListServlet");

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
	}
	

}
