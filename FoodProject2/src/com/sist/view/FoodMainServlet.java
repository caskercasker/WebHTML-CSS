package com.sist.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;

@WebServlet("/FoodMainServlet")
public class FoodMainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			FoodDAO dao=FoodDAO.newInstance();
			ArrayList<CategoryVO> list=dao.categoryAllData();
			
			out.println("<html>");
			out.println("<head>");
			out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
			out.println("</head>");
			out.println("<body>");
			out.println("<div class=container>");
			out.println("<div class=row>");
			//for
			/*
			 * <div class="panel panel-default">
  				<div class="panel-heading">Panel Heading</div>
  				<div class="panel-body">Panel Content</div>
			  </div>s
			 * 
			 */
			for(CategoryVO vo:list){
			out.println("<div class=\"col-md-3\">");
			out.println("<div class=\"panel panel-primary\">");
			out.println("<div class=\"panel-heading\">"+vo.getTitle()+"</div>");
			out.println("<div class=\"panel-body\">");
			out.println("<img src=\""+vo.getPoster()+"\" width=100%>");
			
			out.println("</div>");
			out.println("</div>");
			out.println("</div>");
			}
			//
			out.println("</div>");
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");
	}
}
