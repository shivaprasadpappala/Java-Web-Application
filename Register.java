package com.org;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		float salary=Float.parseFloat(request.getParameter("salary"));
		
		request.getSession().setAttribute("email", email);
		String sql="insert into emp2 values(?,?,?,?,?,?)";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		try(Connection con=DriverManager.getConnection("jdbc:mysql://localhost/mydb","root","Shiva@123");PreparedStatement ps=con.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setString(3, email);
			ps.setString(4, password);
			ps.setFloat(5, salary);
			ps.setString(6, "user");
			int update=ps.executeUpdate();
			if(update>0) {
				request.getSession().setAttribute("message", "user Register SuccessFull");	
				RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
				rd.forward(request, response);
				return;
			}
			else {
				request.getSession().setAttribute("error", "Something Wrong in Register");
				RequestDispatcher rd=request.getRequestDispatcher("register.jsp");
				rd.forward(request, response);
				return;
			}
			
		}
		catch(Exception e) {
			System.out.println("execption  : "+e);
			request.getSession().setAttribute("error", e.getMessage());
			RequestDispatcher rd=request.getRequestDispatcher("register.jsp");
			rd.forward(request, response);
			return;
		}
		

	}

}
