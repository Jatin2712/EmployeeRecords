package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entities.Employee;
import Entities.SkillMaster;
import Dao.EmployeeDao;
import Dao.SkillMasterDao;
import database.DBConnection;

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String gender = request.getParameter("gender");

		DBConnection dbConnection = null;
		Connection connection = null;

		try {
			dbConnection = new DBConnection();
			connection = dbConnection.getConnection();

			double salary = Double.parseDouble(request.getParameter("salary"));
			String employeeId_st = request.getParameter("employeeId");
			System.out.println("employeeId_st===" + employeeId_st);
			int employeeId = Integer.parseInt(employeeId_st);
			
			String birthDate_st = request.getParameter("birthDate");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date birthDate;
			birthDate = simpleDateFormat.parse(birthDate_st);
			
			String skillMasterId[]= request.getParameterValues("skillMasterId");
			
			for(int i=0; i<skillMasterId.length;i++) {
				
			}

			Employee employee = new Employee(employeeId, name, address, gender, birthDate, salary);
			EmployeeDao dao = new EmployeeDao();

			boolean flag = dao.updateEmp(employee, skillMasterId, connection);
			
			
			HttpSession session = request.getSession();


			if (flag) {
				session.setAttribute("success Message", "Employee Details Update Successfully");
				response.sendRedirect("index.jsp");

			} else {

				session.setAttribute("error Message", "Something went wrong");
				response.sendRedirect("index.jsp");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			if (dbConnection != null) {
				try {
					dbConnection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
