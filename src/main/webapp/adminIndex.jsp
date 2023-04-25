<%@ page import="java.util.Vector" %>
<%@ page import="com.demo.jspdemo.entity.Category" %>
<%@ page import="com.demo.jspdemo.dao.DAOCategory" %>
<%@ page import="com.demo.jspdemo.entity.Product" %>
<%@ page import="com.demo.jspdemo.dao.DAOProduct" %>
<!DOCTYPE html>
<html>
<head>
    <title>Main Page</title>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1, shrink-to-fit=no" name="viewport">
    <jsp:include page="./link.jsp"/>
</head>

<%
    Vector<Category> vec = (Vector<Category>)request.getAttribute("vec");
    if (vec == null) {
        DAOCategory dao = new DAOCategory();
        vec = dao.getAllCategory("select * from category where status = 1");
    }
    Vector<Product> vecProduct = (Vector<Product>)request.getAttribute("products");
    if (vecProduct == null) {
        DAOProduct dao = new DAOProduct();
        vecProduct = dao.getAllProduct("select * from product");
    }
%>

<body>
<jsp:include page="./navbar.jsp"/>

<div class="container-fluid bg-dark py-3">
    <jsp:include page="./adminChoice.jsp"/>
    <hr>
    <div class="container">
        <div class="row justify-content-center">
            <p class="text-danger">Brands</p>
        </div>
        <div class="row justify-content-between align-items-center">
            <% for (Category category : vec) { %>
            <div class="col-auto">
                <a href="admin?action=category&cateId=<%= category.getCateId() %>"
                   class="text-white"><%= category.getCateName() %>
                </a>
            </div>
            <% } %>
        </div>
        <hr>
        <form class="form-inline my-2 my-lg-0" method="GET" action="admin">
            <input type="hidden" name="action" value="search">
            <label for="12"></label>
            <input class="form-control mr-sm-2" id="12" type="text" placeholder="Search" name="search">
            <button class="btn btn-outline-light my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
    <hr>


    <div class="container mt-3">
        <div class="row">
            <% for (Product product : vecProduct) { %>
            <div class="col-md-4 mb-3">
                <div class="card h-100">
                    <img src="<%=product.getImage()%>" class="card-img-top" alt="Product Image">
                    <div class="card-body">
                        <h5 class="card-title"><%= product.getPname() %>
                        </h5>
                        <p class="card-text">$<%= product.getPrice() %>
                        </p>
                        <div class="d-flex justify-content-between align-items-center">
                            <a href="admin?action=update-product&pid=<%=product.getPid()%>"
                               class="btn btn-warning">Edit</a>
                            <form action="admin" method="POST">
                                <input type="hidden" name="action" value="delete-product">
                                <input type="hidden" name="pid" value="<%= product.getPid() %>">
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
    </div>
</div>

<script src="./js/jquery-3.3.1.min.js"></script>
<script src="./js/popper.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script src="./js/main.js"></script>
</body>
</html>