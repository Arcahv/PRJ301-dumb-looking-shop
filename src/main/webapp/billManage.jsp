<%@ page import="java.util.Vector" %>
<%@ page import="com.demo.jspdemo.entity.Bill" %>
<%@ page import="com.demo.jspdemo.dao.DAOBill" %>
<%@ page import="com.demo.jspdemo.dao.DAOCustomer" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bill Manager</title>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1, shrink-to-fit=no" name="viewport">
    <jsp:include page="./link.jsp"/>
</head>

<%
    Vector<Bill> vecBill = (Vector<Bill>)request.getAttribute("bills");
    if (vecBill == null) {
        DAOBill dao = new DAOBill();
        vecBill = dao.getAllBill("select * from bill");
    }
    DAOCustomer daoCus = new DAOCustomer();
%>

<body>
<jsp:include page="./navbar.jsp"/>

<div class="container-fluid bg-dark py-3">
    <jsp:include page="./adminChoice.jsp"/>
    <div class="container">
        <hr>
        <div class="row justify-content-between">
            <form class="form-inline my-2 my-lg-0" method="GET" action="admin">
                <input type="hidden" name="action" value="search-bill">
                <label for="12"></label>
                <input class="form-control mr-sm-2" id="12" type="text" placeholder="Search" name="search">
                <button class="btn btn-outline-light my-2 my-sm-0" type="submit">Search</button>
            </form>
            <a href="admin" class="btn-primary btn btn-default">Return to index</a>
        </div>
    </div>

    <hr>
    <p class="text-center text-white">Total: <%=vecBill.size()%>
    </p>
    <div class="container mt-3">
        <table class="table table-dark table-hover">
            <thead>
            <tr>
                <th scope="col">Bill ID</th>
                <th scope="col">Customer Name</th>
                <th scope="col">Date</th>
                <th scope="col">Total</th>
                <th scope="col">Status</th>
                <th scope="col">View</th>
            </tr>
            </thead>
            <tbody>
            <%for (Bill bill : vecBill) {%>
            <tr>
                <th scope="row"><%=bill.getBid()%>
                </th>
                <td><%=daoCus.getAllCustomer("select * from customer where cid = '" + bill.getCid() + "'").get(0).getUsername()%>
                </td>
                <td><%=bill.getDateCreate()%>
                </td>
                <td><%=bill.getTotalMoney()%>
                </td>
                <td>
                    <%
                        int status = bill.getStatus();
                        String displayStatus = (status == 0) ? "Waiting" : (status == 1) ? "Processing" : (status == 2) ? "Done" : "Cancelled";
                        out.print(displayStatus);
                    %>
                </td>
                <td>
                    <a href="admin?action=view-bill-detail&bid=<%=bill.getBid()%>">
                        Detail
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