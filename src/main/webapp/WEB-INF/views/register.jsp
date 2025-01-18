<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-image: url('${pageContext.request.contextPath}/resources/images/bg-image.jpg');
            background-size: cover;
            background-repeat: no-repeat;
            background-attachment: fixed;
        }
        .card {
            background: rgba(255, 255, 255, 0.9);
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
    </style>
    <script>
        <%--window.onload = function () {--%>
        <%--  const error = "${error}";--%>
        <%--  const errorDiv = document.getElementById("error-message");--%>

        <%--  if (error && error.trim() !== "") {--%>
        <%--    errorDiv.innerText = error; // Set the error text--%>
        <%--    errorDiv.style.display = "block"; // Show the error--%>
        <%--  } else {--%>
        <%--    errorDiv.style.display = "none"; // Hide the error--%>
        <%--  }--%>
        <%--};--%>

        function updateFormAction() {
            var role = document.getElementById("role").value;
            var form = document.getElementById("registerForm");
            form.action = "/register/" + role; // Update the action based on the selected role
        }
    </script>
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card p-4">
                <h2 class="text-center mb-4">Register</h2>
                <form:form id="registerForm" method="post" modelAttribute="user" onsubmit="updateFormAction()">
                    <div class="mb-3">
                        <label for="firstName" class="form-label">First Name</label>
                        <form:input type="text" class="form-control" id="firstName" path="firstName" />
                        <form:errors path="firstName" class="text-danger" />
                    </div>
                    <div class="mb-3">
                        <label for="lastName" class="form-label">Last Name</label>
                        <form:input type="text" class="form-control" id="lastName" path="lastName" />
                        <form:errors path="lastName" class="text-danger" />
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <form:input type="email" class="form-control" id="email" path="email" />
                        <form:errors path="email" class="text-danger" />
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <form:input type="password" class="form-control" id="password" path="password" />
                        <form:errors path="password" class="text-danger" />
                    </div>
                    <div class="mb-3">
                        <label for="phone" class="form-label">Phone Number</label>
                        <form:input type="text" class="form-control" id="phone" path="phone" />
                        <form:errors path="phone" class="text-danger" />
                    </div>
                    <div class="mb-3">
                        <label for="role" class="form-label">Register As</label>
                        <form:select class="form-select" id="role" path="role">
                            <form:option value="renter">Renter</form:option>
                            <form:option value="rentee">Rentee</form:option>
                        </form:select>
                        <form:errors path="role" class="text-danger" />
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Register</button>
                </form:form>
                <p class="text-center mt-3">
                    Already have an account? <a href="/login">Login here</a>.
                </p>
            </div>
        </div>
    </div>
</div>
</body>
</html>
