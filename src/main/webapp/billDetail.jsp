<%@ page import="java.util.Vector" %>
<%@ page import="com.demo.jspdemo.entity.BillDetail" %>
<%@ page import="com.demo.jspdemo.entity.Bill" %>
<%@ page import="com.demo.jspdemo.dao.DAOBillDetail" %>
<%@ page import="com.demo.jspdemo.dao.DAOProduct" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bill Detail</title>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1, shrink-to-fit=no" name="viewport">
    <jsp:include page="./link.jsp"/>
</head>

<%
    Vector<BillDetail> vecBillDetail = (Vector<BillDetail>)request.getAttribute("billDetail");
    if (vecBillDetail == null) {
        DAOBillDetail dao = new DAOBillDetail();
        String bid = (String)request.getAttribute("bid");
        vecBillDetail = dao.getAllBillDetail("select * from billdetail where bid = '" + bid + "'");
    }

    Bill bill = (Bill)request.getAttribute("bill");

    String[] statusOptions = {"Waiting", "Processing", "Done", "Cancelled"};
%>

<body>
<jsp:include page="./navbar.jsp"/>

<div class="container-fluid bg-dark py-3">
    <div class="container">
        <a class="btn btn-info" href="admin?action=bill-manage">Return to manage Bills</a>
        <hr>
        <div class="row justify-content-between">
            <h3 class="text-white">Bill ID: <%=bill.getBid() %>
            </h3>
            <form class="form-inline my-2 my-lg-0" method="POST" action="admin">
                <input type="hidden" name="action" value="update-bill-status">
                <input type="hidden" name="bid" value="<%=bill.getBid()%>">
                <label class="text-white" for="status">Status:</label>
                <select class="form-control mr-sm-2" id="status" name="status">
                    <% for (int i = 0; i < statusOptions.length; i++) { %>
                    <% String selected = (bill.getStatus() == i) ? "selected" : ""; %>
                    <option value="<%=i%>" <%=selected%>><%=statusOptions[i]%>
                    </option>
                    <% } %>
                </select>
                <button class="btn btn-outline-light my-2 my-sm-0" type="submit">Update Status</button>
            </form>
        </div>
    </div>

    <hr>
    <p class="text-center text-white">Total: <%=vecBillDetail.size()%>
    </p>
    <div class="container mt-3">
        <table class="table table-dark table-hover">
            <thead>
            <tr>
                <th scope="col">Product ID</th>
                <th scope="col">Product Name</th>
                <th scope="col">Price</th>
                <th scope="col">Quantity</th>
                <th scope="col">Subtotal</th>
            </tr>
            </thead>
            <tbody>
            <%for (BillDetail bd : vecBillDetail) {%>
            <tr>
                <th scope="row"><%=bd.getPid()%>
                </th>
                <td><%=new DAOProduct().getProduct(bd.getPid()).getPname()%>
                </td>
                <td><%=bd.getBuyPrice()%>
                </td>
                <td><%=bd.getBuyQuantity()%>
                </td>
                <td><%=bd.getSubtotal()%>
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