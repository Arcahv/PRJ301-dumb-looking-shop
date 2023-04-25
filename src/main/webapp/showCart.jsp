<%@ page import="java.util.Vector" %>
<%@ page import="com.demo.jspdemo.entity.Category" %>
<%@ page import="com.demo.jspdemo.dao.DAOCategory" %>
<%@ page import="com.demo.jspdemo.entity.Product" %>
<%@ page import="com.demo.jspdemo.dao.DAOProduct" %>
<%@ page import="com.demo.jspdemo.entity.Cart" %>
<%@ page import="com.demo.jspdemo.dao.DAOCart" %>
<%@ page import="com.demo.jspdemo.entity.Customer" %>

<!DOCTYPE html>
<html>
<head>
    <title>Cart</title>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1, shrink-to-fit=no" name="viewport">
    <jsp:include page="./link.jsp"/>

    <style>
        /* Improve contrast */
        body {
            color: #333;
            background-color: #f5f5f5;
        }

        .table {
            background-color: #fff;
        }

        .table img {
            max-width: 50%;
        }

        label, p {
            color: white;
        }
    </style>
</head>

<%
    Vector<Cart> cartItems = (Vector<Cart>)session.getAttribute("carts");

    if (cartItems == null) {
        DAOCart daoCart = new DAOCart();
        cartItems = new DAOCart().getCartVector(((Customer)request.getSession().getAttribute("customer")).getCid());
    }

    DAOProduct daoProduct = new DAOProduct();
%>

<body>
<jsp:include page="./navbar.jsp"/>

<div class="container-fluid bg-dark py-3">
    <div class="container mt-3">
        <form action="cart" method="POST">
            <p>Name: <%=((Customer)request.getSession().getAttribute("customer")).getUsername()%>
            </p>
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th scope="col">Product Image</th>
                    <th scope="col">Product ID</th>
                    <th scope="col">Product Description</th>
                    <th scope="col">Quantity</th>
                    <th scope="col">Price</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <% for (Cart cartItem : cartItems) { %>
                <tr>
                    <td><img src="<%=daoProduct.getProduct(cartItem.getPid()).getImage()%>" alt="product image"
                             class="img-fluid"></td>
                    <td><input type="text" readonly name="pid[<%=cartItem.getPid()%>]" value="<%=cartItem.getPid()%>">
                    </td>
                    <td><%=daoProduct.getProduct(cartItem.getPid()).getDescription()%>
                    </td>
                    <td><input type="number" id="quantity" name="quantity[<%=cartItem.getPid()%>]"
                               value="<%=cartItem.getBuyQuantity()%>" placeholder="<%=cartItem.getBuyQuantity()%>"></td>
                    <td><%=daoProduct.getProduct(cartItem.getPid()).getPrice() * cartItem.getBuyQuantity()%>
                    </td>
                    <td><a href="cart?action=delete&pid=<%=cartItem.getPid()%>" class="btn btn-danger">Delete</a></td>
                </tr>
                <% } %>
                </tbody>
            </table>
            <div class="row">
                <div class="col-md-4">
                    <a href="home" class="btn btn-primary"><i class="fa fa-home"></i> Return to Home Screen</a>
                </div>
                <div class="col-md-4">
                    <button type="submit" class="btn btn-warning" name="action" value="update">Update</button>
                </div>
                <div class="col-md-4">
                    <button type="submit" class="btn btn-success" name="action" value="checkout">Checkout</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script src="./js/jquery-3.3.1.min.js"></script>
<script src="./js/popper.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script src="./js/main.js"></script>
<script>
    document.getElementById("quantity").addEventListener("change", updateCart);
    function updateCart() {
        document.querySelector('button[name="action"][value="update"]').click();
    }
</script>
</body>
</html>
