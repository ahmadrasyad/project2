package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.database;
import model.RecordModel;








/**
 * Servlet implementation class RecordServlet
 */
@WebServlet("/")
public class RecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private database db;   

	public void init() {
		db = new database();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		try {
			switch (action) {
			
			case "/new":
				Showform(request, response);
				break;
			case "/update":
				updateRecord(request, response);
				break;
			case "/home":
				viewrd(request, response);
				break;
			case "/view":
				viewdata(request,response);
				break;
			case "/viewacc":
				viewacc(request, response);
				break;
			case "/viewme":
				viewme(request,response);
				break;
			case "/edit":
				edit (request,response);
				break;
			case "/delete":
				delete(request, response);
				break;
			case "/updateregister":
				updateRegister(request, response); 
				break;	
			case "/list":
				listRecord(request, response);
				break;	
			case "/deleteacc":
				deleteAcc(request, response);
				break;
			case "/editacc":
				editAcc(request, response);
				break;
			case "/updateregisteracc":
				updateRegisteracc(request, response); 
				break;
		
			}
	}catch (SQLException ex) {
		throw new ServletException(ex);
	}
	}
		private void Showform(HttpServletRequest request, HttpServletResponse response) throws SQLException,IOException, ServletException {
			RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
			dispatcher.forward(request, response);
		}
		
		private void listRecord(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
			List<RecordModel> listRecord = db.PeekRecord();
			request.setAttribute("listRecord", listRecord);
			RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
			dispatcher.forward(request, response);
		}
		
		private void viewrd (HttpServletRequest request, HttpServletResponse response)
				throws SQLException, ServletException, IOException {
			HttpSession session = request.getSession();
			int id =(int)session.getAttribute("id");
			RecordModel theuser = db.viewRd(id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
			request.setAttribute("record", theuser);
			dispatcher.forward(request, response);

		}
		
		private void viewdata (HttpServletRequest request, HttpServletResponse response)
				throws SQLException, ServletException, IOException {
			int id = Integer.parseInt(request.getParameter("id"));
			RecordModel all = db.viewall(id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
			request.setAttribute("record", all);
			dispatcher.forward(request, response);

		}
		
		private void viewacc (HttpServletRequest request, HttpServletResponse response)
				throws SQLException, ServletException, IOException {
			int id = Integer.parseInt(request.getParameter("id"));
			RecordModel all = db.viewall(id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("Viewacc.jsp");
			request.setAttribute("record", all);
			dispatcher.forward(request, response);

		}
		
		private void edit (HttpServletRequest request, HttpServletResponse response)
				throws SQLException, ServletException, IOException {
			HttpSession session = request.getSession();
			int id =(int)session.getAttribute("id");
			RecordModel all = db.viewall(id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("edit.jsp");
			request.setAttribute("record", all);
			dispatcher.forward(request, response);

		}
		
		private void editAcc (HttpServletRequest request, HttpServletResponse response)
				throws SQLException, ServletException, IOException {
			int id = Integer.parseInt(request.getParameter("id"));
			RecordModel all = db.viewall(id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("editacc.jsp");
			request.setAttribute("record", all);
			dispatcher.forward(request, response);

		}
		
		private void viewme (HttpServletRequest request, HttpServletResponse response)
				throws SQLException, ServletException, IOException {
			HttpSession session = request.getSession();
			int id =(int)session.getAttribute("id");
			RecordModel all = db.viewall(id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
			request.setAttribute("record", all);
			dispatcher.forward(request, response);

		}
		
		private void updateRecord (HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
			HttpSession session = request.getSession();
			int id =(int)session.getAttribute("id");
			String file = request.getParameter("file");
			String comment = request.getParameter("comment");
			
			RecordModel book = new RecordModel(id, file, comment);
			db.updatefile(book);
			response.sendRedirect("home");
		}
		
		private void delete(HttpServletRequest request, HttpServletResponse response) 
				throws SQLException, IOException {
			HttpSession session = request.getSession();
			int id = (int)session.getAttribute("id");
			db.deleteUser(id);
			response.sendRedirect("logout");

		}
		
		private void deleteAcc(HttpServletRequest request, HttpServletResponse response) 
				throws SQLException, IOException {
			int id =  Integer.parseInt(request.getParameter("id"));
			db.deleteUser(id);
			response.sendRedirect("list");

		}
		
		private void updateRegister (HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
			HttpSession session = request.getSession();
			int id =(int)session.getAttribute("id");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String username = request.getParameter("user");
			String password = request.getParameter("pass");
			String phone = request.getParameter("contact");
			String photo = request.getParameter("photo");
			System.out.println(id+name+email+username+password+phone+photo);
			RecordModel book = new RecordModel(name, email, username, password, phone, photo, id);
			db.updateacc(book);
			response.sendRedirect("logout");
		}
		
		private void updateRegisteracc (HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String username = request.getParameter("user");
			String password = request.getParameter("pass");
			String phone = request.getParameter("contact");
			String photo = request.getParameter("photo");
			System.out.println(id+name+email+username+password+phone+photo);
			RecordModel book = new RecordModel(name, email, username, password, phone, photo, id);
			db.updateacc(book);
			response.sendRedirect("list");
		}
}