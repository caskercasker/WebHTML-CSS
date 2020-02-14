package com.sist.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.dao.*;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=\"css/bootstrap.min.css\">");   //하나의 문자열로 묶어줌, .을 공백으로 인식하는 HTML 의 특성?
		
		out.println("<style type=text/css>");
		out.println(".row {");
		out.println("margin: auto;");  //가운데 정렬 
		out.println("width:350px");
		out.println("}");
		out.println("h2 {");
		out.println("text-align:center");
		out.println("}");
		out.println("</style>");
		out.println("</head>");
		
		out.println("<body>");
		out.println("<div class=container>"); //container, container-fluid
		out.println("<h2>Login</h2>");
		
		out.println("<div class=row>");
		
		out.println("<form method=post action=Login>"); //Login=>doPost
		
		out.println("<table class=\"table table-hover\">");
		out.println("<tr>");
		out.println("<td width=20% class=text-right>ID</td>");
		out.println("<td width=80% class=text-right><input type=text name=id size=15></td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=20% class=text-right>password</td>");
		out.println("<td width=80% class=text-right><input type=password name=pwd size=15></td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td colspan=2 class=text-center><input type=submit class=\"btn btn-sm btn-Success\" value=로그인></td>");
		out.println("</tr>");
		
		out.println("</table>");
		out.println("</form>");
		out.println("</div>");   //.row
		out.println("</div>");   //.container
		out.println("</body>");
		out.println("</html>");
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		//HTML => CSS, JavaScript
		MusicDAO dao=new MusicDAO();
		// 사용자가 보내준 ID,PWD 
		String id=request.getParameter("id");    //name 속성
		String pwd=request.getParameter("pwd");
		
		String result=dao.isLogin(id, pwd);
		
		if(result.equals("NOID")){
			out.println("<script>");
			out.println("alert(\"ID가 존재하지 않는다\");");
			out.println("history.back()");
			out.println("</script>");
		}else if(result.equals("NOPWD")){

			out.println("<script>");
			out.println("alert(\"비밀번호가 틀립니다\");");
			out.println("history.back()");
			out.println("</script>");
		}else{
			HttpSession session = request.getSession();  //전역변수 형태, Static
			// id,name
			session.setAttribute("id", id);
			session.setAttribute("name", result);
			//(중복없는 키와, 값으로 이루어진 컬렉션) MAP 
			//session, request, response.
			
			//로그인/브라우저 종료/30분후 세션에 있는 데이터는 날아간다. => 은행 로그인 연장 시스템
			//세션에 저장을 해두고, 지속적사용하고 사용한다, 메모리에 있던 데이터를 활용
			//세션 서버에 저장, 쿠키는 본인 컴퓨터에 저장. 
			//세션용 컬럼하나가 더 필요하다?????
			
			//tomcat 의 역할 가상머신/ request,response에 값을 실어서 보낸다. 	
			
			//파일 이동
			response.sendRedirect("MusicList");
			
			//doget,dopost 콜백함수.. 톰캣에 의해서 
			
			
			//예약 기능 세션
			//장바구니, 위시리스트
		}
	}

}
