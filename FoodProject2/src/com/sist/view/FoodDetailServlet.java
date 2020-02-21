package com.sist.view;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import com.sist.dao.*;
@WebServlet("/FoodDetailServlet")
public class FoodDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html; charset=UTF-8");		// text/html;charset=ISO default 한글 깨짐을 방지하기 위해 UTF-8세팅
		PrintWriter out = response.getWriter(); 			//브라우저가 읽어갈 위치 지정 (메모리) TCP/IP 메모리 활용  버퍼(오토 플러쉬) 메모리 
		String no = request.getParameter("no");
		//no="11";
		FoodDAO dao = FoodDAO.newInstance();
		FoodHouseVO vo = dao.foodDetailData(Integer.parseInt(no));
		
		String temp = vo.getAddress();
		temp = temp.substring(temp.lastIndexOf("구")+1, temp.lastIndexOf("동"));
		
		ArrayList<FoodHouseVO> list= dao.foodlocationData(temp);
		System.out.println(temp);
		//요청값
		//요청에 해당하는 DAO 
		// HTML 출력
		
		/*
		 * 예약/ 병원, 극장, 항공권, 버스, 기차
		 * 추천
		 * 장바구니
		 * 스트리밍
		 * ERP
		 */
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style type=text/css>");
		out.println(".row{");
		out.println("margin:0px auto;");
		out.println("width:1200px;");
		out.println("}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");  //container-fluid 전체 화면 덮기 
		out.println("<div class=row>");
		
		out.println("<div class=col-md-8>");		//col-md-8 div의 블록속성을 inline 속성을 바꿈
		out.println("<table class=\"table\">");
		out.println("<tr>");
		StringTokenizer st =new StringTokenizer(vo.getImage(),"^");
		System.out.println("pppp");
		System.out.println(st.nextToken());
		
		while(st.hasMoreTokens()){
			out.println("<td>");
			out.println("<img src=\""+st.nextToken()+"\" width =100%>");
			out.println("</td>");
		}
		out.println("</tr>");
		
		
		out.println("<\table>");
		out.println("<table class=\"table\">");
		out.println("<tr>");
		out.println("<td colspan=2>");
		out.println("<h2>"+vo.getTitle()+"&nbsp;<font color=orange>"+vo.getScore()+"</font></h2>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=15% class=text-right>주소</td>");
		out.println("<td width=86% class=text-left>");
		out.println(vo.getAddress());
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=15% class=text-right>전화번호</td>");
		out.println("<td width=85% class=text-left>");
		out.println(vo.getTel());
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=15% class=text-right>음식종류</td>");
		out.println("<td width=85% class=text-left>");
		out.println(vo.getAddress());
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=15% class=text-right>가격대</td>");
		out.println("<td width=85% class=text-left>");
		out.println(vo.getPrice());
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td colspan=2 class=text-center>");
		out.println("좋아요("+vo.getGood()+")|");
		out.println("괜찮아요("+vo.getSoso()+")|");
		out.println("별로("+vo.getBad()+")");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td colspan=2 class=text-right>");
		out.println("<a href=\"#\" class=\"btn btn-xs btn-danger\">찜</a>");
		out.println("<a href=\"#\" class=\"btn btn-xs btn-success\">예약</a>");
		out.println("<a href=\"javascript:history.back()\" class=\"btn btn-xs btn-primary\">목록</a>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("</table>");
		out.println("</div>");
		//out.println("</div>");
		
		out.println("<div class=col-md-4>");
		
		out.println("<table class=\"table\">");
		out.println("<caption><h3>주변 인기 식당</h3></caption>");
		out.println("<tr>");
		out.println("<td>");
		int c=0;
		
		for(FoodHouseVO fvo:list){
			out.println("<table class=\"table\">");
			out.println("<tr>");
			out.println("<td class=text-center width=30% rowspan=4>");
			out.println("<img src="+fvo.getImage().substring(0,fvo.getImage().indexOf("^"))+"width=80 height=80>");
			out.println("</td>");
			out.println("<td>");
			out.println("</td width=70%>");
			out.println(fvo.getTitle()+"&nbsp;"+fvo.getScore());
			out.println("</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td width=70%>");
			out.println("음식종류:"+fvo.getType());
			out.println("</td>");
			out.println("</tr>");
	
			out.println("<tr>");
			out.println("<td width=70%>");
			out.println("전화번호:"+fvo.getTel());
			out.println("</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td width=70%>");
			out.println("가격대:"+fvo.getPrice());
			out.println("</td>");
			out.println("</tr>");
			
			
			out.println("</table>");
			c++;
			System.out.println(c);
		}
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</div>");
		
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
