package controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class getImageServlet
 */
@WebServlet("/getimage")
public class getImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String URL = "jdbc:mysql://localhost:3306/myjavacode?useSSL=false";
	private String username = "root";
	private String password = "root";
    /**
     * @see HttpServlet#HttpServlet()
     */
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		System.out.println("Id in image retrive= "+id);
		try {
	    	Connection con = null;
	    	Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, username, password);
	        PreparedStatement ps = con.prepareStatement("select * from user where id=?");
	        ps.setString(1, id);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            Blob blob = rs.getBlob("photo");
	            byte byteArray[] = blob.getBytes(1, (int) blob.length());
	            response.setContentType("image/gif");
	            OutputStream os = response.getOutputStream();
	            os.write(byteArray);
	            os.flush();
	            os.close();
	        } else {
	            System.out.println("No image found with this id.");
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }	}


}
