<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Access Denied</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEJ6C7h7b6R8bX6X+5zIYQ5t6DNXxjq7STb5VptK9A9z5B5Ht22E39z4Xj2hD" crossorigin="anonymous">
    <style>
        body {
            background-color: #f8f9fa;
            height: 100vh; /* Full viewport height */
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .container {
            text-align: center;
        }
        .error-message {
            font-size: 24px;
            font-weight: bold;
            color: #dc3545;
        }
        .card {
            border-radius: 10px;
            padding: 20px;
        }
        .btn {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="card shadow-lg">
        <div class="card-body">
            <h1 class="error-message">Access Denied</h1>
            <p class="lead">You do not have permission to view this page.</p>
            <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">Go to Login</a>
        </div>
    </div>
</div>
</body>
</html>
