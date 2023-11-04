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

import Dao.EmployeeDao;
import Entities.Employee;
import Entities.SkillMaster;
import database.DBConnection;

@WebServlet("/register") 
//register (use only redirected servlet)
public class RegisterServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DBConnection dbConnection = null;
		Connection connection = null;
		
		try {
			dbConnection = new DBConnection();
			connection = dbConnection.getConnection();
			
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String gender = request.getParameter("gender");
			String birthDate_string = request.getParameter("birthDate");
			SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(birthDate_string);
			Date birthDate = simpledateformat.parse(birthDate_string);
			birthDate = simpledateformat.parse(birthDate_string);
			
			String skillMasterId[] = request.getParameterValues("skillMasterId"); 
					
				for(int i =0;i<skillMasterId.length;i++)
				{
					System.out.println("skillMasterId==="+skillMasterId[i]);
				}
					
			
			double salary = Double.parseDouble(request.getParameter("salary"));
			Employee employee = new Employee(0, name, address, gender, birthDate, salary);
			
			EmployeeDao dao;
			dao = new EmployeeDao();
			
			
			SkillMaster skillMaster = new SkillMaster();
			
			long generated_employeeId = dao.addEmployee(employee, connection);
			
			if (generated_employeeId > 0) {
				dao.insertEmployeeSkill(generated_employeeId, skillMasterId, connection);
			}
			
			HttpSession session = request.getSession();
			if (generated_employeeId > 0) {
				session.setAttribute("success Message", "Employee Details Added");
				response.sendRedirect("index.jsp");
			} else {
				session.setAttribute("error Message", "Something went Wrong");
				response.sendRedirect("register.jsp");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if (dbConnection != null) {
				try {
					dbConnection.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
//		doGet(request, response);
	}
}