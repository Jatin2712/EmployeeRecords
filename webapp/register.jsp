<%@page import="java.sql.Statement"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entities.SkillMaster"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="database.DBConnection"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register Page</title>

<script src="JS/script.js"></script>
</head>
<body>
	<%@include file="base.jsp"%>
	<%@include file="navbar.jsp" %><br>
<%
DBConnection dbConnection = null;
List<SkillMaster> list = null;
Connection connection = null;
Statement statement = null;
ResultSet resultSet = null;
try {
        List<SkillMaster> skillMasterList = new ArrayList<SkillMaster>();

        dbConnection = new DBConnection();
        String sql = "select skillMasterId,name from jbSkillMaster";

        connection = dbConnection.getConnection();

        try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);
                if (resultSet != null) {
        while (resultSet.next()) {
                SkillMaster skillMaster = new SkillMaster();
                skillMaster.setSkillMasterId(resultSet.getInt(1));
                skillMaster.setName(resultSet.getString(2));
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
	<!-- Employee form Register -->
	<div class="row justify-content-center">
		<div class="col-md-6">
			<div class="card">
				<div class="card-body">
					<h5 class="card-title text-center">Register Employee Details Here</h5>
					<form action="register" method="post" class="form-group" name="form1">

						<!--  For Name -->
						<label for="name" class="form-label">Employee Name</label> <input
							type="text" name="name" class="form-control"
							placeholder=" Enter Employee Name " required="required"><br>

						<!--  For Address -->
						<label for="address" class="form-label">Employee Address</label> 
						<textarea class="form-control" name="address" class="form-control"
							placeholder=" Enter Employee Address " rows="1" required="required"></textarea><br>

						<!--  For Gender -->
						<label for="gender" class="form-label"> Gender </label><br>
						<input class="form-check-input" type="radio" name="gender"
							value="1" required="required">Male   <br>
						<input class="form-check-input" type="radio" name="gender" 
							value="2" required="required">Female<br><br>
						
						<!-- For Skills -->	
						<div class="form-group">
                        <label> Skills </label><br>
                        <%
                         for (SkillMaster skillMaster : skillMasterList) {
                         %>
                         <input type="checkbox" name="skillMasterId"
                         value="<%=skillMaster.getSkillMasterId()%>"> 
                         <label for="skills"><%=skillMaster.getName()%></label> <br>
                         <%
                         }
                         %>
                         </div>

						<!--  For Salary -->
						<label for="salary" class="form-label">Employee Salary</label> 
						<input type="number" name="salary" class="form-control" placeholder=" Employee Salary " pattern=".{4,8}" required title="4 to 8 digit number"><br>

						<!--  For DOB -->
						<label for="birthDate" class="form-label">Employee Date-of-Birth</label> 
						<input type="date" name="birthDate" class="form-control" required="required">

						<button class="mt-3 btn btn-primary" type="submit">Register</button>

					</form>
				</div>
			</div>
		</div>
	</div>
</body>
<%
} catch (Exception exception) {
exception.printStackTrace();
} finally {
dbConnection.close();
}
%>
</html>