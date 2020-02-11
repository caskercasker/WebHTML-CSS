package com.sist.board;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.dao.*;
import java.util.*;

@WebServlet("/BoardListServlet")    //이 화면 이름
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//화면을 바꿔줄때 response   outputStrea 형태 
		//사용자 입력을 받는 request  inputStream 형태 
		
		// request : 사용자 요청 정보
		// response : 응답(HTML=> 사용자에게 전송)
		// ==> HTML/XML 
		
		//http://localhost/BoardProject/BoardListServlet
		//=============================
		//root => WebContent => css
		
		response.setContentType("text/html; charset=UTF-8");   //보내는 값 타입을 설정 XML HTML 을 정해야 함
		PrintWriter out = response.getWriter(); //outputstream 의 위치값을 넘겨야 브라우저가 값을 읽는다. 결국 값 자체를 보내는게 아니라. 메모리에 있는 값을   브라우저가 읽는 형태로 생각한다. outputStream
		// 
		//사용자가 요청한 페이지 => 
		String strPage=request.getParameter("page");
		if(strPage==null){
			strPage="1";  //입력값이 있기전 Default 페이지 잡기 
		}
		int curpage = Integer.parseInt(strPage);
		
		
		
		
		BoardDAO dao = new BoardDAO();
		ArrayList<BoardVO>	list=dao.boardListData(curpage);
		
		//총 페이지
		int totalpage=dao.boardTotalPage();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=\"css/table.css\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>자유게시판</h1>");
		
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
		out.println("<select>");
		out.println("<option>이름</option>");
		out.println("<option>제목</option>");
		out.println("<option>내용</option>");
		out.println("</select>");
		out.println("<input type=text size=15>");
		out.println("<input type=button value=찾기>");
		out.println("</td>");
		out.println("<td alignt=right>");
		out.println("<a href=\"BoardListServlet?page="+(curpage>1?curpage-1:curpage)+"\">&lt;이전&gt;</a>");
		/*
		 *  특수문자
		 *  &nbsp; " " 
		 *  &lt;    <
		 *  &gt;    >
		 * 
		 */
		out.println(curpage+" page / "+totalpage+" pages");
		out.println("<a href=\"BoardListServlet?page="+(curpage<totalpage?curpage+1:curpage)+"\">&lt;다음&gt;</a>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
				
		out.println("</center>");		
		out.println("</body>");
		out.println("</html>");
//		
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
