package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.database;



/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private database dao;   
	 
    /**
     * @see HttpServlet#HttpServlet()
     */
 

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirm = request.getParameter("confirm");
		String contact = request.getParameter("contact");
		String photo = request.getParameter("photo");
		
		RequestDispatcher dis = null;
		Connection con = null;
		
		if (password.equals(confirm)) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myjavacode?useSSL=false","root", "root");
				PreparedStatement statement = con.prepareStatement("insert into user (name, email, username, password, contact, photo) values(?,?,?,?,?,?)");
				statement.setString(1, name);
				statement.setString(2, email);
				statement.setString(3, username);
				statement.setString(4, password);
				statement.setString(5, contact);
				File file = new File (photo);
				FileInputStream fis = new FileInputStream(file);
				statement.setBlob(6, fis);
				
				int rowCount = statement.executeUpdate();
				dis = request.getRequestDispatcher("register.jsp");
				
				if (rowCount > 0) {
					request.setAttribute("status", "success");
					
				}else {
					request.setAttribute("status", "failed");
				}
				dis.forward(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}else {	
				dis = request.getRequestDispatcher("register.jsp");
				request.setAttribute("status", "notsame");
				dis.forward(request, response);
			}
		
	}

}
