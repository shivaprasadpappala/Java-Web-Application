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
 * Servlet implementation class Delete
 */
@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));
		
		String sql="DELETE FROM emp2 WHERE id=?;";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		try(Connection con=DriverManager.getConnection("jdbc:mysql://localhost/mydb","root","Shiva@123");
				PreparedStatement ps=con.prepareStatement(sql)) {
			ps.setInt(1, id);
			int update=ps.executeUpdate();
			if(update>0) {
				Login.getAllEmp(request, con);
				request.getSession().setAttribute("message", "user Deleted SuccessFully");	
				RequestDispatcher rd=request.getRequestDispatcher("Home.jsp");
				rd.forward(request, response);
				return;
			}
			else {
				request.getSession().setAttribute("error", "Something Wrong in Deleting");
				RequestDispatcher rd=request.getRequestDispatcher("Home.jsp");
				rd.forward(request, response);
				return;
			}
			
		}
		catch(Exception e) {
			System.out.println("execption  : "+e);
			request.getSession().setAttribute("error", e.getMessage());
			RequestDispatcher rd=request.getRequestDispatcher("Home.jsp");
			rd.forward(request, response);
			return;
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
