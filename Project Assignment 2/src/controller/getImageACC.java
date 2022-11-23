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
 * Servlet implementation class getImageACC
 */
@WebServlet("/getimageacc")
public class getImageACC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String URL = "jdbc:mysql://localhost:3306/myjavacode?useSSL=false";
	private String username = "root";
	private String password = "root";
	
	public getImageACC() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
        System.out.println("Id in imageretrieve="+id);
	    try {
	    	Connection connection = null;
	    	Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, username, password);
	        PreparedStatement ps = connection.prepareStatement("select * from user where id=?");
	        ps.setInt(1, id);
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
	    }
	
	}


}
