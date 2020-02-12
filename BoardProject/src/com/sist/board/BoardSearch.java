package com.sist.board;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.BoardDAO;
import com.sist.dao.BoardVO;

@WebServlet("/BoardSearch")
public class BoardSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		//sQl 실행값을 실행하여 이  page 에다가 작성
		BoardDAO dao = new BoardDAO();
		
		String search = request.getParameter("search");
		String searchWord = request.getParameter("searchWord");
		
		ArrayList<BoardVO>	list=dao.boardSearchData(search,searchWord);
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=\"css/table.css\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>찾기 결과</h1>");
		
		out.println("<table id=\"table_content\"width=700>");
		out.println("<tr>");
		out.println("<td alignt=left>");
		out.println("<a href=\"BoardInsert\">새글</a>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		
		out.println("<table id=\"table_content\"width=700>");
		
		
		out.println("<table id=\"table_content\"width=700>");
		out.println("<tr>");
		out.println("<th width=10%>번호</th>");
		out.println("<th width=45%>제목</th>");
		out.println("<th width=15%>이름</th>");
		out.println("<th width=20%>작성일</th>");
		out.println("<th width=10%>조회수</th>");
		out.println("<tr>");
		
		for(BoardVO vo:list) {
			out.println("<tr class=dataTr>");
			out.println("<td width=10% align=center>"+vo.getNo()+"</td>");
			out.println("<td width=45% align=left>");
			out.println("<a href=BoardDetailServlet?no="+vo.getNo()+">");
			out.println(vo.getSubject()+"</a>");
			out.println("</td>");
			out.println("<td width=15% align=center>"+vo.getName()+"</td>");
			out.println("<td width=20% align=center>"+vo.getRegedate()+"</td>");
			out.println("<td width=10% align=center>"+vo.getHit()+"</td>");
			out.println("</tr>");
			
		}
		out.println("</table>");
		
		out.println("<table id=\"table_content\"width=700>");
		out.println("<tr>");
		out.println("<td alignt=left>");
		out.println("Search:");
		out.println("<select name=search>");
		out.println("<option value=name>이름</option>");
		out.println("<option value=subject>제목</option>");
		out.println("<option value=content>내용</option>");
		out.println("</select>");
		out.println("<input type=text name=searchWord size=15>");   //input 값을 받는 공간. 
		
		
		//변경
		//select name=fs
		//value = name
		//value = subject
		//value = content
		//name ss
		                                                                                                                                                                                                                                
		
		
		
//		out.println("<a href=\"BoardSerach\"+vo.getNo()+"\">수정</a>&nbsp;");
		out.println("<input type=submit value=찾기>");
		out.println("</td>");
		out.println("<td alignt=right>");
//		out.println("<a href=\"BoardListServlet?page="+(curpage>1?curpage-1:curpage)+"\">&lt;이전&gt;</a>");
//		/*
//		 *  특수문자
//		 *  &nbsp; " " 
//		 *  &lt;    <
//		 *  &gt;    >
//		 * 
//		 */
//		out.println(curpage+" page / "+totalpage+" pages");
//		out.println("<a href=\"BoardListServlet?page="+(curpage<totalpage?curpage+1:curpage)+"\">&lt;다음&gt;</a>");
//		out.println("</td>");
//		out.println("</tr>");
		out.println("</table>");
				
		out.println("</center>");		
		out.println("</body>");
		out.println("</html>");
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//sQl 실행값을 실행하여 이  page 에다가 작성
		BoardDAO dao = new BoardDAO();
		
		String search = request.getParameter("serach");
		String searchWord = request.getParameter("searchWord");
		
		ArrayList<BoardVO>	list=dao.boardSearchData(search,searchWord);
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=\"css/table.css\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>찾기 결과</h1>");
		
		out.println("<table id=\"table_content\"width=700>");
		out.println("<tr>");
		out.println("<td alignt=left>");
		out.println("<a href=\"BoardInsert\">새글</a>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		
		out.println("<table id=\"table_content\"width=700>");
		
		
		out.println("<table id=\"table_content\"width=700>");
		out.println("<tr>");
		out.println("<th width=10%>번호</th>");
		out.println("<th width=45%>제목</th>");
		out.println("<th width=15%>이름</th>");
		out.println("<th width=20%>작성일</th>");
		out.println("<th width=10%>조회수</th>");
		out.println("<tr>");
		
		for(BoardVO vo:list) {
			out.println("<tr class=dataTr>");
			out.println("<td width=10% align=center>"+vo.getNo()+"</td>");
			out.println("<td width=45% align=left>");
			out.println("<a href=BoardDetailServlet?no="+vo.getNo()+">");
			out.println(vo.getSubject()+"</a>");
			out.println("</td>");
			out.println("<td width=15% align=center>"+vo.getName()+"</td>");
			out.println("<td width=20% align=center>"+vo.getRegedate()+"</td>");
			out.println("<td width=10% align=center>"+vo.getHit()+"</td>");
			out.println("</tr>");
			
		}
		out.println("</table>");
		
		out.println("<table id=\"table_content\"width=700>");
		out.println("<tr>");
		out.println("<td alignt=left>");
		out.println("Search:");
		out.println("<select name=search>");
		out.println("<option value=name>이름</option>");
		out.println("<option value=subject>제목</option>");
		out.println("<option value=content>내용</option>");
		out.println("</select>");
		out.println("<input type=text name=searchWord size=15>");   //input 값을 받는 공간. 
		
		
		//변경
		//select name=fs
		//value = name
		//value = subject
		//value = content
		//name ss
		                                                                                                                                                                                                                                
		
		
		
//		out.println("<a href=\"BoardSerach\"+vo.getNo()+"\">수정</a>&nbsp;");
		out.println("<input type=submit value=찾기>");
		out.println("</td>");
		out.println("<td alignt=right>");
//		out.println("<a href=\"BoardListServlet?page="+(curpage>1?curpage-1:curpage)+"\">&lt;이전&gt;</a>");
//		/*
//		 *  특수문자
//		 *  &nbsp; " " 
//		 *  &lt;    <
//		 *  &gt;    >
//		 * 
//		 */
//		out.println(curpage+" page / "+totalpage+" pages");
//		out.println("<a href=\"BoardListServlet?page="+(curpage<totalpage?curpage+1:curpage)+"\">&lt;다음&gt;</a>");
//		out.println("</td>");
//		out.println("</tr>");
		out.println("</table>");
				
		out.println("</center>");		
		out.println("</body>");
		out.println("</html>");
	}

}
