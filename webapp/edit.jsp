<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Entities.Employee"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.util.*"%>
<%@page import="Entities.SkillMaster"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="database.DBConnection"%>
<%@page import="Dao.EmployeeDao"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Employee Details</title>
</head>
<body>
	<%@include file="base.jsp"%>
	<%@include file="navbar.jsp"%><br>
	<%
	int employeeId = Integer.parseInt(request.getParameter("employeeId"));

	DBConnection dbConnection = null;
	List<SkillMaster> list = null;
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	try {
		List<SkillMaster> skillMasterList = new ArrayList<SkillMaster>();
		dbConnection = new DBConnection();

		String sql = "select jbSkillMaster.skillMasterId,jbSkillMaster.name,jbEmployeeSkill.employeeId from\r\n" 
				+"jbSkillMaster left join jbEmployeeSkill on\r\n" 
				+"(jbSkillMaster.skillMasterId=jbEmployeeSkill.skillMasterId and jbEmployeeSkill.employeeId=" + employeeId + " )";
		
		connection = dbConnection.getConnection();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			if (resultSet != null) {
		while (resultSet.next()) {
			SkillMaster skillMaster = new SkillMaster();
			skillMaster.setSkillMasterId(resultSet.getInt(1));
			skillMaster.setName(resultSet.getString(2));
			skillMaster.setEmployeeId(resultSet.getInt(3));
			skillMasterList.add(skillMaster);
		}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			resultSet.close();
			statement.close();
		}
	%>

	<%
	EmployeeDao dao = new EmployeeDao();
	Employee s = dao.getEmployeeById(employeeId, dbConnection.getConnection());
	%>

	<div class="row justify-content-center">
		<div class="col-md-6">
			<div class="card">
				<div class="card-body">
					<h5 class="card-title text-center">Edit Employees Here</h5>
					<form action="update" method="post">
						<!-- For EmployeeId -->
						<input type="hidden" name="employeeId"
							value="<%=s.getEmployeeId()%>">

						<!--  For Name -->
						<label for="name" class="form-label">Employee Name</label> <input
							type="text" name="name" class="form-control"
							placeholder=" Enter Employee Name " value="<%=s.getName()%>"><br>

						<!--  For Address -->
						<label for="address" class="form-label">Employee Address</label> <input
							class="form-control" name="address" class="form-control"
							placeholder=" Enter Employee Address "
							value="<%=s.getAddress()%>">

						<!--  For Gender -->
						<label for="gender" class="form-label"> Gender </label><br> <input
							class="form-check-input" type="radio" name="gender" value="1"
							<%=(s.getGender().equals("1") ? "checked" : "")%>>Male <br>
						<input class="form-check-input" type="radio" name="gender"
							value="2" <%=(s.getGender().equals("2") ? "checked" : "")%>>Female<br>
						<br>

						<!-- For Skills -->
						<div class="form-group">
							<label> Skills </label><br>
							<%
							
							for (SkillMaster skillMaster : skillMasterList) {
							%>
							<input type="checkbox" name="skillMasterId" value="<%=skillMaster.getSkillMasterId()%>" <%=skillMaster.getEmployeeId()>0?"checked":"" %>>
							<label for="skills"><%=skillMaster.getName()%></label> <br>
							<%
							}
							%>
						</div>

						<!--  For Salary -->
						<label for="salary" class="form-label">Employee Salary</label> <input
							type="number" name="salary" class="form-control"
							placeholder=" Employee Salary " value="<%=s.getSalary()%>"><br>

						<!--  For DOB -->
						<label for="birthDate" class="form-label">Employee Date-of-Birth</label> 
						<input type="date" name="birthDate" class="form-control">

						<button class="mt-3 btn btn-primary" type="submit">Update</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
<%
} catch (Exception exception) {

} finally {
if (dbConnection != null) {
	dbConnection.close();
}
}
%>
</html>