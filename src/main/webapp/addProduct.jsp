<%@ page import="com.demo.jspdemo.entity.Category" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.demo.jspdemo.dao.DAOCategory" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Add Product</title>
    <!-- Bootstrap CSS -->
    <jsp:include page="./link.jsp"/>
</head>
<%
    DAOCategory daoCat = new DAOCategory();
    Vector<Category> catVec = daoCat.getAllCategory("Select * from category");
%>
<body>
<jsp:include page="./navbar.jsp"/>
<div class="container my-3">
    <div class="row">
        <div class="col-md-6 mx-auto">
            <h1 class="text-center mb-4">Add Product</h1>
            <form method="POST" action="admin">
                <input type="hidden" name="action" value="add-product">
                <div class="form-group">
                    <label for="pname">Name:</label>
                    <input type="text" id="pname" name="pname" class="form-control">
                </div>

                <div class="form-group">
                    <label for="quantity">Quantity:</label>
                    <input type="number" id="quantity" name="quantity" class="form-control">
                </div>

                <div class="form-group">
                    <label for="price">Price:</label>
                    <input type="number" step="0.01" id="price" name="price" class="form-control">
                </div>

                <div class="form-group">
                    <label for="image">Image URL:</label>
                    <input type="text" id="image" name="image" class="form-control">
                </div>

                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea id="description" name="description" class="form-control"></textarea>
                </div>

                <div class="form-group">
                    <label for="status">Status:</label>
                    <select id="status" name="status" class="form-control">
                        <option value="1">Active</option>
                        <option value="0">Inactive</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="cateID">Category ID:</label>
                    <%--                    select box--%>
                    <select id="cateID" name="cateID" class="form-control">
                        <%for (Category cat : catVec) {%>
                        <option value="<%=cat.getCateId()%>"><%=cat.getCateName()%>
                        </option>
                        <%}%>>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Add Product</button>
            </form>

            <a href="admin" class="mt-3 d-block text-center">Back to Dashboard</a>
        </div>
    </div>
</div>
</body>
</html>
