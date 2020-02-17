package com.sist.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.dao.*;
import com.sun.xml.internal.ws.transport.http.HttpAdapter;

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
		System.out.println(Integer.parseInt(mno));
		MusicDAO dao = new MusicDAO();
		MusicVO vo = dao.musicDetailData(Integer.parseInt(mno));
		System.out.println(Integer.parseInt(mno));
		ArrayList<MusicReplyVO> list = dao.replyListData(Integer.parseInt(mno));
		// table 형태를 div로 바꿔야함. 
		System.out.println(Integer.parseInt(mno));
		
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=\"css/bootstrap.min.css\">");
		out.println("<style type=text/css>");
		out.println(".col-sm-9{");
		out.println("margin: 0px auto;");
		out.println("width:900px");
		out.println("}");
		out.println("h1{");
		out.println("text-align:center;");
		out.println("}");
		out.println("</style>");
		out.println("<script type=text/javascript src=\"http://code.jquery.com/jquery.js\"></script>");
		
		out.println("<script>");
		out.println("var i=0;");
		/*
		 * 	변수 => 데이터형 
		 *  var i=0; => i(int)
		 *  var i=10.5 => i(double)
		 *  var i="Hello" => i(String)
		 *  var i=[] => 배열
		 *  var i={} => 객체
		 */
		out.println("$(function(){"); //window.onload()
		out.println("$('#ubtn').click(function(){");
		
		out.println("if(i===0){");
		out.println("$('#ubtn').val('취소');");
		out.println("i=1;");
		out.println("}");
		
		out.println("else {");
		out.println("$('#ubtn').val('수정');");
		out.println("i=1;");
		out.println("}");
		
		out.println("})");
		
		out.println("});");
		out.println("</script>");
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
		out.println("<div class=col-sm-9>");
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
		
		out.println("<tr>");
		out.println("<td colspan=2 class=text-right>");
		out.println("<a href=\"MusicList\" class=\"btn btn-lg btn-success\">목록</a>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
	// 댓글 div
		out.println("<div style=\"height=20px\"><div>");
		//댓글 출력
		HttpSession session = request.getSession();  //session 을 얻어온다.
		//request => session,cookie를 가지고 올 수 있다. 
		String id=(String)session.getAttribute("id");
		//null => 저장이 안된 상태,=> login 이 안된상태 (null), Login 이 된 상태 (등록 id)
		
		System.out.println(list.size());
		if(list.size()<1){
			out.println("<table class=\"table table-striped\">");
			out.println("<tr>");
			out.println("<td class=text-center>");
			out.println("<h3>댓글이 존재하지 않습니다. </h3>");			
			out.println("</td>");
			out.println("</tr>");
			out.println("</table>");
		}else{
			out.println("<table class=\"table table-hover\">");

			for(MusicReplyVO rvo:list){
				out.println("<tr>");
				out.println("<td class=text-left>");
				String temp="";
				if(rvo.getSex().equals("남자"))
					temp="m1.jpg";
				else
					temp="w2.jpg";
				out.println("<img src=\"image/"+temp+"\" width=25 height=25>");
				out.println("&nbsp;"+rvo.getName()+"("+rvo.getDbDay()+")");
				out.println("</td>");
				
				out.println("<td class=text-right>");
				/*
				 * String id=null;
				 * if(id.equals("aaa"))
				 * 
				 * 
				 */
			if(rvo.getId().equals(id)){
				out.println("<input type=button class=\"btn btn-xs btn-primary\" value=수정 id=ubtn data="+rvo.getNo()+">");
				out.println("<a href=\"ReplyDelete?no="+rvo.getNo()+"&mno="+mno+"\" class=\"btn btn-xs btn-danger\">삭제</a>");
			}

			out.println("</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td colspan=2 class=text-left><pre style=\"white-space:pre-wrap;border:none;background:white;\">"+rvo.getMsg()+"</pre></td>");
			out.println("</tr>");
			
			out.println("<tr id=\"m"+rvo.getNo()+"\" style=\"display:none\">");
			out.println("<td colspan=2>");
			
			out.println("<textarea rows=3 cols=80 style=\"float:left\" required name=msg>"+rvo.getMsg()+"</textarea>");
			out.println("<input type=hidden value="+mno+" name=mno>");
			//session 과 hidden에서 값으 넘겨서 댓글 처리 
			out.println("<input type=submit value=댓글수정  style=\"height:65px;float:left\" class=\"btn btn-primary\">");
			//style float:left 옆으로 붙이기. 
			out.println("</td>");
			out.println("</tr>");
			}
			out.println("</table>");
		}
		// 댓글 입력

		//메뉴 처리, 로그인
		//장바구니, 예약, 
		
		if(id!=null){
			
			out.println("<form method=post action=\"ReplyInsert\">");
			out.println("<table class=\"table table-striped\">");
			//out.println("</table>");
			out.println("<tr>");
			out.println("<td>");
			
			out.println("<textarea rows=3 cols=80 style=\"float:left\" required name=msg></textarea>");
			out.println("<input type=hidden value="+mno+" name=mno>");
			//session 과 hidden에서 값으 넘겨서 댓글 처리 
			out.println("<input type=submit value=댓글쓰기  style=\"height:65px;float:left\" class=\"btn btn-primary\">");
			
			//style float:left 옆으로 붙이기. 
			out.println("</td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("</form>");
		}
		
		out.println("</div>");
		
		out.println("<div class=col-sm-3>");
		
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
