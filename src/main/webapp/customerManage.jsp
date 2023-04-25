<%@ page import="java.util.Vector" %>
<%@ page import="com.demo.jspdemo.entity.Customer" %>
<%@ page import="com.demo.jspdemo.dao.DAOCustomer" %>
<!DOCTYPE html>
<html>
<head>
    <title>Main Page</title>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1, shrink-to-fit=no" name="viewport">
    <jsp:include page="./link.jsp"/>
</head>

<%
    Vector<Customer> vecCustomer = (Vector<Customer>)request.getAttribute("customers");
    if (vecCustomer == null) {
        DAOCustomer dao = new DAOCustomer();
        vecCustomer = dao.getAllCustomer("select * from customer");
    }
%>

<body>
<jsp:include page="./navbar.jsp"/>

<div class="container-fluid bg-dark py-3">
    <jsp:include page="./adminChoice.jsp"/>
    <div class="container">
        <hr>
        <div class="row justify-content-between">
            <form class="form-inline my-2 my-lg-0" method="GET" action="admin">
                <input type="hidden" name="action" value="search-customer">
                <label for="12"></label>
                <input class="form-control mr-sm-2" id="12" type="text" placeholder="Search" name="search">
                <button class="btn btn-outline-light my-2 my-sm-0" type="submit">Search</button>
            </form>
            <a href="admin" class="btn-primary btn btn-default">Return to index</a>
        </div>
    </div>

    <hr>
    <p class="text-center text-white">Total: <%=vecCustomer.size()%>
    </p>
    <div class="container mt-3">
        <table class="table table-dark table-hover">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Name</th>
                <th scope="col">Phone</th>
                <th scope="col">Address</th>
                <th scope="col">Action</th>
            </tr>
            </thead>
            <tbody>
            <%for (Customer customer : vecCustomer) {%>
            <tr>
                <th scope="row"><%=customer.getCid()%>
                </th>
                <td><%=customer.getCname()%>
                </td>
                <td><%=customer.getPhone()%>
                </td>
                <td><%=customer.getAddress()%>
                </td>
                <td>
                    <a href="admin?action=delete-customer&cid=<%=customer.getCid()%>">
                        <button type="button" class="btn btn-danger">Delete</button>
                    </a>

                    <a href="admin?action=update-customer&cid=<%=customer.getCid()%>">
                        <button type="button" class="btn btn-warning">Update</button>
                    </a>
                </td>
            </tr>
            <%}%>
            </tbody>
        </table>
    </div>
</div>

<script src="./js/jquery-3.3.1.min.js"></script>
<script src="./js/popper.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script src="./js/main.js"></script>
</body>
</html>