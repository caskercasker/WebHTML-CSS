package com.sist.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.*;

/**
 * Servlet implementation class MusicDetail
 */
@WebServlet("/MusicDetail")
public class MusicDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
				
		String mno = request.getParameter("mno");
		MusicDAO dao = new MusicDAO();
		MusicVO vo = dao.musicDetailData(Integer.parseInt(mno));
		
		
		// table 형태를 div로 바꿔야함. 
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=\"css/bootstrap.min.css\">");
		out.println("<style type=text/css>");
		out.println(".row{");
		out.println("margin: 0px auto;");
		out.println("width:800px");
		out.println("}");
		out.println("h1{");
		out.println("text-align:center;");
		out.println("}");
		out.println("</style>");
		out.println("</head>");
				
		out.println("<body>");
		out.println("<div class=container>");
		
		/*
		 * <select>
		 * 	WHERE no<10 => no&lt;10  &nbsp	< >     크기 비교 방식이 XML파싱시에 괄호와 연산자를 구분해서 가지지 못함.  
		 * </select>
		 * 
		 */
		out.println("<h1>&lt;"+vo.getTitle()+"&gt;상세보기</h1>");
		out.println("<div class=row>");
		out.println("<table class=\"table table-bordered\">");
		out.println("<tr>");
		out.println("<td colspan=2 class=text-center>");
		out.println("<embed src=\"https://youtube.com/embed/"+vo.getKey()+"\"width=100% height=350>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=10% class=text-right>");
		out.println("노래명");
		out.println("</td>");
		out.println("<td width=90% class=text-left>");
		out.println(vo.getTitle());
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=10% class=text-right>");
		out.println("가수명");
		out.println("</td>");
		out.println("<td width=90% class=text-left>");
		out.println(vo.getSinger());
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=10% class=text-right>");
		out.println("앨범명");
		out.println("</td>");
		out.println("<td width=90% class=text-left>");
		out.println(vo.getAlbum());
		out.println("</td>");
		out.println("</tr>");
		
		out.println("</table>");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
