<%@ page import="com.demo.jspdemo.entity.Customer" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Edit Customer</title>
    <!-- Bootstrap CSS -->
    <jsp:include page="./link.jsp"/>
</head>
<%
    Customer customer = (Customer)request.getAttribute("customer");
    if (customer == null) {
        response.sendRedirect("./404.html");
    }
%>
<body>
<jsp:include page="./navbar.jsp"/>
<div class="container my-3">
    <div class="row">
        <div class="col-md-6 mx-auto">
            <h1 class="text-center mb-4">Edit Customer</h1>
            <form method="POST" action="admin">
                <input type="hidden" name="action" value="update-customer">
                <input type="hidden" name="cid" value="<%=customer.getCid()%>">
                <div class="form-group">
                    <label for="cname">Name:</label>
                    <input type="text" id="cname" name="cname" class="form-control" value="<%=customer.getCname()%>"
                           readonly>
                </div>

                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" class="form-control"
                           value="<%=customer.getUsername()%>" readonly>
                </div>

                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" class="form-control"
                           value="<%=customer.getPassword()%>" readonly>
                </div>

                <div class="form-group">
                    <label for="address">Address:</label>
                    <textarea id="address" name="address" class="form-control"
                              readonly><%=customer.getAddress()%></textarea>
                </div>

                <div class="form-group">
                    <label for="phone">Phone:</label>
                    <input type="text" id="phone" name="phone" class="form-control" value="<%=customer.getPhone()%>"
                           readonly>
                </div>

                <div class="form-group">
                    <label for="status">Status:</label>
                    <select id="status" name="status" class="form-control">
                        <option value="1" <%if(customer.getStatus() == 1){%>selected<%}%>>Active</option>
                        <option value="0" <%if(customer.getStatus() == 0){%>selected<%}%>>Inactive</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Update Customer</button>
            </form>

            <a href="admin" class="mt-3 btn btn-google d-block text-center">Back to Dashboard</a>
        </div>
    </div>
</div>
</body>
</html>