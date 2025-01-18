<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .call-to-action {
            background-color: #f1f8ff;
            padding: 15px;
            margin-bottom: 30px;
            border-radius: 8px;
            text-align: center;
            font-size: 0.8rem;
            font-weight: bold;
            color: #007bff;
        }
    </style>
</head>
<body>
<c:choose>
    <c:when test="${sessionScope.role == 'renter'}">
        <%@ include file="/WEB-INF/views/renter-header.jsp" %>
    </c:when>
    <c:otherwise>
        <%@ include file="/WEB-INF/views/rentee-header.jsp" %>
    </c:otherwise>
</c:choose>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h2 class="text-center mb-4">Welcome <c:out value="${sessionScope.role == 'renter' ? 'Renter' : 'Rentee'}" />!</h2>
                    <div class="call-to-action">
                        <p>You can edit and save your details below. Ensure your contact information is up-to-date.</p>
                    </div>

                    <c:if test="${not empty success}">
                        <div class="alert alert-success">${success}</div>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/profile/update" method="post">
                        <div class="mb-3">
                            <label for="firstName" class="form-label">First Name</label>
                            <input type="text" class="form-control" id="firstName" name="firstName" value="${user.firstName}" required>
                        </div>
                        <div class="mb-3">
                            <label for="lastName" class="form-label">Last Name</label>
                            <input type="text" class="form-control" id="lastName" name="lastName" value="${user.lastName}" required>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" name="email" value="${user.email}" readonly>
                        </div>
                        <div class="mb-3">
                            <label for="phone" class="form-label">Phone</label>
                            <input type="text" class="form-control" id="phone" name="phone" value="${user.phone}" required>
                        </div>
                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">Save Changes</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
