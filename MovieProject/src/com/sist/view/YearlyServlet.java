package com.sist.view;

import java.io.*;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class YearlyServlet
 */
@WebServlet("/YearlyServlet")
public class YearlyServlet extends HttpServlet {
	
		private static final long serialVersionUID = 1L;
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
					
			out.println("<html>");
			out.println("<body>");
			out.println("<center>");
			out.println("<h1>연간상영영화</h1>");
			out.println("</center>");
			out.println("</body>");
			out.println("</html>");
		}
	
}
