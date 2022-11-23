package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.RecordModel;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession();
		RequestDispatcher dis = null;
		
		RecordModel record = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myjavacode?useSSL=false", "root", "root");
			PreparedStatement statement = connection.prepareStatement("select * from user where username = ? and password = ?");
			statement.setString(1, username);
			statement.setString(2, password);
			
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				
				session.setAttribute("id", rs.getInt("id"));
				session.setAttribute("username", rs.getString("username"));
				session.setAttribute("admin", rs.getString("admin"));
				dis = request.getRequestDispatcher("list");
			}else {
				request.setAttribute("status", "failed");
				dis = request.getRequestDispatcher("login.jsp");
			}
			dis.forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
