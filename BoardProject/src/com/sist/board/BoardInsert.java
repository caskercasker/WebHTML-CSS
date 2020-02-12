package com.sist.board;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.BoardDAO;
import com.sist.dao.BoardVO;

@WebServlet("/BoardInsert")
public class BoardInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>"); //html 5.0 이면 구분 짓기 위해 사용
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=\"css/table.css\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>글쓰기</h1>");
		
		out.println("<form method=post action=BoardInsert>"); //form tag 안에 있는 방식을 한번에 전송
		
		// 필수 입력에 대한 예외처리  ex>DB 설계에 NOT NULL 이면 NVL으로 0값을 넣어주던가/배포가 가능하지 않도록 한다. 
		
		
		
		//BoardInsert 에 메소드 post 를 하라  ==> dopost  실행, 데이터를 받아서 처리
		//get 받은 데이터를 화면에 출력 get
		
		//! JSP에는 dopost,doget 을 처리하기 위해 파일을 2개 써야 한다. 
		
		out.println("<table id=\"table_content\"width=500>");
		out.println("<tr>");

		
		out.println("<th width=15% align=right>이름</th>");
		out.println("<td width=85%>");
		out.println("<input type=text name=name size=15 required>");
		out.println("</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<th width=15% align=right>제목</th>");
		out.println("<td width=85%>");
		out.println("<input type=text name=subject size=50 required>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<th width=15% align=right valign=top>내용</th>");   //vr Vertical Align top/middel/center
		out.println("<td width=85%>");
		out.println("<textarea rows=8 cols=55 name=content></textarea>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<th width=15% align=right>비밀번호</th>");
		out.println("<td width=85%>");
		out.println("<input type=password name=pwd size=10 required>");
		out.println("</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td colspan=2 align=center>");
		out.println("<input type=submit value=글쓰기>");
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
		
		
		try {
			request.setCharacterEncoding("UTF-8"); // 한글 변환  				===> 웹에서 입력된 파일 (HTML)을 웹 DB에 저장하기 위해 html을 보내야 하는데  여기서 한글이 깨질 것을 방지  
		} catch (Exception e) {}
			// TODO: handle exception

		//사용자가 보낸 데이터 받기 
		
			String name = request.getParameter("name");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			String pwd = request.getParameter("pwd");
			
//			System.out.println("n-"+name);
//			System.out.println("s-"+subject);
//			System.out.println("c-"+content);
//			System.out.println("p-"+pwd);
			
			BoardVO vo = new BoardVO();
			vo.setName(name);
			vo.setSubject(subject);
			vo.setContent(content);
			vo.setPwd(pwd);
			
			//오라클 연동 -> DAO의 메소드를 호출
			BoardDAO dao = new BoardDAO();
			dao.boardInsert(vo);
			response.sendRedirect("BoardListServlet");
			//이동 ==> sendRedirect 보드 리스트 서블릿으로 이동 . 
			
		
	}

}
