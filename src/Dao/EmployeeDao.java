package Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Entities.Employee;
import Entities.SkillMaster;
import database.DBConnection;

public class EmployeeDao {

//	private Connection connection;
//
//	public EmployeeDao(Connection connection) {
//		super();
//		this.connection = connection;
//	}

	// For Adding Employee
	public long addEmployee(Employee employee, Connection connection) {
		long generated_employeeId = 0;
		try {

			String query = "insert into dbo.jbEmployee(name,address,gender,birthDate,salary) values(?,?,?,?,?)";

			PreparedStatement preparedstatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			preparedstatement.setString(1, employee.getName());
			preparedstatement.setString(2, employee.getAddress());
			preparedstatement.setString(3, employee.getGender());

			Date sqlDate = new Date(employee.getBirthDate().getTime());
			preparedstatement.setDate(4, sqlDate);
			preparedstatement.setDouble(5, employee.getSalary());

			int i = preparedstatement.executeUpdate();
			if (i == 1) {
				ResultSet resultset = preparedstatement.getGeneratedKeys();
				if (resultset.next()) {
					generated_employeeId = resultset.getInt(1);
					System.out.println("Generated key: " + generated_employeeId);
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return generated_employeeId;
	}

	// Insert EmployeeSkill
	public void insertEmployeeSkill(long employeeId, String skill[], Connection connection) {
		for (int i = 0; i < skill.length; i++) {
			int skillMasterId = Integer.parseInt(skill[i]);
			insertEmployeeSkill(employeeId, skillMasterId, connection);
		}
	}

	public void insertEmployeeSkill(long employeeId, int skillMasterId, Connection connection) {
		String query1 = "insert into dbo.jbEmployeeSkill(employeeId, skillMasterId) values(?,?)";
		try {
			PreparedStatement preparedstatement = connection.prepareStatement(query1);
			preparedstatement.setLong(1, employeeId);
			preparedstatement.setInt(2, skillMasterId);
			preparedstatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// For Printing List
	public List<Employee> getAllEmployee(Connection connection, int pageNo) {
		List<Employee> list = new ArrayList<Employee>();
		Employee employee = null;
		try {
			String query = "select * from dbo.jbEmployee order by name,employeeId offset (?-1)*5 rows fetch next 5 rows only";
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			preparedstatement.setInt(1, pageNo);
			
			ResultSet resultset = preparedstatement.executeQuery();
			while (resultset.next()) {
				employee = new Employee();
				employee.setEmployeeId(resultset.getInt(1));
				employee.setName(resultset.getString(2));
				employee.setAddress(resultset.getString(3));
				employee.setGender(resultset.getString(4));
				employee.setSalary(resultset.getInt(5));
				java.util.Date date = new java.util.Date(resultset.getTimestamp(6).getTime());
				employee.setBirthDate(date);
				list.add(employee);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// For Getting Data
	public Employee getEmployeeById(int employeeId, Connection connection) {
		Employee employee = null;
		try {
			String query = "select * from dbo.jbEmployee where employeeId=?";
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			preparedstatement.setInt(1, employeeId);
			ResultSet resultset = preparedstatement.executeQuery();
			while (resultset.next()) {
				employee = new Employee();
				employee.setEmployeeId(resultset.getInt(1));
				employee.setName(resultset.getString(2));
				employee.setAddress(resultset.getString(3));
				employee.setGender(resultset.getString(4));
				employee.setSalary(resultset.getInt(5));
				java.util.Date date = new java.util.Date(resultset.getTimestamp(6).getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}

	// For Updating Data

	// ----------------Update Employee Details------------
	public boolean updateEmp(Employee employee, String skillMasterId[], Connection connection) throws Exception {
		boolean flag = false;
		try {
			String query = "update dbo.jbEmployee set name=?,address=?,gender=?,birthDate=?,salary=? where employeeId=?";

			PreparedStatement preparedstatement = connection.prepareStatement(query);

			preparedstatement.setString(1, employee.getName());
			preparedstatement.setString(2, employee.getAddress());
			preparedstatement.setString(3, employee.getGender());

//			Date queryDate = new Date(employee.getBirthDate().getTime());
			java.util.Date utilDate = new java.util.Date();
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			preparedstatement.setDate(4, sqlDate);

			preparedstatement.setDouble(5, employee.getSalary());
			preparedstatement.setInt(6, employee.getEmployeeId());

			deleteSkill(employee.getEmployeeId(), connection);
			insertEmployeeSkill(employee.getEmployeeId(), skillMasterId, connection);

			int i = preparedstatement.executeUpdate();
			if (i == 1) {
				flag = true;
			}
		} finally {

		}
		return flag;
		
//		updateSkill(employeeId);
//		getEmployeeById(employeeId, connection);
	}

	// --------------Update Employee Skills-------------------
//	public boolean updateSkill(SkillMaster skillMaster, Connection connection, int employeeId) throws Exception {
//		boolean flag = false;
//
//		DBConnection dbConnection = null;
//		List<SkillMaster> list = null;
//		Statement statement = null;
//		ResultSet resultSet = null;
//		try {
//			List<SkillMaster> skillMasterList = new ArrayList<SkillMaster>();
//			dbConnection = new DBConnection();
//
//			String sql = "select jbSkillMaster.skillMasterId,jbSkillMaster.name,jbEmployeeSkill.employeeId from\r\n" 
//					+"jbSkillMaster left join jbEmployeeSkill on\r\n" 
//					+"(jbSkillMaster.skillMasterId=jbEmployeeSkill.skillMasterId and jbEmployeeSkill.employeeId=" + employeeId + " )";
//			
//			connection = dbConnection.getConnection();
//			try {
//				statement = connection.createStatement();
//				resultSet = statement.executeQuery(sql);
//				if (resultSet != null) {
//			while (resultSet.next()) {
//				skillMaster.setSkillMasterId(resultSet.getInt(1));
//				skillMaster.setName(resultSet.getString(2));
//				skillMaster.setEmployeeId(resultSet.getInt(3));
//				skillMasterList.add(skillMaster);
//			}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				resultSet.close();
//				statement.close();
//			}
//			} finally {}
//		return flag;
//	}

	// Delete All Skills
	public boolean deleteSkill(int employeeId, Connection connection) throws Exception {

		boolean flag = false;
		String query = "delete from dbo.jbEmployeeSkill where employeeId=?";

		PreparedStatement preparedstatement = connection.prepareStatement(query);
		preparedstatement.setInt(1, employeeId);
		int i = preparedstatement.executeUpdate();

		if (i >= 1) {
			System.out.println("Successfully deleted all skills for " + employeeId);
			flag = true;
		}
		return flag;
	}

	// For Deleting data
	public boolean deleteEmp(int employeeId, Connection connection) {
		boolean flag = false;
		try {

			deleteSkill(employeeId, connection);

			String query = "delete from dbo.jbEmployee where employeeId=?";

			PreparedStatement preparedstatement = connection.prepareStatement(query);
			preparedstatement.setInt(1, employeeId);
			int i = preparedstatement.executeUpdate();

			if (i == 1) {
				System.out.println("SUCCESSFULLY DELETED!!" + employeeId);
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}