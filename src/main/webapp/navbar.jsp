<%@ page import="com.demo.jspdemo.entity.Customer" %>
<%@ page import="com.demo.jspdemo.entity.Admin" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <button aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"
                class="navbar-toggler" data-mdb-target="#navbarSupportedContent" data-mdb-toggle="collapse"
                type="button">
            <i class="fas fa-bars"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">

            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="navbar-brand">
                    <a href="home" class="nav-pills" style="color: white">HOME <br></a>
                </li>
                <%if (request.getSession().getAttribute("customer") != null) {%>
                <li class="nav-item mt-2">
                    <a class="nav-link" style="color: white" href="${pageContext.request.contextPath}/bill?action=view-bill">
                        Welcome:<%=(request.getSession().getAttribute("customer") != null) ? ((Customer)request.getSession().getAttribute("customer")).getCname() : ""%>
                    </a>
                </li>
                <%} else if (request.getSession().getAttribute("admin") != null) {%>
                <li class="nav-item mt-3">
                    <p class="nav-item" style="color: white">
                        Welcome: <%=(request.getSession().getAttribute("admin") != null) ? ((Admin)request.getSession().getAttribute("admin")).getPassword() : ""%>
                    </p>
                </li>
                <%}%>


                <li class="nav-item mt-2">
                    <%if (request.getSession().getAttribute("customer") == null && request.getSession().getAttribute("admin") == null) {%>
                    <a class="nav-link" href="login?action=login">Login</a>
                    <%} else {%>
                    <a class="nav-link" href="login?action=logout">Log out</a>
                    <%}%>
                </li>
                <%if (request.getSession().getAttribute("customer") == null && request.getSession().getAttribute("admin") == null) {%>
                <li class="nav-item mt-2">
                    <a class="nav-link" href="login?action=register">Register</a>
                </li>
                <%}%>

                <%if (request.getSession().getAttribute("admin") == null && request.getSession().getAttribute("customer")== null) {%>
                <li class="nav-item mt-2">
                    <a class="nav-link" href="login?action=admin">Admin Login</a>
                </li>
                <%}%>

                <%if (request.getSession().getAttribute("admin") != null) {%>
                <li class="nav-item dropdown">

                    <ul class="dropdown-menu" aria-labelledby="navbarAdminDropdown">
                        <li><a class="dropdown-item" href="#">Add Product</a></li>
                        <li><a class="dropdown-item" href="#">View Orders</a></li>
                        <li><a class="dropdown-item" href="#">View Customers</a></li>
                    </ul>
                </li>
                <%}%>

                <%if (request.getSession().getAttribute("customer") != null) {%>
                <div class="nav-item mt-2">
                    <a class="nav-link" href="cart">Show cart</a>
                </div>
                <%}%>


            </ul>
        </div>
    </div>
</nav>
