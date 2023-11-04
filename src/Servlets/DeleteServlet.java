package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dao.EmployeeDao;
import database.DBConnection;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DBConnection dbConnection = null;
		Connection connection = null;

		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
//		int skillMasterId = Integer.parseInt(request.getParameter("skillMasterId"));

		try {
			dbConnection = new DBConnection();
			connection = dbConnection.getConnection();

			EmployeeDao dao = new EmployeeDao();
			boolean flag = dao.deleteEmp(employeeId, connection);

			HttpSession session = request.getSession();
			if (flag) {
				session.setAttribute("success Message", "Employee Details Deleted Successfully");
				response.sendRedirect("index.jsp");

			} else {

				session.setAttribute("error Message", "Something went wrong");
				response.sendRedirect("index.jsp");
			}

		} catch (Exception exception) {

		} finally {
			if (dbConnection != null) {
				try {
					dbConnection.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}