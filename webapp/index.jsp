<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Entities.Employee"%>
<%@page import="Dao.EmployeeDao"%>
<%@page import="java.sql.Connection"%>
<%@page import="database.DBConnection"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<%
String pageNo_pn = request.getParameter("pageNo");
int pageNo= 1;
if (pageNo_pn != null){
	try {
		pageNo = Integer.parseInt(pageNo_pn);
	} catch (Exception exception){
		pageNo = 1;
		exception.printStackTrace();
	}
}
int totalRecord = 0;
%>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
<script>
function deleteRecord(id){
    var doIt=confirm('Do you want to delete the record?');
  if(doIt){
   var f=document.form;
    f.method="post";
    f.action='../DeleteServlet?id='+id;
    f.submit();
    }
  else{

    }
}
</script>
</head>
<body>
	<%@include file="base.jsp"%>
	<%@include file="navbar.jsp"%><br>
	<br>
	<br>
	<%
	DBConnection dbConnection = null;
	Connection connection = null;
	try {
		dbConnection = new DBConnection();
		connection = dbConnection.getConnection();
		
		totalRecord = dbConnection.getSingleIntValue("select COUNT(*) FROM [jbEmployee ]");
	%>
	<!-- Table -->
	<div class="row justify-content-center">
		<div class="col-md-10">
			<h3 class="row justify-content-center">All Employees In Our Company Are Listed Here</h3>
			<table class="table table-hover">
				<thead>
					<tr>
						<th scope="col">EmployeeID</th>
						<th scope="col">Name</th>
						<th scope="col">Address</th>
						<th scope="col">Gender</th>
						<th scope="col">Salary</th>
						<th scope="col">BirthDate</th>
						<th scope="col">Edit/Delete</th>
					</tr>
				</thead>
				<tbody>
					<%
					String dt_st = "";
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

					EmployeeDao dao = new EmployeeDao();
					List<Employee> list = dao.getAllEmployee(dbConnection.getConnection(), pageNo);
					for (Employee s : list) {
						dt_st = simpleDateFormat.format(s.getBirthDate());
					%>
					<tr>
						<td><%=s.getEmployeeId()%></td>
						<td><%=s.getName()%></td>
						<td><%=s.getAddress()%></td>
						<td><%=s.getGender().equals("1") ? "Male" : "Female"%></td>
						<td><%=s.getSalary()%></td>
						<td><%=dt_st%></td>
						<td><a href="edit.jsp?employeeId=<%=s.getEmployeeId()%>">Edit</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="delete?employeeId=<%=s.getEmployeeId()%>" onclick="return deleteRecord(<%=s.getEmployeeId()%>)">Delete</a>
							<p id="Delete"></p></td>
					</tr>
					<%
					}
					} catch (Exception e) {
					e.printStackTrace();

					} finally {
					if (dbConnection != null) {
					dbConnection.close();
					}
					}
					double totalPage = Math.ceil(totalRecord / 5.0);
					
					for (int i = 1; i<= totalPage; i++){
						out.print("<a href='index.jsp?pageNo=" + i + "'>" + i + "</a>&nbsp;&nbsp;&nbsp;&nbsp;");
					}
					%>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>